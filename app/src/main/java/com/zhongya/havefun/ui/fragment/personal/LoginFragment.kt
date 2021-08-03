package com.zhongya.havefun.ui.fragment.personal

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import com.zhongya.havefun.R
import com.zhongya.havefun.databinding.FragmentLoginBinding
import com.zhongya.havefun.viewmodel.personal.LoginFragmentVM
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.bus.LiveDataBus
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.ext.initClose
import com.zhongya666.library.network.IStateObserver
import com.zhongya666.library.util.SPUtils
import com.zhongya666.library.view.nav
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseAppFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private lateinit var phonenum: String
    private lateinit var password: String
    private var firstTime: Long = 0

    private val viewModel: LoginFragmentVM by viewModel()

    private val PERMISSIONS = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun initView() {

        mDatabind.click = ProxyClick()


        mDatabind.include.toolbar.initClose("登录") {

            nav().navigateUp()

        }
    }

    inner class ProxyClick {

        fun signin_btn() {
            phonenum = mDatabind.phonenumberEdt.text.toString()
            password = mDatabind.passwordEdt.text.toString()
            mDatabind.signinBtn.text = "登录中"

            viewModel.login(phonenum, password)

        }

        fun tv_guest() {
            /*val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()*/
        }

        fun tv_chat2me() {
            val qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=997934916&version=2"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)))
        }
    }


    override fun lazyLoadData() {
    }

    override fun createObserver() {
        viewModel.loginLiveData.observe(
            this,
            object :
                IStateObserver<com.zhongya.havefun.bean.personal.LoginResp>(mDatabind.refreshLayout) {
                override fun onDataChange(data: com.zhongya.havefun.bean.personal.LoginResp?) {
                    showToast("登录成功")
                    SPUtils.put(Constant.Sp.USER_NAME, data?.nickname)
                    LiveDataBus.get().with(Constant.Bus.login_response)
                        .postValue(data)
                    nav().navigateUp()
                }

                override fun onError(e: Throwable?) {
                    super.onError(e)
                    Log.e("wzy", "onError")
                    mDatabind.signinBtn.text = "登录"
                }

                override fun onReload(v: View?) {

                }
            })
    }

}