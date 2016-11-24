package com.antwerkz.kotlin

import com.antwerkz.kotlin.KotlinParser.ClassDeclarationContext
import com.antwerkz.kotlin.KotlinParser.FunctionDeclarationContext
import com.antwerkz.kotlin.KotlinParser.ImportHeaderContext
import com.antwerkz.kotlin.KotlinParser.MemberDeclarationContext
import com.antwerkz.kotlin.KotlinParser.ModifierContext
import com.antwerkz.kotlin.KotlinParser.PackageHeaderContext
import com.antwerkz.kotlin.KotlinParser.ParameterContext
import com.antwerkz.kotlin.KotlinParser.PrimaryConstructorContext
import com.antwerkz.kotlin.model.Import
import com.antwerkz.kotlin.model.KotlinClass
import com.antwerkz.kotlin.model.KotlinFile
import com.antwerkz.kotlin.model.KotlinFunction
import com.antwerkz.kotlin.model.Modifiable
import com.antwerkz.kotlin.model.Parameter
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.Stack

class KotlinFileListener : LoggingFileListener() {

    @Suppress("UNCHECKED_CAST")
    fun <T> peek(): T {
        return context.peek() as T
    }

    override fun exitPackageHeader(ctx: PackageHeaderContext) {
        super.exitPackageHeader(ctx)
        file.pkgName = terminals
                .popWhile { it != "package" }
                .joinToString("")
    }

    override fun exitImportHeader(ctx: ImportHeaderContext) {
        super.exitImportHeader(ctx)
        file.imports.add(Import(terminals
                .popWhile { it != "import" }
                .joinToString("")))
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        super.enterFunctionDeclaration(ctx)
        val function = KotlinFunction(ctx.SimpleName().symbol.text)
        currentClass!!.functions += function
        context.push(function)
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        super.exitFunctionDeclaration(ctx)
        context.pop()
    }

    override fun exitParameter(ctx: ParameterContext) {
        super.exitParameter(ctx)
        val params = terminals.pop(4)
        parameters.add(Parameter(params[0], params[1], params[3]))
    }

    override fun exitModifier(ctx: ModifierContext) {
        super.exitModifier(ctx)
        val element = ctx?.start?.text
        if (element != null) {
            val modifiable: Modifiable = peek()
            modifiable.addModifier(element)
        }
    }

    override fun enterMemberDeclaration(ctx: MemberDeclarationContext) {
        super.enterMemberDeclaration(ctx)
        terminals.add("MEMBERDECLARATION")
    }

    override fun exitMemberDeclaration(ctx: MemberDeclarationContext) {
        super.exitMemberDeclaration(ctx)
        val list = terminals.popWhile { it != "MEMBERDECLARATION" }
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
        super.enterClassDeclaration(ctx)
        currentClass = KotlinClass()
        context.push(currentClass)
        file.classes.add(currentClass!!)
    }

    override fun enterPrimaryConstructor(ctx: PrimaryConstructorContext) {
        super.enterPrimaryConstructor(ctx)
    }

    override fun exitPrimaryConstructor(ctx: PrimaryConstructorContext) {
        super.exitPrimaryConstructor(ctx)
        val constructor = KotlinFunction(currentClass!!.name)
        currentClass?.constructors?.add(constructor)
        constructor.parameters.addAll(parameters)
        parameters.clear()
    }

    override fun visitTerminal(node: TerminalNode?) {
        super.visitTerminal(node)
        terminals.add(node?.symbol?.text ?: "")
    }

}

fun <T> MutableList<T>.pop(count: Int = 1): List<T> {
    val items = takeLast(count)
    var i = count
    while (i > 0) {
        removeAt(size - i)
        i--
    }

    return items
}

fun <T> MutableList<T>.popWhile(predicate: (T) -> Boolean): List<T> {
    val items = takeLastWhile(predicate)
    var i = items.size + 1
    while (i > 0) {
        removeAt(size - i)
        i--
    }

    return items
}
