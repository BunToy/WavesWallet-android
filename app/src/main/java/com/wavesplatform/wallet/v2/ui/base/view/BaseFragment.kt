package com.wavesplatform.wallet.v2.ui.base.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment : MvpAppCompatFragment(), BaseView, BaseMvpView, HasSupportFragmentInjector {

    protected lateinit var mContext: Context

    val supportActionBar: ActionBar?
        get() = baseActivity.supportActionBar

    val baseActivity: BaseActivity
        get() = activity as BaseActivity

    val toolbar: Toolbar
        get() = baseActivity.toolbar

    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        Timber.tag(javaClass.simpleName)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(configLayoutRes(), container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity!!
        onViewReady(savedInstanceState)
    }

    protected abstract fun onViewReady(savedInstanceState: Bundle?)

    fun setTitle(title: Int) {
        baseActivity.title = getString(title)
    }

    override fun showNetworkError() {
        baseActivity.showNetworkError()
    }

    override fun showProgressBar(isShowProgress: Boolean) {
        baseActivity.showProgressBar(isShowProgress)
    }

    override fun onBackPressed() {
        baseActivity.onBackPressed()
    }
}
