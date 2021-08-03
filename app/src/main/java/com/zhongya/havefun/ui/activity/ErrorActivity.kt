package com.zhongya.havefun.ui.activity

import android.content.ClipData
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.zhongya.havefun.R
import com.zhongya.havefun.app.BaseAppActivity
import com.zhongya.havefun.app.ext.showMessage
import com.zhongya.havefun.databinding.ActivityErrorBinding
import com.zhongya666.library.ext.init
import com.zhongya666.library.util.SettingUtil
import com.zhongya666.library.util.ToastUtil
import com.zhongya666.library.util.clipboardManager
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat


/**
 * GitHub/Gitee：zhongya666 2021/7/29 15:13
 *
 */
class ErrorActivity : BaseAppActivity<ActivityErrorBinding>(R.layout.activity_error) {

    override fun initView() {
        mDatabind.include.toolbar.init("发生错误")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        mDatabind.errorRestart.clickNoRepeat {
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }
        mDatabind.errorSendError.clickNoRepeat {
            CustomActivityOnCrash.getStackTraceFromIntent(intent)?.let {
                showMessage(it, "发现有Bug不去打作者脸？", "必须打", {
                    val mClipData = ClipData.newPlainText("errorLog", it)
                    // 将ClipData内容放到系统剪贴板里。
                    clipboardManager?.setPrimaryClip(mClipData)
                    ToastUtil.show("已复制错误日志")
                    try {
                        val url = "mqqwpa://im/chat?chat_type=wpa&uin=824868922"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        ToastUtil.show("请先安装QQ")
                    }
                }, "我不敢")
            }


        }
    }

    override fun createObserver() {

    }
}