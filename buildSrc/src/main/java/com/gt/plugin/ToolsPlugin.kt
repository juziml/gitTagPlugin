package com.gt.plugin

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.plugins.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ToolsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        "project.name ${project.name}\"".println()
        val toolsExFun = project
            .extensions.create("zmToolsEx", ToolsExtension::class.java)
//       val libExFun =  project.extensions.findByType(LibraryExtension::class.java) //lib 编译拓展函数，暂时用不到
        val appPlugin: AppPlugin? = project.plugins.findPlugin(AppPlugin::class.java)
        if (null == appPlugin) {
            throw IllegalStateException("can not found 'android' plugin,only support on 'android' building")
        }
        project.afterEvaluate {
            autoCreateAndPushTag(project, toolsExFun)
            checkDependenciesRelease(project, toolsExFun)
//            val t = variant.javaCompileProvider
//            t.dependsOn()
        }
    }

    /**
     * 当前版本检查，dependencies中是否包含SNPASHOT
     */
    private fun checkDependenciesRelease(project: Project, exFun: ToolsExtension) {


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
        "tagAble=${tag.tagAble} tag=${tag.tagName} msg=${tag.tagMsg} followAnyReleaseBuild=${tag.followAnyReleaseBuild}".println()
        if (!tag.tagAble) {
            "GitTagConfig.tagAble = false".println()
            return
        }
        val appModuleExtension = project.extensions.findByType(BaseAppModuleExtension::class.java)!!
        appModuleExtension.applicationVariants.forEach { variant ->
            val debug = variant.buildType.isDebuggable
            if (!debug) {
                if (tag.followAnyReleaseBuild) {
                    variant.assembleProvider.get().doLast {
                        "assembleProvider".println("followAnyReleaseBuild doLast createAndPushTag")
                        GitTagTask.createAndPushTag(tagName = tag.tagName, tagMsg = tag.tagMsg)
                    }
                    //直接安装
                    variant.installProvider.get().doLast {
                        "installProvider".println("followAnyReleaseBuild doLast createAndPushTag")
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
                                "match one task:${it.name} doLast createAndPushTag ---------".println(
                                    "appointTask"
                                )
                            }
                        }
                    } else {
                        "appointTask isNullOrEmpty".println()
                    }
                }
            }
//                variant.preBuildProvider.get().doLast {
//                    "preBuildProvider".println("doLast debug---------")
//                }
//                variant.javaCompileProvider.get().doLast {
//                    "javaCompileProvider".println("doLast debug-----------")
//                }
            //生成apk
        }
    }
}

