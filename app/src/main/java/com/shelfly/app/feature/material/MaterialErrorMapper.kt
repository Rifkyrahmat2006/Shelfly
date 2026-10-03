package com.shelfly.app.feature.material

import java.io.FileNotFoundException
import java.io.IOException

// PIC: Person C — Error handling mapping (PRD section 29)
// Dipakai saat Open Material gagal, convert Exception teknis jadi pesan yang dipahami user.
fun mapMaterialError(throwable: Throwable): String = when (throwable) {
    is FileNotFoundException -> "File tidak ditemukan"
    is SecurityException -> "Tidak punya izin akses ke file ini"
    is IOException -> "File tidak dapat dibuka"
    else -> "Terjadi kesalahan saat membuka file"
}
