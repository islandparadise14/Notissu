package com.yourssu.notissu.data

import java.util.regex.Pattern

const val TYPE_CONNECTED = 1
const val TYPE_NOT_CONNECTED = 0

const val MAJOR_KEY = "major"
const val MAJOR_INTENT_KEY = "majorIntent"
const val KEYWORD_INTENT_KEY = "keywordIntent"
const val URL_INTENT_KEY = "urlIntent"

val MEDIA_NOTICE_ID_PATTERN = Pattern.compile("\"['](.*?)[']\"")
val INFOCOM_URL_PATTERN = Pattern.compile("(?=')\\\\S+(?=')")