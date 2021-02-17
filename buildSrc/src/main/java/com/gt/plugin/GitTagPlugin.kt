package com.gt.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class GitTagPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        //创建拓展函数，拓展函数名称，拓展函数类
        val extensionFun = project
            .extensions.create("autoPublishTag", GitTagExtension::class.java)
        project.afterEvaluate {
            project.tasks.forEach {
                val name = it.name
                if (name == extensionFun.targetTask) {
                    it.doLast {
                        println("crate git tag name=${extensionFun.tagName} msg=${extensionFun.tagMsg}")
                        val createTag = "git tag ${extensionFun.tagName} -m ${extensionFun.tagMsg}"
                        val pushTag = " git push origin --tag"
//                        val pushTag = " git push origin ${extensionFun.tagName}"//推单个不知道为什么这里不管用，用全推就行
                        println("createTag:$createTag")
                        Runtime.getRuntime().exec(createTag)
                        println("pushTag:$pushTag")
                        Runtime.getRuntime().exec(pushTag)
                    }
                }
            }
        }


    }
}