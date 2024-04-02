package com.ltl.apero.languageopen.language.extention

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import androidx.core.view.WindowInsetsControllerCompat
import com.ltl.apero.languageopen.language.model.Language


private fun getLanguageDevice(languageList: MutableList<Language>): Language? {
    val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Resources.getSystem().configuration.locales.get(0)
    } else {
        Resources.getSystem().configuration.locale
    }
    return languageList.find { it.code == locale.language }
}

fun MutableList<Language>.getHandleListLanguage(
    languageCodeDefault: String?,
    positionLanguageDevice: Int
): List<Language> {
    languageCodeDefault?.let { code ->
        this.find { it.code == code }?.isChoose = true
    } ?: run {
        this.firstOrNull()?.isChoose = true
        getLanguageDevice(this)?.let {
            val indexLanguage = this.indexOfLast { language ->
                language.code == it.code
            }
            if (indexLanguage > positionLanguageDevice) {
                this.remove(it)
                this.add(positionLanguageDevice, it)
            }
            it.isSystem = true
        }
    }
    return this
}

fun Activity.lightStatusBar(isLightStatusBar:Boolean) {
    val controller = WindowInsetsControllerCompat(window, window.decorView)
    controller.isAppearanceLightStatusBars = isLightStatusBar
}
