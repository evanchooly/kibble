package com.antwerkz.kibble

import com.antwerkz.kibble.model.Constructor
import com.antwerkz.kibble.model.InitBlock
import com.antwerkz.kibble.model.KibbleAnnotation
import com.antwerkz.kibble.model.KibbleArgument
import com.antwerkz.kibble.model.KibbleClass
import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleFunction
import com.antwerkz.kibble.model.KibbleFunctionType
import com.antwerkz.kibble.model.KibbleImport
import com.antwerkz.kibble.model.KibbleObject
import com.antwerkz.kibble.model.KibbleParameter
import com.antwerkz.kibble.model.KibbleProperty
import com.antwerkz.kibble.model.KibbleType
import com.antwerkz.kibble.model.Modality
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Mutability
import com.antwerkz.kibble.model.Mutability.NEITHER
import com.antwerkz.kibble.model.Mutability.VAL
import com.antwerkz.kibble.model.Mutability.VAR
import com.antwerkz.kibble.model.SecondaryConstructor
import com.antwerkz.kibble.model.SuperCall
import com.antwerkz.kibble.model.TypeParameter
import com.antwerkz.kibble.model.TypeParameterVariance
import com.antwerkz.kibble.model.Visibility
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
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
import org.jetbrains.kotlin.psi.KtModifierListOwner
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
import org.jetbrains.kotlin.psi.KtProjectionKind.NONE
import org.jetbrains.kotlin.psi.KtProjectionKind.OUT
import org.jetbrains.kotlin.psi.KtProjectionKind.STAR
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
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier
import org.jetbrains.kotlin.types.Variance

@Suppress("unused", "MemberVisibilityCanBePrivate")
internal class KibbleVisitor(private val context: KibbleContext) : KtVisitorVoid() {
    override fun visitKtFile(kt: KtFile) {
        val kibbleFile = KibbleFile()

        kibbleFile.sourceTimestamp = kt.virtualFile.modificationStamp
        val directive = kt.packageDirective?.fqName?.asString()
        kibbleFile.pkgName = if(directive != "") directive else null
        kibbleFile.imports.addAll(kt.importList?.evaluate(this) ?: listOf())

        kt.declarations.forEach {
            val declaration = it.evaluate<Any>(this)
            when (declaration) {
                is KibbleFunction -> kibbleFile.functions += declaration
                is KibbleClass -> kibbleFile.classes += declaration
                is KibbleObject -> kibbleFile.objects += declaration
                is KibbleProperty -> kibbleFile.properties += declaration
                else -> TODO("handle declaration type $declaration")
            }
        }

        context.register(kibbleFile)
    }

    override fun visitImportDirective(directive: KtImportDirective) {
        val fqName = directive.importedFqName!!
        val type = KibbleType(fqName.parent().asString(), fqName.shortName().identifier)
        val kibbleImport = KibbleImport(type, directive.aliasName)

        context.push(kibbleImport)
    }

    override fun visitKtElement(element: KtElement) {
        throw Exception("Found an unprocessed type: $element")
    }

    override fun visitDeclaration(dcl: KtDeclaration) {
        return when (dcl) {
            is KtClass -> visitClass(dcl)
            is KtObjectDeclaration -> visitObjectDeclaration(dcl)
            is KtNamedFunction -> visitNamedFunction(dcl)
            is KtProperty -> visitProperty(dcl)
            is KtTypeParameter -> visitTypeParameter(dcl)
            else -> TODO("Unprocessed declaration: $dcl")
        }
    }

