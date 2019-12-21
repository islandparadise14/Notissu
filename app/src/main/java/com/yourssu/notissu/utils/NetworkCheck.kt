package com.yourssu.notissu.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.os.Build
import androidx.annotation.RequiresApi
import com.yourssu.notissu.data.TYPE_CONNECTED
import com.yourssu.notissu.data.TYPE_NOT_CONNECTED


class NetworkCheck {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getConnectivityStatus(context: Context): Int { //해당 context의 서비스를 사용하기위해서 context객체를 받는다.
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.allNetworks
        return if(activeNetwork.isEmpty())
            TYPE_NOT_CONNECTED
        else
            TYPE_CONNECTED
    }

    @Suppress("DEPRECATION")
    fun getConnectivityStatusforLowVersion(context: Context): Int { //해당 context의 서비스를 사용하기위해서 context객체를 받는다.
        val manager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val networkInfo = manager.activeNetworkInfo
            if (networkInfo != null) {
                val type = networkInfo.type
                if (type == ConnectivityManager.TYPE_MOBILE) { //쓰리지나 LTE로 연결된것(모바일을 뜻한다.)
                    return TYPE_CONNECTED
                } else if (type == TYPE_WIFI) { //와이파이 연결된것
                    return TYPE_CONNECTED
                }
            }
            return TYPE_NOT_CONNECTED //연결이 되지않은 상태
        }
        return TYPE_NOT_CONNECTED
    }
}