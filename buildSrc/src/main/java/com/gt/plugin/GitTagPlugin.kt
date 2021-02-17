package com.gt.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitTagPlugin :Plugin<Project> {
    override fun apply(project: Project) {
        //创建拓展函数，拓展函数名称，拓展函数类
        val extensionFun = project
            .extensions.create("grockName", GitTagExtension::class.java)
        project.afterEvaluate {
            println("GitTagPlugin-kotlin afterEvaluate:${extensionFun.name}")
        }
        project.tasks.forEach {
            val name = it.name
            println("task name= $name")
            it.doLast {
                Runtime.getRuntime().exec("")
            }
        }
    }
}