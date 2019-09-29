package com.kotlin.turbo.utils

import android.app.Application
import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


/**
 * 文件名：Logger
 * 作者：Turbo
 * 时间：2019-09-18 12:03
 * 开心对待每一天，真心对待每一个
 */

private var TAG = "Entrepreneurial"
private var LOG_DEBUG = true
private val LINE_SEPARATOR: String = System.getProperty("line.separator").toString()
const val VERBOSE = 2
const val DEBUG = 3
const val INFO = 4
const val WARN = 5
const val ERROR = 6
const val ASSERT = 7
const val JSON = 8
const val XML = 9
const val JSON_INDENT = 4

fun Application.initLogger(isDebug: Boolean, tag: String) {
    TAG = tag
    LOG_DEBUG = isDebug
}

fun logv(msg: String) = log(VERBOSE, null, msg)

fun logv(tag: String, msg: String) = log(VERBOSE, tag, msg)

fun logd(msg: String) = log(DEBUG, null, msg)

fun logd(tag: String, msg: String) = log(DEBUG, tag, msg)

fun logi(vararg msg: Any) {
    val sb = StringBuilder()
    for (obj in msg) {
        sb.append(obj)
        sb.append(",")
    }
    log(INFO, null, sb.toString())
}

fun logw(msg: String) = log(WARN, null, msg)

fun logw(tag: String, msg: String) = log(WARN, tag, msg)

fun loge(msg: String) = log(ERROR, null, msg)

fun loge(tag: String, msg: String) = log(ERROR, tag, msg)

fun loga(msg: String) = log(ASSERT, null, msg)

fun loga(tag: String, msg: String) = log(ASSERT, tag, msg)

fun logJson(json: String) = log(JSON, null, json)

fun logjson(tag: String, json: String) = log(JSON, tag, json)

fun logXml(xml: String) = log(XML, null, xml)

fun logXml(tag: String, xml: String) = log(XML, tag, xml)

private fun log(logType: Int, tagStr: String?, objects: Any) {
    val contents = wrapperContent(tagStr, objects)
    val tag = contents[0]
    val msg = contents[1]
    val headString = contents[2]
    if (LOG_DEBUG) {
        when (logType) {
            VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT -> printDefault(
                logType,
                tag,
                headString + msg
            )
            JSON -> printJson(tag, msg, headString)
            XML -> printXml(tag, msg, headString)
            else -> {
            }
        }
    }
}

fun printDefault(type: Int, tag: String, msg: String) {
    var tag = tag
    if (TextUtils.isEmpty(tag)) {
        tag = TAG
    }
    var index = 0
    val maxLength = 3900
    val countOfSub = msg.length / maxLength

    if (countOfSub > 0) {  // The log is so long
        for (i in 0 until countOfSub) {
            val sub = msg.substring(index, index + maxLength)
            printSub(type, tag, sub)
            index += maxLength
        }
        printSub(type, tag, msg.substring(index, msg.length))
    } else {
        printSub(type, tag, msg)
    }

}

private fun printSub(type: Int, tag: String?, sub: String) {
    var tag = tag
    if (tag == null) {
        tag = TAG
    }
    printLine(tag, true)
    when (type) {
        VERBOSE -> Log.v(tag, sub)
        DEBUG -> Log.d(tag, sub)
        INFO -> Log.i(tag, sub)
        WARN -> Log.w(tag, sub)
        ERROR -> Log.e(tag, sub)
        ASSERT -> Log.wtf(tag, sub)
    }
    printLine(tag, false)
}

private fun printJson(tag: String, json: String, headString: String) {
    var tag = tag
    if (TextUtils.isEmpty(json)) {
        logd("Empty/Null json content")
        return
    }
    if (TextUtils.isEmpty(tag)) {
        tag = TAG
    }
    var message: String

    message = try {
        when {
            json.startsWith("{") -> {
                val jsonObject = JSONObject(json)
                jsonObject.toString(JSON_INDENT)
            }
            json.startsWith("[") -> {
                val jsonArray = JSONArray(json)
                jsonArray.toString(JSON_INDENT)
            }
            else -> json
        }
    } catch (e: JSONException) {
        json
    }

    printLine(tag, true)
    message = headString + LINE_SEPARATOR + message
    val lines =
        message.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    for (line in lines) {
        Log.d(tag, "|$line")
    }
    printLine(tag, false)
}

private fun printXml(tag: String, xml: String?, headString: String) {
    var tag = tag
    var xml = xml
    if (TextUtils.isEmpty(tag)) {
        tag = TAG
    }
    if (xml != null) {
        try {
            val xmlInput = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            xml = xmlOutput.writer.toString().replaceFirst(">", ">\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        xml = headString + "\n" + xml
    } else {
        xml = headString + "Log with null object"
    }

    printLine(tag, true)
    val lines = xml.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    for (line in lines) {
        if (!TextUtils.isEmpty(line)) {
            Log.d(tag, "|$line")
        }
    }
    printLine(tag, false)
}

private fun wrapperContent(tag: String?, vararg objects: Any): Array<String> {
    var tag = tag
    if (TextUtils.isEmpty(tag)) {
        tag = TAG
    }
    val stackTrace = Thread.currentThread().stackTrace
    val targetElement = stackTrace[5]
    var className = targetElement.className
    val classNameInfo =
        className.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    if (classNameInfo.isNotEmpty()) {
        className = classNameInfo[classNameInfo.size - 1] + ".java"
    }
    val methodName = targetElement.methodName
    var lineNumber = targetElement.lineNumber
    if (lineNumber < 0) {
        lineNumber = 0
    }
    val methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1)
    val msg = if (objects == null) "Log with null object" else getObjectsString(*objects)
    val headString = "[($className:$lineNumber)#$methodNameShort ] "
    return arrayOf(tag!!, msg, headString)
}

fun getObjectsString(vararg objects: Any): String {

    if (objects.size > 1) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n")
        for (i in objects.indices) {
            val `object` = objects[i]
            if (`object` == null) {
                stringBuilder.append("param").append("[").append(i).append("]").append(" = ")
                    .append("null").append("\n")
            } else {
                stringBuilder.append("param").append("[").append(i).append("]").append(" = ")
                    .append(`object`.toString()).append("\n")
            }
        }
        return stringBuilder.toString()
    } else {
        val `object` = objects[0]
        return `object`?.toString()
    }
}

private fun printLine(tag: String, isTop: Boolean) {
    if (isTop) {
        Log.d(
            tag,
            "╔═══════════════════════════════════════════════════════════════════════════════════════"
        )
    } else {
        Log.d(
            tag,
            "╚═══════════════════════════════════════════════════════════════════════════════════════"
        )
    }
}