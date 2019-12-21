@file:Suppress("DEPRECATION")

package com.yourssu.notissu.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

object FileUtil {
    fun downloadFile(context: Context, url: String, name: String) {

        val r = DownloadManager.Request(Uri.parse(url))
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
        r.allowScanningByMediaScanner()
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        dm!!.enqueue(r)
    }
}