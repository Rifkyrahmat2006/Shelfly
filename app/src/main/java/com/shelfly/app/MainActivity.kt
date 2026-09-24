package com.shelfly.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shelfly.app.core.navigation.ShelflyNavHost
import com.shelfly.app.core.theme.ShelflyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShelflyTheme {
                ShelflyNavHost()
            }
        }
    }
}
