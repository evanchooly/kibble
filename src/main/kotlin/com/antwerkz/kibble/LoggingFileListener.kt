package com.antwerkz.kibble

import com.antwerkz.kibble.KotlinParser.AccessModifierContext
import com.antwerkz.kibble.KotlinParser.AdditiveExpressionContext
import com.antwerkz.kibble.KotlinParser.AdditiveOperationContext
import com.antwerkz.kibble.KotlinParser.AnnotatedLambdaContext
import com.antwerkz.kibble.KotlinParser.AnnotationContext
import com.antwerkz.kibble.KotlinParser.AnnotationUseSiteTargetContext
import com.antwerkz.kibble.KotlinParser.AnnotationsContext
import com.antwerkz.kibble.KotlinParser.AnonymousInitializerContext
import com.antwerkz.kibble.KotlinParser.ArrayAccessContext
import com.antwerkz.kibble.KotlinParser.AssignmentOperatorContext
import com.antwerkz.kibble.KotlinParser.AtomicExpressionContext
import com.antwerkz.kibble.KotlinParser.BlockContext
import com.antwerkz.kibble.KotlinParser.CallSuffixContext
import com.antwerkz.kibble.KotlinParser.CallableReferenceContext
import com.antwerkz.kibble.KotlinParser.CatchBlockContext
import com.antwerkz.kibble.KotlinParser.ClassBodyContext
import com.antwerkz.kibble.KotlinParser.ClassDeclarationContext
import com.antwerkz.kibble.KotlinParser.ClassModifierContext
import com.antwerkz.kibble.KotlinParser.CompanionObjectContext
import com.antwerkz.kibble.KotlinParser.ComparisonContext
import com.antwerkz.kibble.KotlinParser.ComparisonOperationContext
import com.antwerkz.kibble.KotlinParser.ConjunctionContext
import com.antwerkz.kibble.KotlinParser.ConstructorDelegationCallContext
import com.antwerkz.kibble.KotlinParser.ConstructorInvocationContext
import com.antwerkz.kibble.KotlinParser.DeclarationContext
import com.antwerkz.kibble.KotlinParser.DelegationSpecifierContext
import com.antwerkz.kibble.KotlinParser.DisjunctionContext
import com.antwerkz.kibble.KotlinParser.DoWhileLoopContext
import com.antwerkz.kibble.KotlinParser.ElvisExpressionContext
import com.antwerkz.kibble.KotlinParser.EnumClassBodyContext
import com.antwerkz.kibble.KotlinParser.EnumEntriesContext
import com.antwerkz.kibble.KotlinParser.EnumEntryContext
import com.antwerkz.kibble.KotlinParser.EqualityComparisonContext
import com.antwerkz.kibble.KotlinParser.EqualityOperationContext
import com.antwerkz.kibble.KotlinParser.ExplicitDelegationContext
import com.antwerkz.kibble.KotlinParser.ExpressionContext
import com.antwerkz.kibble.KotlinParser.ExtensionFunctionTypeNoReceiverContext
import com.antwerkz.kibble.KotlinParser.FinallyBlockContext
import com.antwerkz.kibble.KotlinParser.ForLoopContext
import com.antwerkz.kibble.KotlinParser.FunctionBodyContext
import com.antwerkz.kibble.KotlinParser.FunctionDeclarationContext
import com.antwerkz.kibble.KotlinParser.FunctionLiteralContext
import com.antwerkz.kibble.KotlinParser.FunctionParameterContext
import com.antwerkz.kibble.KotlinParser.FunctionTypeNoReceiverContext
import com.antwerkz.kibble.KotlinParser.FunctionType_paramOrTypeContext
import com.antwerkz.kibble.KotlinParser.GetterContext
import com.antwerkz.kibble.KotlinParser.HierarchyModifierContext
import com.antwerkz.kibble.KotlinParser.IdentifierContext
import com.antwerkz.kibble.KotlinParser.IfExpressionContext
import com.antwerkz.kibble.KotlinParser.ImportHeaderContext
import com.antwerkz.kibble.KotlinParser.InOperationContext
import com.antwerkz.kibble.KotlinParser.InfixFunctionCallContext
import com.antwerkz.kibble.KotlinParser.IsOperationContext
import com.antwerkz.kibble.KotlinParser.JumpContext
import com.antwerkz.kibble.KotlinParser.KotlinFileContext
import com.antwerkz.kibble.KotlinParser.LabelDefinitionContext
import com.antwerkz.kibble.KotlinParser.LabelReferenceContext
import com.antwerkz.kibble.KotlinParser.LiteralConstantContext
import com.antwerkz.kibble.KotlinParser.LoopContext
import com.antwerkz.kibble.KotlinParser.MemberAccessOperationContext
import com.antwerkz.kibble.KotlinParser.MemberDeclarationContext
import com.antwerkz.kibble.KotlinParser.MembersContext
import com.antwerkz.kibble.KotlinParser.ModifierContext
import com.antwerkz.kibble.KotlinParser.ModifierKeywordContext
import com.antwerkz.kibble.KotlinParser.ModifiersContext
import com.antwerkz.kibble.KotlinParser.MultipleVariableDeclarationsContext
import com.antwerkz.kibble.KotlinParser.MultiplicativeExpressionContext
import com.antwerkz.kibble.KotlinParser.MultiplicativeOperationContext
import com.antwerkz.kibble.KotlinParser.NamedInfixContext
import com.antwerkz.kibble.KotlinParser.ObjectDeclarationContext
import com.antwerkz.kibble.KotlinParser.ObjectLiteralContext
import com.antwerkz.kibble.KotlinParser.OptionalProjectionContext
import com.antwerkz.kibble.KotlinParser.PackageHeaderContext
import com.antwerkz.kibble.KotlinParser.ParameterContext
import com.antwerkz.kibble.KotlinParser.PostfixUnaryExpressionContext
import com.antwerkz.kibble.KotlinParser.PostfixUnaryOperationContext
import com.antwerkz.kibble.KotlinParser.PreambleContext
import com.antwerkz.kibble.KotlinParser.PrefixUnaryExpressionContext
import com.antwerkz.kibble.KotlinParser.PrefixUnaryOperationContext
import com.antwerkz.kibble.KotlinParser.PrimaryConstructorContext
import com.antwerkz.kibble.KotlinParser.PropertyDeclarationContext
import com.antwerkz.kibble.KotlinParser.RangeExpressionContext
import com.antwerkz.kibble.KotlinParser.SecondaryConstructorContext
import com.antwerkz.kibble.KotlinParser.SetterContext
import com.antwerkz.kibble.KotlinParser.SimpleUserTypeContext
import com.antwerkz.kibble.KotlinParser.SimpleUserType_typeParamContext
import com.antwerkz.kibble.KotlinParser.StatementContext
import com.antwerkz.kibble.KotlinParser.StatementsContext
import com.antwerkz.kibble.KotlinParser.StringLiteralContext
import com.antwerkz.kibble.KotlinParser.SupertypesSpecifiersContext
import com.antwerkz.kibble.KotlinParser.ToplevelObjectContext
import com.antwerkz.kibble.KotlinParser.TryExpressionContext
import com.antwerkz.kibble.KotlinParser.TypeArgumentsContext
import com.antwerkz.kibble.KotlinParser.TypeConstraintContext
import com.antwerkz.kibble.KotlinParser.TypeConstraintsContext
import com.antwerkz.kibble.KotlinParser.TypeContext
import com.antwerkz.kibble.KotlinParser.TypeDescriptorContext
import com.antwerkz.kibble.KotlinParser.TypeOperationContext
import com.antwerkz.kibble.KotlinParser.TypeParameterContext
import com.antwerkz.kibble.KotlinParser.TypeParametersContext
import com.antwerkz.kibble.KotlinParser.TypeRHSContext
import com.antwerkz.kibble.KotlinParser.UnescapedAnnotationContext
import com.antwerkz.kibble.KotlinParser.UserTypeContext
import com.antwerkz.kibble.KotlinParser.ValueArgumentContext
import com.antwerkz.kibble.KotlinParser.ValueArgumentsContext
import com.antwerkz.kibble.KotlinParser.ValueParametersContext
import com.antwerkz.kibble.KotlinParser.VariableDeclarationEntryContext
import com.antwerkz.kibble.KotlinParser.VarianceAnnotationContext
import com.antwerkz.kibble.KotlinParser.WhenConditionContext
import com.antwerkz.kibble.KotlinParser.WhenContext
import com.antwerkz.kibble.KotlinParser.WhenEntryContext
import com.antwerkz.kibble.KotlinParser.WhileLoopContext
import com.antwerkz.kibble.model.KotlinClass
import com.antwerkz.kibble.model.KotlinFile
import com.antwerkz.kibble.model.Type
import com.antwerkz.kibble.model.TypeDescriptor
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

