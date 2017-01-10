package com.antwerkz.kibble

import com.antwerkz.kibble.KotlinParser.AtomicExpressionContext
import com.antwerkz.kibble.KotlinParser.ClassBodyContext
import com.antwerkz.kibble.KotlinParser.ClassDeclarationContext
import com.antwerkz.kibble.KotlinParser.FunctionDeclarationContext
import com.antwerkz.kibble.KotlinParser.FunctionParameterContext
import com.antwerkz.kibble.KotlinParser.IdentifierContext
import com.antwerkz.kibble.KotlinParser.ImportHeaderContext
import com.antwerkz.kibble.KotlinParser.KotlinFileContext
import com.antwerkz.kibble.KotlinParser.ModifiersContext
import com.antwerkz.kibble.KotlinParser.PackageHeaderContext
import com.antwerkz.kibble.KotlinParser.ParameterContext
import com.antwerkz.kibble.KotlinParser.PrimaryConstructorContext
import com.antwerkz.kibble.KotlinParser.PropertyDeclarationContext
import com.antwerkz.kibble.KotlinParser.SimpleUserTypeContext
import com.antwerkz.kibble.KotlinParser.TypeContext
import com.antwerkz.kibble.KotlinParser.TypeDescriptorContext
import com.antwerkz.kibble.KotlinParser.VariableDeclarationEntryContext
import com.antwerkz.kibble.model.Constructor
import com.antwerkz.kibble.model.FunctionHolder
import com.antwerkz.kibble.model.Import
import com.antwerkz.kibble.model.KotlinClass
import com.antwerkz.kibble.model.KotlinFile
import com.antwerkz.kibble.model.KotlinFunction
import com.antwerkz.kibble.model.Parameter
import com.antwerkz.kibble.model.ParameterHolder
import com.antwerkz.kibble.model.Type
import org.antlr.v4.runtime.tree.TerminalNode

class KotlinFileListener(enableLogging: Boolean = false) : LoggingFileListener(enableLogging) {
    override fun exitPropertyDeclaration(ctx: PropertyDeclarationContext) {
        val name = ctx.children.filterIsInstance(VariableDeclarationEntryContext::class.java)
                .first()
                .children[0].text
//        val property = KotlinProperty(name, pop<Type>())
//        (ctx.children[0] as ModifiersContext).children?.forEach {
//            property.addModifier(it.text)
//        }
        super.exitPropertyDeclaration(ctx)
    }

    override fun enterKotlinFile(ctx: KotlinFileContext) {
        push(file)
    }

    override fun exitKotlinFile(ctx: KotlinFileContext) {
        pop<KotlinFile>()
        super.exitKotlinFile(ctx)
    }

    override fun exitPackageHeader(ctx: PackageHeaderContext) {
        file.pkgName = ctx.children
                .filterIsInstance(IdentifierContext::class.java)
                .flatMap { it.children }
                .map { it.text }
                .joinToString(".")

        super.exitPackageHeader(ctx)
    }

    override fun exitImportHeader(ctx: ImportHeaderContext) {
        file.imports.add(Import(ctx.children
                .filterIsInstance(IdentifierContext::class.java)
                .flatMap { it.children }
                .map { it.text }
                .joinToString(".")))
        super.exitImportHeader(ctx)
    }

    override fun enterClassBody(ctx: ClassBodyContext) {
        super.enterClassBody(ctx)
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        push(KotlinFunction())
        super.enterFunctionDeclaration(ctx)
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        val function: KotlinFunction = pop()
        if (peek<FunctionHolder>() is FunctionHolder) {
            val holder: FunctionHolder? = peek<FunctionHolder>()
            holder?.let {
                holder += function
            }
        } else {
            throw IllegalStateException("Did not find a function holder for ${function.name}: ${pop<Any>()} ")
        }
        super.exitFunctionDeclaration(ctx)
    }

    override fun exitFunctionParameter(ctx: FunctionParameterContext) {
        val parameter: Parameter = pop()
        if (peek<ParameterHolder>() is ParameterHolder) {
            peek<ParameterHolder>()?.let {
                it += parameter
            }
        }
        super.exitFunctionParameter(ctx)
    }

    override fun exitModifiers(ctx: ModifiersContext) {
        super.exitModifiers(ctx)
    }

    override fun exitParameter(ctx: ParameterContext) {
        val name = ctx.children
                .filterIsInstance<TerminalNode>()
                .map { it.text }
                .first<String>()
        push(Parameter(name = name, type = pop()))
        super.exitParameter(ctx)
    }

    override fun exitSimpleUserType(ctx: SimpleUserTypeContext) {
        push(ctx.children
                .filterIsInstance(TerminalNode::class.java)
                .map { it.text }[0]
        )
        super.exitSimpleUserType(ctx)
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
        super.enterClassDeclaration(ctx)
        val klass = KotlinClass()
        currentClass = klass
        push(klass)
        file.classes.add(currentClass!!)
    }

    override fun enterPrimaryConstructor(ctx: PrimaryConstructorContext) {
        push(Constructor())
        super.enterPrimaryConstructor(ctx)
    }

    override fun exitPrimaryConstructor(ctx: PrimaryConstructorContext) {
        currentClass?.constructor = pop()
        super.exitPrimaryConstructor(ctx)
    }

    override fun exitAtomicExpression(ctx: AtomicExpressionContext) {
        super.exitAtomicExpression(ctx)
    }

    override fun exitType(ctx: TypeContext) {
        val top = pop<Any>()
        val type = when {
            top is String -> Type(top)
            top is Type -> top
            else -> throw IllegalStateException("Unknown state for Type: ${top.javaClass}")
        }
        if ( peek<String>() is String) {
            type.name = pop<String>()
        }
        while ( peek<Type>() is Type) {
            type += pop<Type>()
        }
        push(type)
        super.exitType(ctx)
    }

    override fun exitTypeDescriptor(ctx: TypeDescriptorContext) {
        push(Type(pop<String>()))
        super.exitTypeDescriptor(ctx)
    }
}
