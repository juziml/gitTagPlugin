package com.gt.plugin

import groovy.lang.Closure
import org.gradle.util.ConfigureUtil
import javax.swing.Action

/**
 *@Desc:
 *-
 *-
 *create by zhusw on 2021/2/8 16:45
 */
//作为拓展函数对象，不能是final
open class GitTagExtension {
    var targetTask = "taskName"

    //    var tag:Closure<Any>? = null
    fun tag(closure: Closure<Any>) {
        val tagConfig = TagConfig()
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.delegate = tagConfig
        closure.call()
        println("extensionFun name = ${tagConfig.name} ")
    }
}

open class TagHandler(private val closure: Closure<in Any>) {
    fun call(): TagConfig {
        val tagConfig = TagConfig()
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.delegate = tagConfig
        closure.call()
//        ConfigureUtil.configure(closure,tagConfig)
        return tagConfig
    }
}

open class TagConfig {
    var name: String = ""
    var msg: String = ""

//    fun name(s:String){
//        mName = s
//    }
//    fun msg(s:String){
//        mMsg = s
//    }
}
