package com.streakify.android.view.home.contactlist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import javax.inject.Inject

class Permissions @Inject constructor(private val context: Context) {

    fun isContactPermissionGiven(): Boolean {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            return context.checkSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
//                    context.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
            return context.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }
}