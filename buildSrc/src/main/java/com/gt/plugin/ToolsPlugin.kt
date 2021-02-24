package com.gt.plugin

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.plugins.AppPlugin
import groovy.lang.Closure
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

class ToolsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        "project.name ${project.name}\"".println()
        val toolsExFun = project
            .extensions.create("zmToolsEx", ToolsExtension::class.java)
//       val libExFun =  project.extensions.findByType(LibraryExtension::class.java) //lib 编译拓展函数，暂时用不到
        project.plugins.findPlugin(AppPlugin::class.java)
            ?: throw IllegalStateException("can not found 'android' plugin,only support on 'android' building")
        project.afterEvaluate {
            autoCreateAndPushTag(project, toolsExFun)
            releaseCheck(project, toolsExFun)
        }
    }

    /**
     * 当前版本检查，dependencies中是否包含SNPASHOT
     */
    private fun releaseCheck(project: Project, exFun: ToolsExtension) {
        val check = ToolsClosureHandler.build( exFun.releaseCheck, ReleaseCheckConfig::class.java)
        "check deps-snapshot;${check.checkDependenciesSnapshot} check minifyEnable=${check.checkMinifyEnabled}".println(
            "---------releaseCheck config:"
        )
        val appModuleExtension = project.extensions.findByType(BaseAppModuleExtension::class.java)!!
        appModuleExtension.applicationVariants.forEach { variant ->
            val debug = variant.buildType.isDebuggable
            if (!debug) {
                if(check.checkMinifyEnabled){
                   if(!variant.buildType.isMinifyEnabled){
                       throw IllegalArgumentException(
                           "minifyEnabled is not allowed false when buildRelease,set true try again")
                   }
                }
                if (check.checkDependenciesSnapshot) {
                    val strBuilder = StringBuilder()
                    val whiteList = check.snapShotWhiteList
                    project.dependencies.components.all { rule ->
                        val depFull = rule.toString()
                        if (depFull.contains(":")) {
                            val group_name = depFull.substring(0, depFull.lastIndexOf(":"))
                            if (!whiteList.contains(group_name)) {
                                if (depFull.contains("SNAPSHOT",ignoreCase = true)) {
                                    strBuilder.append(rule.toString() + "\n")
                                }
                            }
                        }
                    }
                    variant.javaCompileProvider.get().doFirst {
                        "$strBuilder".println("snapshot libs:")
                        if (strBuilder.toString().isNotEmpty()) {
                            throw IllegalArgumentException(
                                "snapshot deps is not allowed when buildRelease" +
                                        ":${strBuilder.toString()}"
                            )
                        }
                    }
                }
                return@releaseCheck
            }

        }

    }

    /**
     * 创建tag与push tag 操作将在所有发布人最后执行，
     * 要确认当前打包环境区分debug与release，所以选用依赖于
     * {BaseAppModuleExtension.applicationVariants}来读取打包环境
     * 选择安装任务{installProvider}、生成apk任务{assembleProvider}执行完执行tag相关操作，
     * 这非常满足对「最后」可靠性的要求
     * 当选择指定多个任务锚定tag操作时，避免指定有依赖关系的任务，否则将多次创建tag{虽然创建同名tag会失败}
     */
    private fun autoCreateAndPushTag(project: Project, exFun: ToolsExtension) {
        val tag = ToolsClosureHandler.build(exFun.tag, GitTagConfig::class.java)
        ("tagAble=${tag.tagAble} tag=${tag.tagName} msg=${tag.tagMsg} " +
                "followAnyReleaseBuild=${tag.followAnyReleaseBuild}").println("---------git tag config:")
        if (!tag.tagAble) {
            return
        }
        val appModuleExtension = project.extensions.findByType(BaseAppModuleExtension::class.java)!!
        appModuleExtension.applicationVariants.forEach { variant ->
            val debug = variant.buildType.isDebuggable
            if (!debug) {
                if (tag.followAnyReleaseBuild) {
                    //打包apk
                    variant.assembleProvider.get().doLast {
                        GitTagTask.createAndPushTag(tagName = tag.tagName, tagMsg = tag.tagMsg)
                    }
                    //直接安装
                    variant.installProvider.get().doLast {
                        GitTagTask.createAndPushTag(tagName = tag.tagName, tagMsg = tag.tagMsg)
                    }
                } else {
                    if (!tag.appointTask.isNullOrEmpty()) {
                        project.tasks.forEach {
                            if (tag.appointTask.contains(it.name)) {
                                it.doLast {
                                    GitTagTask.createAndPushTag(
                                        tagName = tag.tagName,
                                        tagMsg = tag.tagMsg
                                    )
                                }
                            }
                        }
                    }
                }
                return@autoCreateAndPushTag //只对一类release生效即可
            }
        }
    }
}

