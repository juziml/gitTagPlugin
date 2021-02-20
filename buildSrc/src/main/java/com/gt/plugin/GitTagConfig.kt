package com.gt.plugin
/**
 *@Desc:
 *-
 *-
 *create by zhusw on 2021/2/20 16:07
 */
open class GitTagConfig {
     var targetTaskName:ArrayList<String> = arrayListOf()

     var tagName: String = ""
     var tagMsg: String = ""

    fun targetTaskName(v:ArrayList<String>){
        targetTaskName = v
    }
    fun tagName(v:String){
        tagName = v
    }
    fun tagMsg(v:String){
        tagMsg = v
    }
}