    override fun visitClass(kt: KtClass) {
        val kibbleClass = KibbleClass(kt.name ?: "",
                kt.modalityModifier().toModality(),
                kt.visibilityModifier().toVisibility())

        kibbleClass.isInterface = kt.isInterface()
        kibbleClass.enum = kt.isEnum()
        kibbleClass.data = kt.isData()
        kibbleClass.sealed = kt.isSealed()

        kibbleClass.typeParameters += kt.typeParameterList?.evaluate<List<TypeParameter>>(this) ?: listOf()

        kt.getSuperTypeList()?.evaluate<List<Any>>(this)
                ?.forEach {
                    when (it) {
                        is SuperCall -> {
                            kibbleClass.extends(it.type, it.arguments)
                        }
                        is KibbleType -> kibbleClass.implements(it)
                        else -> TODO("handled this type: $it")
                    }
                }
        kibbleClass.annotations += kt.annotationEntries.evaluate(this)

        kt.declarations.forEach {
            val declaration = it.evaluate<Any>(this)
            when (declaration) {
                is KibbleFunction -> kibbleClass.functions += declaration
                is KibbleClass -> kibbleClass.classes += declaration
                is KibbleObject -> kibbleClass.objects += declaration
                is KibbleProperty -> kibbleClass.properties += declaration
                is SecondaryConstructor -> kibbleClass.secondaries += declaration
                is InitBlock -> kibbleClass.initBlock = declaration
                else -> TODO("handle declaration type $declaration")
            }
        }

        kt.primaryConstructor?.evaluate<Constructor>(this)?.let { kibbleClass.constructor = it }

        kibbleClass.properties += kibbleClass.constructor.filterProperties()
        context.push(kibbleClass)
    }

    override fun visitObjectDeclaration(declaration: KtObjectDeclaration) {
        val kibbleObject = KibbleObject(declaration.name ?: "",
                declaration.isCompanion())

        declaration.getSuperTypeList()?.evaluate<List<Any>>(this)
                ?.forEach {
                    when (it) {
                        is SuperCall -> {
                            kibbleObject.extends(it.type, it.arguments)
                        }
                        is KibbleType -> kibbleObject.implements(it)
                        else -> TODO("handled this type: $it")
                    }
                }
        kibbleObject.annotations += declaration.annotationEntries.evaluate(this)
        kibbleObject.visibility = declaration.visibilityModifier().toVisibility()

        declaration.declarations.forEach {
            val declaration = it.evaluate<Any>(this)
            when (declaration) {
                is KibbleFunction -> kibbleObject.functions += declaration
                is KibbleClass -> kibbleObject.classes += declaration
                is KibbleObject -> kibbleObject.objects += declaration
                is KibbleProperty -> kibbleObject.properties += declaration
                else -> TODO("handle declaration type $declaration")
            }
        }

        context.push(kibbleObject)
    }

    override fun visitClassOrObject(classOrObject: KtClassOrObject) {
        this.visitNamedDeclaration(classOrObject)
    }

    override fun visitSecondaryConstructor(constructor: KtSecondaryConstructor) {
        val ctor = SecondaryConstructor()
        constructor.bodyExpression?.let {
            ctor.body = it.evaluate<String>(this)
        }
        ctor.delegationArguments += constructor.getDelegationCall().evaluate<List<KibbleArgument>>(this)
        ctor.parameters += constructor.valueParameters.evaluate(this)
        context.push(ctor)
    }

    override fun visitPrimaryConstructor(constructor: KtPrimaryConstructor) {
        val ctor = Constructor()
        constructor.bodyExpression?.let {
            ctor.body = it.evaluate<String>(this)
        }
        ctor.parameters += constructor.valueParameters.evaluate(this)
        context.push(ctor)
    }

    override fun visitNamedFunction(kt: KtNamedFunction) {
        val kibbleFunction = KibbleFunction(kt.name, kt.visibilityModifier().toVisibility(), kt.modalityModifier().toModality(),
                overriding = kt.isOverridden())

        kibbleFunction.parameters += kt.valueParameterList?.evaluate<List<KibbleParameter>>(this) ?: listOf()
        kibbleFunction.typeParameters += kt.typeParameterList?.evaluate<List<TypeParameter>>(this) ?: listOf()
        kibbleFunction.annotations += kt.annotationEntries
                .map { it.evaluate<KibbleAnnotation>(this) }
        kibbleFunction.body = kt.bodyExpression?.evaluate<String>(this) ?: ""
        kibbleFunction.type = kt.typeReference?.evaluate<KibbleType>(this)
//        val modifiers = kt.modifierList?.evaluate<List<Any>>(this)

        context.push(kibbleFunction)
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
        val type: KibbleType? = property.typeReference?.evaluate(this)

        val visibility = property.visibilityModifier().toVisibility()
        val kibbleProperty = KibbleProperty(property.name ?: "", type,
                property.initializer?.evaluate(this), property.modalityModifier().toModality(),
                property.isOverridden())
        kibbleProperty.visibility = visibility
        kibbleProperty.mutability = if(property.isVar) VAR else VAL
        kibbleProperty.annotations += property.annotationEntries.evaluate(this)
        val modifiers = property.modifierList?.evaluate<List<String>>(this) ?: listOf()
        kibbleProperty.lateInit = "lateinit" in modifiers

        context.push(kibbleProperty)
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
        context.push(importList.imports
                .map { it.evaluate<KibbleImport>(this) }
                .toList())
    }

