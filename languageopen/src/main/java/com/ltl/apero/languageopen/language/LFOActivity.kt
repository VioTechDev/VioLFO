package com.ltl.apero.languageopen.language

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.ltl.apero.languageopen.databinding.ActivityLfoBinding
import com.ltl.apero.languageopen.language.extention.lightStatusBar


class LFOActivity : BaseFLO() {
    private var binding: ActivityLfoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLfoBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateStatusBarColor()
    }

    private fun updateStatusBarColor() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        VioLFO.lfoConfig.colorStatusBar.let {
            window.statusBarColor = ContextCompat.getColor(this, it)
        }
        lightStatusBar(VioLFO.lfoConfig.isLightStatusBar)
    }
}
