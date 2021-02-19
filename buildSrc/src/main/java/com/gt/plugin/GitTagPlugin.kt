package com.gt.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitTagPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        //创建拓展函数，拓展函数名称，拓展函数类
        val exFun = project
            .extensions.create("autoPublishTag", GitTagExtension::class.java)
        project.afterEvaluate {

            println("extensionFun taskName = ${exFun.targetTask} ")
            val tagHandler = TagHandler(exFun.tag!!)
           val tagConfig =  tagHandler.call()
            println("extensionFun taskName = ${tagConfig.mName} ")
        }
    }
}

