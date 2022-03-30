package com.example.imageloaderdemo.ui.moment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.imageloaderdemo.ui.moment.MomentComponent

class ComposeMomentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MomentComponent()
        }
    }
}