package com.antwerkz.kibble

import com.antwerkz.kibble.KotlinParser.AccessModifierContext
import com.antwerkz.kibble.KotlinParser.ClassDeclarationContext
import com.antwerkz.kibble.KotlinParser.FunctionDeclarationContext
import com.antwerkz.kibble.KotlinParser.FunctionParameterContext
import com.antwerkz.kibble.KotlinParser.IdentifierContext
import com.antwerkz.kibble.KotlinParser.ImportHeaderContext
import com.antwerkz.kibble.KotlinParser.KotlinFileContext
import com.antwerkz.kibble.KotlinParser.MemberDeclarationContext
import com.antwerkz.kibble.KotlinParser.ModifierContext
import com.antwerkz.kibble.KotlinParser.ModifiersContext
import com.antwerkz.kibble.KotlinParser.PackageHeaderContext
import com.antwerkz.kibble.KotlinParser.ParameterContext
import com.antwerkz.kibble.KotlinParser.PrimaryConstructorContext
import com.antwerkz.kibble.KotlinParser.SimpleUserTypeContext
import com.antwerkz.kibble.KotlinParser.SimpleUserType_typeParamContext
import com.antwerkz.kibble.KotlinParser.TypeContext
import com.antwerkz.kibble.KotlinParser.TypeDescriptorContext
import com.antwerkz.kibble.KotlinParser.UserTypeContext
import com.antwerkz.kibble.KotlinParser.ValueParametersContext
import com.antwerkz.kibble.model.FunctionHolder
import com.antwerkz.kibble.model.Import
import com.antwerkz.kibble.model.KotlinClass
import com.antwerkz.kibble.model.KotlinFile
import com.antwerkz.kibble.model.KotlinFunction
import com.antwerkz.kibble.model.Modifiable
import com.antwerkz.kibble.model.Parameter
import org.antlr.v4.runtime.tree.TerminalNode

@Suppress("UNCHECKED_CAST")
class KotlinFileListener(enableLogging: Boolean = false) : LoggingFileListener(enableLogging) {

    @Suppress("UNCHECKED_CAST")
    fun <T> peek(): T {
        try {
            return context.last() as T
        } catch(e: ClassCastException) {
            println("ClassCastException with ${context.last()}")
            throw e
        }
    }

    override fun enterKotlinFile(ctx: KotlinFileContext) {
        context.add(file)
    }

    override fun exitKotlinFile(ctx: KotlinFileContext) {
        context.pop<KotlinFile>()
        super.exitKotlinFile(ctx)
    }

    override fun enterPackageHeader(ctx: PackageHeaderContext) {
        markEntry("PackageHeader")
        super.enterPackageHeader(ctx)
    }

    override fun exitPackageHeader(ctx: PackageHeaderContext) {
        file.pkgName = markExit("PackageHeader")
                .drop(1)
                .joinToString("")
        super.exitPackageHeader(ctx)
    }

    override fun enterImportHeader(ctx: ImportHeaderContext) {
        markEntry("ImportHeader")
        super.enterImportHeader(ctx)
    }

    override fun exitImportHeader(ctx: ImportHeaderContext) {
        val importName = ctx.children
                .filterIsInstance(IdentifierContext::class.java)
                .flatMap { it.children }
                .map { it.text }
                .joinToString(".")
        file.imports.add(Import(importName))
        super.exitImportHeader(ctx)
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        markEntry("FunctionDeclaration")
        context.add(KotlinFunction())
        super.enterFunctionDeclaration(ctx)
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        val function: KotlinFunction
        if (context.peek<KotlinFunction>() is KotlinFunction) {
            function = context.pop()
            val holder: FunctionHolder? = context.peek()
            holder?.let {
                holder += function
            }
        }
        super.exitFunctionDeclaration(ctx)
    }

    override fun enterFunctionParameter(ctx: FunctionParameterContext) {
        markEntry("FunctionParameter")
    }

