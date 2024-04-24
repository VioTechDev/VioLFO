package com.vio

import android.content.Context
import android.content.Intent
import com.ltl.apero.languageopen.R
import com.vio.extention.getHandleListLanguage
import com.vio.listener.LFOCallBack
import com.vio.model.Language
import com.vio.utils.LFONativeUtils
import java.util.concurrent.CopyOnWriteArrayList

/**
 * The VioLFO singleton is designed to manage Language Feature Options (LFO) configuration and provide methods to interact with the language-related functionalities in an Android application.
 */
object VioLFO {
    private val listLFOCallback: CopyOnWriteArrayList<LFOCallBack> = CopyOnWriteArrayList()

    @field:Volatile
    internal var lfoConfig: LFOConfig = getDefaultLfoConfig()

    @field:Volatile
    internal var listLanguageValid: List<Language> = mutableListOf()

    /**
     * Registers a listener for language change events.
     * @param adCallback: An implementation of LFOCallBack interface to handle language change events.
     * */
    fun registerAdListener(adCallback: LFOCallBack) {
        listLFOCallback.add(adCallback)
    }

    /**
     * Unregisters a previously registered language change listener.
     * @param adCallback: An implementation of LFOCallBack interface to handle language change events.
     * */
    fun unregisterAdListener(adCallback: LFOCallBack) {
        listLFOCallback.remove(adCallback)
    }

    /**
     * Unregisters all language change listeners.
     * */
    fun unregisterAllAdListener() {
        listLFOCallback.clear()
    }

    /**
     * Invokes the provided action for each registered language change listener.
     * @param action: The action to be performed on each registered listener.
     * */

    private fun invokeAdListener(action: (adCallback: LFOCallBack) -> Unit) {
        listLFOCallback.forEach(action)
    }

    /**
     *@param internalAdCallback: An optional internal callback to handle language change events.
     * @return a composite LFOCallBack that can be used internally to handle language change events and also invokes registered listeners.
     *
     * */
    fun invokeListenerAdCallback(internalAdCallback: LFOCallBack? = null): LFOCallBack {
        return object : LFOCallBack {
            override fun onChangeLanguage(language: Language?) {
                internalAdCallback?.onChangeLanguage(language)
                invokeAdListener { it.onChangeLanguage(language) }
            }

            override fun onBackPressLanguage() {
                internalAdCallback?.onBackPressLanguage()
                invokeAdListener { it.onBackPressLanguage() }
            }

        }
    }

    /**
     * Initializes the AperoLFO module with the provided LFOConfig instance and sets up valid language options.
     * @param context : The application context.
     * @param lfoConfig : The configuration for Language Feature Options.
     * */
    fun initLFO(context: Context, lfoConfig: LFOConfig) {
        VioLFO.lfoConfig = lfoConfig
        listLanguageValid =
            (lfoConfig.listLanguage as MutableList<Language>).getHandleListLanguage(
                lfoConfig.languageCodeDefault,
                lfoConfig.positionLanguageDevice
            )
        if (lfoConfig.requestNativePriorityLFO1) {
            LFONativeUtils.requestLFO1Alternate(
                context,
                lfoConfig.idNativePriorityLFO1,
                lfoConfig.idNativeLFO1,
                lfoConfig.layoutNativeAd
            ) {
                lfoConfig.isShowNativeLFO1
            }
        } else {
            LFONativeUtils.requestNativeLFO1(
                context,
                lfoConfig.idNativeLFO1
            ) {
                lfoConfig.isShowNativeLFO1
            }
        }
    }

    /**
     * Requests the Language Feature Options interface by launching the LFOActivity.
     * @param context : The application context.
     * */

    fun requestLFO(context: Context) {
        context.startActivity(Intent(context, LFOActivity::class.java))
    }

    private fun getDefaultLfoConfig(): LFOConfig {
        val languages: MutableList<Language> = arrayListOf()
        languages.add(
            Language(
                "en",
                "English",
                "English",
                R.drawable.ic_language_en,
                false
            )
        )
        languages.add(
            Language(
                "es",
                "Español",
                "Español",
                R.drawable.ic_language_es,
                false
            )
        )
        languages.add(
            Language(
                "pt",
                "Português",
                "Português",
                R.drawable.ic_language_pt,
                false
            )
        )
        languages.add(
            Language(
                "hi",
                "हिन्दी",
                "हिन्दी",
                R.drawable.ic_language_hi,
                false
            )
        )
        languages.add(
            Language(
                "id",
                "Bahasa Indonesia",
                "Bahasa Indonesia",
                R.drawable.ic_language_indo,
                false
            )
        )

        languages.add(
            Language(
                "vi",
                "Viet nam",
                "Viet nam",
                R.drawable.ic_language_ru,
                false
            )
        )
        return LFOConfig.Builder(
            idNativeLFO1 = "",
            isShowNativeLFO1 = false,
            listLanguage = languages
        )
            .layoutToolbar(R.layout.layout_header_1)
            .layoutNativeAd(R.layout.native_language_big)
            .shimmerNativeAd(R.layout.shimmer_native_language_big)
            .layoutItemLanguage(R.layout.view_item_language_lfo_border)
            .showLFO2(false)
            .showMeta(false)
            .showMetaAllPlatform(false)
            .layoutNativeAdMeta(R.layout.native_language_meta)
            .shimmerNativeAdMeta(R.layout.shimmer_native_language_meta)
            .idNativeLFO2("")
            .languageCodeDefault("en")
            .positionLanguageDevice(3)
            .itemLimit(6)
            .colorStatusBar(R.color.themeColor)
            .setLightStatusBar(true)
            .backgroundColorLfo(R.color.themeColor)
            .build()
    }
}
