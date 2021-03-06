package com.taskworld.android.restfulandroidkotlin.action

/**
 * Created by Kittinun Vantasin on 11/14/14.
 */

trait SignInUIAction {
    fun showProgress()
    fun hideProgress()
    fun setUnauthorizedError()
    fun setNetworkError()
    fun navigateToMain(sessionId: String)
}