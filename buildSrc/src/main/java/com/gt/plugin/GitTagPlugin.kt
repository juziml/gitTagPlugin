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
                        val  createTag = "git tag ${extensionFun.tagName} -m ${extensionFun.tagMsg}"
                        println("createTag:$createTag")
                        Runtime.getRuntime().exec(createTag)
                    }
                    it.doLast {
                        println("createTag:sleep"+System.currentTimeMillis())
                        Thread.sleep(2000)
                        println("createTag:sleep"+System.currentTimeMillis())
                    }
                    //需要先后执行的命令，不能放在一个节点里，命令执行似乎是异步并发的
                    it.doLast {
                        val pushTag = "git push origin ${extensionFun.tagName}"
                        println("pushTag:$pushTag")
                        Runtime.getRuntime().exec(pushTag)
                    }
                }
            }
        }
    }
}

