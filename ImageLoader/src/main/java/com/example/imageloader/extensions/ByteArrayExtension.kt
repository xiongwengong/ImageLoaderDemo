package com.example.imageloader.extensions

fun ByteArray.bytesToHexString(): String {
    val sb = StringBuilder()
    for (i in indices) {
        val hex = Integer.toHexString(0xFF and this[i].toInt())
        if (hex.length == 1) {
            sb.append('0')
        }
        sb.append(hex)
    }
    return sb.toString()
}