    fun visitFileAnnotationList(fileAnnotationList: KtFileAnnotationList) {
        this.visitKtElement(fileAnnotationList)
    }

    override fun visitClassBody(classBody: KtClassBody) {
        this.visitKtElement(classBody)
    }

    override fun visitModifierList(list: KtModifierList) {
        context.push(list.allChildren.map { it.text }.toList())
    }

    override fun visitAnnotation(annotation: KtAnnotation) {
        this.visitKtElement(annotation)
    }

    override fun visitAnnotationEntry(annotationEntry: KtAnnotationEntry) {
        val annType = annotationEntry.typeReference?.evaluate<KibbleType>(this)!!
        val arguments = annotationEntry.valueArgumentList?.evaluate<List<KibbleArgument>>(this) ?: listOf()
        context.push(KibbleAnnotation(annType, arguments))
    }

    fun visitAnnotationUseSiteTarget(annotationTarget: KtAnnotationUseSiteTarget) {
        this.visitKtElement(annotationTarget)
    }

    override fun visitConstructorCalleeExpression(constructorCalleeExpression: KtConstructorCalleeExpression) {
        this.visitKtElement(constructorCalleeExpression)
    }

    override fun visitTypeParameterList(list: KtTypeParameterList) {
        context.push(list.parameters.evaluate<TypeParameter>(this))
    }

    override fun visitTypeParameter(parameter: KtTypeParameter) {
        val bounds: KibbleType? = parameter.extendsBound?.evaluate(this)

        val variance = when (parameter.variance) {
            Variance.INVARIANT -> null
            Variance.IN_VARIANCE -> TypeParameterVariance.IN
            Variance.OUT_VARIANCE -> TypeParameterVariance.OUT
        }

        val value = KibbleType.from(parameter.name ?: "")
        context.push(TypeParameter(value, variance, bounds))
    }

    override fun visitEnumEntry(enumEntry: KtEnumEntry) {
        this.visitClass(enumEntry)
    }

    override fun visitParameterList(list: KtParameterList) {
        context.push(list.parameters.map { it.evaluate<KibbleParameter>(this) }.toList())
    }

    override fun visitParameter(parameter: KtParameter) {
        val name = parameter.name
        val type = parameter.typeReference?.evaluate<KibbleType>(this)
        val defaultValue = parameter.defaultValue?.evaluate<String>(this)
        val kibbleParameter = KibbleParameter(name, type, defaultValue, parameter.isVarArg)

        if (parameter.hasValOrVar()) {
            val valOrVarKeyword = parameter.valOrVarKeyword?.text ?: "NEITHER"
            kibbleParameter.mutability = Mutability.valueOf(valOrVarKeyword.toUpperCase())
        }
        kibbleParameter.annotations += parameter.annotationEntries.evaluate(this)

        context.push(kibbleParameter)
    }

    override fun visitSuperTypeList(list: KtSuperTypeList) {
        context.push(list.entries.map {
            it.evaluate<Any>(this)
        })
    }

    override fun visitSuperTypeListEntry(specifier: KtSuperTypeListEntry) {
        specifier.typeReference?.evaluate<KibbleType>(this)?.let {
            context.push(it)
        }
    }

    override fun visitDelegatedSuperTypeEntry(specifier: KtDelegatedSuperTypeEntry) {
        this.visitSuperTypeListEntry(specifier)
    }

    override fun visitSuperTypeCallEntry(call: KtSuperTypeCallEntry) {
        val type = call.typeReference?.evaluate<KibbleType>(this)
        type?.let {
            val invocation = SuperCall(type).also { superCall ->
                call.valueArgumentList?.evaluate<List<KibbleArgument>>(this)?.let {
                    superCall.arguments.addAll(it)
                }
            }
            context.push(invocation)
        }
    }

