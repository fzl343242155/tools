package com.kotlin.turbo.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * 文件名：JumperUtils
 * 作者：Turbo
 * 时间：2019-09-19 15:56
 * 开心对待每一天，真心对待每一个
 */

class JumperUtils {
    companion object {
        fun JumpTo(ctx: Context, clazz: Class<Any>) = try {
            val intent = Intent()
            intent.setClass(ctx, clazz)
            ctx.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            loge("JumperUtils", e.message.toString())
        } catch (e: NullPointerException) {
            loge("JumperUtils", e.message.toString())
        }

        fun JumpTo(ctx: Context, clazz: Class<Any>, bundle: Bundle) {
            try {
                val intent = Intent()
                intent.setClass(ctx, clazz)
                intent.putExtras(bundle)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                ctx.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                loge("JumperUtils", e.message.toString())
            } catch (e: NullPointerException) {
                loge("JumperUtils", e.message.toString())
            }

        }

        fun JumpToForResult(ctx: Activity, clazz: Class<Any>, requestCode: Int) {
            try {
                val intent = Intent()
                intent.setClass(ctx, clazz)
                ctx.startActivityForResult(intent, requestCode)
            } catch (e: ActivityNotFoundException) {
                loge("JumperUtils", e.message.toString())
            } catch (e: NullPointerException) {
                loge("JumperUtils", e.message.toString())
            }

        }

        fun JumpToForResult(ctx: Activity, clazz: Class<Any>, requestCode: Int, bundle: Bundle) {
            try {
                val intent = Intent()
                intent.setClass(ctx, clazz)
                intent.putExtras(bundle)
                ctx.startActivityForResult(intent, requestCode)
            } catch (e: ActivityNotFoundException) {
                loge("JumperUtils", e.message.toString())
            } catch (e: NullPointerException) {
                loge("JumperUtils", e.message.toString())
            }

        }


    }
}