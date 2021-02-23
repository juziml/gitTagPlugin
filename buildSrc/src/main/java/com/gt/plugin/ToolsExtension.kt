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
    var openLog = true
    set(value) {
        field = value
        logable = value
    }
    fun openLog(v: Boolean){
        openLog = v
    }

    var tag: Closure<Any> = object : Closure<Any>(GitTagConfig()){}
    fun tag(closure: Closure<Any>) {
        tag = closure
    }

    var releaseCheck: Closure<Any> = object : Closure<Any>(ReleaseCheckConfig()){}
    fun releaseCheck(closure: Closure<Any>){
        releaseCheck = closure
    }

}