    override fun visitSuperTypeEntry(specifier: KtSuperTypeEntry) {
        specifier.typeReference?.evaluate<KibbleType>(this)?.let {
            context.push(it)
        }
    }

    override fun visitConstructorDelegationCall(call: KtConstructorDelegationCall) {
        context.push(call.valueArguments.map {
            KibbleArgument(
                    it.getArgumentName()?.toString(),
                    it.getArgumentExpression()?.evaluate<String>(this) ?: "")
        })
    }

    override fun visitPropertyDelegate(delegate: KtPropertyDelegate) {
        this.visitKtElement(delegate)
    }

    override fun visitTypeReference(typeReference: KtTypeReference) {
        typeReference.typeElement?.evaluate<Any>(this)?.let {
            context.push(it)
        }
    }

    override fun visitValueArgumentList(list: KtValueArgumentList) {
        context.push(list.arguments.evaluate<KibbleArgument>(this))
    }

    override fun visitArgument(argument: KtValueArgument) {
        val name = if (argument.isNamed()) argument.getArgumentName()!!.text else null
        val evaluate = argument.getArgumentExpression()?.evaluate<Any>(this)
        val value = evaluate ?: ""
        context.push(KibbleArgument(name, value))
    }

    override fun visitExpression(expression: KtExpression) {
        expression.accept(this)
    }

    override fun visitLoopExpression(loopExpression: KtLoopExpression) {
        this.visitExpression(loopExpression)
    }

    override fun visitConstantExpression(expression: KtConstantExpression) {
        context.push(expression.text)
    }

    override fun visitSimpleNameExpression(expression: KtSimpleNameExpression) {
        context.push(expression.getReferencedName())
    }

    override fun visitReferenceExpression(expression: KtReferenceExpression) {
        this.visitExpression(expression)
    }

    override fun visitLabeledExpression(expression: KtLabeledExpression) {
        context.push(expression.text)
    }

    override fun visitPrefixExpression(expression: KtPrefixExpression) {
        context.push(expression.text)
    }

    override fun visitPostfixExpression(expression: KtPostfixExpression) {
        this.visitUnaryExpression(expression)
    }

    override fun visitUnaryExpression(expression: KtUnaryExpression) {
        val operationToken = expression.operationToken
        val token = when (operationToken) {
            is KtSingleValueToken -> operationToken.value
            else -> TODO("handle token type $operationToken")
        }
        context.push(token)
        expression.baseExpression?.accept(this)
    }

    override fun visitBinaryExpression(expression: KtBinaryExpression) {
        this.visitExpression(expression)
    }

    override fun visitReturnExpression(expression: KtReturnExpression) {
        val returned = expression.returnedExpression?.evaluate<String>(this)
        val retVal = returned
                ?: (expression.labeledExpression?.evaluate<String>(this)?.let { it }
                        ?: throw RuntimeException("Could not find value for return expression: $expression"))
        context.push("return $retVal")
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
        this.visitExpression(expression)
    }

    override fun visitCollectionLiteralExpression(expression: KtCollectionLiteralExpression) {
        this.visitExpression(expression)
    }

    override fun visitTryExpression(expression: KtTryExpression) {
        this.visitExpression(expression)
    }

    override fun visitForExpression(expression: KtForExpression) {
        this.visitLoopExpression(expression)
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
        if(expression.annotationEntries.size != 1) {
            throw RuntimeException("Should have found one entry")
        }
        context.push(expression.annotationEntries[0].evaluate(this))
    }

    override fun visitCallExpression(expression: KtCallExpression) {
        val prevSibling = expression.prevSibling
        var indent = if (prevSibling is PsiWhiteSpace) {
            prevSibling.text.removePrefix("\n")
        } else ""
        if (indent.length == 1) {
            indent = ""
        }
        val value = expression.text
        context.push(indent + value)
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
        context.push(expression.text)
    }

    override fun visitSafeQualifiedExpression(expression: KtSafeQualifiedExpression) {
        this.visitQualifiedExpression(expression)
    }

    override fun visitObjectLiteralExpression(expression: KtObjectLiteralExpression) {
        this.visitExpression(expression)
    }

    override fun visitBlockExpression(expression: KtBlockExpression) {
        context.push(expression.statements.evaluate<String>(this).joinToString("\n"))
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
        this.visitKtElement(list)
    }

