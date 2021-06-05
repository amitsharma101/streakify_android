package com.streakify.android.base.adapter;

interface ItemClickListener<in T> {

    fun onItemClick(value: T)
}