package com.vio.fragment

import android.os.Bundle
import android.view.View
import com.ads.admob.data.ContentAd
import com.ads.admob.helper.adnative.NativeAdConfig
import com.ads.admob.helper.adnative.NativeAdHelper
import com.ads.admob.helper.adnative.params.AdNativeMediation
import com.ads.admob.helper.adnative.params.NativeAdParam
import com.ads.admob.helper.adnative.params.NativeLayoutMediation
import com.ads.admob.listener.NativeAdCallback
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.LoadAdError
import com.vio.BaseLFOFragment
import com.vio.VioLFO
import com.vio.languageopen.R
import com.vio.listener.LFOSelectLanguage
import com.vio.model.Language
import com.vio.utils.LFONativeUtils

class LFOFragment : BaseLFOFragment(), LFOSelectLanguage {

    private val nativeAdHelper by lazy { initNativeAd() }
    private fun initNativeAd(): NativeAdHelper {
        val idNativeAd = if (VioLFO.lfoConfig.requestNativePriorityLFO1
            && VioLFO.lfoConfig.idNativePriorityLFO1.isNotBlank()
        ) {
            VioLFO.lfoConfig.idNativePriorityLFO1
        } else {
            VioLFO.lfoConfig.idNativeLFO1
        }
        val layoutNativeId =
            if (VioLFO.lfoConfig.isShowMeta && VioLFO.lfoConfig.isShowMetaAllPlatform) {
                VioLFO.lfoConfig.layoutNativeAdMeta
            } else {
                VioLFO.lfoConfig.layoutNativeAd
            }
        val config = NativeAdConfig(
            idNativeAd,
            VioLFO.lfoConfig.isShowNativeLFO1,
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
            if (VioLFO.lfoConfig.requestNativePriorityLFO1) {
                registerAdListener(object : NativeAdCallback {
                    override fun onAdClicked() {

                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    }

                    override fun onAdFailedToShow(adError: AdError) {

                    }

                    override fun onAdImpression() {

                    }


                    override fun onAdLoaded(data: ContentAd) {

                    }

                    override fun populateNativeAd() {

                    }

                })
            }
        }
    }

    override fun initView() {
        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNativeAd()
    }

    private fun setupNativeAd() {
        nativeAdHelper.setNativeContentView(binding.layoutAdNative)
        nativeAdHelper.setShimmerLayoutView(binding.flShimmerNative.findViewById(R.id.shimmerContainerNative))
        LFONativeUtils.nativeLFO1.observe(viewLifecycleOwner) { nativeAd ->
            if (nativeAd != null) {
                nativeAdHelper.requestAds(NativeAdParam.Ready(nativeAd))
            } else {
                nativeAdHelper.requestAds(NativeAdParam.Request.create())
            }
        }
        //request native LFO2
        if (VioLFO.lfoConfig.requestNativePriorityLFO2 && VioLFO.lfoConfig.idNativePriorityLFO2.isNotBlank()) {
            LFONativeUtils.requestLFO2Alternate(
                myActivity,
                VioLFO.lfoConfig.idNativePriorityLFO2,
                VioLFO.lfoConfig.idNativeLFO2,
                VioLFO.lfoConfig.layoutNativeAd
            ) {
                VioLFO.lfoConfig.isShowNativeLFO1 && VioLFO.lfoConfig.showLFO2
            }

        } else {
            LFONativeUtils.requestNativeLFO2(
                myActivity,
                VioLFO.lfoConfig.idNativeLFO2
            ) {
                VioLFO.lfoConfig.isShowNativeLFO1 && VioLFO.lfoConfig.showLFO2
            }
        }
    }

    private fun initAdapter() {
        lfoAdapter.registerListener(this)
        binding.recyclerView.adapter = lfoAdapter
    }

    override fun onSelectLanguage(language: Language) {
        if (VioLFO.lfoConfig.showLFO2) {
            navigateToSelect(
                lfoAdapter.getListData().indexOf(language),
                binding.recyclerView.computeVerticalScrollOffset()
            )
        }
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

}
