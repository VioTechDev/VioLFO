package com.ltl.apero.languageopen.language

import androidx.annotation.LayoutRes
import com.ltl.apero.languageopen.R
import com.ltl.apero.languageopen.language.model.Language

class LFOConfig private constructor(
    val idNativeLFO1: String,
    val idNativePriorityLFO1: String,
    val idNativeLFO2: String,
    val idNativePriorityLFO2: String,
    val idNativeLFO3: String,
    val isShowNativeLFO1: Boolean = true,
    val isShowMeta: Boolean = false,
    val isShowMetaAllPlatform: Boolean = false,
    @LayoutRes val layoutToolbar: Int,
    @LayoutRes val layoutItemLanguage: Int,
    @LayoutRes val layoutNativeAd: Int,
    @LayoutRes val layoutNativeAdMeta: Int,
    @LayoutRes val shimmerNativeAd: Int,
    @LayoutRes val shimmerNativeAdMeta: Int,
    val colorStatusBar: Int = R.color.themeColor,
    val isLightStatusBar: Boolean = true,
    val backgroundColorLfo: Int? = null,
    val showLFO2: Boolean = false,
    val showLFO3: Boolean = false,
    val listLanguage: List<Language>,
    val itemLimit: Int = 6,
    val languageCodeDefault: String? = null,
    var positionLanguageDevice: Int = 3,
    val lfo1NativeAdClickEventName: String = "",
    val lfo2NativeAdClickEventName: String = "",
    val requestNativePriorityLFO1: Boolean = false,
    val requestNativePriorityLFO2: Boolean = false,
) {
    class Builder(
        private var idNativeLFO1: String,
        private var idNativePriorityLFO1: String = "",
        private var idNativeLFO2: String = "",
        private var idNativePriorityLFO2: String = "",
        private var idNativeLFO3: String = "",
        private var isShowNativeLFO1: Boolean,
        private var isShowMeta: Boolean = false,
        private var isShowMetaAllPlatform: Boolean = false,
        @LayoutRes private var layoutToolbar: Int = R.layout.layout_header_1,
        @LayoutRes private var layoutItemLanguage: Int = R.layout.view_item_language_lfo_border,
        @LayoutRes private var layoutNativeAd: Int = R.layout.native_language_big,
        @LayoutRes private var layoutNativeAdMeta: Int = R.layout.native_language_meta,
        @LayoutRes private var shimmerNativeAd: Int = R.layout.shimmer_native_language_big,
        @LayoutRes private var shimmerNativeAdMeta: Int = R.layout.shimmer_native_language_meta,
        private var colorStatusBar: Int = R.color.themeColor,
        private var isLightStatusBar: Boolean = true,
        private var backgroundColorLfo: Int? = null,
        private var showLFO2: Boolean = false,
        private var showLFO3: Boolean = false,
        private var listLanguage: List<Language>,
        private var itemLimit: Int = 6,
        private var languageCodeDefault: String? = null,
        private var positionLanguageDevice: Int = 3,
        private var lfo1NativeAdClickEventName: String = "",
        private var lfo2NativeAdClickEventName: String = "",
        private var requestNativePriorityLFO1: Boolean = false,
        private var requestNativePriorityLFO2: Boolean = false,
    ) {
        /**
         * Used to change the toolbar interface
         *
         * It serves the purpose of customizing the toolbar appearance as desired
         *
         * <b>note</b>: the interface must comply with the view element and the id provided
         *
         * @param layoutId layout id desired to change
         * note: the interface must comply with the view element and the id provided
         * */
        fun layoutToolbar(layoutId: Int) = apply { this.layoutToolbar = layoutId }

        /**
         * Used to change the native ad interface
         *
         * It serves the purpose of customizing the admob's native ad interface as desired
         *
         * @param layoutId layout id desired to change
         * note: the interface must comply with the view element and the id provided
         * */
        fun layoutNativeAd(layoutId: Int) = apply { this.layoutNativeAd = layoutId }

        /**
         * Used to change the native ad interface
         *
         * It serves the purpose of customizing the admob's native ad interface with Meta mediation as desired
         *
         * @param layoutId layout id desired to change
         * note: the interface must comply with the view element and the id provided
         * */
        fun layoutNativeAdMeta(layoutId: Int) = apply { this.layoutNativeAdMeta = layoutId }

        /**
         * Used to change the native ad loading interface
         *
         * It serves the purpose of customizing the admob's native ad loading interface as desired
         *
         * @param layoutId layout id desired to change
         * note: the interface must comply with the view element and the id provided
         * */
        fun shimmerNativeAd(layoutId: Int) = apply { this.shimmerNativeAd = layoutId }

        /**
         * Used to change the native ad loading interface
         *
         * It serves the purpose of customizing the admob's native ad loading interface with Meta mediation as desired
         *
         * @param layoutId layout id desired to change
         * note: the interface must comply with the view element and the id provided
         * */
        fun shimmerNativeAdMeta(layoutId: Int) = apply { this.shimmerNativeAdMeta = layoutId }

        /**
         * Used to change the item's language appearance
         *
         * It serves the purpose of customizing the item's language as desired
         *
         * @param layoutId layout id desired to change
         *
         * note: the interface must comply with the view element and the id provided
         * */
        fun layoutItemLanguage(layoutId: Int) = apply { this.layoutItemLanguage = layoutId }


        /**
         * Used to configure LFO 2 root id
         *<p>
         * @param id Native id desired to change
         *</p>
         * ad format : Native Ad
         *
         * default id : null
         *
         * Configure id by variant
         * */
        fun idNativeLFO2(id: String) = apply { this.idNativeLFO2 = id }

        /**
         * Used to configure LFO 3 root id
         *<p>
         * @param id Native id desired to change
         *</p>
         * ad format : Native Ad
         *
         * default id : null
         *
         * Configure id by variant
         * */
        @Deprecated("not configured yet")
        fun idNativeLFO3(id: String) = apply { this.idNativeLFO3 = id }

        /**
         * Used to configure color status bar
         *
         * default value: R.color.themeColor
         *
         *<p>
         * @param color  id desired to change
         *</p>
         * */
        fun colorStatusBar(color: Int) = apply { this.colorStatusBar = color }

        /**
         * Flag indicating whether the status bar has a light theme or not
         *
         * default value: true
         *
         *<p>
         * @param isLightStatusBar  value (true/false) desired to change
         *</p>
         * */
        fun setLightStatusBar(isLightStatusBar: Boolean) =
            apply { this.isLightStatusBar = isLightStatusBar }

        /**
         * Background color resource for the LFO component
         *
         * default value: null
         *
         *<p>
         * @param color  id desired to change
         *</p>
         * */
        fun backgroundColorLfo(color: Int) = apply { this.backgroundColorLfo = color }

        /**
         * Maximum number of items to be displayed
         *
         * default value: 6
         *
         *<p>
         * @param count  value desired to change
         *</p>
         * */
        fun itemLimit(count: Int) = apply { this.itemLimit = count }

        /**
         * Flag indicating whether the second LFO2 should be displayed
         *
         * default value: false
         *
         *<p>
         * @param isShow  value(true/false) desired to change
         *</p>
         * */
        fun showLFO2(isShow: Boolean) = apply { this.showLFO2 = isShow }

        /**
         * Flag indicating whether the second LFO3 should be displayed
         *
         * default value: false
         *
         *<p>
         * @param isShow  value(true/false) desired to change
         *</p>
         * */
        @Deprecated("not configured yet")
        fun showLFO3(isShow: Boolean) = apply { this.showLFO3 = isShow }

        /**
         * Flag indicating whether meta information should be displayed.
         *
         * default value: false
         *
         *<p>
         * @param isShow  value(true/false) desired to change
         *</p>
         * */
        fun showMeta(isShow: Boolean) = apply { this.isShowMeta = isShow }

        /**
         * Flag indicating whether meta information should be displayed on all platforms.
         *
         * default value: false
         *
         *<p>
         * @param isShow  value(true/false) desired to change
         *</p>
         * */
        fun showMetaAllPlatform(isShow: Boolean) = apply { this.isShowMetaAllPlatform = isShow }

        /**
         * Default language code
         *
         * default value: en
         *
         *<p>
         * @param languageCode  value desired to change
         *</p>
         * */
        fun languageCodeDefault(languageCode: String) =
            apply { this.languageCodeDefault = languageCode }

        /**
         * Position of the language on the device
         *
         * default value: 3
         *
         *<p>
         * @param position  value desired to change
         *</p>
         * */
        fun positionLanguageDevice(position: Int) =
            apply { this.positionLanguageDevice = position - 1 }

        /**
         * Used to configure LFO 1 Priority id
         *<p>
         * @param id Native id desired to change
         *</p>
         * ad format : Native Ad
         *
         * default id : null
         *
         * Configure id by variant
         * */
        fun idNativePriorityLFO1(id: String) = apply { this.idNativePriorityLFO1 = id }

        /**
         * Used to configure LFO 2 Priority id
         *<p>
         * @param id Native id desired to change
         *</p>
         * ad format : Native Ad
         *
         * default id : null
         *
         * Configure id by variant
         * */
        fun idNativePriorityLFO2(id: String) = apply { this.idNativePriorityLFO2 = id }

        fun requestNativePriorityLFO1(requestPriority: Boolean, idNativePriority: String) = apply {
            idNativePriorityLFO1(idNativePriority)
            this.requestNativePriorityLFO1 = requestPriority
        }

        fun requestNativePriorityLFO2(requestPriority: Boolean, idNativePriority: String) = apply {
            idNativePriorityLFO2(idNativePriority)
            this.requestNativePriorityLFO2 = requestPriority
        }

        fun build() = LFOConfig(
            idNativeLFO1,
            idNativePriorityLFO1,
            idNativeLFO2,
            idNativePriorityLFO2,
            idNativeLFO3,
            isShowNativeLFO1,
            isShowMeta,
            isShowMetaAllPlatform,
            layoutToolbar,
            layoutItemLanguage,
            layoutNativeAd,
            layoutNativeAdMeta,
            shimmerNativeAd,
            shimmerNativeAdMeta,
            colorStatusBar,
            isLightStatusBar,
            backgroundColorLfo,
            showLFO2,
            showLFO3,
            listLanguage,
            itemLimit,
            languageCodeDefault,
            positionLanguageDevice,
            lfo1NativeAdClickEventName,
            lfo2NativeAdClickEventName,
            requestNativePriorityLFO1,
            requestNativePriorityLFO2
        )
    }
}
