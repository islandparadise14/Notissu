package com.yourssu.notissu.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface

object AlertDialogUtil {
    fun createDialogWithTitle(title: String, context: Context, listener: DialogInterface.OnClickListener) {
        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setPositiveButton("확인", listener)
        builder.create().show()
    }

    fun createDialogWithCancelButton(title: String, message:String, context: Context, cancelText: String, confirmText: String, listener: DialogInterface.OnClickListener) {
        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(true)
        builder.setNegativeButton(cancelText, DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
        builder.setPositiveButton(confirmText, listener)
        builder.create().show()
    }
}