open class LoggingFileListener(var log: Boolean = false): KotlinParserBaseListener() {
    companion object {
        private val INDENT = "  "
    }

    var level = 0
    val file = KotlinFile()
    var currentClass: KotlinClass? = null
    private val stack = mutableListOf<Any>()

    fun logEntry(name: String, line: Int) {
        if (log) {
            (0..level).forEach { print(INDENT) }
            println("-> $name:$line:$stack")
            level += 1
        }
    }

    fun logExit(name: String, line: Int) {
        if (log) {
            level -= 1
            (0..level).forEach { print(INDENT) }
            println("<- $name:$line:$stack")
        }
    }

    override fun enterKotlinFile(ctx: KotlinFileContext) {
        logEntry("KotlinFile", ctx.start.line)
    }

    override fun exitKotlinFile(ctx: KotlinFileContext) {
        logExit("KotlinFile", ctx.stop.line)
    }

    override fun enterPreamble(ctx: PreambleContext) {
        logEntry("Preamble", ctx.start.line)
    }

    override fun exitPreamble(ctx: PreambleContext) {
        logExit("Preamble", ctx.stop.line)
    }

    override fun enterPackageHeader(ctx: PackageHeaderContext) {
        logEntry("PackageHeader", ctx.start.line)
    }

    override fun exitPackageHeader(ctx: PackageHeaderContext) {
        logExit("PackageHeader", ctx.stop.line)
    }

