package com.vio.fragment

import com.vio.BaseLFOFragment
import com.vio.utils.LfoConstants

class LFOAccessFragment : BaseLFOFragment() {
    private var openFromMain = false
    private var positionSelect = 0
    override fun initView() {
        openFromMain = arguments?.getBoolean(LfoConstants.KEY_OPEN_FROM_MAIN) ?: false
        positionSelect = arguments?.getInt(LfoConstants.KEY_SELECT_POSITION) ?: 1
    }

}