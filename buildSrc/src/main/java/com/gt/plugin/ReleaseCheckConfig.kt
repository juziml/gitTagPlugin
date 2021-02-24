package com.gt.plugin

/**
 *@Desc:
 *-
 *- 在执行release打包任务时生效
 *create by zhusw on 2021/2/20 16:07
 */
open class ReleaseCheckConfig {

    var checkMinifyEnabled: Boolean = true
    fun checkMinifyEnabled(v: Boolean) {
        checkMinifyEnabled = v
    }

    var checkDependenciesSnapshot: Boolean = true
    fun checkDependenciesSnapshot(v: Boolean) {
        checkDependenciesSnapshot = v
    }

    var snapShotWhiteList: Array<String> = arrayOf()
    fun snapShotWhiteList(v: Array<String>) {
        snapShotWhiteList = v
    }

}