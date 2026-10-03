package com.shelfly.app.feature.material

import android.content.Context
import androidx.core.content.FileProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.junit.Test
import org.junit.Assert.assertEquals
import java.io.File

@RunWith(AndroidJUnit4::class)
class FileMetadataExtractorTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    @Test
    fun extractMetadata_realFile_returnsCorrectTitleTypeSize() {
        val file = File(context.cacheDir, "test-material.pdf")
        file.writeText("dummy content")
        val uri = FileProvider.getUriForFile(context, "com.shelfly.app.fileprovider", file)

        val metadata = extractFileMetadata(context, uri)

        assertEquals("test-material.pdf", metadata.title)
        assertEquals("application/pdf", metadata.fileType)
        assertEquals(13L, metadata.fileSize)
    }
}