package com.skyd.nightscreen.ui.component.dialog

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.skyd.nightscreen.R


fun Context.getMessageDialog(
    title: CharSequence = getString(R.string.warning),
    message: CharSequence? = null,
    @DrawableRes icon: Int = 0,
    cancelable: Boolean = true,
    negativeText: String = getString(R.string.cancel),
    positiveText: String = getString(R.string.ok),
    onCancel: ((dialog: DialogInterface) -> Unit)? = null,
    onNegative: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
    onPositive: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
): AlertDialog {
    return MaterialAlertDialogBuilder(ContextThemeWrapper(this, R.style.Theme_NightScreen))
        .setTitle(title)
        .setMessage(message)
        .apply { onPositive?.let { setPositiveButton(positiveText, it) } }
        .apply { onNegative?.let { setNegativeButton(negativeText, it) } }
        .setCancelable(cancelable)
        .setIcon(icon)
        .setOnCancelListener { onCancel?.invoke(it) }
        .create()
}

fun Activity.getMessageDialog(
    title: CharSequence = getString(R.string.warning),
    message: CharSequence? = null,
    @DrawableRes icon: Int = 0,
    cancelable: Boolean = true,
    negativeText: String = getString(R.string.cancel),
    positiveText: String = getString(R.string.ok),
    onCancel: ((dialog: DialogInterface) -> Unit)? = null,
    onNegative: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
    onPositive: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
): AlertDialog? {
    return if (!isFinishing) {
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
        )
            .setTitle(title)
            .setMessage(message)
            .apply { onPositive?.let { setPositiveButton(positiveText, it) } }
            .apply { onNegative?.let { setNegativeButton(negativeText, it) } }
            .setCancelable(cancelable)
            .setIcon(icon)
            .setOnCancelListener { onCancel?.invoke(it) }
            .create()
    } else null
}

fun Activity.showSingleChoiceListDialog(
    title: CharSequence? = null,
    items: List<CharSequence>? = null,
    checkedItem: Int = -1,
    onItemClickListener: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
    icon: Drawable? = null,
    cancelable: Boolean = true,
    negativeText: String = getString(R.string.cancel),
    neutralText: String? = null,
    positiveText: String = getString(R.string.ok),
    onNegative: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
    onNeutral: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
    onPositive: ((dialog: DialogInterface, which: Int, itemIndex: Int) -> Unit)? = null,
): AlertDialog? {
    var itemIndex = checkedItem
    var positionButton: Button? = null
    return MaterialAlertDialogBuilder(
        this,
        com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
    )
        .setTitle(title)
        .setSingleChoiceItems(items?.toTypedArray(), checkedItem) { dialog, which ->
            itemIndex = which
            positionButton?.isEnabled = which != -1
            onItemClickListener?.invoke(dialog, which)
        }
        .apply {
            onPositive?.let {
                setPositiveButton(positiveText) { dialog, which ->
                    onPositive.invoke(dialog, which, itemIndex)
                }
            }
        }
        .apply { onNegative?.let { setNegativeButton(negativeText, it) } }
        .apply {
            if (onNeutral != null && neutralText != null) {
                setNeutralButton(neutralText, onNeutral)
            }
        }
        .setCancelable(cancelable)
        .setIcon(icon)
        .run {
            if (!isFinishing) {
                show().apply {
                    positionButton = getButton(AlertDialog.BUTTON_POSITIVE)
                    positionButton?.isEnabled = itemIndex != -1
                }
            } else null
        }
}

fun Context.getListDialog(
    title: CharSequence? = null,
    items: List<CharSequence>? = null,
    checkedItem: Int = -1,
    onItemClickListener: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
    icon: Drawable? = null,
    cancelable: Boolean = true,
    negativeText: String = getString(R.string.cancel),
    neutralText: String? = null,
    positiveText: String = getString(R.string.ok),
    onNegative: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
    onNeutral: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
    onPositive: ((dialog: DialogInterface, which: Int, itemIndex: Int) -> Unit)? = null,
): AlertDialog {
    var itemIndex = checkedItem
    return MaterialAlertDialogBuilder(ContextThemeWrapper(this, R.style.Theme_NightScreen))
        .setTitle(title)
        .setItems(items?.toTypedArray()) { dialog, which ->
            itemIndex = which
            onItemClickListener?.invoke(dialog, which)
        }
        .apply {
            onPositive?.let {
                setPositiveButton(positiveText) { dialog, which ->
                    onPositive.invoke(dialog, which, itemIndex)
                }
            }
        }
        .apply { onNegative?.let { setNegativeButton(negativeText, it) } }
        .apply {
            if (onNeutral != null && neutralText != null) {
                setNeutralButton(neutralText, onNeutral)
            }
        }
        .setCancelable(cancelable)
        .setIcon(icon)
        .create()
}
