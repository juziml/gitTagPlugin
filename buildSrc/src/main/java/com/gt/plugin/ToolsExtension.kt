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
   lateinit var tag: Closure<Any>
    fun tag(closure: Closure<Any>) {
        tag = closure
    }
    lateinit var checkSnapshot: Closure<Any>
    fun checkSnapshot(closure: Closure<Any>){
        checkSnapshot = closure
    }
}


