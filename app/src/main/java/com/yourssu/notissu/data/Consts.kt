package com.yourssu.notissu.data

import java.util.regex.Pattern

const val MAJOR_KEY = "major"

val MEDIA_NOTICE_ID_PATTERN = Pattern.compile("\"['](.*?)[']\"")
val INFOCOM_URL_PATTERN = Pattern.compile("(?=')\\\\S+(?=')")