package com.yourssu.notissu.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.yourssu.notissu.data.MajorData
import java.util.Arrays.asList


object AlertDialogUtil {
    fun createDialogWithTitle(title: String, context: Context, listener: DialogInterface.OnClickListener) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setPositiveButton("확인", listener)
        builder.create().show()
    }

    fun createDialogWithCancelButton(title: String, message:String, context: Context, cancelText: String, confirmText: String, listener: DialogInterface.OnClickListener) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(true)
        builder.setNegativeButton(cancelText, DialogInterface.OnClickListener { dialogInterface, _ ->
            dialogInterface.cancel()
        })
        builder.setPositiveButton(confirmText, listener)
        builder.create().show()
    }

    fun createMajorListDialog(items: List<String>, context: Context, currentMajor: Int, itemClick: (Int) -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("메인 전공 변경")
        val i = {index: Int -> MajorData.getInstance().getMajorByIndex(index = index).name}
        val itemList = Array(items.size, i)

        var selected = currentMajor
        builder.setSingleChoiceItems(itemList, currentMajor
        ) { _, position ->
            selected = position
        }
        builder.setNegativeButton(
            "취소"
        ) { dialog, _ ->
            dialog.cancel()
        }
        builder.setPositiveButton(
            "확인"
        ) { dialog, _ ->
            itemClick(selected)
            dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}