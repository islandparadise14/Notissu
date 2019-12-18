package com.yourssu.notissu.data

import java.util.regex.Pattern

const val MAJOR_KEY = "major"
const val MAJOR_INTENT_KEY = "majorIntent"
const val URL_INTENT_KEY = "urlIntent"

val MEDIA_NOTICE_ID_PATTERN = Pattern.compile("\"['](.*?)[']\"")
val INFOCOM_URL_PATTERN = Pattern.compile("(?=')\\\\S+(?=')")