    override fun enterImportHeader(ctx: ImportHeaderContext) {
        logEntry("ImportHeader", ctx.start.line)
    }

    override fun exitImportHeader(ctx: ImportHeaderContext) {
        logExit("ImportHeader", ctx.stop.line)
    }

    override fun enterToplevelObject(ctx: ToplevelObjectContext) {
        logEntry("ToplevelObject", ctx.start.line)
    }

    override fun exitToplevelObject(ctx: ToplevelObjectContext) {
        logExit("ToplevelObject", ctx.stop.line)
    }

    override fun enterMemberDeclaration(ctx: MemberDeclarationContext) {
        logEntry("MemberDeclaration", ctx.start.line)
    }

    override fun exitMemberDeclaration(ctx: MemberDeclarationContext) {
        logExit("MemberDeclaration", ctx.stop.line)
    }

    override fun enterOptionalProjection(ctx: OptionalProjectionContext) {
        logEntry("OptionalProjection", ctx.start.line)
    }

    override fun exitOptionalProjection(ctx: OptionalProjectionContext) {
        logExit("OptionalProjection", ctx.stop.line)
    }

    override fun enterTypeParameters(ctx: TypeParametersContext) {
        logEntry("TypeParameters", ctx.start.line)
    }

    override fun exitTypeParameters(ctx: TypeParametersContext) {
        logExit("TypeParameters", ctx.stop.line)
    }

    override fun enterTypeParameter(ctx: TypeParameterContext) {
        logEntry("TypeParameter", ctx.start.line)
    }

    override fun exitTypeParameter(ctx: TypeParameterContext) {
        logExit("TypeParameter", ctx.stop.line)
    }

    override fun enterTypeArguments(ctx: TypeArgumentsContext) {
        logEntry("TypeArguments", ctx.start.line)
    }

    override fun exitTypeArguments(ctx: TypeArgumentsContext) {
        logExit("TypeArguments", ctx.stop.line)
    }

    override fun enterTypeConstraints(ctx: TypeConstraintsContext) {
        logEntry("TypeConstraints", ctx.start.line)
    }

    override fun exitTypeConstraints(ctx: TypeConstraintsContext) {
        logExit("TypeConstraints", ctx.stop.line)
    }

    override fun enterTypeConstraint(ctx: TypeConstraintContext) {
        logEntry("TypeConstraint", ctx.start.line)
    }

    override fun exitTypeConstraint(ctx: TypeConstraintContext) {
        logExit("TypeConstraint", ctx.stop.line)
    }

    override fun enterType(ctx: TypeContext) {
        logEntry("Type", ctx.start.line)
    }

    override fun exitType(ctx: TypeContext) {
        logExit("Type", ctx.stop.line)
    }

    override fun enterTypeDescriptor(ctx: TypeDescriptorContext) {
        logEntry("TypeDescriptor", ctx.start.line)
    }

    override fun exitTypeDescriptor(ctx: TypeDescriptorContext) {
        logExit("TypeDescriptor", ctx.stop.line)
    }

    override fun enterUserType(ctx: UserTypeContext) {
        logEntry("UserType", ctx.start.line)
    }

    override fun exitUserType(ctx: UserTypeContext) {
        logExit("UserType", ctx.stop.line)
    }

    override fun enterSimpleUserType_typeParam(ctx: SimpleUserType_typeParamContext) {
        logEntry("SimpleUserType_typeParam", ctx.start.line)
    }

    override fun exitSimpleUserType_typeParam(ctx: SimpleUserType_typeParamContext) {
        logExit("SimpleUserType_typeParam", ctx.stop.line)
    }

    override fun enterSimpleUserType(ctx: SimpleUserTypeContext) {
        logEntry("SimpleUserType", ctx.start.line)
    }

    override fun exitSimpleUserType(ctx: SimpleUserTypeContext) {
        logExit("SimpleUserType", ctx.stop.line)
    }

    override fun enterVariableDeclarationEntry(ctx: VariableDeclarationEntryContext) {
        logEntry("VariableDeclarationEntry", ctx.start.line)
    }

    override fun exitVariableDeclarationEntry(ctx: VariableDeclarationEntryContext) {
        logExit("VariableDeclarationEntry", ctx.stop.line)
    }

    override fun enterFunctionType_paramOrType(ctx: FunctionType_paramOrTypeContext) {
        logEntry("FunctionType_paramOrType", ctx.start.line)
    }

    override fun exitFunctionType_paramOrType(ctx: FunctionType_paramOrTypeContext) {
        logExit("FunctionType_paramOrType", ctx.stop.line)
    }

    override fun enterExtensionFunctionTypeNoReceiver(ctx: ExtensionFunctionTypeNoReceiverContext) {
        logEntry("ExtensionFunctionTypeNoReceiver", ctx.start.line)
    }

    override fun exitExtensionFunctionTypeNoReceiver(ctx: ExtensionFunctionTypeNoReceiverContext) {
        logExit("ExtensionFunctionTypeNoReceiver", ctx.stop.line)
    }

