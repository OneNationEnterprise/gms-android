package com.gymapp.helper

import android.content.Context
import okio.BufferedSource
import okio.buffer
import okio.source
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object Temporary {

    fun readFileFromAssets(context: Context, fileName: String): String? {
        try {
            val input: InputStream = context.assets.open(fileName)
            val source: BufferedSource = input.source().buffer()
            return source.readByteString().string(Charset.forName("utf-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}