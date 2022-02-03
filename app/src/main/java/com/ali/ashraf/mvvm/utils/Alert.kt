package com.example.codechallenge_plentina.util

import android.os.Looper
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import java.lang.Exception

object Alert {
    @JvmStatic
    fun show(context: Context?, title: String?, msg: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable { override fun run() {
                handler.removeCallbacks(this)
                val builder = AlertDialog.Builder(context)
                builder.setNeutralButton(
                    "OK"
                ) { dialog, which -> dialog.dismiss() }
                builder.setTitle(title)
                builder.setMessage(msg)
                try {
                    builder.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }
}