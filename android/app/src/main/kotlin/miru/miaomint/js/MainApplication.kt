package miru.miaomint.js

import android.app.Application
import com.hjq.toast.ToastUtils

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
    }
}