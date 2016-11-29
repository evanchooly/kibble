package com.antwerkz.kotlin

import com.antwerkz.kotlin.KotlinParser.AccessModifierContext
import com.antwerkz.kotlin.KotlinParser.ClassDeclarationContext
import com.antwerkz.kotlin.KotlinParser.FunctionDeclarationContext
import com.antwerkz.kotlin.KotlinParser.FunctionParameterContext
import com.antwerkz.kotlin.KotlinParser.ImportHeaderContext
import com.antwerkz.kotlin.KotlinParser.MemberDeclarationContext
import com.antwerkz.kotlin.KotlinParser.ModifiersContext
import com.antwerkz.kotlin.KotlinParser.PackageHeaderContext
import com.antwerkz.kotlin.KotlinParser.ParameterContext
import com.antwerkz.kotlin.KotlinParser.PrimaryConstructorContext
import com.antwerkz.kotlin.model.Import
import com.antwerkz.kotlin.model.KotlinClass
import com.antwerkz.kotlin.model.KotlinFunction
import com.antwerkz.kotlin.model.Modifiable
import com.antwerkz.kotlin.model.Parameter
import org.antlr.v4.runtime.tree.TerminalNode

@Suppress("UNCHECKED_CAST")
class KotlinFileListener : LoggingFileListener(false) {

    @Suppress("UNCHECKED_CAST")
    fun <T> peek(): T {
        try {
            return context.last() as T
        } catch(e: ClassCastException) {
            println("ClassCastException with ${context.last()}")
            throw e
        }
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
        file.imports.add(Import(markExit("ImportHeader")
                .drop(1)
                .joinToString("")))
        super.exitImportHeader(ctx)
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        markEntry("FunctionDeclaration")
        context.add(KotlinFunction())
        super.enterFunctionDeclaration(ctx)
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        val list = markExit("FunctionDeclaration")
        val function = list[0] as KotlinFunction
        function.name = list[2] as String
        var start = list.indexOf("(") + 1

        while ( list[start] != ")") {
            function.parameters.add(list[start++] as Parameter)
        }
        currentClass!!.functions += function
        context.add(function)
        super.exitFunctionDeclaration(ctx)
    }

    override fun enterFunctionParameter(ctx: FunctionParameterContext) {
        markEntry("FunctionParameter")
    }

    override fun exitFunctionParameter(ctx: FunctionParameterContext) {
        val list = markExit("FunctionParameter")
        if (list.size > 1) {
            val parameter = list[1] as Parameter
            parameter.addModifier(list[0] as String)
            context.add(parameter)
        } else if(list[0] is Parameter) {
            context.add(list[0])
        } else {
            throw IllegalStateException("Unknown state for function parameter: $list")
        }
    }

    override fun enterParameter(ctx: ParameterContext) {
        markEntry("Parameter")
        super.enterParameter(ctx)
    }

    override fun exitParameter(ctx: ParameterContext) {
        val params = markExit("Parameter") as List<String>
        context.add(Parameter(name = params[0], type = params[2]))
        super.exitParameter(ctx)
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

    override fun exitModifiers(ctx: ModifiersContext) {
        val modifiers = markExit("Modifiers") as List<String>
        modifiers.forEach {
            val modifiable: Modifiable = peek()
            modifiable.addModifier(it)
        }
        super.exitModifiers(ctx)
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
        super.visitTerminal(node)
        context.add(node?.symbol?.text ?: "")
    }

}