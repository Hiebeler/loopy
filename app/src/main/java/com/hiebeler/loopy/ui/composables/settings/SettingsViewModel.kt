package com.hiebeler.loopy.ui.composables.settings

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.LoginActivity
import com.hiebeler.loopy.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
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

    fun logout(context: Context) {
        viewModelScope.launch {
            logoutUseCase()
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
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