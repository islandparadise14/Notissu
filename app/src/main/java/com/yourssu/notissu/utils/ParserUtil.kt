package com.yourssu.notissu.utils

fun String.isNumeric() : Boolean {
    return this.toDoubleOrNull() != null
}