    override fun exitFunctionParameter(ctx: FunctionParameterContext) {
        val parameter: Parameter = context.pop()
        val function: KotlinFunction?  = context.peek()
        function?.let {
            function += parameter
        }
        super.exitFunctionParameter(ctx)
    }

    override fun exitModifiers(ctx: ModifiersContext) {
        val modifiers = markExit("Modifiers") as List<String>
        modifiers.forEach {
            val modifiable: Modifiable = peek()
            modifiable.addModifier(it)
        }
        super.exitModifiers(ctx)
    }

    override fun exitModifier(ctx: ModifierContext) {
        super.exitModifier(ctx)
    }

    override fun exitValueParameters(ctx: ValueParametersContext) {
        super.exitValueParameters(ctx)
    }

    override fun exitParameter(ctx: ParameterContext) {
        val name = ctx.children
                .filterIsInstance<TerminalNode>()
                .map { it.text }
                .first<String>()
        context.add(Parameter(name = name, type = context.pop()))
        super.exitParameter(ctx)
    }

    override fun exitType(ctx: TypeContext) {
        super.exitType(ctx)
    }

    override fun exitTypeDescriptor(ctx: TypeDescriptorContext) {
        super.exitTypeDescriptor(ctx)
    }

    override fun exitUserType(ctx: UserTypeContext) {
        super.exitUserType(ctx)
    }

    override fun exitSimpleUserType(ctx: SimpleUserTypeContext) {
        context.add(ctx.children
                .filterIsInstance(TerminalNode::class.java)
                .map { it.text }[0]
        )
        super.exitSimpleUserType(ctx)
    }

    override fun exitSimpleUserType_typeParam(ctx: SimpleUserType_typeParamContext) {
        super.exitSimpleUserType_typeParam(ctx)
    }

    /*
        override fun enterModifiers(ctx: ModifiersContext) {
            markEntry("Modifiers")
            super.enterModifiers(ctx)
        }

        override fun exitModifiers(ctx: ModifiersContext) {
            val list = markExit("Modifiers")
            super.exitModifiers(ctx)
        }

    */
    override fun enterModifiers(ctx: ModifiersContext) {
        markEntry("Modifiers")
        super.enterModifiers(ctx)
    }

    override fun enterAccessModifier(ctx: AccessModifierContext) {
        super.enterAccessModifier(ctx)
    }

    override fun enterMemberDeclaration(ctx: MemberDeclarationContext) {
        markEntry("MemberDeclaration")
        super.enterMemberDeclaration(ctx)
    }

    override fun exitMemberDeclaration(ctx: MemberDeclarationContext) {
        markExit("MemberDeclaration")
        super.exitMemberDeclaration(ctx)
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
        markEntry("ClassDeclaration")
        super.enterClassDeclaration(ctx)
        val klass = KotlinClass()
        currentClass = klass
        context.add(klass)
        file.classes.add(currentClass!!)
    }

    override fun exitClassDeclaration(ctx: ClassDeclarationContext) {
        markExit("ClassDeclaration")
        super.exitClassDeclaration(ctx)
    }

    override fun enterPrimaryConstructor(ctx: PrimaryConstructorContext) {
        markEntry("PrimaryConstructor")
        super.enterPrimaryConstructor(ctx)
    }

    override fun exitPrimaryConstructor(ctx: PrimaryConstructorContext) {
        val constructor = KotlinFunction()
        val parameters = markExit("PrimaryConstructor")
                .drop(1)
                .dropLast(1)
                .filterNot { (it is String) && "," == it }
        currentClass?.constructors?.add(constructor)
        constructor.parameters.addAll(parameters as List<Parameter>)
        super.exitPrimaryConstructor(ctx)
    }

    override fun visitTerminal(node: TerminalNode?) {
//        super.visitTerminal(node)
//        context.add(node?.symbol?.text ?: "")
    }

}