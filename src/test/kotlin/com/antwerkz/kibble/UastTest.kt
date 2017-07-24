package com.antwerkz.kibble

import org.jetbrains.kotlin.cli.jvm.compiler.CliLightClassGenerationSupport
import org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProvider
import org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.com.intellij.openapi.extensions.Extensions
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager
import org.jetbrains.kotlin.com.intellij.psi.PsiManager
import org.jetbrains.kotlin.resolve.jvm.TopDownAnalyzerFacadeForJVM
import org.jetbrains.uast.UastContext
import org.jetbrains.uast.UastLanguagePlugin
import org.jetbrains.uast.evaluation.UEvaluatorExtension
import org.testng.annotations.Test
import java.io.File

class UastTest {
    companion object {
        val TEST_KOTLIN_MODEL_DIR = File("src/test/resources")
        val FILE_PROTOCOL = "file"
    }

    protected lateinit var project: MockProject

    protected val psiManager: PsiManager by lazy {
        PsiManager.getInstance(project)
    }

    @Test
    fun uast() {
        doTest("generated.kt")
    }

    fun doTest(testName: String) {
        val virtualFile = getVirtualFile(testName)

        val psiFile = psiManager.findFile(virtualFile) ?: error("Can't get psi file for $testName")
        val uFile = uastContext.convertElementWithParent(psiFile, null) ?: error("Can't get UFile for $testName")

//        checkCallback(testName, uFile as UFile)

    }


    fun getVirtualFile(testName: String): VirtualFile {
        val projectDir = TEST_KOTLIN_MODEL_DIR
        val testFile = File(TEST_KOTLIN_MODEL_DIR, testName.substringBefore('/') + ".kt")

        initializeEnvironment(testFile)

        initializeKotlinEnvironment()

        val trace = CliLightClassGenerationSupport.NoScopeRecordCliBindingTrace()

        val kotlinCoreEnvironment = kotlinCoreEnvironment!!

        TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration(
                project,
                kotlinCoreEnvironment.getSourceFiles(),
                trace,
                compilerConfiguration,
                { scope -> JvmPackagePartProvider(kotlinCoreEnvironment, scope) }
        )

        val vfs = VirtualFileManager.getInstance().getFileSystem(FILE_PROTOCOL)

        val ideaProject = project
        ideaProject.baseDir = vfs.findFileByPath(projectDir.canonicalPath)

        return vfs.findFileByPath(testFile.canonicalPath)!!
    }

    protected fun initializeEnvironment(source: File) {
        if (myEnvironment != null) {
            error("Environment is already initialized")
        }
        myEnvironment = createEnvironment(source)
        project = environment.project

        CoreApplicationEnvironment.registerExtensionPoint(
                Extensions.getRootArea(),
                UastLanguagePlugin.extensionPointName,
                UastLanguagePlugin::class.java)

        CoreApplicationEnvironment.registerExtensionPoint(
                Extensions.getRootArea(),
                UEvaluatorExtension.EXTENSION_POINT_NAME,
                UEvaluatorExtension::class.java)

        project.registerService(UastContext::class.java, UastContext::class.java)

        registerUastLanguagePlugins()
    }

    private fun initializeKotlinEnvironment() {
        val area = Extensions.getRootArea()
        area.getExtensionPoint(UastLanguagePlugin.extensionPointName)
                .registerExtension(KotlinUastLanguagePlugin())
        area.getExtensionPoint(UEvaluatorExtension.EXTENSION_POINT_NAME)
                .registerExtension(KotlinEvaluatorExtension())

        project.registerService(
                KotlinUastBindingContextProviderService::class.java,
                CliKotlinUastBindingContextProviderService::class.java)
    }

}