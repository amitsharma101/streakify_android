package com.streakify.android.view.home.launcher

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.navigation.fragment.findNavController
import com.streakify.android.BuildConfig
import com.streakify.android.R
import com.streakify.android.application.AppConstants
import com.streakify.android.base.BaseFragment
import com.streakify.android.databinding.FragmentSplashBinding
import com.streakify.android.utils.livedata.Event
import com.streakify.android.view.activity.MainActivity


class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM>() {

    override fun setLayout() = R.layout.fragment_splash

    override fun setViewModel() = SplashVM::class.java


    /** Bind View with ViewModel */
    override fun bindViews() {
        binding.viewModel = viewModel

        /* Hide ActionBar */
        (activity as MainActivity).hideActionBar()

        checkForUpdates()
//        viewModel.checkUserLoggedIn()

        /* Set Observers to capture actions */
        bindObservers()
    }

    private fun checkForUpdates() {

        val version = BuildConfig.VERSION_CODE
        viewModel.checkForUpdate(version)
    }

    override fun handleEvent(event: Event) {
        when(event){
            is SplashEvent.UpdateInfoFetchedEvent -> {
                val updateInfo = event.updateCheckerResponse.body
                if(updateInfo?.updateAvailable == true){
                    if(updateInfo.updateType == AppConstants.UPDATE_TYPE_FORCE){
                        doForceUpdate(updateInfo.actionUrl!!)
                    }
                }
                else{
                    viewModel.checkUserLoggedIn()
                }
            }
        }
    }

    private fun doForceUpdate(updateUrl: String) {
        val dialog: AlertDialog = AlertDialog.Builder(requireContext())
            .setTitle("New version available")
            .setMessage("Please, update app to new version to continue reposting.")
            .setPositiveButton("Update",
                DialogInterface.OnClickListener { dialog, which -> redirectStore(updateUrl) })
            .setNegativeButton("No, thanks",
                DialogInterface.OnClickListener { dialog, which ->  closeApp()}).create()
        dialog.show()
    }

    private fun redirectStore(updateUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun closeApp(){
        activity?.moveTaskToBack(true);
        activity?.finish();
    }

    private fun bindObservers() {
        viewModel.mUserAccountLD.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled()?.let {
                if (it) {
                    findNavController().navigate(R.id.action_splashFragment_to_streakListFragment)
                } else
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        })
    }
}