    override fun visitAnonymousInitializer(initializer: KtAnonymousInitializer) {
        this.visitDeclaration(initializer)
    }

    override fun visitScriptInitializer(initializer: KtScriptInitializer) {
        this.visitAnonymousInitializer(initializer)
    }

    override fun visitClassInitializer(initializer: KtClassInitializer) {
        context.push(InitBlock(initializer.body?.evaluate(this) ?: ""))
    }

    override fun visitPropertyAccessor(accessor: KtPropertyAccessor) {
        this.visitDeclaration(accessor)
    }

    override fun visitTypeConstraintList(list: KtTypeConstraintList) {
        this.visitKtElement(list)
    }

    override fun visitTypeConstraint(constraint: KtTypeConstraint) {
        this.visitKtElement(constraint)
    }

    private fun visitTypeElement(type: KtTypeElement) {
//        return when(type) {
////            is KtUserType -> visitUserType(type)
//            else -> throw RuntimeException("unknown type: ${type}")
//        }
        throw RuntimeException("unhandled type: $type")
    }

    override fun visitUserType(type: KtUserType) {
        val value = KibbleType(type.qualifier?.text, type.referencedName!!)
        value.typeParameters += type.typeArguments.evaluate(this)
        context.push(value)
    }

    override fun visitDynamicType(type: KtDynamicType) {
        this.visitTypeElement(type)
    }

    override fun visitFunctionType(type: KtFunctionType) {
        val parameters = type.parameterList?.evaluate<List<KibbleParameter>>(this) ?: listOf()
        val receiver = type.receiver?.evaluate<KibbleType>(this)
        val returnType = type.returnTypeReference?.evaluate<KibbleType>(this)

        context.push(KibbleFunctionType(type.name ?: "", parameters, returnType, receiver))
    }

    override fun visitSelfType(type: KtSelfType) {
        this.visitTypeElement(type)
    }

    override fun visitBinaryWithTypeRHSExpression(expression: KtBinaryExpressionWithTypeRHS) {
        this.visitExpression(expression)
    }

    override fun visitStringTemplateExpression(expression: KtStringTemplateExpression) {
        context.push(expression.text)
    }

    override fun visitNamedDeclaration(declaration: KtNamedDeclaration) {
        this.visitDeclaration(declaration)
    }

    override fun visitNullableType(nullableType: KtNullableType) {
        nullableType.innerType?.accept(this)
        val type = context.pop<KibbleType>()
        if (nullableType.modifierList?.allChildren?.isEmpty == true) {
            TODO("handle the modifiers: ${nullableType.modifierList}")
        }
        context.push(KibbleType(type, true))
    }

    override fun visitTypeProjection(typeProjection: KtTypeProjection) {
        val value = typeProjection.typeReference?.evaluate<KibbleType>(this)
        value?.let {
            val modifier = when (typeProjection.projectionKind) {
                IN -> TypeParameterVariance.IN
                OUT -> TypeParameterVariance.OUT
                STAR -> TypeParameterVariance.STAR
                NONE -> null
            }
            val bounds: KibbleType? = typeProjection.projectionToken?.let {
                TODO("handle projection tokens")
            }
            context.push(TypeParameter(value, modifier, bounds))
        }
    }

    override fun visitWhenEntry(jetWhenEntry: KtWhenEntry) {
        this.visitKtElement(jetWhenEntry)
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
        TODO("process the directive directly since nulls aren't allowed on the stack")
    }

    private fun KtModifierListOwner.isOverridden(): Boolean {
        return modifierList?.allChildren?.find { it.text == "override" } != null
    }

    private fun PsiElement?.toModality(): Modality {
        return this?.text?.let {
            Modality.valueOf(it.toUpperCase())
        } ?: FINAL
    }

    private fun PsiElement?.toVisibility(): Visibility {
        return this?.text?.let {
            Visibility.valueOf(it.toUpperCase())
        } ?: PUBLIC
    }


    private fun extractPkgAndClassName(raw: String): Pair<String?, String> {
        val name = raw.split(".")
                .dropLastWhile { it.isEmpty() || it[0].isUpperCase() }
                .filter { it != "" }
                .joinToString(".")
        val pkgName = if (name != "") name else null
        val className = pkgName?.let { raw.substring(it.length + 1) } ?: raw

        return pkgName to className
    }
}