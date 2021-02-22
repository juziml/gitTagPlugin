package com.gt.plugin

/**
 *@Desc:
 *-
 *-
 *create by zhusw on 2021/2/22 19:56
 */
const val COMMON_TAG = "ToolsPlugin"
fun String.println(secTag:String=COMMON_TAG){
    kotlin.io.println("$COMMON_TAG:$secTag : $this")
}
