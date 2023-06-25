package com.skyd.nightscreen.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

val Context.activity: Activity
    get() {
        var ctx = this
        while (ctx is ContextWrapper) {
            if (ctx is Activity) {
                return ctx
            }
            ctx = ctx.baseContext
        }
        error("can't find activity: $this")
    }

val Context.fragmentActivity: FragmentActivity?
    get() {
        return this.activity as? FragmentActivity
    }

val Context.appCompatActivity: AppCompatActivity?
    get() {
        return this.activity as? AppCompatActivity
    }

val Context.screenIsLand: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE