package com.gt.plugin

/**
 *@Desc:
 *-
 *- 在执行release打包任务时生效
 *create by zhusw on 2021/2/20 16:07
 */
open class GitTagConfig {
    var tagAble = false
    var followAnyReleaseBuild = true
    var appointTask: Array<String> = arrayOf()
    var tagName: String = ""
    var tagMsg: String = ""

    fun tagAble(v: Boolean) {
        tagAble = v
    }

    /**
     * 跟随编译release任务触发
     */
    fun followAnyReleaseBuild(v: Boolean) {
        followAnyReleaseBuild = v
    }

    /**
     * 指定任务触发
     */
    fun appointTask(v: Array<String>) {
        appointTask = v
    }

    fun tagName(v: String) {
        tagName = v
    }

    fun tagMsg(v: String) {
        tagMsg = v
    }

}