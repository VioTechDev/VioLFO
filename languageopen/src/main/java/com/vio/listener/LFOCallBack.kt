package com.vio.listener

import com.vio.model.Language

interface LFOCallBack {
    fun onChangeLanguage(language: Language?)
    fun onBackPressLanguage()
}