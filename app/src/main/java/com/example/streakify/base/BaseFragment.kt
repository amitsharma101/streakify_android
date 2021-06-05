package com.example.streakify.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.streakify.utils.livedata.Event
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel>() : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: V
    lateinit var binding: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, setLayout(), container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(setViewModel())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        /* Bind Views */
        bindViews()
        startObservation()
    }

    open fun startObservation() {
        viewModel.event.observe(viewLifecycleOwner, Observer {
            handleEvent(it)
        } )
    }

    /**
     * override this method to get events from vm in a synchronous fashion
     */
    open fun handleEvent(event: Event) {

    }

    /**
     * Bind Views
     * e.g. binding.viewModel(variable name of the layout data) = viewModel
     * */
    abstract fun bindViews()

    /**
     * @return Layout Resource Such as R.layout.main_layout
     * */
    abstract fun setLayout(): Int

    /**
     * @return Class which extends {@link ViewModel}
     * */
    abstract fun setViewModel(): Class<V>
}