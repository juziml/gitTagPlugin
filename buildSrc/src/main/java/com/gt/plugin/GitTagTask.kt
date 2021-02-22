package com.gt.plugin

/**
 *@Desc:
 *-
 *-
 *create by zhusw on 2021/2/22 20:27
 */
object GitTagTask  {
    fun createAndPushTag(tagName:String,tagMsg:String) {
        "GitTagTask.run >>".println()
        val createTag = "git tag $tagName -m $tagMsg"
        Runtime.getRuntime().exec(createTag)
        Thread.sleep(2000)
        val pushTag = "git push origin $tagName"
        Runtime.getRuntime().exec(pushTag)
        "GitTagTask.run END >> $createTag >> $pushTag".println()
    }
}