package com.antwerkz.kibble

import com.antwerkz.kibble.Kibble.addAutoImport
import com.antwerkz.kibble.Kibble.autoimport
import com.antwerkz.kibble.model.Accessor
import com.antwerkz.kibble.model.CallBlock.SuperCallBlock
import com.antwerkz.kibble.model.CallBlock.ThisCallBlock
import com.antwerkz.kibble.model.ClassType
import com.antwerkz.kibble.model.ClassType.ENUM
import com.antwerkz.kibble.model.ClassType.INTERFACE
import com.antwerkz.kibble.model.Delegate
import com.antwerkz.kibble.model.Import
import com.antwerkz.kibble.model.Mutator
import com.antwerkz.kibble.model.SuperCall
import com.antwerkz.kibble.model.SuperType
import com.antwerkz.kibble.model.TypeProjection
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec.UseSiteTarget
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.VARARG
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.PropertySpec.Builder
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken
import org.jetbrains.kotlin.lexer.KtSingleValueToken
import org.jetbrains.kotlin.psi.KtAnnotatedExpression
import org.jetbrains.kotlin.psi.KtAnnotation
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget
import org.jetbrains.kotlin.psi.KtAnonymousInitializer
import org.jetbrains.kotlin.psi.KtArrayAccessExpression
import org.jetbrains.kotlin.psi.KtBinaryExpression
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtBlockStringTemplateEntry
import org.jetbrains.kotlin.psi.KtBreakExpression
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression
import org.jetbrains.kotlin.psi.KtCatchClause
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtClassBody
import org.jetbrains.kotlin.psi.KtClassInitializer
import org.jetbrains.kotlin.psi.KtClassLiteralExpression
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression
import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression
import org.jetbrains.kotlin.psi.KtContinueExpression
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry
import org.jetbrains.kotlin.psi.KtDoWhileExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtDoubleColonExpression
import org.jetbrains.kotlin.psi.KtDynamicType
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtEnumEntry
import org.jetbrains.kotlin.psi.KtEscapeStringTemplateEntry
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtExpressionWithLabel
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFileAnnotationList
import org.jetbrains.kotlin.psi.KtFinallySection
import org.jetbrains.kotlin.psi.KtForExpression
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtImportAlias
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtImportList
import org.jetbrains.kotlin.psi.KtInitializerList
import org.jetbrains.kotlin.psi.KtIsExpression
import org.jetbrains.kotlin.psi.KtLabeledExpression
import org.jetbrains.kotlin.psi.KtLambdaExpression
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry
import org.jetbrains.kotlin.psi.KtLoopExpression
import org.jetbrains.kotlin.psi.KtModifierList
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression
import org.jetbrains.kotlin.psi.KtPackageDirective
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtParameterList
import org.jetbrains.kotlin.psi.KtParenthesizedExpression
import org.jetbrains.kotlin.psi.KtPostfixExpression
import org.jetbrains.kotlin.psi.KtPrefixExpression
import org.jetbrains.kotlin.psi.KtPrimaryConstructor
import org.jetbrains.kotlin.psi.KtProjectionKind.IN
import org.jetbrains.kotlin.psi.KtProjectionKind.OUT
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtPropertyAccessor
import org.jetbrains.kotlin.psi.KtPropertyDelegate
import org.jetbrains.kotlin.psi.KtQualifiedExpression
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.psi.KtReturnExpression
import org.jetbrains.kotlin.psi.KtSafeQualifiedExpression
import org.jetbrains.kotlin.psi.KtScript
import org.jetbrains.kotlin.psi.KtScriptInitializer
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.KtSelfType
import org.jetbrains.kotlin.psi.KtSimpleNameExpression
import org.jetbrains.kotlin.psi.KtSimpleNameStringTemplateEntry
import org.jetbrains.kotlin.psi.KtStringTemplateEntry
import org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression
import org.jetbrains.kotlin.psi.KtStringTemplateExpression
import org.jetbrains.kotlin.psi.KtSuperExpression
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeEntry
import org.jetbrains.kotlin.psi.KtSuperTypeList
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry
import org.jetbrains.kotlin.psi.KtThisExpression
import org.jetbrains.kotlin.psi.KtThrowExpression
import org.jetbrains.kotlin.psi.KtTryExpression
import org.jetbrains.kotlin.psi.KtTypeAlias
import org.jetbrains.kotlin.psi.KtTypeArgumentList
import org.jetbrains.kotlin.psi.KtTypeConstraint
import org.jetbrains.kotlin.psi.KtTypeConstraintList
import org.jetbrains.kotlin.psi.KtTypeElement
import org.jetbrains.kotlin.psi.KtTypeParameter
import org.jetbrains.kotlin.psi.KtTypeParameterList
import org.jetbrains.kotlin.psi.KtTypeProjection
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUnaryExpression
import org.jetbrains.kotlin.psi.KtUserType
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.KtVisitorVoid
import org.jetbrains.kotlin.psi.KtWhenConditionInRange
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression
import org.jetbrains.kotlin.psi.KtWhenEntry
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.psi.KtWhileExpression
import org.jetbrains.kotlin.psi.psiUtil.PsiChildRange
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.types.Variance
import org.slf4j.LoggerFactory
import java.util.Locale

