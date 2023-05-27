package com.gotadi.AndroidGotadiSDK

import org.intellij.lang.annotations.Language

enum class GTDThemeMode {primary, secondary}
data class GotadiPartnerSetting(val env: String, val partnerName: String, val language: String, val token: String, val theme: GTDThemeMode)
