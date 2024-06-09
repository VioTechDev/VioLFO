package com.ltl.languagefirstopen

import android.app.Application
import com.ads.admob.admob.AdmobFactory
import com.ads.admob.config.NetworkProvider
import com.ads.admob.config.VioAdConfig
import com.ads.admob.config.VioAdjustConfig


class App : Application() {
    private val ADJUST_TOKEN = "cc4jvudppczk"
    private val EVENT_PURCHASE_ADJUST = "gzel1k"

    override fun onCreate() {
        super.onCreate()
        val vioAdjustConfig = VioAdjustConfig.Build("mpuaogf4tngg",  false).build()
        val vioAdConfig = VioAdConfig.Builder(vioAdjustConfig = vioAdjustConfig)
            .buildVariantProduce(false)
            .mediationProvider(NetworkProvider.ADMOB)
            .listTestDevices(arrayListOf("FBDA72C75E0671544A38367B5AACCEC7"))
            .build()
        AdmobFactory.INSTANCE.initAdmob(this, vioAdConfig)
    }
}