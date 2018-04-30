package com.wavesplatform.wallet.v2.ui.language.choose

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wavesplatform.wallet.R
import com.wavesplatform.wallet.v2.data.model.local.Language
import com.wavesplatform.wallet.v2.ui.base.view.BaseActivity
import com.wavesplatform.wallet.v2.ui.language.LanguageAdapter
import com.wavesplatform.wallet.v2.ui.language.LanguagePresenter
import com.wavesplatform.wallet.v2.ui.language.LanguageView
import com.wavesplatform.wallet.v2.ui.welcome.WelcomeActivity
import com.wavesplatform.wallet.v2.util.launchActivity
import kotlinx.android.synthetic.main.activity_choose_language.*
import pers.victor.ext.click
import pers.victor.ext.dp2px
import pers.victor.ext.visiable
import javax.inject.Inject
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.app.Activity
import android.os.Build
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import com.wavesplatform.wallet.v2.ui.tutorial.TutorialActivity


class ChooseLanguageActivity : BaseActivity(), LanguageView {

    @Inject
    @InjectPresenter
    lateinit var presenter: LanguagePresenter


    @Inject
    lateinit var adapter: LanguageAdapter

    @ProvidePresenter
    fun providePresenter(): LanguagePresenter = presenter

    override fun configLayoutRes() = R.layout.activity_choose_language

    override fun onViewReady(savedInstanceState: Bundle?) {
//        image_logo.translationY = getStatusBarHeight().toFloat()

        recycle_language.layoutManager = LinearLayoutManager(this)
        recycle_language.adapter = adapter

        adapter.setNewData(presenter.getLanguages())

        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as Language

            if (presenter.currentLanguagePosition == position) return@OnItemClickListener

            if (presenter.currentLanguagePosition == -1) {
                // check new item
                button_continue.visiable()
                presenter.currentLanguagePosition = position
                item.checked = true
                adapter.setData(position, item)
            } else {
                // uncheck old item
                val currentCheckedItem = adapter.getItem(presenter.currentLanguagePosition) as Language
                currentCheckedItem.checked = false
                adapter.setData(presenter.currentLanguagePosition, currentCheckedItem)

                // check new item
                presenter.currentLanguagePosition = position
                item.checked = true
                adapter.setData(position, item)
            }
        }

        button_continue.click {
            exitAnimation()
        }

        enterAnimation()
    }

    private fun enterAnimation() {
        image_logo.post {
            image_logo.animate()
                    .translationY(-(image_logo.y - dp2px(80)))
                    .setDuration(500)
                    .setStartDelay(500)
                    .withEndAction({
                        recycle_language.animate()
                                .alpha(1f)
                                .translationY(0f)
                                .setDuration(500)
                                .start()
                    })
                    .start()
        }
    }

    private fun exitAnimation() {
        relative_root.post {
            relative_root.animate()
                    .translationY(dp2px(50).toFloat())
                    .alpha(0f)
                    .setDuration(400)
                    .withEndAction({
                        launchActivity<TutorialActivity>()
                        overridePendingTransition(0, 0)
                    })
                    .start()
        }
    }

}
