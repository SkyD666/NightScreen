package com.skyd.nightscreen.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.skyd.nightscreen.ext.theme.transparentSystemBar

abstract class BaseComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 透明状态栏
        window.transparentSystemBar(window.decorView.findViewById<ViewGroup>(android.R.id.content))
    }
}