package com.yourssu.notissu.utils

fun isNumeric(string: String) : Boolean {
    return string.toDoubleOrNull() != null
}
