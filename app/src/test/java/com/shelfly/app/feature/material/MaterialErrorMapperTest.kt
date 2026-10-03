package com.shelfly.app.feature.material

import org.junit.Test
import org.junit.Assert.assertEquals
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.SecurityException

class MaterialErrorMapperTest {

    @Test
    fun fileNotFoundException_mapsToFileNotFoundMessage() {
        val msg = mapMaterialError(FileNotFoundException())
        assertEquals("File tidak ditemukan", msg)
    }

    @Test
    fun securityException_mapsToPermissionMessage() {
        val msg = mapMaterialError(SecurityException())
        assertEquals("Tidak punya izin akses ke file ini", msg)
    }

    @Test
    fun ioException_mapsToCannotOpenMessage() {
        val msg = mapMaterialError(IOException())
        assertEquals("File tidak dapat dibuka", msg)
    }

    @Test
    fun unknownException_mapsToGenericMessage() {
        val msg = mapMaterialError(RuntimeException("random error"))
        assertEquals("Terjadi kesalahan saat membuka file", msg)
    }
}
