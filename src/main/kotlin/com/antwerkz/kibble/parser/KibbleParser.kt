package com.antwerkz.kibble.parser

import com.antwerkz.kibble.KibbleContext
import com.antwerkz.kibble.model.KibbleFunction
import com.antwerkz.kibble.model.Nameable
import com.antwerkz.kibble.parser.KotlinParser.ClassDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionValueParameterContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionValueParametersContext
import com.antwerkz.kibble.parser.KotlinParser.KotlinFileContext
import com.antwerkz.kibble.parser.KotlinParser.PackageHeaderContext
import com.antwerkz.kibble.parser.KotlinParser.ParameterContext
import com.antwerkz.kibble.parser.KotlinParser.PropertyDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.SimpleIdentifierContext
import com.antwerkz.kibble.parser.KotlinParser.SimpleUserTypeContext
import com.antwerkz.kibble.parser.KotlinParser.TypeContext
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec

class KibbleParser(private val context: KibbleContext) : KotlinParserBaseListener() {
    override fun enterKotlinFile(ctx: KotlinFileContext) {
    }

    override fun enterPackageHeader(ctx: PackageHeaderContext) {
        context.defaultPackageName = ctx.identifier().text
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        context.bookmark("enter function")
        val builder = FunSpec.builder(ctx.simpleIdentifier().text)
        context.push(builder)
    }
    override fun enterFunctionValueParameter(ctx: FunctionValueParameterContext) {
//        context.peek<FunSpec.Builder>().addParameter(
//        )
        super.enterFunctionValueParameter(ctx)
    }
    override fun enterParameter(ctx: ParameterContext?) {
        super.enterParameter(ctx)
    }
    override fun enterType(ctx: TypeContext?) {
        super.enterType(ctx)
    }
    override fun enterSimpleUserType(ctx: SimpleUserTypeContext) {
        context.push(TypeSpec.classBuilder(ctx.simpleIdentifier().text))
    }
    /*
    override fun enterSimpleIdentifier(ctx: SimpleIdentifierContext) {
        context.apply<(String) -> Any>() {
            println("hwat")
        }
        super.enterSimpleIdentifier(ctx)
    }
*/

    override fun enterPropertyDeclaration(ctx: PropertyDeclarationContext) {
        context.bookmark("property ${ctx.variableDeclaration().simpleIdentifier()}")
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
        context.bookmark("class ${ctx.simpleIdentifier()}")
    }
}