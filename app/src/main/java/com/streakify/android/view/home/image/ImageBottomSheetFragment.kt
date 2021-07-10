package com.streakify.android.view.home.image

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.streakify.android.R
import com.streakify.android.databinding.ImageBottomSheetFragmentBinding
import com.streakify.android.di.provider.ResourceProvider
import com.streakify.android.utils.Logger
import com.streakify.android.utils.Util
import dagger.android.support.AndroidSupportInjection
import java.io.File
import java.util.*
import javax.inject.Inject

const val FILE_NAME = "file_name"
const val SOURCE = "source"

class ImageBottomSheetFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val MAX_FILE_SIZE = 20
    private val CAPTURE_IMAGE = 1
    private val PICK_IMAGE = 2

    private var callback: ((Boolean) -> Unit)? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: ImageBottomSheetFragmentVM

    lateinit var imageSharedViewModel: ImageSharedViewModel

    lateinit var binding: ImageBottomSheetFragmentBinding

    var sharedFileData: SharedFileData? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.image_bottom_sheet_fragment,
                container,
                false
            )
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(ImageBottomSheetFragmentVM::class.java)
        imageSharedViewModel =
            ViewModelProvider(requireActivity()).get(ImageSharedViewModel::class.java)
        initView()
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.selectedFileData.observe(viewLifecycleOwner, {
            imageSharedViewModel.selectedFileData.value = it
            dialog?.dismiss()
        })
    }

    private fun initView() {

        if (imageSharedViewModel.selectedFileData.value?.fileUri == null) {
            binding.llremove.visibility = View.GONE
        } else {
            binding.llremove.visibility = View.VISIBLE
        }

        binding.llCamera.setOnClickListener {
            requestPermission(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) { isGranted ->
                if (isGranted) {
                    performImageCapture()
                }
            }
        }

        binding.llGallery.setOnClickListener {

            requestPermission(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) { isGranted ->
                if (isGranted) {
                    performImagePick()
                }
            }
        }

        binding.llremove.setOnClickListener {
            imageSharedViewModel.selectedFileData.value?.fileUri = null
            //Note : below line is used to observe selectedFileData of caller fragment
            imageSharedViewModel.selectedFileData.value = imageSharedViewModel.selectedFileData.value
            dialog?.dismiss()
        }

    }
    fun getFileForImageCamera():Uri{
        val path: File = File(requireActivity().getExternalCacheDir(), "camera")
        if (!path.exists()) path.mkdirs()
        val image = File(path,  UUID.randomUUID().toString() + ".jpg")
        Log.d("main",requireActivity().getPackageName().toString())
        return FileProvider.getUriForFile(
            requireActivity(),
            requireActivity().packageName.toString() + ".fileprovider",
            image
        )

    }

    private fun performImageCapture() {

        try {
            val file = getFileForImageCamera()
            file?.let {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    sharedFileData = SharedFileData().apply {
                        this.fileName = arguments?.getString(FILE_NAME) ?: ""
                        this.source = arguments?.getString(SOURCE) ?: ""
                        this.fileUri = file
                    }
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, sharedFileData?.fileUri)
                    Logger.log(
                        "CameraIssue",
                        "fileName -> ${sharedFileData?.fileName} source -> ${sharedFileData?.source}" +
                                "fileUri -> ${sharedFileData?.fileUri}"
                    )
                    //COMPATIBILITY
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                    }else{
                        var resolveInfoList:List<ResolveInfo> = activity?.packageManager?.queryIntentActivities(
                            cameraIntent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        ) as List<ResolveInfo>
                        for (resolveInfo in resolveInfoList) {
                            val packageName = resolveInfo.activityInfo.packageName
                            requireActivity().grantUriPermission(
                                packageName,
                                SharedFileData().fileUri,
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                        }

                    }
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE)
            }
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.camera_app_not_found),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun performImagePick() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {



            if (requestCode == CAPTURE_IMAGE) {
                viewModel.selectedFileData.value = sharedFileData
            } else if (requestCode == PICK_IMAGE) {
                sharedFileData = SharedFileData().apply {
                    fileName = arguments?.getString(FILE_NAME) ?: ""
                    source = arguments?.getString(SOURCE) ?: ""
                    fileUri = data?.data
                }
                val selectedImageUri: Uri? = data?.data
                selectedImageUri?.let {
                    val size = Util.getFileSizeFromUriInMb(requireContext(), it)
                    if (size == null) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.select_proper_file),
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    //Restrict User to Upload less than MAX File Size Limit
                    if (size >= MAX_FILE_SIZE) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.select_lesser_file),
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    viewModel.selectedFileData.value = sharedFileData
                }
            }

        }
    }

    private fun requestPermission(
        permissions: Array<String>,
        callback: (isGranted: Boolean) -> Unit,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var granted = true
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    granted = false
                    break
                }
            }
            if (granted) {
                callback(true)
            } else {
                this.callback = callback
                requestPermissions(permissions, 4)
            }
        } else callback(true)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 4) {
            var granted = true
            for (i in 0 until grantResults.size) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    break
                }
            }
            if (granted)
                callback?.invoke(true)
            else onDenied()
        }
    }

    private fun onDenied() {
        callback?.invoke(false)
    }

    class SharedFileData {
        var fileUri: Uri? = null
        var fileName: String? = null
        var source: String? = null

    }
}

