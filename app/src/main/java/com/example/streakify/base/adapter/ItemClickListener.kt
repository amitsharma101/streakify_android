package com.example.streakify.base.adapter;

interface ItemClickListener<in T> {

    fun onItemClick(value: T)
}