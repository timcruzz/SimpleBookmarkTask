package com.timothy.simplebookmarktask.utilities

import androidx.navigation.NavController

class ExtensionUtils {

    companion object {
        fun isNumber(str: String) = str.toDoubleOrNull()?.let { true } ?: false
    }

}

fun <T> NavController?.setCurrentBackstackSavedContent(key: String, value: T){
    this?.currentBackStackEntry?.savedStateHandle?.set(
        key,
        value
    )
}