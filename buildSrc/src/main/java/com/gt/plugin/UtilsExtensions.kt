package com.gt.plugin

/**
 *@Desc:
 *-
 *-
 *create by zhusw on 2021/2/22 19:56
 */
const val COMMON_TAG = "ToolsPlugin"
var logable = true
fun String.println(secTag:String=COMMON_TAG){
    if(logable){
        kotlin.io.println("$COMMON_TAG:$secTag : $this")
    }
}
