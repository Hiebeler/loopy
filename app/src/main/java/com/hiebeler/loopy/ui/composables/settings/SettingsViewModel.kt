package com.hiebeler.loopy.ui.composables.settings

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

): ViewModel(){
    var versionName by mutableStateOf("")

    fun getVersionName(context: Context) {
        try {
            versionName =
                context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    fun logout() {
        viewModelScope.launch {
            //logoutUseCase()
        }
    }

    fun openMoreSettingsPage(context: Context) {
//        viewModelScope.launch {
//            val domain = getOwnInstanceDomainUseCase()
//            val moreSettingUrl = "https://$domain/settings/home"
//            openExternalUrlUseCase(context, moreSettingUrl)
//        }
    }
}