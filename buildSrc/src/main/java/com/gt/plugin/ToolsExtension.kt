package com.gt.plugin

import groovy.lang.Closure
import org.gradle.util.ConfigureUtil
import javax.swing.Action

/**
 *@Desc:
 *create by zhusw on 2021/2/8 16:45
 */
//作为拓展函数对象，不能是final
open class ToolsExtension {
    var logEnable = true
        set(value) {
            field = value
            logable = value
        }
    fun logEnable(v: Boolean) {
        logEnable = v
    }

    var gitTag: Closure<Any>? = null
    fun gitTag(closure: Closure<Any>) {
        gitTag = closure
    }

    var releaseCheck: Closure<Any>? = null
    fun releaseCheck(closure: Closure<Any>) {
        releaseCheck = closure
    }

}



