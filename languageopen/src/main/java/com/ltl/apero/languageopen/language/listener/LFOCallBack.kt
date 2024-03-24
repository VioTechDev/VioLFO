package com.ltl.apero.languageopen.language.listener

import com.ltl.apero.languageopen.language.model.Language

interface LFOCallBack {
    fun onChangeLanguage(language: Language?)
    fun onBackPressLanguage()
}