package com.ali.ashraf.mvvm.utils

import android.app.Activity
import androidx.navigation.Navigation
import com.ali.ashraf.mvvm.R

object Util {
    fun performMainNavigation(activity: Activity?, fragmentId: Int) {
        Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(fragmentId)
    }
}