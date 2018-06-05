package com.antwerkz.kibble

import com.antwerkz.kibble.model.KibbleClass
import com.antwerkz.kibble.model.KibbleFile
import com.antwerkz.kibble.model.KibbleFunction
import com.antwerkz.kibble.model.KibbleImport
import com.antwerkz.kibble.model.KibbleObject
import com.antwerkz.kibble.model.KibbleProperty
import com.antwerkz.kibble.model.KibbleType
import com.antwerkz.kibble.model.Modality
import com.antwerkz.kibble.model.Modality.FINAL
import com.antwerkz.kibble.model.Visibility
import com.antwerkz.kibble.model.Visibility.PUBLIC
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
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
import org.jetbrains.kotlin.psi.KtWhenConditionInRange
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression
import org.jetbrains.kotlin.psi.KtWhenEntry
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.psi.KtWhileExpression
import org.jetbrains.kotlin.psi.psiUtil.allChildren
import org.jetbrains.kotlin.psi.psiUtil.modalityModifier
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifier

@Suppress("unused", "MemberVisibilityCanBePrivate")
internal class KibbleVisitor(private val context: KibbleContext) {
    fun visitKtFile(kt: KtFile): KibbleFile {
        val kibbleFile = KibbleFile()

        kibbleFile.sourceTimestamp = kt.virtualFile.modificationStamp
        kt.packageDirective?.let { kibbleFile.pkgName = visitPackageDirective(it) }
        kt.importDirectives.forEach {
            kibbleFile.addImport(visitImportDirective(it))
        }

        kt.declarations.forEach { decl ->
            val declaration = visitDeclaration(decl)
            when (declaration) {
                is KibbleClass -> kibbleFile.classes.add(declaration)
                is KibbleObject -> kibbleFile.objects.add(declaration)
                is KibbleFunction -> kibbleFile.functions.add(declaration)
                is KibbleProperty -> kibbleFile.properties.add(declaration)
                else -> throw RuntimeException("unknown type: ${declaration::class.java}")
            }
        }

        context.register(kibbleFile)
        return kibbleFile
    }

    fun visitImportDirective(directive: KtImportDirective): KibbleImport {
        val fqName = directive.importedFqName!!
        val type = KibbleType(fqName.parent().asString(), fqName.shortName().identifier)

        return KibbleImport(type, directive.aliasName)
    }

    fun visitKtElement(element: KtElement) {
        Exception("Found an unprocessed type: ${element.name}").printStackTrace()
    }

    fun visitDeclaration(dcl: KtDeclaration): Any {
        when (dcl) {
            is KtClass -> { return visitClass(dcl)}
            is KtObjectDeclaration -> { return visitObjectDeclaration(dcl)}
            is KtNamedFunction -> { return visitNamedFunction(dcl)}
            is KtProperty -> { return visitProperty(dcl) }
            else -> throw RuntimeException("Unprocessed declaration: ${dcl.name}")
        }
    }

    fun visitClass(kt: KtClass): KibbleClass {
        val kibbleClass = KibbleClass(kt.name ?: "",
                kt.modalityModifier().toModality(),
                kt.visibilityModifier().toVisibility())

        kibbleClass.isInterface = kt.isInterface()

        kt.typeParameterList?.let { visitTypeParameterList(it) }

        val superTypeList = kt.getSuperTypeList()
        superTypeList?.let { visitSuperTypeList(it) }
        val annotationEntries = kt.annotationEntries
        annotationEntries.forEach { visitAnnotationEntry(it) }

        kt.declarations.forEach { visitDeclaration(it) }

        return kibbleClass
    }

    fun visitObjectDeclaration(declaration: KtObjectDeclaration): KibbleObject {
        TODO("this.visitClassOrObject(declaration)")
    }

    fun visitClassOrObject(classOrObject: KtClassOrObject) {
        this.visitNamedDeclaration(classOrObject)
    }

    fun visitSecondaryConstructor(constructor: KtSecondaryConstructor) {
        this.visitNamedDeclaration(constructor)
    }

    fun visitPrimaryConstructor(constructor: KtPrimaryConstructor) {
        this.visitNamedDeclaration(constructor)
    }

