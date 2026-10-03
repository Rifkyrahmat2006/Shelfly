package com.shelfly.app.feature.material

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.junit.Test
import org.junit.Assert.assertFalse
import android.content.Context
import android.net.Uri

@RunWith(AndroidJUnit4::class)
class UriPermissionManagerTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    @Test
    fun persistPermission_uriWithoutPersistableFlag_returnsFalseNoCrash() {
        val fakeUri = Uri.parse("content://com.shelfly.app.fileprovider/cache/nonexistent.pdf")

        val result = persistUriPermission(context, fakeUri)

        assertFalse(result)
    }
}
