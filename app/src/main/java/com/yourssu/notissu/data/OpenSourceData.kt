package com.yourssu.notissu.data

import com.yourssu.notissu.model.OpenSource

class OpenSourceData {
    companion object {
        val OpenSources = ArrayList<OpenSource>(
            listOf(
                OpenSource("Jsoup", "https://github.com/jhy/jsoup", "jsoup is a Java library for working with real-world HTML. It provides a very convenient API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods."),
                OpenSource("coroutine", "https://github.com/Kotlin/kotlinx.coroutines", "This gives you access to Android Dispatchers.Main coroutine dispatcher and also makes sure that in case of crashed coroutine with unhandled exception this exception is logged before crashing Android application, similarly to the way uncaught exceptions in threads are handled by Android runtime."),
                OpenSource("lottie", "https://github.com/airbnb/lottie-android", "Lottie is a mobile library for Android and iOS that parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile!")
            )
        )

        @JvmStatic
        fun getInstance() = OpenSourceData()
    }

    fun getOpenSources(): ArrayList<OpenSource> {
        return OpenSources
    }
}