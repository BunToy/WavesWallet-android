package com.wavesplatform.wallet.v2.ui.splash

import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.wavesplatform.wallet.v1.api.NodeManager
import com.wavesplatform.wallet.v1.util.PrefsUtil
import com.wavesplatform.wallet.v2.ui.splash.SplashView
import com.wavesplatform.wallet.v2.ui.base.presenter.BasePresenter
import javax.inject.Inject

@InjectViewState
class SplashPresenter @Inject constructor() : BasePresenter<SplashView>() {

    fun storeIncomingURI(intent: Intent) {
        val action = intent.action
        val scheme = intent.scheme
        if (action != null && Intent.ACTION_VIEW == action && scheme != null && scheme == "waves") {
            prefsUtil.setGlobalValue(PrefsUtil.GLOBAL_SCHEME_URL, intent.data!!.toString())
        }
    }

    fun resolveNextAction() {
        val loggedInGuid = prefsUtil.getGlobalValue(PrefsUtil.GLOBAL_LOGGED_IN_GUID, "")
        val pubKey = prefsUtil.getValue(PrefsUtil.KEY_PUB_KEY, "")
        if (loggedInGuid.isEmpty() || pubKey.isEmpty()
                || NodeManager.createInstance(pubKey) == null) {
            viewState.onNotLoggedIn()
        }else{
            viewState.onStartMainActivity(pubKey)
        }
    }

}
