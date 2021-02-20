package com.gt.plugin
import com.android.build.gradle.internal.plugins.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.lang.IllegalArgumentException

class ToolsPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val exFun = project
            .extensions.create("zmToolsEx", ToolsExtension::class.java)
        println( "befor :task szie=${project.tasks.size}")
        project.afterEvaluate {
            val gitTagConfig = ToolsClosureHandler.build(exFun.tag,GitTagConfig::class.java)
            println( "tagConfig name = ${gitTagConfig.tagName} size=${gitTagConfig.targetTaskName.size}" )
            handlerGitTag(project)
            println("project class ${project.javaClass}")
           val appPlugin: AppPlugin = project.plugins.findPlugin(AppPlugin::class.java)!!
                println("project class ${appPlugin!!::class.java}")
//            val hasApp = project.plugins.withType(AppPlugin::class.java)
            appPlugin.variantInputModel.buildTypes.forEach {
                println("project buildTypes key=${it.key}")
            }
           val versionName = appPlugin.variantInputModel.defaultConfigData.defaultConfig.versionName!!
            println("project defaultConfig versionName=${versionName}")

        }
    }
    private fun handlerGitTag(project: Project){

    }
}

