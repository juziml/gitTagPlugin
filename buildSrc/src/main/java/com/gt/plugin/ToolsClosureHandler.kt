package com.gt.plugin

import groovy.lang.Closure

/**
 *@Desc:
 *-
 *-
 *create by zhusw on 2021/2/20 16:06
 */
object ToolsClosureHandler {
    fun <T> build(closure: Closure<Any>, clz:Class<T>):T{
       val delegateInstance = clz.newInstance()
        //闭包执行对象为 仅代理
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = delegateInstance
        closure.call()
        return delegateInstance
    }
}