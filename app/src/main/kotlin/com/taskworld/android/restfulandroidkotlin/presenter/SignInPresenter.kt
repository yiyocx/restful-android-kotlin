package com.taskworld.android.restfulandroidkotlin.presenter

import com.taskworld.android.restfulandroidkotlin.action.SignInUIAction
import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.interactor.SignInInteractorImpl
import com.taskworld.android.restfulandroidkotlin.interactor.SignInInteractor
import de.greenrobot.event.EventBus
import com.octo.android.robospice.persistence.exception.SpiceException
import kotlin.properties.Delegates
import retrofit.RetrofitError

/**
 * Created by Kittinun Vantasin on 11/14/14.
 */

trait SignInPresenter : Presenter {
    fun logInWithCredentials(username: String, password: String)
}

class SignInPresenterImpl(val mAction: SignInUIAction, val mSpiceManager: SpiceManager, val mBus: EventBus) : SignInPresenter {

    var mUsername: String by Delegates.notNull()
    var mPassword: String by Delegates.notNull()

    var mInteractor: SignInInteractor

    init {
        mInteractor = SignInInteractorImpl(mSpiceManager, mBus)
    }

    override fun onResume() {
        mBus.register(this)
    }

    override fun onPause() {
        mBus.unregister(this)
    }

    override fun logInWithCredentials(username: String, password: String) {
        mAction.showProgress()
        mUsername = username
        mPassword = password
        mInteractor.requestNewToken()
    }

    fun requestNewSession(requestToken: String) {
        mInteractor.requestNewSession(requestToken)
    }

    fun validateCredentials(requestToken: String) {
        mInteractor.validateCredentials(mUsername, mPassword, requestToken)
    }

    fun onEvent(map: Map<String, String>) {
        val requestToken = map["request_token"]
        if (map.contains("expires_at")) {
            validateCredentials(requestToken!!)
        } else if (map.contains("session_id")) {
            mAction.hideProgress()
            mAction.navigateToMain(map["session_id"]!!)
        } else {
            requestNewSession(requestToken!!)
        }
    }

    fun onEvent(spiceException: SpiceException?) {
        mAction.hideProgress()

        val error = spiceException?.getCause() as RetrofitError
        if (error.getResponse().getStatus() == 401) {
            mAction.setUnauthorizedError()
        } else if (error.isNetworkError()) {
            mAction.setNetworkError()
        }
    }

}
