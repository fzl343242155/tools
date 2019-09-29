package com.kotlin.turbo

import android.app.Application
import com.kotlin.turbo.utils.AppPrefs

/**
 * 文件名：TurboApplication
 * 作者：Turbo
 * 时间：2019-09-26 15:12
 * 开心对待每一天，真心对待每一个
 */

class TurboApplication : Application() {

    companion object {
        lateinit var instance: TurboApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppPrefs.init(this)
    }

}