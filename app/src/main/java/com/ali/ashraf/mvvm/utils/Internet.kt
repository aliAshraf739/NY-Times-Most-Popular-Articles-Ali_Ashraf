package com.builbee.app.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AlertDialog

object Internet {
    fun isAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For 29 api or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
                else ->     false
            }
        }
        // For below 29 api
        else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }
    fun displayNetworkError(context: Context, result: NetworkError) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle("ERROR !!")
        builder.setMessage("Sorry there was an error getting data from the Internet.\nNetwork Unavailable!")

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.setPositiveButton("Retry") { dialog, _ ->
            dialog.dismiss()
            result.Retry(true)
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()
    }
    interface NetworkError {
        fun Retry(result: Boolean)
    }
}