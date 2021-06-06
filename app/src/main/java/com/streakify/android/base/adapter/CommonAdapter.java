package com.streakify.android.base.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.streakify.android.BR;

import java.util.List;


public class CommonAdapter<T extends ViewModel> extends RecyclerView.Adapter<CommonAdapter.ViewHolder<T>> {

    public List<T> items;
    private final ItemClickListener<T> itemClickListener;
    private LayoutInflater mLayoutInflater;

    public CommonAdapter(List<T> items, ItemClickListener<T> itemClickListener) {
        this.items = items;
        this.itemClickListener = itemClickListener;
    }
    @Override
    public ViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new ViewHolder<>(mLayoutInflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder<T> holder, int position) {
        holder.bind(items.get(position), itemClickListener);

    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder<T> extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bind(final T value, final ItemClickListener<T> clickListener) {
          binding.setVariable(BR.data, value);
          binding.getRoot().setOnClickListener(v -> clickListener.onItemClick(value));
        }
    }
}