@Suppress("unused", "MemberVisibilityCanBePrivate")
internal class KibbleVisitor(private val context: KibbleContext) : KtVisitorVoid() {
    companion object {
        private val LOG = LoggerFactory.getLogger(KibbleVisitor::class.java)

        object None : Any()

        init {
            addAutoImport("Any", "kotlin.Any")
            addAutoImport("Array", "kotlin.Array")
            addAutoImport("Boolean", "kotlin.Boolean")
            addAutoImport("Byte", "kotlin.Byte")
            addAutoImport("Char", "kotlin.Char")
            addAutoImport("Double", "kotlin.Double")
            addAutoImport("Float", "kotlin.Float")
            addAutoImport("Int", "kotlin.Int")
            addAutoImport("Long", "kotlin.Long")
            addAutoImport("Short", "kotlin.Short")
            addAutoImport("String", "kotlin.String")
            addAutoImport("Unit", "kotlin.Unit")
            addAutoImport("Nothing", "kotlin.Nothing")
            addAutoImport("List", "kotlin.collections.List")
            addAutoImport("Map", "kotlin.collections.Map")
            addAutoImport("Set", "kotlin.collections.Set")
            addAutoImport("MutableList", "kotlin.collections.MutableList")
            addAutoImport("MutableMap", "kotlin.collections.MutableMap")
            addAutoImport("MutableSet", "kotlin.collections.MutableSet")
            addAutoImport("Iterable", "kotlin.collections.Iterable")
            addAutoImport("Iterator", "kotlin.collections.Iterator")
            addAutoImport("Sequence", "kotlin.sequences.Sequence")
        }
    }

    private var pkgName = ""
    private val imports = mutableMapOf<String, ClassName>()
    override fun visitKtFile(kt: KtFile) {
        context.defaultPackageName = kt.packageDirective?.fqName?.asString() ?: ""
        val builder = FileSpec.builder(context.defaultPackageName, kt.name)
        context.bookmark("visitKtFile: ${kt.name}")

        kt.allChildren.forEach { it.accept(this) }

        context.popToBookmark().forEach {
            when (it) {
                is TypeSpec -> builder.addType(it)
                is FunSpec -> builder.addFunction(it)
                is PropertySpec -> builder.addProperty(it)
                is AnnotationSpec -> builder.addAnnotation(it)
                is FileSpec.Builder -> {
                } // ignore
                is Import -> {
                    if (it.alias != null) {
                        builder.addAliasedImport(ClassName(it.packageName, it.shortName), it.alias)
                    } else {
                        builder.addImport(it.packageName, it.shortName)
                    }
                }
                else -> unknownType(it)
            }
        }
        context.register(builder.build())
    }

    override fun visitImportDirective(directive: KtImportDirective) {
        val fqName = directive.importedFqName!!
        val alias = directive.aliasName

        registerImport(fqName.parent().asString(), fqName.shortName().asString(), alias)
        context.push(Import(fqName.parent().asString(), fqName.shortName().asString(), alias))
    }

    private fun registerImport(packageName: String, className: String, alias: String?) {
        imports[alias ?: className] = ClassName(packageName, className)
    }

    override fun visitKtElement(element: KtElement) {
        when (element) {
            is KtAnnotationUseSiteTarget -> visitAnnotationUseSiteTarget(element)
            is KtFileAnnotationList -> visitKtFileAnnotationList(element)
            else -> unknownType(element)
        }
    }

    override fun visitDeclaration(dcl: KtDeclaration) {
        return when (dcl) {
            is KtClass -> visitClass(dcl)
            is KtObjectDeclaration -> visitObjectDeclaration(dcl)
            is KtNamedFunction -> visitNamedFunction(dcl)
            is KtProperty -> visitProperty(dcl)
            is KtTypeParameter -> visitTypeParameter(dcl)
            else -> unknownType(dcl)
        }
    }