    fun visitNamedFunction(kt: KtNamedFunction): KibbleFunction {
        val kibbleFunction = KibbleFunction(kt.name, kt.visibilityModifier().toVisibility(), kt.modalityModifier().toModality(),
                overriding = kt.isOverridden())
        kt.valueParameterList?.let { visitParameterList(it) }
        kt.typeParameterList?.let { visitTypeParameterList(it) }

/*
        kibbleFunction.body = kt.bodyExpression?.text?.trim() ?: ""
        bodyBlock = kt.hasBlockBody()
        if (bodyBlock) {
            body = (kt.bodyExpression?.text ?: "")
                    .drop(1)
                    .dropLast(1)
                    .trimIndent()
        }
        kt.typeReference?.let {
            type = KibbleType.from(it.text)
        }
        kt.modifierList
*/
        kt.annotationEntries.forEach { visitAnnotationEntry(it) }

        return kibbleFunction
    }

    fun visitProperty(property: KtProperty): KibbleProperty {
        TODO("property = ${property}")
    }

    fun visitDestructuringDeclaration(multiDeclaration: KtDestructuringDeclaration) {
        this.visitDeclaration(multiDeclaration)
    }

    fun visitDestructuringDeclarationEntry(multiDeclarationEntry: KtDestructuringDeclarationEntry) {
        this.visitNamedDeclaration(multiDeclarationEntry)
    }

    fun visitTypeAlias(typeAlias: KtTypeAlias) {
        this.visitNamedDeclaration(typeAlias)
    }

    fun visitScript(script: KtScript) {
        this.visitDeclaration(script)
    }

    fun visitImportAlias(importAlias: KtImportAlias) {
        this.visitKtElement(importAlias)
    }

    fun visitImportList(importList: KtImportList) {
        this.visitKtElement(importList)
    }

    fun visitFileAnnotationList(fileAnnotationList: KtFileAnnotationList) {
        this.visitKtElement(fileAnnotationList)
    }

    fun visitClassBody(classBody: KtClassBody) {
        this.visitKtElement(classBody)
    }

    fun visitModifierList(list: KtModifierList) {
        this.visitKtElement(list)
    }

    fun visitAnnotation(annotation: KtAnnotation) {
        this.visitKtElement(annotation)
    }

    fun visitAnnotationEntry(annotationEntry: KtAnnotationEntry) {
         val annType = visitTypeReference(annotationEntry.typeReference!!)
        val arguments = annotationEntry.valueArgumentList?.let { visitValueArgumentList(it) }

//        return KibbleAnnotation(type, arguments)
        this.visitKtElement(annotationEntry)
    }

    fun visitAnnotationUseSiteTarget(annotationTarget: KtAnnotationUseSiteTarget) {
        this.visitKtElement(annotationTarget)
    }

    fun visitConstructorCalleeExpression(constructorCalleeExpression: KtConstructorCalleeExpression) {
        this.visitKtElement(constructorCalleeExpression)
    }

    fun visitTypeParameterList(list: KtTypeParameterList) {
        this.visitKtElement(list)
    }

    fun visitTypeParameter(parameter: KtTypeParameter) {
        this.visitNamedDeclaration(parameter)
    }

    fun visitEnumEntry(enumEntry: KtEnumEntry) {
        this.visitClass(enumEntry)
    }

    fun visitParameterList(list: KtParameterList) {
        this.visitKtElement(list)
    }

    fun visitParameter(parameter: KtParameter) {
        this.visitNamedDeclaration(parameter)
    }

    fun visitSuperTypeList(list: KtSuperTypeList) {
        this.visitKtElement(list)
    }

    fun visitSuperTypeListEntry(specifier: KtSuperTypeListEntry) {
        this.visitKtElement(specifier)
    }

    fun visitDelegatedSuperTypeEntry(specifier: KtDelegatedSuperTypeEntry) {
        this.visitSuperTypeListEntry(specifier)
    }

    fun visitSuperTypeCallEntry(call: KtSuperTypeCallEntry) {
        this.visitSuperTypeListEntry(call)
    }

    fun visitSuperTypeEntry(specifier: KtSuperTypeEntry) {
        this.visitSuperTypeListEntry(specifier)
    }

    fun visitConstructorDelegationCall(call: KtConstructorDelegationCall) {
        this.visitKtElement(call)
    }

    fun visitPropertyDelegate(delegate: KtPropertyDelegate) {
        this.visitKtElement(delegate)
    }

    fun visitTypeReference(typeReference: KtTypeReference): KibbleType {
        val type = visitTypeElement(typeReference.typeElement!!)
        return type
    }

    fun visitValueArgumentList(list: KtValueArgumentList) {
        val map = list.arguments.map { visitArgument(it) }
        TODO()
    }

