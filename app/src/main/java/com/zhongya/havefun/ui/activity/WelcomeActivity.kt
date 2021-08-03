package com.zhongya.havefun.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.view.KeyEvent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.zhongya.havefun.R
import com.zhongya.havefun.app.BaseAppActivity
import com.zhongya.havefun.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WelcomeActivity : BaseAppActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {

    private var firstTime: Long = 0

    override fun initView() {

        if (ActivityCompat.checkSelfPermission(
                this@WelcomeActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@WelcomeActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }

        toMain()
    }

    private fun toMain() {
        /**
         * runBlocking 与 coroutineScope 的主要区别在于后者在等待所有子协程执行完毕时不会阻塞当前线程。
         */

        lifecycleScope.launchWhenCreated {
            delay(1000)
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > 2000) {
                firstTime = secondTime
                return true
            } else {
                System.exit(0) //结束线程

            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun createObserver() {

    }
}