    override fun visitClass(kt: KtClass) {
        val name = kt.name
        val (builder, kind) = classBuilder(kt, name)
        var primarySet = false
        acceptChildren("visitClass", kt, builder) {
            when (it) {
                is FunSpec -> {
                    if (it.isConstructor && !primarySet) {
                        primaryConstructor(it)
                        primarySet = true
                    } else {
                        addFunction(it)
                    }
                }
                is KModifier -> addModifiers(it)
                is PropertySpec -> addProperty(it)
                is TypeName -> addSuperinterface(it)
                is AnnotationSpec -> addAnnotation(it)
                is SuperCall -> {
                    superclass(it.type)
                    it.arguments.forEach { arg -> addSuperclassConstructorParameter(arg) }
                }
                is TypeSpec -> {
                    when (kind) {
                        ENUM -> addEnumConstant(it.name ?: "", it)
                        else -> addType(it)
                    }
                }
                is CodeBlock -> addInitializerBlock(it)
                is SuperType -> if (it.delegate != null) addSuperinterface(it.type, it.delegate!!) else addSuperinterface(it.type)
                is TypeConstraint -> addTypeVariable(it.type)
                else -> unknownType(it)
            }
        }

        context.push(builder.build())
    }

    private fun classBuilder(kt: KtClass, name: String?): Pair<TypeSpec.Builder, ClassType> {
        val kind: ClassType
        val builder = when {
            kt.isInterface() -> {
                kind = INTERFACE
                TypeSpec.interfaceBuilder(name!!)
            }
            kt.isEnum() -> {
                kind = ENUM
                TypeSpec.enumBuilder(name!!)
            }
            kt.isAnnotation() -> {
                kind = ClassType.ANNOTATION
                TypeSpec.annotationBuilder(name!!)
            }
            name == null -> {
                kind = ClassType.CLASS
                TypeSpec.anonymousClassBuilder()
            }
            !kt.isInterface() && !kt.isEnum() -> {
                kind = ClassType.CLASS
                TypeSpec.classBuilder(name)
            }
            else -> unknownType(kt)
        }
        return Pair(builder, kind)
    }

    override fun visitObjectDeclaration(declaration: KtObjectDeclaration) {
        val builder = acceptChildren(
            "visitObjectDeclaration", declaration,
            TypeSpec.objectBuilder(declaration.name ?: "")
        ) {
            when (it) {
                is FunSpec -> addFunction(it)
                is KModifier -> addModifiers(it)
                is PropertySpec -> addProperty(it)
                is TypeName -> addSuperinterface(it)
                is AnnotationSpec -> addAnnotation(it)
                is SuperCall -> {
                    superclass(it.type)
                    it.arguments.forEach { arg -> addSuperclassConstructorParameter(arg) }
                }
                is TypeSpec -> addType(it)
                is CodeBlock -> addInitializerBlock(it)
                else -> unknownType(it)
            }
        }

        context.push(builder.build())
    }

    override fun visitClassOrObject(classOrObject: KtClassOrObject) {
        this.visitNamedDeclaration(classOrObject)
    }

    override fun visitSecondaryConstructor(secondary: KtSecondaryConstructor) {
        val builder = FunSpec.constructorBuilder()
        acceptChildren("visitSecondaryConstructor", secondary, builder) {
            when (it) {
                is CodeBlock -> builder.addCode(it)
                is KModifier -> builder.addModifiers(it)
                is ParameterSpec -> builder.addParameter(it)
                is AnnotationSpec -> builder.addAnnotation(it)
                is ThisCallBlock -> builder.callThisConstructor(*it.arguments.toTypedArray())
                is SuperCallBlock -> builder.callSuperConstructor(*it.arguments.toTypedArray())
                else -> unknownType(it)
            }
        }
        context.push(builder.build())
    }

    override fun visitPrimaryConstructor(primary: KtPrimaryConstructor) {
        context.bookmark("visitPrimaryConstructor")
        primary.valueParameters.forEach { it.accept(this) }
        primary.modifierList?.acceptChildren(this)
        val values = context.popToBookmark()
        val builder = FunSpec.constructorBuilder()
        processChildren(builder, values) {
            when (it) {
                is ParameterSpec -> builder.addParameter(it)
                is PropertySpec -> context.push(it)
                is AnnotationSpec -> builder.addAnnotation(it)
                else -> unknownType(it)
            }
        }

        context.push(builder.build())
    }

    private fun unknownType(it: Any): Nothing {
        val text = if (it is KtElement) it.text else it.toString()
        TODO("unknown type: ${it.javaClass}\n --> $text")
    }

