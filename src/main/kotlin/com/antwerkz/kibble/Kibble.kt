package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleType
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.analyzer.ModuleContent
import org.jetbrains.kotlin.analyzer.ModuleInfo
import org.jetbrains.kotlin.analyzer.ResolverForModule
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_FULL_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProvider
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import org.jetbrains.kotlin.container.getService
import org.jetbrains.kotlin.context.ProjectContext
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.idea.project.ResolveElementCache
import org.jetbrains.kotlin.idea.resolve.ResolutionFacade
import org.jetbrains.kotlin.load.java.structure.impl.JavaClassImpl
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.platform.JvmBuiltIns
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.CompilerEnvironment
import org.jetbrains.kotlin.resolve.MultiTargetPlatform
import org.jetbrains.kotlin.resolve.jvm.JvmAnalyzerFacade
import org.jetbrains.kotlin.resolve.jvm.JvmPlatformParameters
import org.jetbrains.kotlin.resolve.jvm.TopDownAnalyzerFacadeForJVM
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode
import org.jetbrains.kotlin.resolve.lazy.ResolveSession
import java.io.File


/**
 * This is the primary entry point for parsing existing Kotlin code
 */
object Kibble {

    var sources = mutableListOf<KtFile>()
//    val compilerConfiguration = CompilerConfiguration().apply {
//        put(CompilerConfigurationKey.create<File>("output directory"), File(""))
//        put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
//                PrintingMessageCollector(System.err, PLAIN_FULL_PATHS, false))
//    }
//    val coreEnvironment = KotlinCoreEnvironment.createForProduction(Disposable { }, compilerConfiguration, listOf())

    /**
     * Parses source found at the given path.
     *
     * @return the KibbleFile
     */
    fun parseFile(file: String): KibbleFile {
        return parse(File(file))[0]
    }

    /**
     * Parses source found at the given path.
     *
     * @return the KibbleFile
     */
    fun parseFile(file: File): KibbleFile {
        return parse(file.absoluteFile)[0]
    }

    /**
     * Parses sources found at the given path. This String can represent a source file or a directory to scan for sources
     *
     * @return the list of KibbleFiles from sources found at the given path
     */
    fun parse(path: File): List<KibbleFile> {
        val configuration = CompilerConfiguration()
        configuration.put(CompilerConfigurationKey.create<File>("output directory"), File(""))
        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, PrintingMessageCollector(System.err, PLAIN_FULL_PATHS, false))
        configuration.addKotlinSourceRoot(path.absolutePath)

        val createForProduction = createKibbleEnvironment(Disposable { }, configuration, listOf())
//        createForProduction.project.picoContainer.
        val sourceFiles: List<KtFile> = createForProduction
                .getSourceFiles()
        sources.addAll(sourceFiles)
        return sourceFiles
                .map(::KibbleFile)
    }

    fun createKibbleEnvironment(parentDisposable: Disposable, configuration: CompilerConfiguration,
                                configFilePaths: List<String>): KotlinCoreEnvironment {
//        val appEnv: JavaCoreApplicationEnvironment = getOrCreateApplicationEnvironmentForProduction(configuration, configFilePaths)
//
//        return KotlinCoreEnvironment(parentDisposable, appEnv, configuration)
        val env = KotlinCoreEnvironment.createForProduction(Disposable { }, configuration, listOf())
//        env.project.
        val picoContainer = env.application.getPicoContainer()
        picoContainer.registerComponentInstance(ResolveElementCache::class.java)
        return env
    }

    /**
     * Parses a code snippet in to a Kibble model
     *
     * @return a KibbleFile holding the results of the parsed snippet
     */
    fun parseSource(source: String): KibbleFile {
        val tempFile = File.createTempFile("kibble-", ".kt")
        tempFile.deleteOnExit()

        try {
            tempFile.writeText(source)
            return parse(tempFile.absoluteFile)[0]
        } finally {
            tempFile.delete()
        }
    }

