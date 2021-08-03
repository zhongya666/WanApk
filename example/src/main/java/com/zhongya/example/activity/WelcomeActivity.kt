package com.zhongya.example.activity

import android.content.Intent
import android.view.KeyEvent
import androidx.lifecycle.lifecycleScope
import com.zhongya.example.R
import com.zhongya.example.databinding.ExampleActivityWelcomeBinding
import com.zhongya666.library.base.activity.BaseDbActivity
import kotlinx.coroutines.delay

class WelcomeActivity : BaseDbActivity<ExampleActivityWelcomeBinding>(R.layout.example_activity_welcome) {

    private var duration = 500
    private var firstTime: Long = 0

    override fun initView() {
        toMain()
    }

    private fun toMain() {
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