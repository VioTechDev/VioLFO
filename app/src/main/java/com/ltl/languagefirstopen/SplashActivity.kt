package com.ltl.languagefirstopen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vio.VioLFO
import com.vio.LFOConfig
import com.vio.listener.LFOCallBack
import com.vio.model.Language
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity(), LFOCallBack {
    companion object {
        private val TAG = SplashActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initLFO()
        lifecycleScope.launch {
            delay(5000)
            VioLFO.requestLFO(this@SplashActivity)
        }
    }

    private fun initLFO() {
        val languages: MutableList<Language> = arrayListOf()
        languages.add(
            Language(
                "en",
                getString(com.vio.languageopen.R.string.language_english),
                getString(com.vio.languageopen.R.string.language_english),
                com.vio.languageopen.R.drawable.ic_language_en,
                false
            )
        )
        languages.add(
            Language(
                "es",
                getString(com.vio.languageopen.R.string.language_spanish),
                getString(com.vio.languageopen.R.string.language_spanish),
                com.vio.languageopen.R.drawable.ic_language_es,
                false
            )
        )
        languages.add(
            Language(
                "pt",
                getString(com.vio.languageopen.R.string.language_portuguese),
                getString(com.vio.languageopen.R.string.language_portuguese),
                com.vio.languageopen.R.drawable.ic_language_pt,
                false
            )
        )
        languages.add(
            Language(
                "hi",
                getString(com.vio.languageopen.R.string.language_hindi),
                getString(com.vio.languageopen.R.string.language_hindi),
                com.vio.languageopen.R.drawable.ic_language_hi,
                false
            )
        )
        languages.add(
            Language(
                "id",
                getString(com.vio.languageopen.R.string.language_indo),
                getString(com.vio.languageopen.R.string.language_indo),
                com.vio.languageopen.R.drawable.ic_language_indo,
                false
            )
        )

        languages.add(
            Language(
                "vi",
                "Viet nam",
                "Viet nam",
                com.vio.languageopen.R.drawable.ic_language_ru,
                false
            )
        )
        val lfoConfig = LFOConfig.Builder(
            idNativeLFO1 = "ca-app-pub-3940256099942544/2247696110",
            isShowNativeLFO1 = true,
            listLanguage = languages
        )
            .layoutToolbar(com.vio.languageopen.R.layout.layout_header_1)
            .layoutNativeAd(R.layout.native_language_test)
            .shimmerNativeAd(R.layout.shimmer_native_language_test)
            .layoutItemLanguage(com.vio.languageopen.R.layout.view_item_language_lfo_border)
            .showLFO2(true)
            .showMeta(true)
            .showMetaAllPlatform(true)
            .layoutNativeAdMeta(R.layout.layout_native_meta_full)
            .shimmerNativeAdMeta(R.layout.shimmer_native_meta)
            .idNativeLFO2("ca-app-pub-3940256099942544/2247696110321")
            .positionLanguageDevice(3)
            .languageCodeDefault("en")
            .itemLimit(6)
            .colorStatusBar(R.color.purple_500)
            .setLightStatusBar(true)
            .backgroundColorLfo(R.color.black)
            .requestNativePriorityLFO1(true, "")
            .requestNativePriorityLFO2(true, "")
            .finishActivityWhenBackAction(false)
            .build()
        VioLFO.initLFO(this, lfoConfig)
        VioLFO.registerAdListener(this)
    }

    override fun onChangeLanguage(language: Language?) {
        Log.e(TAG, "onChangeLanguage: $language")
        this.finish()
    }

    override fun onBackPressLanguage() {
        Log.e(TAG, "onBack: ")
        this.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        VioLFO.unregisterAdListener(this)
    }
}
