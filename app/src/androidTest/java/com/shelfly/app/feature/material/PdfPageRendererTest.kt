package com.shelfly.app.feature.material

import android.content.Context
import android.graphics.pdf.PdfDocument
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.io.File

@RunWith(AndroidJUnit4::class)
class PdfPageRendererTest {

    private lateinit var context: Context
    private lateinit var testPdfFile: File

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        testPdfFile = File(context.cacheDir, "test_pdf_renderer.pdf")
        val document = PdfDocument()
        repeat(3) { pageIndex ->
            val pageInfo = PdfDocument.PageInfo.Builder(200, 300, pageIndex).create()
            val page = document.startPage(pageInfo)
            document.finishPage(page)
        }
        testPdfFile.outputStream().use { document.writeTo(it) }
        document.close()
    }

    @Test
    fun pageCount_threePageDocument_returnsThree() {
        val renderer = PdfPageRenderer.fromFile(testPdfFile)
        assertEquals(3, renderer.pageCount)
        renderer.close()
    }

    @Test
    fun renderPage_validIndex_returnsNonEmptyBitmap() {
        val renderer = PdfPageRenderer.fromFile(testPdfFile)
        val bitmap = renderer.renderPage(0)
        assertTrue(bitmap.width > 0 && bitmap.height > 0)
        renderer.close()
    }

    @Test
    fun renderPage_invalidIndex_throwsIndexOutOfBounds() {
        val renderer = PdfPageRenderer.fromFile(testPdfFile)
        try {
            renderer.renderPage(99)
            assertTrue("Harus throw IndexOutOfBoundsException", false)
        } catch (e: IndexOutOfBoundsException) {
            // expected
        } finally {
            renderer.close()
        }
    }
}