    fun visitArgument(argument: KtValueArgument): Pair<String?, Any> {
        val name = if (argument.isNamed()) argument.getArgumentName()!!.text else null
        val value = argument.getArgumentExpression()?.let { visitExpression(it) }
        return Pair(name, argument.text)
    }

    fun visitExpression(expression: KtExpression) {
        expression.a
        TODO()
    }

    fun visitLoopExpression(loopExpression: KtLoopExpression) {
        this.visitExpression(loopExpression)
    }

    fun visitConstantExpression(expression: KtConstantExpression) {
        this.visitExpression(expression)
    }

    fun visitSimpleNameExpression(expression: KtSimpleNameExpression) {
        this.visitReferenceExpression(expression)
    }

    fun visitReferenceExpression(expression: KtReferenceExpression) {
        this.visitExpression(expression)
    }

    fun visitLabeledExpression(expression: KtLabeledExpression) {
        this.visitExpressionWithLabel(expression)
    }

    fun visitPrefixExpression(expression: KtPrefixExpression) {
        this.visitUnaryExpression(expression)
    }

    fun visitPostfixExpression(expression: KtPostfixExpression) {
        this.visitUnaryExpression(expression)
    }

    fun visitUnaryExpression(expression: KtUnaryExpression) {
        this.visitExpression(expression)
    }

    fun visitBinaryExpression(expression: KtBinaryExpression) {
        this.visitExpression(expression)
    }

    fun visitReturnExpression(expression: KtReturnExpression) {
        this.visitExpressionWithLabel(expression)
    }

    fun visitExpressionWithLabel(expression: KtExpressionWithLabel) {
        this.visitExpression(expression)
    }

    fun visitThrowExpression(expression: KtThrowExpression) {
        this.visitExpression(expression)
    }

    fun visitBreakExpression(expression: KtBreakExpression) {
        this.visitExpressionWithLabel(expression)
    }

    fun visitContinueExpression(expression: KtContinueExpression) {
        this.visitExpressionWithLabel(expression)
    }

    fun visitIfExpression(expression: KtIfExpression) {
        this.visitExpression(expression)
    }

    fun visitWhenExpression(expression: KtWhenExpression) {
        this.visitExpression(expression)
    }

    fun visitCollectionLiteralExpression(expression: KtCollectionLiteralExpression) {
        this.visitExpression(expression)
    }

    fun visitTryExpression(expression: KtTryExpression) {
        this.visitExpression(expression)
    }

    fun visitForExpression(expression: KtForExpression) {
        this.visitLoopExpression(expression)
    }

    fun visitWhileExpression(expression: KtWhileExpression) {
        this.visitLoopExpression(expression)
    }

    fun visitDoWhileExpression(expression: KtDoWhileExpression) {
        this.visitLoopExpression(expression)
    }

    fun visitLambdaExpression(expression: KtLambdaExpression) {
        this.visitExpression(expression)
    }

    fun visitAnnotatedExpression(expression: KtAnnotatedExpression) {
        this.visitExpression(expression)
    }

    fun visitCallExpression(expression: KtCallExpression) {
        this.visitReferenceExpression(expression)
    }

    fun visitArrayAccessExpression(expression: KtArrayAccessExpression) {
        this.visitReferenceExpression(expression)
    }

    fun visitQualifiedExpression(expression: KtQualifiedExpression) {
        this.visitExpression(expression)
    }

    fun visitDoubleColonExpression(expression: KtDoubleColonExpression) {
        this.visitExpression(expression)
    }

    fun visitCallableReferenceExpression(expression: KtCallableReferenceExpression) {
        this.visitDoubleColonExpression(expression)
    }

    fun visitClassLiteralExpression(expression: KtClassLiteralExpression) {
        this.visitDoubleColonExpression(expression)
    }

    fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        this.visitQualifiedExpression(expression)
    }

    fun visitSafeQualifiedExpression(expression: KtSafeQualifiedExpression) {
        this.visitQualifiedExpression(expression)
    }

    fun visitObjectLiteralExpression(expression: KtObjectLiteralExpression) {
        this.visitExpression(expression)
    }

    fun visitBlockExpression(expression: KtBlockExpression) {
        this.visitExpression(expression)
    }

    fun visitCatchSection(catchClause: KtCatchClause) {
        this.visitKtElement(catchClause)
    }

    fun visitFinallySection(finallySection: KtFinallySection) {
        this.visitKtElement(finallySection)
    }

    fun visitTypeArgumentList(typeArgumentList: KtTypeArgumentList) {
        this.visitKtElement(typeArgumentList)
    }

    fun visitThisExpression(expression: KtThisExpression) {
        this.visitExpressionWithLabel(expression)
    }

    fun visitSuperExpression(expression: KtSuperExpression) {
        this.visitExpressionWithLabel(expression)
    }

    fun visitParenthesizedExpression(expression: KtParenthesizedExpression) {
        this.visitExpression(expression)
    }

    fun visitInitializerList(list: KtInitializerList) {
        this.visitKtElement(list)
    }

    fun visitAnonymousInitializer(initializer: KtAnonymousInitializer) {
        this.visitDeclaration(initializer)
    }

    fun visitScriptInitializer(initializer: KtScriptInitializer) {
        this.visitAnonymousInitializer(initializer)
    }

    fun visitClassInitializer(initializer: KtClassInitializer) {
        this.visitAnonymousInitializer(initializer)
    }

    fun visitPropertyAccessor(accessor: KtPropertyAccessor) {
        this.visitDeclaration(accessor)
    }

    fun visitTypeConstraintList(list: KtTypeConstraintList) {
        this.visitKtElement(list)
    }

    fun visitTypeConstraint(constraint: KtTypeConstraint) {
        this.visitKtElement(constraint)
    }

    private fun visitTypeElement(type: KtTypeElement): KibbleType {
        return when(type) {
            is KtUserType -> visitUserType(type)
            else -> throw RuntimeException("unknown type: ${type}")
        }
/*
        return if (!type..contains("*") && (type.contains(".") || type.contains("<"))) {
            Kibble.parseSource("val temp: $type").properties[0].type!!
        } else {
            KibbleType(className = type)
        }
*/

        TODO()
    }

    fun visitUserType(type: KtUserType): KibbleType {
        return KibbleType(className = type.referencedName!!)
    }

    fun visitDynamicType(type: KtDynamicType) {
        this.visitTypeElement(type)
    }

    fun visitFunctionType(type: KtFunctionType) {
        this.visitTypeElement(type)
    }

    fun visitSelfType(type: KtSelfType) {
        this.visitTypeElement(type)
    }

    fun visitBinaryWithTypeRHSExpression(expression: KtBinaryExpressionWithTypeRHS) {
        this.visitExpression(expression)
    }

    fun visitStringTemplateExpression(expression: KtStringTemplateExpression) {
        this.visitExpression(expression)
    }

    fun visitNamedDeclaration(declaration: KtNamedDeclaration) {
        this.visitDeclaration(declaration)
    }

    fun visitNullableType(nullableType: KtNullableType) {
        this.visitTypeElement(nullableType)
    }

    fun visitTypeProjection(typeProjection: KtTypeProjection) {
        this.visitKtElement(typeProjection)
    }

    fun visitWhenEntry(jetWhenEntry: KtWhenEntry) {
        this.visitKtElement(jetWhenEntry)
    }

    fun visitIsExpression(expression: KtIsExpression) {
        this.visitExpression(expression)
    }

    fun visitWhenConditionIsPattern(condition: KtWhenConditionIsPattern) {
        this.visitKtElement(condition)
    }

    fun visitWhenConditionInRange(condition: KtWhenConditionInRange) {
        this.visitKtElement(condition)
    }

    fun visitWhenConditionWithExpression(condition: KtWhenConditionWithExpression) {
        this.visitKtElement(condition)
    }

    fun visitStringTemplateEntry(entry: KtStringTemplateEntry) {
        this.visitKtElement(entry)
    }

    fun visitStringTemplateEntryWithExpression(entry: KtStringTemplateEntryWithExpression) {
        this.visitStringTemplateEntry(entry)
    }

    fun visitBlockStringTemplateEntry(entry: KtBlockStringTemplateEntry) {
        this.visitStringTemplateEntryWithExpression(entry)
    }

    fun visitSimpleNameStringTemplateEntry(entry: KtSimpleNameStringTemplateEntry) {
        this.visitStringTemplateEntryWithExpression(entry)
    }

    fun visitLiteralStringTemplateEntry(entry: KtLiteralStringTemplateEntry) {
        this.visitStringTemplateEntry(entry)
    }

    fun visitEscapeStringTemplateEntry(entry: KtEscapeStringTemplateEntry) {
        this.visitStringTemplateEntry(entry)
    }

    fun visitPackageDirective(directive: KtPackageDirective): String {
        return directive.fqName.asString()
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

}