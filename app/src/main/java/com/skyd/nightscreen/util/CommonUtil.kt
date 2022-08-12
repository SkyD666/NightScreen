package com.skyd.nightscreen.util

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.skyd.nightscreen.R
import com.skyd.nightscreen.appContext
import com.skyd.nightscreen.ui.component.showToast

object CommonUtil {
    fun openBrowser(url: String) {
        try {
            val uri: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            appContext.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            appContext.getString(R.string.no_browser_found, url).showToast(Toast.LENGTH_LONG)
        }
    }
}