    override fun visitNamedFunction(kt: KtNamedFunction) {
        val builder = FunSpec.builder(kt.name ?: missing("function name is missing: ${kt.text}"))
        acceptChildren("visitNamedFunction", kt, builder) {
            when (it) {
                is CodeBlock -> addCode(it)
                is KModifier -> addModifiers(it)
                is ParameterSpec -> addParameter(it)
                is AnnotationSpec -> addAnnotation(it)
                is TypeVariableName -> addTypeVariable(it)
                is TypeConstraint -> addTypeVariable(it.type)
                is TypeName -> returns(it)
                else -> unknownType(it)
            }
        }

        context.push(builder.build())
    }

    internal fun <T : Any> acceptChildren(bookmark: String, element: PsiElement, t: T, func: T.(it: Any) -> Unit): T {
        context.bookmark(bookmark)
        element.allChildren.accept(this)
        processChildren(t, context.popToBookmark(), func)

        return t
    }

    internal fun <T> processChildren(t: T, list: List<Any>, func: T.(it: Any) -> Unit) {
        try {
            list.forEach {
                t.func(it)
            }
        } catch (e: NotImplementedError) {
            LOG.error(" ===>  ${context.stack[0]}")
            LOG.error(" ===>  $list")
            throw e
        }
    }

    private fun <T> KtElement.evaluate(visitor: KibbleVisitor): T {
        this.accept(visitor)
        return visitor.context.pop()
    }

    private fun <T> PsiElement.evaluate(visitor: KibbleVisitor): T {
        this.accept(visitor)
        return visitor.context.pop()
    }

    private fun <T> List<KtElement>.evaluate(visitor: KibbleVisitor): List<T> {
        return map { it.evaluate<T>(visitor) }
    }

    override fun visitProperty(property: KtProperty) {
        val type = property.typeReference?.evaluate<TypeName>(this) ?: ClassName("", "")
        // ?: missing("properties must have types: ${property.text  }")
        val name = property.name ?: missing("properties must be named")
        val builder = PropertySpec.builder(name, type)
        builder.mutable(property.isVar)
        buildProperty("visitProperty", property, builder)
        context.push(builder.build())
    }

    private fun buildProperty(bookmark: String, property: PsiElement, builder: Builder) {
        acceptChildren(bookmark, property, builder) {
            when (it) {
                is CodeBlock -> initializer(it)
                is Delegate -> delegate(it.delegation)
                is AnnotationSpec -> addAnnotation(it)
                is KModifier -> addModifiers(it)
                is TypeName -> {
                } /*  already handled */
                is Accessor -> getter(it.function)
                is Mutator -> setter(it.function)
                else -> unknownType(it)
            }
        }
        builder.modifiers.remove(VARARG)
    }

    private fun missing(message: String): Nothing {
        throw Exception(message)
    }

    override fun visitDestructuringDeclaration(multiDeclaration: KtDestructuringDeclaration) {
        this.visitDeclaration(multiDeclaration)
    }

    override fun visitDestructuringDeclarationEntry(multiDeclarationEntry: KtDestructuringDeclarationEntry) {
        this.visitNamedDeclaration(multiDeclarationEntry)
    }

    override fun visitTypeAlias(typeAlias: KtTypeAlias) {
        this.visitNamedDeclaration(typeAlias)
    }

    override fun visitScript(script: KtScript) {
        this.visitDeclaration(script)
    }

    override fun visitImportAlias(importAlias: KtImportAlias) {
        this.visitKtElement(importAlias)
    }

    override fun visitImportList(importList: KtImportList) {
        importList.imports
            .map { it.accept(this) }
            .toList()
    }

    fun visitFileAnnotationList(fileAnnotationList: KtFileAnnotationList) {
        this.visitKtElement(fileAnnotationList)
    }

    override fun visitClassBody(classBody: KtClassBody) {
        classBody.acceptChildren(this)
/*
        acceptChildren("visitClassBody", classBody, None) {
            when (it) {
                else -> unknownType(it)
            }
        }
*/
    }

    override fun visitModifierList(list: KtModifierList) {
        list.allChildren.forEach {
            when (it) {
                is LeafPsiElement -> if (it.elementType is KtModifierKeywordToken) {
                    context.push(it.toKModifier())
                }
                else -> it.accept(this)
            }
        }
    }

