package com.yourssu.notissu.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notice(
    @PrimaryKey
    var url: String,
    var title: String,
    var date: String,
    var majorNumber: Int
)