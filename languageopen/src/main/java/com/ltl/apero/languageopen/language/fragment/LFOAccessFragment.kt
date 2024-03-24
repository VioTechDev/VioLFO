package com.ltl.apero.languageopen.language.fragment

import com.ltl.apero.languageopen.language.BaseLFOFragment
import com.ltl.apero.languageopen.language.utils.LfoConstants

class LFOAccessFragment : BaseLFOFragment() {
    private var openFromMain = false
    private var positionSelect = 0
    override fun initView() {
        openFromMain = arguments?.getBoolean(LfoConstants.KEY_OPEN_FROM_MAIN) ?: false
        positionSelect = arguments?.getInt(LfoConstants.KEY_SELECT_POSITION) ?: 1
    }

}