//    val environment = AnalysisEnvironment(object : MessageCollector {
//        override fun clear() {
//        }
//
//        override fun report(severity: CompilerMessageSeverity, message: String, location: CompilerMessageLocation) {
//            if (severity != CompilerMessageSeverity.EXCEPTION) {
//                println("$severity: $message at $location")
//            } else {
//                throw Exception("$severity: $message at $location")
//            }
//        }
//
//        override fun hasErrors() = false
//    })

    fun createResolutionFacade(environment: KotlinCoreEnvironment, sourceFiles: List<KtFile>): KibbleResolutionFacade {

        val projectContext = ProjectContext(environment.project)


        val library = object : ModuleInfo {
            override val name: Name = Name.special("<library>")
            override fun dependencies(): List<ModuleInfo> = listOf(this)
        }
        val module = object : ModuleInfo {
            override val name: Name = Name.special("<module>")
            override fun dependencies(): List<ModuleInfo> = listOf(this, library)
        }
        val sourcesScope = createSourceModuleSearchScope(environment.project, sourceFiles)

        val builtIns = JvmBuiltIns(projectContext.storageManager)
        val resolverForProject = JvmAnalyzerFacade.setupResolverForProject(
                "KIbble",
                projectContext,
                listOf(library, module),
                {
                    when (it) {
                        library -> ModuleContent(emptyList(), GlobalSearchScope.notScope(sourcesScope))
                        module -> ModuleContent(sourceFiles, sourcesScope)
                        else -> throw IllegalArgumentException("Unexpected module info")
                    }
                },
                JvmPlatformParameters {
                    val file = (it as JavaClassImpl).psi.containingFile.virtualFile
                    if (file in sourcesScope)
                        module
                    else
                        library
                },
                CompilerEnvironment,
                packagePartProviderFactory = {
                    info, content ->
                    JvmPackagePartProvider(environment, content.moduleContentScope)
                },
                builtIns = { builtIns },
                modulePlatforms = { MultiTargetPlatform.Specific("JVM") }
        )

        resolverForProject.resolverForModule(library) // Required before module to initialize library properly
        val resolverForModule = resolverForProject.resolverForModule(module)
        val moduleDescriptor = resolverForProject.descriptorForModule(module)
        builtIns.initialize(moduleDescriptor, true)
        return KibbleResolutionFacade(environment.project, moduleDescriptor, resolverForModule)
    }

    fun createSourceModuleSearchScope(project: Project, sourceFiles: List<KtFile>): GlobalSearchScope {
        return TopDownAnalyzerFacadeForJVM.newModuleSearchScope(project, sourceFiles)
    }

    fun inferType(expression: KtElement): KibbleType? {
//        val analyze1 = expression.analyze()
        val configuration = CompilerConfiguration()
        configuration.put(CompilerConfigurationKey.create<File>("output directory"), File(""))
        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, PrintingMessageCollector(System.err, PLAIN_FULL_PATHS, false))

        val environment = KotlinCoreEnvironment
                .createForProduction(Disposable { }, configuration, listOf())
        val facade = createResolutionFacade(environment, sources)
        val analyze = facade.analyze(expression)
        return null
    }

}

class KibbleResolutionFacade(override val project: Project,
                             override val moduleDescriptor: ModuleDescriptor,
                             val resolverForModule: ResolverForModule): ResolutionFacade {
    override fun resolveToDescriptor(declaration: KtDeclaration, bodyResolveMode: BodyResolveMode): DeclarationDescriptor {
        return resolveSession.resolveToDescriptor(declaration)
    }

    override fun analyze(elements: Collection<KtElement>, bodyResolveMode: BodyResolveMode): BindingContext {
          if (elements.isEmpty()) return BindingContext.EMPTY
          val resolveElementCache = getFrontendService(elements.first(), ResolveElementCache::class.java)
          return resolveElementCache.resolveToElements(elements, bodyResolveMode)
      }
    val resolveSession: ResolveSession get() = getFrontendService(ResolveSession::class.java)

    override fun analyze(element: KtElement, bodyResolveMode: BodyResolveMode): BindingContext {
        return analyze(listOf(element), bodyResolveMode)
    }

    override fun analyzeFullyAndGetResult(elements: Collection<KtElement>): AnalysisResult {
        throw UnsupportedOperationException()
    }

    override fun <T : Any> getFrontendService(element: PsiElement, serviceClass: Class<T>): T {
        return project.getComponent(serviceClass)
    }

    override fun <T : Any> getFrontendService(serviceClass: Class<T>): T {
        return resolverForModule.componentProvider.getService(serviceClass)
    }

    override fun <T : Any> getFrontendService(moduleDescriptor: ModuleDescriptor, serviceClass: Class<T>): T {
        return resolverForModule.componentProvider.getService(serviceClass)
    }

    override fun <T : Any> getIdeService(serviceClass: Class<T>): T {
        throw UnsupportedOperationException()
    }

}




