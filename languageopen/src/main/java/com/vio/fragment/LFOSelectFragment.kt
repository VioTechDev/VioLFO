package com.vio.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.ads.admob.data.ContentAd
import com.ads.admob.helper.adnative.NativeAdConfig
import com.ads.admob.helper.adnative.NativeAdHelper
import com.ads.admob.helper.adnative.params.AdNativeMediation
import com.ads.admob.helper.adnative.params.NativeAdParam
import com.ads.admob.helper.adnative.params.NativeLayoutMediation
import com.ads.admob.listener.NativeAdCallback
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.LoadAdError
import com.vio.VioLFO
import com.vio.BaseLFOFragment
import com.vio.languageopen.R
import com.vio.listener.LFOSelectLanguage
import com.vio.model.Language
import com.vio.utils.LFONativeUtils
import com.vio.utils.LfoConstants

class LFOSelectFragment : BaseLFOFragment(), LFOSelectLanguage {

    private val nativeAdHelper by lazy { initNativeAd() }
    private val canShow = VioLFO.lfoConfig.isShowNativeLFO1 && VioLFO.lfoConfig.showLFO2

    private fun initNativeAd(): NativeAdHelper {
        val layoutNativeId =
            if (VioLFO.lfoConfig.isShowMeta && VioLFO.lfoConfig.isShowMetaAllPlatform) {
                VioLFO.lfoConfig.layoutNativeAdMeta
            } else {
                VioLFO.lfoConfig.layoutNativeAd
            }

        val idNativeAd = if (VioLFO.lfoConfig.requestNativePriorityLFO2) {
            VioLFO.lfoConfig.idNativePriorityLFO2
        } else {
            VioLFO.lfoConfig.idNativeLFO2
        }
        val config = NativeAdConfig(
            idNativeAd,
            canShow,
            true,
            layoutNativeId
        )
        if (VioLFO.lfoConfig.isShowMeta && !VioLFO.lfoConfig.isShowMetaAllPlatform) {
            config.setLayoutMediation(
                NativeLayoutMediation(
                    AdNativeMediation.FACEBOOK,
                    VioLFO.lfoConfig.layoutNativeAdMeta
                )
            )
        }
        return NativeAdHelper(myActivity, this, config).apply {
            if (VioLFO.lfoConfig.requestNativePriorityLFO2) {
                registerAdListener(object : NativeAdCallback {
                    override fun onAdClicked() {

                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        LFONativeUtils.requestNativeLFO2(
                            myActivity,
                            VioLFO.lfoConfig.idNativeLFO2
                        ) {
                            canShow
                        }
                    }

                    override fun onAdFailedToShow(adError: AdError) {

                    }

                    override fun onAdImpression() {

                    }


                    override fun onAdLoaded(data: ContentAd.AdmobAd.ApNativeAd) {

                    }

                    override fun populateNativeAd() {

                    }

                })
            }
        }
    }

    override fun initView() {
        initAdapter()
        arguments?.getInt(LfoConstants.KEY_SELECT_POSITION)?.let {
            if (
                lfoAdapter.getListData().isNotEmpty()
                && lfoAdapter.getListData().size > it
                && it >= 0
            ) {
                updateUiSelect(lfoAdapter.getListData()[it])
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val scrollY = arguments?.getInt(LfoConstants.KEY_SCROLL_Y) ?: 0
        Log.d("TAG", "onresume() called, $scrollY")
        binding.recyclerView.post {
            binding.recyclerView.scrollBy(0, scrollY)
        }
    }


    private fun initAdapter() {
        lfoAdapter.registerListener(this)
        binding.recyclerView.adapter = lfoAdapter
    }

    override fun onSelectLanguage(language: Language) {
        updateUiSelect(language)
    }

    private fun updateUiSelect(language: Language) {
        val positionSelected = lfoAdapter.getListData().indexOf(language)
        val positionUnSelect = lfoAdapter.getListData().indexOfFirst { it.isChoose }
        lfoAdapter.getListData().find { it.isChoose }?.isChoose = false
        language.isChoose = true
        lfoAdapter.notifyItemChanged(positionSelected)
        lfoAdapter.notifyItemChanged(positionUnSelect)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNativeAd()
    }

    private fun setupNativeAd() {
        binding.layoutAdNative.isVisible = canShow
        nativeAdHelper.setNativeContentView(binding.layoutAdNative)
        nativeAdHelper.setShimmerLayoutView(binding.flShimmerNative.findViewById(R.id.shimmerContainerNative))
        LFONativeUtils.nativeLFO2.observe(viewLifecycleOwner) { nativeAd ->
            if (nativeAd != null) {
                nativeAdHelper.requestAds(NativeAdParam.Ready(nativeAd.nativeAd))
            } else {
                nativeAdHelper.requestAds(NativeAdParam.Request.create())
            }
        }
    }
}