    override fun visitAnnotation(annotation: KtAnnotation) {
//        val annType = annotation.typeReference?.evaluate<ClassName>(this)!!
        var useSiteTarget: UseSiteTarget? = null
        acceptChildren("visitAnnotation", annotation, None) {
            when (it) {
                is UseSiteTarget -> useSiteTarget = it
                is AnnotationSpec -> {
                    context.push(
                        it.toBuilder().useSiteTarget(useSiteTarget)
                            .build()
                    )
                }
                else -> unknownType(it)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun visitAnnotationEntry(annotationEntry: KtAnnotationEntry) {
        val annType = annotationEntry.typeReference?.evaluate<ClassName>(this)!!
        val builder = AnnotationSpec.builder(annType)
        context.bookmark("visitAnnotationEntry: $annType")
        annotationEntry.valueArgumentList?.accept(this)
        val arguments = context.popToBookmark()
        arguments.forEach {
            it as Pair<String, String>
            builder.addMember(it.first, it.second)
        }
        context.push(builder.build())
    }

    fun visitAnnotationUseSiteTarget(annotationTarget: KtAnnotationUseSiteTarget) {
        context.push(UseSiteTarget.valueOf(annotationTarget.getAnnotationUseSiteTarget().name))
    }

    fun visitKtFileAnnotationList(annotationTarget: KtFileAnnotationList) {
        annotationTarget.allChildren.accept(this)
    }

    override fun visitConstructorCalleeExpression(kt: KtConstructorCalleeExpression) {
        val superCall = SuperCall()
        acceptChildren("visitConstructorCalleeExpression", kt, superCall) {
            type = when (it) {
                is ClassName -> it
                is ParameterizedTypeName -> it
                else -> unknownType(it)
            }
        }
        context.push(superCall)
    }

    override fun visitTypeParameterList(list: KtTypeParameterList) {
        list.acceptChildren(this)
    }

    override fun visitTypeParameter(parameter: KtTypeParameter) {
        val bounds = acceptChildren<TypeName>("type parameter bounds", parameter.extendsBound)
        val value = parameter.name ?: ""
        val variance = when (parameter.variance) {
            Variance.INVARIANT -> null
            Variance.IN_VARIANCE -> KModifier.IN
            Variance.OUT_VARIANCE -> KModifier.OUT
        }

        context.push(TypeVariableName(value, variance).copy(bounds = bounds))
    }

    override fun visitEnumEntry(enumEntry: KtEnumEntry) {
        this.visitClass(enumEntry)
    }

    override fun visitParameterList(list: KtParameterList) {
        list.parameters.map { it.accept(this) }
    }

    override fun visitParameter(parameter: KtParameter) {
        val name = parameter.name ?: ""
        visitTypeReference(parameter.typeReference!!)
        val type = context.pop<TypeName>()
        val builder = ParameterSpec.builder(name, type)
        acceptChildren("visitParameter accept", parameter, builder) {
            when (it) {
                is TypeName -> { } // handled
                is KModifier -> addModifiers(it)
                is CodeBlock -> defaultValue(it)
                is AnnotationSpec -> addAnnotation(it)
                else -> unknownType(it)
            }
        }
        if (parameter.hasValOrVar()) {
            val propBuilder = PropertySpec.builder(name, type)
            buildProperty("visitParameter", parameter, propBuilder)
            propBuilder.mutable(parameter.isMutable)
            context.push(propBuilder.build())
        }
        context.push(builder.build())
    }

    override fun visitSuperTypeList(list: KtSuperTypeList) {
        list.entries.forEach { it.accept(this) }
    }

    override fun visitSuperTypeListEntry(specifier: KtSuperTypeListEntry) {
        acceptChildren("visitSuperTypeListEntry", specifier, SuperType()) {
            when (it) {
                is CodeBlock -> delegate = it
                is ParameterizedTypeName -> type = it
                else -> unknownType(it)
            }
        }
    }

    override fun visitDelegatedSuperTypeEntry(specifier: KtDelegatedSuperTypeEntry) {
        this.visitSuperTypeListEntry(specifier)
    }

    fun Pair<*, *>.toCodeBlock(): CodeBlock {
        return when (first) {
            "" -> second as CodeBlock
            else -> CodeBlock.of(first as String, second)
        }
    }

    override fun visitSuperTypeCallEntry(call: KtSuperTypeCallEntry) {
        val superCall = SuperCall()
        acceptChildren("visitSuperTypeCallEntry", call, superCall) {
            when (it) {
                is Pair<*, *> -> arguments += it.toCodeBlock()
                is SuperCall -> type = it.type
                else -> unknownType(it)
            }
        }
        context.push(superCall)
    }

    override fun visitSuperTypeEntry(specifier: KtSuperTypeEntry) {
        specifier.typeReference?.evaluate<TypeName>(this)?.let {
            context.push(it)
        }
    }

    override fun visitConstructorDelegationCall(call: KtConstructorDelegationCall) {
        val callBlock = if (call.isCallToThis) ThisCallBlock() else SuperCallBlock()
        context.bookmark("visitConstructorDelegationCall")
        call.allChildren.accept(this)
        val list = context.popToBookmark()
        list.forEach {
            when (it) {
                is Pair<*, *> -> callBlock.arguments += it.toCodeBlock()
                else -> unknownType(it)
            }
        }
        context.push(callBlock)
    }

    override fun visitPropertyDelegate(delegate: KtPropertyDelegate) {
        acceptChildren("visitPropertyDelegate", delegate, None) {
            when (it) {
                is CodeBlock -> context.push(Delegate(it))
                else -> unknownType(it)
            }
        }
    }

    override fun visitTypeReference(typeReference: KtTypeReference) {
        typeReference.acceptChildren(this)
        // TODO:  reexamine this call
    }

    override fun visitValueArgumentList(list: KtValueArgumentList) {
        list.arguments.forEach { it.accept(this) }
    }

    override fun visitArgument(argument: KtValueArgument) {
        val name = argument.getArgumentName()?.text ?: ""
        val value = argument.getArgumentExpression()?.evaluate<Any>(this) ?: ""
        context.push(name to value)
    }

    @Suppress("UNUSED_PARAMETER")
    fun visitConstructorDelegationReferenceExpression(ignore: KtConstructorDelegationReferenceExpression) {
    }

    override fun visitExpression(expression: KtExpression) {
        when (expression) {
            is KtConstructorDelegationReferenceExpression -> visitConstructorDelegationReferenceExpression(expression)
            else -> context.push(expression.toCodeBlock())
        }
    }

    override fun visitLoopExpression(loopExpression: KtLoopExpression) {
        this.visitExpression(loopExpression)
    }

    override fun visitConstantExpression(expression: KtConstantExpression) {
        context.push(expression.toCodeBlock())
    }

    override fun visitSimpleNameExpression(expression: KtSimpleNameExpression) {
        context.push(CodeBlock.of(expression.getReferencedName()))
    }

    override fun visitReferenceExpression(expression: KtReferenceExpression) {
        this.visitExpression(expression)
    }

    override fun visitLabeledExpression(expression: KtLabeledExpression) {
        context.push(expression.toCodeBlock())
    }

    override fun visitPrefixExpression(expression: KtPrefixExpression) {
        context.push(expression.toCodeBlock())
    }

    override fun visitPostfixExpression(expression: KtPostfixExpression) {
        this.visitUnaryExpression(expression)
    }

    override fun visitUnaryExpression(expression: KtUnaryExpression) {
        val operationToken = expression.operationToken
        val token = when (operationToken) {
            is KtSingleValueToken -> operationToken.value
            else -> unknownType(operationToken)
        }
        context.push(CodeBlock.of(token))
        expression.baseExpression?.accept(this)
    }

    override fun visitBinaryExpression(expression: KtBinaryExpression) {
        context.push(expression.toCodeBlock())
    }

    override fun visitReturnExpression(expression: KtReturnExpression) {
        val returned = expression.returnedExpression?.evaluate<String>(this)
        val retVal = returned ?: expression.labeledExpression?.evaluate<String>(this)?.let { it }
        if (retVal == null) throw RuntimeException("Could not find value for return expression: $expression")
        context.push(CodeBlock.of("return $retVal"))
    }

    override fun visitExpressionWithLabel(expression: KtExpressionWithLabel) {
        this.visitExpression(expression)
    }

    override fun visitThrowExpression(expression: KtThrowExpression) {
        this.visitExpression(expression)
    }

    override fun visitBreakExpression(expression: KtBreakExpression) {
        this.visitExpressionWithLabel(expression)
    }

    override fun visitContinueExpression(expression: KtContinueExpression) {
        this.visitExpressionWithLabel(expression)
    }

    override fun visitIfExpression(expression: KtIfExpression) {
        this.visitExpression(expression)
    }

    override fun visitWhenExpression(expression: KtWhenExpression) {
        context.push(expression.toCodeBlock())
    }

    override fun visitCollectionLiteralExpression(expression: KtCollectionLiteralExpression) {
        this.visitExpression(expression)
    }

    override fun visitTryExpression(expression: KtTryExpression) {
        this.visitExpression(expression)
    }

    override fun visitForExpression(expression: KtForExpression) {
        val loopText = expression.prevSibling.text.dropWhile { it == '\n' } + expression.text

        context.push(CodeBlock.of(loopText.trimIndent()))
    }

    override fun visitWhileExpression(expression: KtWhileExpression) {
        this.visitLoopExpression(expression)
    }

    override fun visitDoWhileExpression(expression: KtDoWhileExpression) {
        this.visitLoopExpression(expression)
    }

    override fun visitLambdaExpression(expression: KtLambdaExpression) {
        this.visitExpression(expression)
    }

    override fun visitAnnotatedExpression(expression: KtAnnotatedExpression) {
        if (expression.annotationEntries.size != 1) {
            throw RuntimeException("Should have found one entry")
        }
        context.push(expression.annotationEntries[0].evaluate(this))
    }

    override fun visitCallExpression(expression: KtCallExpression) {
        context.push(expression.toCodeBlock())
    }

    override fun visitArrayAccessExpression(expression: KtArrayAccessExpression) {
        this.visitReferenceExpression(expression)
    }

    override fun visitQualifiedExpression(expression: KtQualifiedExpression) {
        this.visitExpression(expression)
    }

    override fun visitDoubleColonExpression(expression: KtDoubleColonExpression) {
        this.visitExpression(expression)
    }

    override fun visitCallableReferenceExpression(expression: KtCallableReferenceExpression) {
        this.visitDoubleColonExpression(expression)
    }

    override fun visitClassLiteralExpression(expression: KtClassLiteralExpression) {
        this.visitDoubleColonExpression(expression)
    }

    override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        context.push(expression.toCodeBlock())
    }

    override fun visitSafeQualifiedExpression(expression: KtSafeQualifiedExpression) {
        this.visitQualifiedExpression(expression)
    }

    override fun visitObjectLiteralExpression(expression: KtObjectLiteralExpression) {
        this.visitExpression(expression)
    }

    override fun visitBlockExpression(expression: KtBlockExpression) {
        val builder = CodeBlock.builder()

        builder.add("%L", expression.text.drop(1).dropLast(1).trimIndent())

        context.push(builder.build())
    }

    override fun visitCatchSection(catchClause: KtCatchClause) {
        this.visitKtElement(catchClause)
    }

    override fun visitFinallySection(finallySection: KtFinallySection) {
        this.visitKtElement(finallySection)
    }

    override fun visitTypeArgumentList(typeArgumentList: KtTypeArgumentList) {
        this.visitKtElement(typeArgumentList)
    }

    override fun visitThisExpression(expression: KtThisExpression) {
        this.visitExpressionWithLabel(expression)
    }

    override fun visitSuperExpression(expression: KtSuperExpression) {
        this.visitExpressionWithLabel(expression)
    }

    override fun visitParenthesizedExpression(expression: KtParenthesizedExpression) {
        this.visitExpression(expression)
    }

    override fun visitInitializerList(list: KtInitializerList) {
        acceptChildren("visitInitializerList", list, None) {
            when (it) {
                is SuperCall -> context.push(it)
                else -> unknownType(it)
            }
        }
    }

    override fun visitAnonymousInitializer(initializer: KtAnonymousInitializer) {
        this.visitDeclaration(initializer)
    }

    override fun visitScriptInitializer(initializer: KtScriptInitializer) {
        this.visitAnonymousInitializer(initializer)
    }

    override fun visitClassInitializer(initializer: KtClassInitializer) {
        context.push(initializer.body?.evaluate(this) ?: CodeBlock.of(""))
    }

    override fun visitPropertyAccessor(accessor: KtPropertyAccessor) {
        val builder = FunSpec.builder(if (accessor.isGetter) "get()" else "set()")
        acceptChildren("visitPropertyAccessor", accessor, None) {
            when (it) {
                is CodeBlock -> builder.addCode(it)
                is ParameterSpec -> builder.addParameter(it)
                is KModifier -> builder.addModifiers(it)
                else -> unknownType(it)
            }
        }
        context.push(if (accessor.isGetter) Accessor(builder.build()) else Mutator(builder.build()))
    }

    data class TypeConstraint(val type: TypeVariableName)

    override fun visitTypeConstraintList(list: KtTypeConstraintList) {
        acceptChildren("visitTypeConstraintList", list, None) {
            when (it) {
                is TypeVariableName -> context.push(TypeConstraint(it))
                else -> unknownType(it)
            }
        }
    }

    override fun visitTypeConstraint(constraint: KtTypeConstraint) {
        var typeName: CodeBlock? = null
        val bounds = mutableListOf<TypeName>()
        val modifier: KModifier? = null
        acceptChildren("visitTypeConstraint", constraint, None) {
            when (it) {
                is CodeBlock -> typeName = it
                is TypeName -> bounds += it
                else -> unknownType(it)
            }
        }
        context.push(
            TypeVariableName(typeName.toString(), modifier)
                .copy(bounds = bounds)
        )
    }

    private fun visitTypeElement(type: KtTypeElement) {
        unknownType(type)
    }

    override fun visitUserType(type: KtUserType) {
        val qualifier = type.qualifier?.text?.split(".") ?: listOf()
        var pkgName = qualifier.takeWhile { it[0].isLowerCase() }
            .joinToString(".")
        val className: String = (qualifier.takeLastWhile { it[0].isUpperCase() } + type.referencedName!!)
            .joinToString(".")
        val list = acceptChildren<TypeProjection>("user type type arguments", type.typeArgumentList)
        if (pkgName.isBlank()) {
            pkgName = autoimport(className)?.packageName ?: (imports[className]?.packageName ?: this.pkgName)
        }
        val value = ClassName(pkgName, className)
        if (list.isNotEmpty()) {
            context.push(value.parameterizedBy(*list.map { it.type }.toTypedArray()))
        } else {
            context.push(value)
        }
    }

    override fun visitDynamicType(type: KtDynamicType) {
        this.visitTypeElement(type)
    }

    @Suppress("UNCHECKED_CAST")
    override fun visitFunctionType(type: KtFunctionType) {
        val parameters: List<ParameterSpec> = acceptChildren("function type parameters", type.parameterList)
        val receiver = acceptChildren<TypeName>("function type receiver", type.receiver).firstOrNull()
        val returnType = acceptChildren<TypeName>("function type return type", type.returnTypeReference).first()
        val lambdaTypeName = LambdaTypeName.get(receiver, parameters, returnType)
        context.push(lambdaTypeName)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> acceptChildren(bookmark: String, element: KtElement?): List<T> {
        context.bookmark(bookmark)
        element?.acceptChildren(this)
        return context.popToBookmark() as List<T>
    }

    override fun visitSelfType(type: KtSelfType) {
        this.visitTypeElement(type)
    }

    override fun visitBinaryWithTypeRHSExpression(expression: KtBinaryExpressionWithTypeRHS) {
        this.visitExpression(expression)
    }

    override fun visitStringTemplateExpression(expression: KtStringTemplateExpression) {
        context.push(expression.toCodeBlock())
    }

    override fun visitNamedDeclaration(declaration: KtNamedDeclaration) {
        this.visitDeclaration(declaration)
    }

    override fun visitNullableType(nullableType: KtNullableType) {
        nullableType.innerType?.accept(this)
        val type = context.pop<TypeName>().copy(nullable = true)
        if (nullableType.modifierList?.allChildren?.isEmpty == true) {
            TODO("handle the modifiers: ${nullableType.modifierList}")
        }
        context.push(type)
    }

    override fun visitTypeProjection(typeProjection: KtTypeProjection) {
        val value = typeProjection.typeReference?.evaluate<TypeName>(this) ?: ClassName("", "")
        var variance: KModifier? = null
        when (typeProjection.projectionKind) {
            IN -> variance = KModifier.IN
            OUT -> variance = KModifier.OUT
            else -> {
                LOG.debug("Found something other than IN/OUT for ${typeProjection.text}")
            }
        }

        context.push(TypeProjection(value, variance))
    }

    override fun visitWhenEntry(jetWhenEntry: KtWhenEntry) {
        context.push(jetWhenEntry.toCodeBlock())
    }

    override fun visitIsExpression(expression: KtIsExpression) {
        this.visitExpression(expression)
    }

    override fun visitWhenConditionIsPattern(condition: KtWhenConditionIsPattern) {
        this.visitKtElement(condition)
    }

    override fun visitWhenConditionInRange(condition: KtWhenConditionInRange) {
        this.visitKtElement(condition)
    }

    override fun visitWhenConditionWithExpression(condition: KtWhenConditionWithExpression) {
        this.visitKtElement(condition)
    }

    override fun visitStringTemplateEntry(entry: KtStringTemplateEntry) {
        this.visitKtElement(entry)
    }

    override fun visitStringTemplateEntryWithExpression(entry: KtStringTemplateEntryWithExpression) {
        this.visitStringTemplateEntry(entry)
    }

    override fun visitBlockStringTemplateEntry(entry: KtBlockStringTemplateEntry) {
        this.visitStringTemplateEntryWithExpression(entry)
    }

    override fun visitSimpleNameStringTemplateEntry(entry: KtSimpleNameStringTemplateEntry) {
        this.visitStringTemplateEntryWithExpression(entry)
    }

    override fun visitLiteralStringTemplateEntry(entry: KtLiteralStringTemplateEntry) {
        this.visitStringTemplateEntry(entry)
    }

    override fun visitEscapeStringTemplateEntry(entry: KtEscapeStringTemplateEntry) {
        this.visitStringTemplateEntry(entry)
    }

    override fun visitPackageDirective(directive: KtPackageDirective) {
        pkgName = directive.qualifiedName
    }

    private fun PsiElement.toKModifier(): KModifier {
        return KModifier.valueOf(this.text.uppercase(Locale.getDefault()))
    }

    internal fun PsiChildRange.accept(visitor: KibbleVisitor) {
        forEach {
            it.accept(visitor)
        }
    }

    internal fun KtElement.toCodeBlock() = CodeBlock.of("%L", text)
}