    override fun enterFunctionTypeNoReceiver(ctx: FunctionTypeNoReceiverContext) {
        logEntry("FunctionTypeNoReceiver", ctx.start.line)
    }

    override fun exitFunctionTypeNoReceiver(ctx: FunctionTypeNoReceiverContext) {
        logExit("FunctionTypeNoReceiver", ctx.stop.line)
    }

    override fun enterMultipleVariableDeclarations(ctx: MultipleVariableDeclarationsContext) {
        logEntry("MultipleVariableDeclarations", ctx.start.line)
    }

    override fun exitMultipleVariableDeclarations(ctx: MultipleVariableDeclarationsContext) {
        logExit("MultipleVariableDeclarations", ctx.stop.line)
    }

    override fun enterPropertyDeclaration(ctx: PropertyDeclarationContext) {
        logEntry("PropertyDeclaration", ctx.start.line)
    }

    override fun exitPropertyDeclaration(ctx: PropertyDeclarationContext) {
        logExit("PropertyDeclaration", ctx.stop.line)
    }

    override fun enterGetter(ctx: GetterContext) {
        logEntry("Getter", ctx.start.line)
    }

    override fun exitGetter(ctx: GetterContext) {
        logExit("Getter", ctx.stop.line)
    }

    override fun enterSetter(ctx: SetterContext) {
        logEntry("Setter", ctx.start.line)
    }

    override fun exitSetter(ctx: SetterContext) {
        logExit("Setter", ctx.stop.line)
    }

    override fun enterModifiers(ctx: ModifiersContext) {
        logEntry("Modifiers", ctx.start.line)
    }

    override fun exitModifiers(ctx: ModifiersContext) {
        logExit("Modifiers", ctx.stop.line)
    }

    override fun enterModifier(ctx: ModifierContext) {
        logEntry("Modifier", ctx.start.line)
    }

    override fun exitModifier(ctx: ModifierContext) {
        logExit("Modifier", ctx.stop.line)
    }

    override fun enterModifierKeyword(ctx: ModifierKeywordContext) {
        logEntry("ModifierKeyword", ctx.start.line)
    }

    override fun exitModifierKeyword(ctx: ModifierKeywordContext) {
        logExit("ModifierKeyword", ctx.stop.line)
    }

    override fun enterHierarchyModifier(ctx: HierarchyModifierContext) {
        logEntry("HierarchyModifier", ctx.start.line)
    }

    override fun exitHierarchyModifier(ctx: HierarchyModifierContext) {
        logExit("HierarchyModifier", ctx.stop.line)
    }

    override fun enterClassModifier(ctx: ClassModifierContext) {
        logEntry("ClassModifier", ctx.start.line)
    }

    override fun exitClassModifier(ctx: ClassModifierContext) {
        logExit("ClassModifier", ctx.stop.line)
    }

    override fun enterAccessModifier(ctx: AccessModifierContext) {
        logEntry("AccessModifier", ctx.start.line)
    }

    override fun exitAccessModifier(ctx: AccessModifierContext) {
        logExit("AccessModifier", ctx.stop.line)
    }

    override fun enterVarianceAnnotation(ctx: VarianceAnnotationContext) {
        logEntry("VarianceAnnotation", ctx.start.line)
    }

    override fun exitVarianceAnnotation(ctx: VarianceAnnotationContext) {
        logExit("VarianceAnnotation", ctx.stop.line)
    }

    override fun enterAnnotations(ctx: AnnotationsContext) {
        logEntry("Annotations", ctx.start.line)
    }

    override fun exitAnnotations(ctx: AnnotationsContext) {
        logExit("Annotations", ctx.stop.line)
    }

    override fun enterAnnotation(ctx: AnnotationContext) {
        logEntry("Annotation", ctx.start.line)
    }

    override fun exitAnnotation(ctx: AnnotationContext) {
        logExit("Annotation", ctx.stop.line)
    }

