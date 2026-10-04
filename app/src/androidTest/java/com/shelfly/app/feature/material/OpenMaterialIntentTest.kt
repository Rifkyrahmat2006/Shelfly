package com.shelfly.app.feature.material

import android.content.Intent
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpenMaterialIntentTest {

    @Test
    fun buildOpenIntent_setsActionViewAndMimeType() {
        val uri = Uri.parse("content://com.shelfly.app.fileprovider/cache/modul.pdf")

        val intent = buildOpenMaterialIntent(uri, "application/pdf")

        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals("application/pdf", intent.type)
        assertEquals(uri, intent.data)
        assertTrue(
            intent.flags and Intent.FLAG_GRANT_READ_URI_PERMISSION != 0
        )
    }
}
