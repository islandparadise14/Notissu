package com.yourssu.notissu.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notice(
    var titleName: String,
    var dataName: String,
    var urlName: String,
    var majorNumberInfo: Int
) {
    @PrimaryKey(autoGenerate = true)
    var nid: Int = 0
    @ColumnInfo(name = "title") var title: String = titleName
    @ColumnInfo(name = "date") var date: String = dataName
    @ColumnInfo(name = "url") var url: String = urlName
    @ColumnInfo(name = "majorNumber") var majorNumber: Int = majorNumberInfo
}