    override fun enterAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext) {
        logEntry("AnnotationUseSiteTarget", ctx.start.line)
    }

    override fun exitAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext) {
        logExit("AnnotationUseSiteTarget", ctx.stop.line)
    }

    override fun enterValueArgument(ctx: ValueArgumentContext) {
        logEntry("ValueArgument", ctx.start.line)
    }

    override fun exitValueArgument(ctx: ValueArgumentContext) {
        logExit("ValueArgument", ctx.stop.line)
    }

    override fun enterValueArguments(ctx: ValueArgumentsContext) {
        logEntry("ValueArguments", ctx.start.line)
    }

    override fun exitValueArguments(ctx: ValueArgumentsContext) {
        logExit("ValueArguments", ctx.stop.line)
    }

    override fun enterUnescapedAnnotation(ctx: UnescapedAnnotationContext) {
        logEntry("UnescapedAnnotation", ctx.start.line)
    }

    override fun exitUnescapedAnnotation(ctx: UnescapedAnnotationContext) {
        logExit("UnescapedAnnotation", ctx.stop.line)
    }

    override fun enterJump(ctx: JumpContext) {
        logEntry("Jump", ctx.start.line)
    }

    override fun exitJump(ctx: JumpContext) {
        logExit("Jump", ctx.stop.line)
    }

    override fun enterLabelReference(ctx: LabelReferenceContext) {
        logEntry("LabelReference", ctx.start.line)
    }

    override fun exitLabelReference(ctx: LabelReferenceContext) {
        logExit("LabelReference", ctx.stop.line)
    }

    override fun enterLabelDefinition(ctx: LabelDefinitionContext) {
        logEntry("LabelDefinition", ctx.start.line)
    }

    override fun exitLabelDefinition(ctx: LabelDefinitionContext) {
        logExit("LabelDefinition", ctx.stop.line)
    }

    override fun enterParameter(ctx: ParameterContext) {
        logEntry("Parameter", ctx.start.line)
    }

    override fun exitParameter(ctx: ParameterContext) {
        logExit("Parameter", ctx.stop.line)
    }

    override fun enterFunctionParameter(ctx: FunctionParameterContext) {
        logEntry("FunctionParameter", ctx.start.line)
    }

    override fun exitFunctionParameter(ctx: FunctionParameterContext) {
        logExit("FunctionParameter", ctx.stop.line)
    }

    override fun enterPrimaryConstructor(ctx: PrimaryConstructorContext) {
        logEntry("PrimaryConstructor", ctx.start.line)
    }

    override fun exitPrimaryConstructor(ctx: PrimaryConstructorContext) {
        logExit("PrimaryConstructor", ctx.stop.line)
    }

    override fun enterSecondaryConstructor(ctx: SecondaryConstructorContext) {
        logEntry("SecondaryConstructor", ctx.start.line)
    }

    override fun exitSecondaryConstructor(ctx: SecondaryConstructorContext) {
        logExit("SecondaryConstructor", ctx.stop.line)
    }

    override fun enterConstructorDelegationCall(ctx: ConstructorDelegationCallContext) {
        logEntry("ConstructorDelegationCall", ctx.start.line)
    }

    override fun exitConstructorDelegationCall(ctx: ConstructorDelegationCallContext) {
        logExit("ConstructorDelegationCall", ctx.stop.line)
    }

    override fun enterCallSuffix(ctx: CallSuffixContext) {
        logEntry("CallSuffix", ctx.start.line)
    }

    override fun exitCallSuffix(ctx: CallSuffixContext) {
        logExit("CallSuffix", ctx.stop.line)
    }

    override fun enterConstructorInvocation(ctx: ConstructorInvocationContext) {
        logEntry("ConstructorInvocation", ctx.start.line)
    }

    override fun exitConstructorInvocation(ctx: ConstructorInvocationContext) {
        logExit("ConstructorInvocation", ctx.stop.line)
    }

    override fun enterExplicitDelegation(ctx: ExplicitDelegationContext) {
        logEntry("ExplicitDelegation", ctx.start.line)
    }

    override fun exitExplicitDelegation(ctx: ExplicitDelegationContext) {
        logExit("ExplicitDelegation", ctx.stop.line)
    }

    override fun enterDelegationSpecifier(ctx: DelegationSpecifierContext) {
        logEntry("DelegationSpecifier", ctx.start.line)
    }

    override fun exitDelegationSpecifier(ctx: DelegationSpecifierContext) {
        logExit("DelegationSpecifier", ctx.stop.line)
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
        logEntry("ClassDeclaration", ctx.start.line)
    }

    override fun exitClassDeclaration(ctx: ClassDeclarationContext) {
        logExit("ClassDeclaration", ctx.stop.line)
    }

    override fun enterSupertypesSpecifiers(ctx: SupertypesSpecifiersContext) {
        logEntry("SupertypesSpecifiers", ctx.start.line)
    }

    override fun exitSupertypesSpecifiers(ctx: SupertypesSpecifiersContext) {
        logExit("SupertypesSpecifiers", ctx.stop.line)
    }

    override fun enterObjectDeclaration(ctx: ObjectDeclarationContext) {
        logEntry("ObjectDeclaration", ctx.start.line)
    }

    override fun exitObjectDeclaration(ctx: ObjectDeclarationContext) {
        logExit("ObjectDeclaration", ctx.stop.line)
    }

    override fun enterCompanionObject(ctx: CompanionObjectContext) {
        logEntry("CompanionObject", ctx.start.line)
    }

    override fun exitCompanionObject(ctx: CompanionObjectContext) {
        logExit("CompanionObject", ctx.stop.line)
    }

    override fun enterClassBody(ctx: ClassBodyContext) {
        logEntry("ClassBody", ctx.start.line)
    }

    override fun exitClassBody(ctx: ClassBodyContext) {
        logExit("ClassBody", ctx.stop.line)
    }

    override fun enterMembers(ctx: MembersContext) {
        logEntry("Members", ctx.start.line)
    }

    override fun exitMembers(ctx: MembersContext) {
        logExit("Members", ctx.stop.line)
    }

    override fun enterValueParameters(ctx: ValueParametersContext) {
        logEntry("ValueParameters", ctx.start.line)
    }

    override fun exitValueParameters(ctx: ValueParametersContext) {
        logExit("ValueParameters", ctx.stop.line)
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        logEntry("FunctionDeclaration", ctx.start.line)
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        logExit("FunctionDeclaration", ctx.stop.line)
    }

    override fun enterStatements(ctx: StatementsContext) {
        logEntry("Statements", ctx.start.line)
    }

    override fun exitStatements(ctx: StatementsContext) {
        logExit("Statements", ctx.stop.line)
    }

    override fun enterFunctionBody(ctx: FunctionBodyContext) {
        logEntry("FunctionBody", ctx.start.line)
    }

    override fun exitFunctionBody(ctx: FunctionBodyContext) {
        logExit("FunctionBody", ctx.stop.line)
    }

    override fun enterBlock(ctx: BlockContext) {
        logEntry("Block", ctx.start.line)
    }

    override fun exitBlock(ctx: BlockContext) {
        logExit("Block", ctx.stop.line)
    }

    override fun enterAnonymousInitializer(ctx: AnonymousInitializerContext) {
        logEntry("AnonymousInitializer", ctx.start.line)
    }

    override fun exitAnonymousInitializer(ctx: AnonymousInitializerContext) {
        logExit("AnonymousInitializer", ctx.stop.line)
    }

    override fun enterEnumClassBody(ctx: EnumClassBodyContext) {
        logEntry("EnumClassBody", ctx.start.line)
    }

    override fun exitEnumClassBody(ctx: EnumClassBodyContext) {
        logExit("EnumClassBody", ctx.stop.line)
    }

    override fun enterEnumEntries(ctx: EnumEntriesContext) {
        logEntry("EnumEntries", ctx.start.line)
    }

    override fun exitEnumEntries(ctx: EnumEntriesContext) {
        logExit("EnumEntries", ctx.stop.line)
    }

    override fun enterEnumEntry(ctx: EnumEntryContext) {
        logEntry("EnumEntry", ctx.start.line)
    }

    override fun exitEnumEntry(ctx: EnumEntryContext) {
        logExit("EnumEntry", ctx.stop.line)
    }

    override fun enterIfExpression(ctx: IfExpressionContext) {
        logEntry("IfExpression", ctx.start.line)
    }

    override fun exitIfExpression(ctx: IfExpressionContext) {
        logExit("IfExpression", ctx.stop.line)
    }

    override fun enterTryExpression(ctx: TryExpressionContext) {
        logEntry("TryExpression", ctx.start.line)
    }

    override fun exitTryExpression(ctx: TryExpressionContext) {
        logExit("TryExpression", ctx.stop.line)
    }

    override fun enterCatchBlock(ctx: CatchBlockContext) {
        logEntry("CatchBlock", ctx.start.line)
    }

    override fun exitCatchBlock(ctx: CatchBlockContext) {
        logExit("CatchBlock", ctx.stop.line)
    }

    override fun enterFinallyBlock(ctx: FinallyBlockContext) {
        logEntry("FinallyBlock", ctx.start.line)
    }

    override fun exitFinallyBlock(ctx: FinallyBlockContext) {
        logExit("FinallyBlock", ctx.stop.line)
    }

    override fun enterLoop(ctx: LoopContext) {
        logEntry("Loop", ctx.start.line)
    }

    override fun exitLoop(ctx: LoopContext) {
        logExit("Loop", ctx.stop.line)
    }

    override fun enterForLoop(ctx: ForLoopContext) {
        logEntry("ForLoop", ctx.start.line)
    }

    override fun exitForLoop(ctx: ForLoopContext) {
        logExit("ForLoop", ctx.stop.line)
    }

    override fun enterWhileLoop(ctx: WhileLoopContext) {
        logEntry("WhileLoop", ctx.start.line)
    }

    override fun exitWhileLoop(ctx: WhileLoopContext) {
        logExit("WhileLoop", ctx.stop.line)
    }

    override fun enterDoWhileLoop(ctx: DoWhileLoopContext) {
        logEntry("DoWhileLoop", ctx.start.line)
    }

    override fun exitDoWhileLoop(ctx: DoWhileLoopContext) {
        logExit("DoWhileLoop", ctx.stop.line)
    }

    override fun enterExpression(ctx: ExpressionContext) {
        logEntry("Expression", ctx.start.line)
    }

    override fun exitExpression(ctx: ExpressionContext) {
        logExit("Expression", ctx.stop.line)
    }

    override fun enterDisjunction(ctx: DisjunctionContext) {
        logEntry("Disjunction", ctx.start.line)
    }

    override fun exitDisjunction(ctx: DisjunctionContext) {
        logExit("Disjunction", ctx.stop.line)
    }

    override fun enterConjunction(ctx: ConjunctionContext) {
        logEntry("Conjunction", ctx.start.line)
    }

    override fun exitConjunction(ctx: ConjunctionContext) {
        logExit("Conjunction", ctx.stop.line)
    }

    override fun enterEqualityComparison(ctx: EqualityComparisonContext) {
        logEntry("EqualityComparison", ctx.start.line)
    }

    override fun exitEqualityComparison(ctx: EqualityComparisonContext) {
        logExit("EqualityComparison", ctx.stop.line)
    }

    override fun enterComparison(ctx: ComparisonContext) {
        logEntry("Comparison", ctx.start.line)
    }

    override fun exitComparison(ctx: ComparisonContext) {
        logExit("Comparison", ctx.stop.line)
    }

    override fun enterNamedInfix(ctx: NamedInfixContext) {
        logEntry("NamedInfix", ctx.start.line)
    }

    override fun exitNamedInfix(ctx: NamedInfixContext) {
        logExit("NamedInfix", ctx.stop.line)
    }

    override fun enterElvisExpression(ctx: ElvisExpressionContext) {
        logEntry("ElvisExpression", ctx.start.line)
    }

    override fun exitElvisExpression(ctx: ElvisExpressionContext) {
        logExit("ElvisExpression", ctx.stop.line)
    }

    override fun enterInfixFunctionCall(ctx: InfixFunctionCallContext) {
        logEntry("InfixFunctionCall", ctx.start.line)
    }

    override fun exitInfixFunctionCall(ctx: InfixFunctionCallContext) {
        logExit("InfixFunctionCall", ctx.stop.line)
    }

    override fun enterRangeExpression(ctx: RangeExpressionContext) {
        logEntry("RangeExpression", ctx.start.line)
    }

    override fun exitRangeExpression(ctx: RangeExpressionContext) {
        logExit("RangeExpression", ctx.stop.line)
    }

    override fun enterAdditiveExpression(ctx: AdditiveExpressionContext) {
        logEntry("AdditiveExpression", ctx.start.line)
    }

    override fun exitAdditiveExpression(ctx: AdditiveExpressionContext) {
        logExit("AdditiveExpression", ctx.stop.line)
    }

    override fun enterMultiplicativeExpression(ctx: MultiplicativeExpressionContext) {
        logEntry("MultiplicativeExpression", ctx.start.line)
    }

    override fun exitMultiplicativeExpression(ctx: MultiplicativeExpressionContext) {
        logExit("MultiplicativeExpression", ctx.stop.line)
    }

    override fun enterTypeRHS(ctx: TypeRHSContext) {
        logEntry("TypeRHS", ctx.start.line)
    }

    override fun exitTypeRHS(ctx: TypeRHSContext) {
        logExit("TypeRHS", ctx.stop.line)
    }

    override fun enterPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext) {
        logEntry("PrefixUnaryExpression", ctx.start.line)
    }

    override fun exitPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext) {
        logExit("PrefixUnaryExpression", ctx.stop.line)
    }

    override fun enterPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext) {
        logEntry("PostfixUnaryExpression", ctx.start.line)
    }

    override fun exitPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext) {
        logExit("PostfixUnaryExpression", ctx.stop.line)
    }

    override fun enterCallableReference(ctx: CallableReferenceContext) {
        logEntry("CallableReference", ctx.start.line)
    }

    override fun exitCallableReference(ctx: CallableReferenceContext) {
        logExit("CallableReference", ctx.stop.line)
    }

    override fun enterIdentifier(ctx: IdentifierContext) {
//        enter("Identifier")
    }

    override fun exitIdentifier(ctx: IdentifierContext) {
//        exit("Identifier")
    }

    override fun enterStringLiteral(ctx: StringLiteralContext) {
        logEntry("StringLiteral", ctx.start.line)
    }

    override fun exitStringLiteral(ctx: StringLiteralContext) {
        logExit("StringLiteral", ctx.stop.line)
    }

    override fun enterAtomicExpression(ctx: AtomicExpressionContext) {
        logEntry("AtomicExpression", ctx.start.line)
    }

    override fun exitAtomicExpression(ctx: AtomicExpressionContext) {
        logExit("AtomicExpression", ctx.stop.line)
    }

    override fun enterLiteralConstant(ctx: LiteralConstantContext) {
        logEntry("LiteralConstant", ctx.start.line)
    }

    override fun exitLiteralConstant(ctx: LiteralConstantContext) {
        logExit("LiteralConstant", ctx.stop.line)
    }

    override fun enterDeclaration(ctx: DeclarationContext) {
        logEntry("Declaration", ctx.start.line)
    }

    override fun exitDeclaration(ctx: DeclarationContext) {
        logExit("Declaration", ctx.stop.line)
    }

    override fun enterStatement(ctx: StatementContext) {
        logEntry("Statement", ctx.start.line)
    }

    override fun exitStatement(ctx: StatementContext) {
        logExit("Statement", ctx.stop.line)
    }

    override fun enterMultiplicativeOperation(ctx: MultiplicativeOperationContext) {
        logEntry("MultiplicativeOperation", ctx.start.line)
    }

    override fun exitMultiplicativeOperation(ctx: MultiplicativeOperationContext) {
        logExit("MultiplicativeOperation", ctx.stop.line)
    }

    override fun enterAdditiveOperation(ctx: AdditiveOperationContext) {
        logEntry("AdditiveOperation", ctx.start.line)
    }

    override fun exitAdditiveOperation(ctx: AdditiveOperationContext) {
        logExit("AdditiveOperation", ctx.stop.line)
    }

    override fun enterInOperation(ctx: InOperationContext) {
        logEntry("InOperation", ctx.start.line)
    }

    override fun exitInOperation(ctx: InOperationContext) {
        logExit("InOperation", ctx.stop.line)
    }

    override fun enterTypeOperation(ctx: TypeOperationContext) {
        logEntry("TypeOperation", ctx.start.line)
    }

    override fun exitTypeOperation(ctx: TypeOperationContext) {
        logExit("TypeOperation", ctx.stop.line)
    }

    override fun enterIsOperation(ctx: IsOperationContext) {
        logEntry("IsOperation", ctx.start.line)
    }

    override fun exitIsOperation(ctx: IsOperationContext) {
        logExit("IsOperation", ctx.stop.line)
    }

    override fun enterComparisonOperation(ctx: ComparisonOperationContext) {
        logEntry("ComparisonOperation", ctx.start.line)
    }

    override fun exitComparisonOperation(ctx: ComparisonOperationContext) {
        logExit("ComparisonOperation", ctx.stop.line)
    }

    override fun enterEqualityOperation(ctx: EqualityOperationContext) {
        logEntry("EqualityOperation", ctx.start.line)
    }

    override fun exitEqualityOperation(ctx: EqualityOperationContext) {
        logExit("EqualityOperation", ctx.stop.line)
    }

    override fun enterAssignmentOperator(ctx: AssignmentOperatorContext) {
        logEntry("AssignmentOperator", ctx.start.line)
    }

    override fun exitAssignmentOperator(ctx: AssignmentOperatorContext) {
        logExit("AssignmentOperator", ctx.stop.line)
    }

    override fun enterPrefixUnaryOperation(ctx: PrefixUnaryOperationContext) {
        logEntry("PrefixUnaryOperation", ctx.start.line)
    }

    override fun exitPrefixUnaryOperation(ctx: PrefixUnaryOperationContext) {
        logExit("PrefixUnaryOperation", ctx.stop.line)
    }

    override fun enterPostfixUnaryOperation(ctx: PostfixUnaryOperationContext) {
        logEntry("PostfixUnaryOperation", ctx.start.line)
    }

    override fun exitPostfixUnaryOperation(ctx: PostfixUnaryOperationContext) {
        logExit("PostfixUnaryOperation", ctx.stop.line)
    }

    override fun enterAnnotatedLambda(ctx: AnnotatedLambdaContext) {
        logEntry("AnnotatedLambda", ctx.start.line)
    }

    override fun exitAnnotatedLambda(ctx: AnnotatedLambdaContext) {
        logExit("AnnotatedLambda", ctx.stop.line)
    }

    override fun enterMemberAccessOperation(ctx: MemberAccessOperationContext) {
        logEntry("MemberAccessOperation", ctx.start.line)
    }

    override fun exitMemberAccessOperation(ctx: MemberAccessOperationContext) {
        logExit("MemberAccessOperation", ctx.stop.line)
    }

    override fun enterFunctionLiteral(ctx: FunctionLiteralContext) {
        logEntry("FunctionLiteral", ctx.start.line)
    }

    override fun exitFunctionLiteral(ctx: FunctionLiteralContext) {
        logExit("FunctionLiteral", ctx.stop.line)
    }

    override fun enterArrayAccess(ctx: ArrayAccessContext) {
        logEntry("ArrayAccess", ctx.start.line)
    }

    override fun exitArrayAccess(ctx: ArrayAccessContext) {
        logExit("ArrayAccess", ctx.stop.line)
    }

    override fun enterObjectLiteral(ctx: ObjectLiteralContext) {
        logEntry("ObjectLiteral", ctx.start.line)
    }

    override fun exitObjectLiteral(ctx: ObjectLiteralContext) {
        logExit("ObjectLiteral", ctx.stop.line)
    }

    override fun enterWhen(ctx: WhenContext) {
        logEntry("When", ctx.start.line)
    }

    override fun exitWhen(ctx: WhenContext) {
        logExit("When", ctx.stop.line)
    }

    override fun enterWhenEntry(ctx: WhenEntryContext) {
        logEntry("WhenEntry", ctx.start.line)
    }

    override fun exitWhenEntry(ctx: WhenEntryContext) {
        logExit("WhenEntry", ctx.stop.line)
    }

    override fun enterWhenCondition(ctx: WhenConditionContext) {
        logEntry("WhenCondition", ctx.start.line)
    }

    override fun exitWhenCondition(ctx: WhenConditionContext) {
        logExit("WhenCondition", ctx.stop.line)
    }

    override fun visitTerminal(node: TerminalNode?) {
/*
        val symbol = node?.symbol?.text ?: ""
        if (log) {
            (0..level).forEach { print(INDENT) }
            println("=== $symbol")
        }
*/
        super.visitTerminal(node)
    }

    override fun visitErrorNode(node: ErrorNode?) {
        super.visitErrorNode(node)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> peek(): T? {
        return if (stack.size > 0) stack.last() as T else null
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> pop(): T {
        return stack.removeAt(stack.size - 1) as T
    }
    @Suppress("UNCHECKED_CAST")
    protected fun push(t: Any) {
        stack.add(t)
    }
}
