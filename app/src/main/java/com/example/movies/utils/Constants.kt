package com.example.movies.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Constants {
    const val BASE_URL = "http://omdbapi.com/"

    fun showAlert(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                //(context as Activity).finish()
            })
            show()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}