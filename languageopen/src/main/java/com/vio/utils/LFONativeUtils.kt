package com.vio.utils

import android.content.Context
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.MutableLiveData
import com.ads.admob.admob.AdmobFactory
import com.ads.admob.data.ContentAd
import com.ads.admob.listener.NativeAdCallback
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.LoadAdError
import com.google.firebase.analytics.FirebaseAnalytics
import com.vio.VioLFO

internal object LFONativeUtils {
    private val TAG = LFONativeUtils::class.simpleName
    var nativeLFO1: MutableLiveData<ContentAd.AdmobAd.ApNativeAd> = MutableLiveData()
    var nativeLFO2: MutableLiveData<ContentAd.AdmobAd.ApNativeAd> = MutableLiveData()
    fun requestNativeLFO1(
        context: Context,
        idAd: String,
        requestValid: () -> Boolean
    ) {
        if (requestValid()) {
            Log.d(TAG, "requestNativeLFO1 ")
            AdmobFactory.getInstance().requestNativeAd(context, idAd, object : NativeAdCallback {
                override fun onAdClicked() {
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    nativeLFO1.postValue(null)
                }

                override fun onAdFailedToShow(adError: AdError) {
                }

                override fun onAdImpression() {
                }

                override fun onAdLoaded(data: ContentAd.AdmobAd.ApNativeAd) {
                    nativeLFO1.postValue(data)
                }

                override fun populateNativeAd() {
                }

            })
        } else {
            nativeLFO1.postValue(null)
            Log.e(TAG, "onAdLFO1FailedToLoad: invalid")
        }
    }

    fun requestNativeLFO2(
        context: Context,
        idAd: String,
        requestValid: () -> Boolean
    ) {
        if (requestValid()) {
            Log.d(TAG, "requestNativeLFO2 ")
            AdmobFactory.getInstance().requestNativeAd(context, idAd, object : NativeAdCallback {
                override fun onAdClicked() {
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    nativeLFO2.postValue(null)
                }

                override fun onAdFailedToShow(adError: AdError) {
                }

                override fun onAdImpression() {
                }

                override fun onAdLoaded(data: ContentAd.AdmobAd.ApNativeAd) {
                    nativeLFO2.postValue(data)
                }

                override fun populateNativeAd() {
                }

            })
        } else {
            Log.e(TAG, "onLFO2AdFailedToLoad: Invalid")
            nativeLFO2.postValue(null)
        }
    }

    fun requestLFO1Alternate(
        context: Context,
        idAdPriorityAd: String,
        idAdAllPrice: String,
        @LayoutRes layoutNativeAd: Int,
        requestValid: () -> Boolean
    ) {
        if (requestValid()) {
            requestNativeAlternate(
                context,
                idAdPriorityAd,
                idAdAllPrice,
                layoutNativeAd,
                onAdLoaded = { nativeAd ->
                    nativeLFO1.postValue(nativeAd)
                },
                onFailedToLoad = {
                    nativeLFO1.postValue(null)
                },
                onAdImpression = {

                },
                onClick = {
                    VioLFO.lfoConfig.lfo1NativeAdClickEventName
                        .takeIf { it.isNotBlank() }
                        ?.let {
                            FirebaseAnalytics.getInstance(context)
                                .logEvent(it, null)
                        }
                })
        } else {
            nativeLFO1.postValue(null)
        }
    }

    fun requestLFO2Alternate(
        context: Context,
        idAdPriorityAd: String,
        idAdAllPrice: String,
        @LayoutRes layoutNativeAd: Int,
        requestValid: () -> Boolean
    ) {
        if (requestValid()) {
            requestNativeAlternate(
                context,
                idAdPriorityAd,
                idAdAllPrice,
                layoutNativeAd,
                onAdLoaded = { nativeAd ->
                    nativeLFO2.postValue(nativeAd)
                },
                onFailedToLoad = {
                    nativeLFO2.postValue(null)
                },
                onAdImpression = {
                },
                onClick = {
                    VioLFO.lfoConfig.lfo2NativeAdClickEventName
                        .takeIf { it.isNotBlank() }
                        ?.let {
                            FirebaseAnalytics.getInstance(context)
                                .logEvent(it, null)
                        }
                })
        } else {
            nativeLFO2.postValue(null)
        }
    }

    private fun requestNativeAlternate(
        context: Context,
        idAdPriorityAd: String,
        idAdAllPrice: String,
        @LayoutRes layoutNativeAd: Int,
        onAdLoaded: (ContentAd.AdmobAd.ApNativeAd) -> Unit,
        onFailedToLoad: () -> Unit,
        onAdImpression: () -> Unit = {},
        onClick: () -> Unit
    ) {
        requestNativeAd(
            context,
            idAdPriorityAd,
            layoutNativeAd,
            onAdLoaded = { nativeAd ->
                Log.d(TAG, "requestNativeAlternate: Priority Loaded  $idAdPriorityAd")
                onAdLoaded(nativeAd)
            },
            onFailedToLoad = {
                Log.d(TAG, "requestNativeAlternate: Priority Failed To Load ")
                requestNativeAd(
                    context,
                    idAdAllPrice,
                    layoutNativeAd,
                    onAdLoaded = { nativeAd ->
                        Log.d(TAG, "requestNativeAlternate: All price loaded $idAdAllPrice")
                        onAdLoaded(nativeAd)
                    },
                    onFailedToLoad = {
                        Log.d(TAG, "requestNativeAlternate: All price Failed To Load ")
                        onFailedToLoad()
                    },
                    onAdImpression = onAdImpression,
                    onClick = onClick
                )
            },
            onClick = onClick
        )
    }

    private fun requestNativeAd(
        context: Context,
        idAd: String,
        @LayoutRes layoutNativeAd: Int,
        onAdLoaded: (ContentAd.AdmobAd.ApNativeAd) -> Unit,
        onFailedToLoad: () -> Unit,
        onAdImpression: () -> Unit = {},
        onClick: () -> Unit
    ) {
        AdmobFactory.getInstance().requestNativeAd(context, idAd, object : NativeAdCallback {
            override fun onAdClicked() {
                onClick()
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                onFailedToLoad()
            }

            override fun onAdFailedToShow(adError: AdError) {
            }

            override fun onAdImpression() {
                onAdImpression()
            }

            override fun onAdLoaded(data: ContentAd.AdmobAd.ApNativeAd) {
                onAdLoaded(data)
            }

            override fun populateNativeAd() {
            }

        })
    }
}
