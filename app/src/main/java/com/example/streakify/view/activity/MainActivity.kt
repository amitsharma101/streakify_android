package com.example.streakify.view.activity


import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.streakify.R
import com.example.streakify.application.App
import com.example.streakify.databinding.ActivityMainBinding
import com.example.streakify.view.binding.snackBarMessage
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var app: App

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainActivityViewModel
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    private var exitApp = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel

        setSupportActionBar(toolbar)
        setupNavigation()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment ->{
                    toolbar?.title = getString(R.string.add_goal)
                }
                else -> {
//                    toolbar?.title = destination.label
//                    toolbar?.subtitle = ""
                }
            }
        }
        bindViews()
    }

    fun bindViews() {

        /* Click Listeners */
        setClickListeners()

        /* bind observer */
        bindObservers()
    }

    fun bindObservers() {

        /* Observe for SnackBar Message */
        viewModel.eventListener.snackbarMessage.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
                binding.container.snackBarMessage(it)
            }
        })

        /* Observe for Loading View */
        viewModel.eventListener.loadingDialogConfig.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
//                binding.loadingShowingView.bindTextAndActions(it)
//                binding.loadingShowingView.visibleOrGone(!it.message.isNullOrEmpty())
            }
        })

        /* Observe for Message Dialog Visibility */
        viewModel.eventListener.isDialogVisible.observe(this, { event ->
            event?.getContentIfNotHandled()?.let {
//                binding.dialogShowingView.show(it)
            }
        })

        /* Observe for Message Dialog Handling */
        viewModel.eventListener.errorDialogViewModel.observe(this, { event ->
//            event?.getContentIfNotHandled()?.let { it ->
//                binding.dialogShowingView.bindTextAndActions(
//                    viewModel.eventListener.errorDialogConfig.value?.peekContent(),
//                    it
//                )
//            }
        })


    }

    fun setClickListeners() {

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    private fun setupNavigation() {

        navController = Navigation.findNavController(this, R.id.mainNavigationFragment)

        appBarConfiguration =
            AppBarConfiguration.Builder(
                *intArrayOf(
                    R.id.loginFragment
                )
            ).build()

        setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    /** Show ActionBar with Status Bar Color Handling */
    fun showActionBar() {
        supportActionBar?.show()
    }

    fun setActionBarTitle(title:String) {
        supportActionBar?.title = title
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
}