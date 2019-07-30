package com.example.anothertimdatxe.splash

interface SplashView {
    fun goToLoginScreen()
    fun goToUpdateInfoScreen()
    fun goToHomeScreen()
    fun showNoConnectedToNetword()
    fun showLoading()
    fun hideLoading()
    fun showDialogExpiredSessionLogin()
    fun refreshTokenError(message:String)
}