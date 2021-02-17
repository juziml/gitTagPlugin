package com.gt.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitTagPlugin :Plugin<Project> {
    override fun apply(project: Project) {
        //创建拓展函数，拓展函数名称，拓展函数类
        val extensionFun = project
            .extensions.create("grockName", GitTagExtension::class.java)
        project.afterEvaluate {
            project.tasks.forEach {
                val name = it.name
                if(name == extensionFun.taskName){
                    it.doLast {
                        println("crate git tag")
//                Runtime.getRuntime().exec("")
                    }
                }
            }
        }


    }
}