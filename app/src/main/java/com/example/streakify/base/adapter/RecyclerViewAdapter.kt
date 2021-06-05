package com.example.streakify.base.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.streakify.BR

abstract class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    var list = ArrayList<RecyclerViewItem>()

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), view, parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        bind(holder.viewBinding, getViewModel(position), position)
        holder.viewBinding.executePendingBindings()
    }

    fun bind(viewDataBinding: ViewDataBinding, viewModel: RecyclerViewItemViewModel, position: Int) {
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        addCustomization(viewDataBinding, position, viewModel)
    }

    class RecyclerViewHolder(val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root)

    open fun addCustomization(viewDataBinding: ViewDataBinding, position: Int, viewModel: RecyclerViewItemViewModel) {}

    abstract fun getViewModel(position: Int): RecyclerViewItemViewModel

    override fun getItemCount(): Int {
        return list.size
    }

    data class RecyclerViewItem(var viewType: String, var any: Any?)

    interface RecyclerViewItemViewModel
}