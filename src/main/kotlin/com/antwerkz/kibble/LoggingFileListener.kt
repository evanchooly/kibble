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
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

open class LoggingFileListener(var log: Boolean = false): KotlinParserBaseListener() {
    companion object {
        private val INDENT = "  "
        private val PREFIX = "--->"
    }

    var level = 0
    val file = KotlinFile()
    var currentClass: KotlinClass? = null
    var context = mutableListOf<Any>()

    fun logEntry(name: String) {
        if (log) {
            (0..level).forEach { print(INDENT) }
            println("-> $name")
            level += 1
        }
    }

    protected fun markEntry(name: String) {
//        context.add(PREFIX + name)
    }

    protected fun markExit(name: String): List<Any> {
        return mutableListOf()
//        return context.popUntil(PREFIX + name)
    }

    fun logExit(name: String) {
        if (log) {
            level -= 1
            (0..level).forEach { print(INDENT) }
            println("<- $name")
        }
    }

    override fun enterKotlinFile(ctx: KotlinFileContext) {
        logEntry("KotlinFile")
    }

    override fun exitKotlinFile(ctx: KotlinFileContext) {
        logExit("KotlinFile")
    }

    override fun enterPreamble(ctx: PreambleContext) {
        logEntry("Preamble")
    }

    override fun exitPreamble(ctx: PreambleContext) {
        logExit("Preamble")
    }

    override fun enterPackageHeader(ctx: PackageHeaderContext) {
        logEntry("PackageHeader")
    }

    override fun exitPackageHeader(ctx: PackageHeaderContext) {
        logExit("PackageHeader")
    }

    override fun enterImportHeader(ctx: ImportHeaderContext) {
        logEntry("ImportHeader")
    }

    override fun exitImportHeader(ctx: ImportHeaderContext) {
        logExit("ImportHeader")
    }

    override fun enterToplevelObject(ctx: ToplevelObjectContext) {
        logEntry("ToplevelObject")
    }

    override fun exitToplevelObject(ctx: ToplevelObjectContext) {
        logExit("ToplevelObject")
    }

    override fun enterMemberDeclaration(ctx: MemberDeclarationContext) {
        logEntry("MemberDeclaration")
    }

    override fun exitMemberDeclaration(ctx: MemberDeclarationContext) {
        logExit("MemberDeclaration")
    }

    override fun enterOptionalProjection(ctx: OptionalProjectionContext) {
        logEntry("OptionalProjection")
    }

    override fun exitOptionalProjection(ctx: OptionalProjectionContext) {
        logExit("OptionalProjection")
    }

    override fun enterTypeParameters(ctx: TypeParametersContext) {
        logEntry("TypeParameters")
    }

    override fun exitTypeParameters(ctx: TypeParametersContext) {
        logExit("TypeParameters")
    }

    override fun enterTypeParameter(ctx: TypeParameterContext) {
        logEntry("TypeParameter")
    }

    override fun exitTypeParameter(ctx: TypeParameterContext) {
        logExit("TypeParameter")
    }

    override fun enterTypeArguments(ctx: TypeArgumentsContext) {
        logEntry("TypeArguments")
    }

    override fun exitTypeArguments(ctx: TypeArgumentsContext) {
        logExit("TypeArguments")
    }

    override fun enterTypeConstraints(ctx: TypeConstraintsContext) {
        logEntry("TypeConstraints")
    }

    override fun exitTypeConstraints(ctx: TypeConstraintsContext) {
        logExit("TypeConstraints")
    }

    override fun enterTypeConstraint(ctx: TypeConstraintContext) {
        logEntry("TypeConstraint")
    }

    override fun exitTypeConstraint(ctx: TypeConstraintContext) {
        logExit("TypeConstraint")
    }

    override fun enterType(ctx: TypeContext) {
        logEntry("Type")
    }

    override fun exitType(ctx: TypeContext) {
        logExit("Type")
    }

    override fun enterTypeDescriptor(ctx: TypeDescriptorContext) {
        logEntry("TypeDescriptor")
    }

    override fun exitTypeDescriptor(ctx: TypeDescriptorContext) {
        logExit("TypeDescriptor")
    }

    override fun enterUserType(ctx: UserTypeContext) {
        logEntry("UserType")
    }

    override fun exitUserType(ctx: UserTypeContext) {
        logExit("UserType")
    }

    override fun enterSimpleUserType_typeParam(ctx: SimpleUserType_typeParamContext) {
        logEntry("SimpleUserType_typeParam")
    }

    override fun exitSimpleUserType_typeParam(ctx: SimpleUserType_typeParamContext) {
        logExit("SimpleUserType_typeParam")
    }

    override fun enterSimpleUserType(ctx: SimpleUserTypeContext) {
        logEntry("SimpleUserType")
    }

    override fun exitSimpleUserType(ctx: SimpleUserTypeContext) {
        logExit("SimpleUserType")
    }

    override fun enterVariableDeclarationEntry(ctx: VariableDeclarationEntryContext) {
        logEntry("VariableDeclarationEntry")
    }

    override fun exitVariableDeclarationEntry(ctx: VariableDeclarationEntryContext) {
        logExit("VariableDeclarationEntry")
    }

    override fun enterFunctionType_paramOrType(ctx: FunctionType_paramOrTypeContext) {
        logEntry("FunctionType_paramOrType")
    }

    override fun exitFunctionType_paramOrType(ctx: FunctionType_paramOrTypeContext) {
        logExit("FunctionType_paramOrType")
    }

    override fun enterExtensionFunctionTypeNoReceiver(ctx: ExtensionFunctionTypeNoReceiverContext) {
        logEntry("ExtensionFunctionTypeNoReceiver")
    }

    override fun exitExtensionFunctionTypeNoReceiver(ctx: ExtensionFunctionTypeNoReceiverContext) {
        logExit("ExtensionFunctionTypeNoReceiver")
    }

    override fun enterFunctionTypeNoReceiver(ctx: FunctionTypeNoReceiverContext) {
        logEntry("FunctionTypeNoReceiver")
    }

    override fun exitFunctionTypeNoReceiver(ctx: FunctionTypeNoReceiverContext) {
        logExit("FunctionTypeNoReceiver")
    }

    override fun enterMultipleVariableDeclarations(ctx: MultipleVariableDeclarationsContext) {
        logEntry("MultipleVariableDeclarations")
    }

    override fun exitMultipleVariableDeclarations(ctx: MultipleVariableDeclarationsContext) {
        logExit("MultipleVariableDeclarations")
    }

    override fun enterPropertyDeclaration(ctx: PropertyDeclarationContext) {
        logEntry("PropertyDeclaration")
    }

    override fun exitPropertyDeclaration(ctx: PropertyDeclarationContext) {
        logExit("PropertyDeclaration")
    }

    override fun enterGetter(ctx: GetterContext) {
        logEntry("Getter")
    }

    override fun exitGetter(ctx: GetterContext) {
        logExit("Getter")
    }

    override fun enterSetter(ctx: SetterContext) {
        logEntry("Setter")
    }

    override fun exitSetter(ctx: SetterContext) {
        logExit("Setter")
    }

    override fun enterModifiers(ctx: ModifiersContext) {
        logEntry("Modifiers")
    }

    override fun exitModifiers(ctx: ModifiersContext) {
        logExit("Modifiers")
    }

    override fun enterModifier(ctx: ModifierContext) {
        logEntry("Modifier")
    }

    override fun exitModifier(ctx: ModifierContext) {
        logExit("Modifier")
    }

    override fun enterModifierKeyword(ctx: ModifierKeywordContext) {
        logEntry("ModifierKeyword")
    }

    override fun exitModifierKeyword(ctx: ModifierKeywordContext) {
        logExit("ModifierKeyword")
    }

    override fun enterHierarchyModifier(ctx: HierarchyModifierContext) {
        logEntry("HierarchyModifier")
    }

    override fun exitHierarchyModifier(ctx: HierarchyModifierContext) {
        logExit("HierarchyModifier")
    }

    override fun enterClassModifier(ctx: ClassModifierContext) {
        logEntry("ClassModifier")
    }

    override fun exitClassModifier(ctx: ClassModifierContext) {
        logExit("ClassModifier")
    }

    override fun enterAccessModifier(ctx: AccessModifierContext) {
        logEntry("AccessModifier")
    }

    override fun exitAccessModifier(ctx: AccessModifierContext) {
        logExit("AccessModifier")
    }

    override fun enterVarianceAnnotation(ctx: VarianceAnnotationContext) {
        logEntry("VarianceAnnotation")
    }

    override fun exitVarianceAnnotation(ctx: VarianceAnnotationContext) {
        logExit("VarianceAnnotation")
    }

    override fun enterAnnotations(ctx: AnnotationsContext) {
        logEntry("Annotations")
    }

    override fun exitAnnotations(ctx: AnnotationsContext) {
        logExit("Annotations")
    }

    override fun enterAnnotation(ctx: AnnotationContext) {
        logEntry("Annotation")
    }

    override fun exitAnnotation(ctx: AnnotationContext) {
        logExit("Annotation")
    }

    override fun enterAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext) {
        logEntry("AnnotationUseSiteTarget")
    }

    override fun exitAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext) {
        logExit("AnnotationUseSiteTarget")
    }

    override fun enterValueArgument(ctx: ValueArgumentContext) {
        logEntry("ValueArgument")
    }

    override fun exitValueArgument(ctx: ValueArgumentContext) {
        logExit("ValueArgument")
    }

    override fun enterValueArguments(ctx: ValueArgumentsContext) {
        logEntry("ValueArguments")
    }

    override fun exitValueArguments(ctx: ValueArgumentsContext) {
        logExit("ValueArguments")
    }

    override fun enterUnescapedAnnotation(ctx: UnescapedAnnotationContext) {
        logEntry("UnescapedAnnotation")
    }

    override fun exitUnescapedAnnotation(ctx: UnescapedAnnotationContext) {
        logExit("UnescapedAnnotation")
    }

    override fun enterJump(ctx: JumpContext) {
        logEntry("Jump")
    }

    override fun exitJump(ctx: JumpContext) {
        logExit("Jump")
    }

    override fun enterLabelReference(ctx: LabelReferenceContext) {
        logEntry("LabelReference")
    }

    override fun exitLabelReference(ctx: LabelReferenceContext) {
        logExit("LabelReference")
    }

    override fun enterLabelDefinition(ctx: LabelDefinitionContext) {
        logEntry("LabelDefinition")
    }

    override fun exitLabelDefinition(ctx: LabelDefinitionContext) {
        logExit("LabelDefinition")
    }

    override fun enterParameter(ctx: ParameterContext) {
        logEntry("Parameter")
    }

    override fun exitParameter(ctx: ParameterContext) {
        logExit("Parameter")
    }

    override fun enterFunctionParameter(ctx: FunctionParameterContext) {
        logEntry("FunctionParameter")
    }

    override fun exitFunctionParameter(ctx: FunctionParameterContext) {
        logExit("FunctionParameter")
    }

    override fun enterPrimaryConstructor(ctx: PrimaryConstructorContext) {
        logEntry("PrimaryConstructor")
    }

    override fun exitPrimaryConstructor(ctx: PrimaryConstructorContext) {
        logExit("PrimaryConstructor")
    }

    override fun enterSecondaryConstructor(ctx: SecondaryConstructorContext) {
        logEntry("SecondaryConstructor")
    }

    override fun exitSecondaryConstructor(ctx: SecondaryConstructorContext) {
        logExit("SecondaryConstructor")
    }

    override fun enterConstructorDelegationCall(ctx: ConstructorDelegationCallContext) {
        logEntry("ConstructorDelegationCall")
    }

    override fun exitConstructorDelegationCall(ctx: ConstructorDelegationCallContext) {
        logExit("ConstructorDelegationCall")
    }

    override fun enterCallSuffix(ctx: CallSuffixContext) {
        logEntry("CallSuffix")
    }

    override fun exitCallSuffix(ctx: CallSuffixContext) {
        logExit("CallSuffix")
    }

    override fun enterConstructorInvocation(ctx: ConstructorInvocationContext) {
        logEntry("ConstructorInvocation")
    }

    override fun exitConstructorInvocation(ctx: ConstructorInvocationContext) {
        logExit("ConstructorInvocation")
    }

    override fun enterExplicitDelegation(ctx: ExplicitDelegationContext) {
        logEntry("ExplicitDelegation")
    }

    override fun exitExplicitDelegation(ctx: ExplicitDelegationContext) {
        logExit("ExplicitDelegation")
    }

    override fun enterDelegationSpecifier(ctx: DelegationSpecifierContext) {
        logEntry("DelegationSpecifier")
    }

    override fun exitDelegationSpecifier(ctx: DelegationSpecifierContext) {
        logExit("DelegationSpecifier")
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
        logEntry("ClassDeclaration")
    }

    override fun exitClassDeclaration(ctx: ClassDeclarationContext) {
        logExit("ClassDeclaration")
    }

    override fun enterSupertypesSpecifiers(ctx: SupertypesSpecifiersContext) {
        logEntry("SupertypesSpecifiers")
    }

    override fun exitSupertypesSpecifiers(ctx: SupertypesSpecifiersContext) {
        logExit("SupertypesSpecifiers")
    }

    override fun enterObjectDeclaration(ctx: ObjectDeclarationContext) {
        logEntry("ObjectDeclaration")
    }

    override fun exitObjectDeclaration(ctx: ObjectDeclarationContext) {
        logExit("ObjectDeclaration")
    }

    override fun enterCompanionObject(ctx: CompanionObjectContext) {
        logEntry("CompanionObject")
    }

    override fun exitCompanionObject(ctx: CompanionObjectContext) {
        logExit("CompanionObject")
    }

    override fun enterClassBody(ctx: ClassBodyContext) {
        logEntry("ClassBody")
    }

    override fun exitClassBody(ctx: ClassBodyContext) {
        logExit("ClassBody")
    }

    override fun enterMembers(ctx: MembersContext) {
        logEntry("Members")
    }

    override fun exitMembers(ctx: MembersContext) {
        logExit("Members")
    }

    override fun enterValueParameters(ctx: ValueParametersContext) {
        logEntry("ValueParameters")
    }

    override fun exitValueParameters(ctx: ValueParametersContext) {
        logExit("ValueParameters")
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        logEntry("FunctionDeclaration")
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        logExit("FunctionDeclaration")
    }

    override fun enterStatements(ctx: StatementsContext) {
        logEntry("Statements")
    }

    override fun exitStatements(ctx: StatementsContext) {
        logExit("Statements")
    }

    override fun enterFunctionBody(ctx: FunctionBodyContext) {
        logEntry("FunctionBody")
    }

    override fun exitFunctionBody(ctx: FunctionBodyContext) {
        logExit("FunctionBody")
    }

    override fun enterBlock(ctx: BlockContext) {
        logEntry("Block")
    }

    override fun exitBlock(ctx: BlockContext) {
        logExit("Block")
    }

    override fun enterAnonymousInitializer(ctx: AnonymousInitializerContext) {
        logEntry("AnonymousInitializer")
    }

    override fun exitAnonymousInitializer(ctx: AnonymousInitializerContext) {
        logExit("AnonymousInitializer")
    }

    override fun enterEnumClassBody(ctx: EnumClassBodyContext) {
        logEntry("EnumClassBody")
    }

    override fun exitEnumClassBody(ctx: EnumClassBodyContext) {
        logExit("EnumClassBody")
    }

    override fun enterEnumEntries(ctx: EnumEntriesContext) {
        logEntry("EnumEntries")
    }

    override fun exitEnumEntries(ctx: EnumEntriesContext) {
        logExit("EnumEntries")
    }

    override fun enterEnumEntry(ctx: EnumEntryContext) {
        logEntry("EnumEntry")
    }

    override fun exitEnumEntry(ctx: EnumEntryContext) {
        logExit("EnumEntry")
    }

    override fun enterIfExpression(ctx: IfExpressionContext) {
        logEntry("IfExpression")
    }

    override fun exitIfExpression(ctx: IfExpressionContext) {
        logExit("IfExpression")
    }

    override fun enterTryExpression(ctx: TryExpressionContext) {
        logEntry("TryExpression")
    }

    override fun exitTryExpression(ctx: TryExpressionContext) {
        logExit("TryExpression")
    }

    override fun enterCatchBlock(ctx: CatchBlockContext) {
        logEntry("CatchBlock")
    }

    override fun exitCatchBlock(ctx: CatchBlockContext) {
        logExit("CatchBlock")
    }

    override fun enterFinallyBlock(ctx: FinallyBlockContext) {
        logEntry("FinallyBlock")
    }

    override fun exitFinallyBlock(ctx: FinallyBlockContext) {
        logExit("FinallyBlock")
    }

    override fun enterLoop(ctx: LoopContext) {
        logEntry("Loop")
    }

    override fun exitLoop(ctx: LoopContext) {
        logExit("Loop")
    }

    override fun enterForLoop(ctx: ForLoopContext) {
        logEntry("ForLoop")
    }

    override fun exitForLoop(ctx: ForLoopContext) {
        logExit("ForLoop")
    }

    override fun enterWhileLoop(ctx: WhileLoopContext) {
        logEntry("WhileLoop")
    }

    override fun exitWhileLoop(ctx: WhileLoopContext) {
        logExit("WhileLoop")
    }

    override fun enterDoWhileLoop(ctx: DoWhileLoopContext) {
        logEntry("DoWhileLoop")
    }

    override fun exitDoWhileLoop(ctx: DoWhileLoopContext) {
        logExit("DoWhileLoop")
    }

    override fun enterExpression(ctx: ExpressionContext) {
        logEntry("Expression")
    }

    override fun exitExpression(ctx: ExpressionContext) {
        logExit("Expression")
    }

    override fun enterDisjunction(ctx: DisjunctionContext) {
        logEntry("Disjunction")
    }

    override fun exitDisjunction(ctx: DisjunctionContext) {
        logExit("Disjunction")
    }

    override fun enterConjunction(ctx: ConjunctionContext) {
        logEntry("Conjunction")
    }

    override fun exitConjunction(ctx: ConjunctionContext) {
        logExit("Conjunction")
    }

    override fun enterEqualityComparison(ctx: EqualityComparisonContext) {
        logEntry("EqualityComparison")
    }

    override fun exitEqualityComparison(ctx: EqualityComparisonContext) {
        logExit("EqualityComparison")
    }

    override fun enterComparison(ctx: ComparisonContext) {
        logEntry("Comparison")
    }

    override fun exitComparison(ctx: ComparisonContext) {
        logExit("Comparison")
    }

    override fun enterNamedInfix(ctx: NamedInfixContext) {
        logEntry("NamedInfix")
    }

    override fun exitNamedInfix(ctx: NamedInfixContext) {
        logExit("NamedInfix")
    }

    override fun enterElvisExpression(ctx: ElvisExpressionContext) {
        logEntry("ElvisExpression")
    }

    override fun exitElvisExpression(ctx: ElvisExpressionContext) {
        logExit("ElvisExpression")
    }

    override fun enterInfixFunctionCall(ctx: InfixFunctionCallContext) {
        logEntry("InfixFunctionCall")
    }

    override fun exitInfixFunctionCall(ctx: InfixFunctionCallContext) {
        logExit("InfixFunctionCall")
    }

    override fun enterRangeExpression(ctx: RangeExpressionContext) {
        logEntry("RangeExpression")
    }

    override fun exitRangeExpression(ctx: RangeExpressionContext) {
        logExit("RangeExpression")
    }

    override fun enterAdditiveExpression(ctx: AdditiveExpressionContext) {
        logEntry("AdditiveExpression")
    }

    override fun exitAdditiveExpression(ctx: AdditiveExpressionContext) {
        logExit("AdditiveExpression")
    }

    override fun enterMultiplicativeExpression(ctx: MultiplicativeExpressionContext) {
        logEntry("MultiplicativeExpression")
    }

    override fun exitMultiplicativeExpression(ctx: MultiplicativeExpressionContext) {
        logExit("MultiplicativeExpression")
    }

    override fun enterTypeRHS(ctx: TypeRHSContext) {
        logEntry("TypeRHS")
    }

    override fun exitTypeRHS(ctx: TypeRHSContext) {
        logExit("TypeRHS")
    }

    override fun enterPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext) {
        logEntry("PrefixUnaryExpression")
    }

    override fun exitPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext) {
        logExit("PrefixUnaryExpression")
    }

    override fun enterPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext) {
        logEntry("PostfixUnaryExpression")
    }

    override fun exitPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext) {
        logExit("PostfixUnaryExpression")
    }

    override fun enterCallableReference(ctx: CallableReferenceContext) {
        logEntry("CallableReference")
    }

    override fun exitCallableReference(ctx: CallableReferenceContext) {
        logExit("CallableReference")
    }

    override fun enterIdentifier(ctx: IdentifierContext) {
//        enter("Identifier")
    }

    override fun exitIdentifier(ctx: IdentifierContext) {
//        exit("Identifier")
    }

    override fun enterStringLiteral(ctx: StringLiteralContext) {
        logEntry("StringLiteral")
    }

    override fun exitStringLiteral(ctx: StringLiteralContext) {
        logExit("StringLiteral")
    }

    override fun enterAtomicExpression(ctx: AtomicExpressionContext) {
        logEntry("AtomicExpression")
    }

    override fun exitAtomicExpression(ctx: AtomicExpressionContext) {
        logExit("AtomicExpression")
    }

    override fun enterLiteralConstant(ctx: LiteralConstantContext) {
        logEntry("LiteralConstant")
    }

    override fun exitLiteralConstant(ctx: LiteralConstantContext) {
        logExit("LiteralConstant")
    }

    override fun enterDeclaration(ctx: DeclarationContext) {
        logEntry("Declaration")
    }

    override fun exitDeclaration(ctx: DeclarationContext) {
        logExit("Declaration")
    }

    override fun enterStatement(ctx: StatementContext) {
        logEntry("Statement")
    }

    override fun exitStatement(ctx: StatementContext) {
        logExit("Statement")
    }

    override fun enterMultiplicativeOperation(ctx: MultiplicativeOperationContext) {
        logEntry("MultiplicativeOperation")
    }

    override fun exitMultiplicativeOperation(ctx: MultiplicativeOperationContext) {
        logExit("MultiplicativeOperation")
    }

    override fun enterAdditiveOperation(ctx: AdditiveOperationContext) {
        logEntry("AdditiveOperation")
    }

    override fun exitAdditiveOperation(ctx: AdditiveOperationContext) {
        logExit("AdditiveOperation")
    }

    override fun enterInOperation(ctx: InOperationContext) {
        logEntry("InOperation")
    }

    override fun exitInOperation(ctx: InOperationContext) {
        logExit("InOperation")
    }

    override fun enterTypeOperation(ctx: TypeOperationContext) {
        logEntry("TypeOperation")
    }

    override fun exitTypeOperation(ctx: TypeOperationContext) {
        logExit("TypeOperation")
    }

    override fun enterIsOperation(ctx: IsOperationContext) {
        logEntry("IsOperation")
    }

    override fun exitIsOperation(ctx: IsOperationContext) {
        logExit("IsOperation")
    }

    override fun enterComparisonOperation(ctx: ComparisonOperationContext) {
        logEntry("ComparisonOperation")
    }

    override fun exitComparisonOperation(ctx: ComparisonOperationContext) {
        logExit("ComparisonOperation")
    }

    override fun enterEqualityOperation(ctx: EqualityOperationContext) {
        logEntry("EqualityOperation")
    }

    override fun exitEqualityOperation(ctx: EqualityOperationContext) {
        logExit("EqualityOperation")
    }

    override fun enterAssignmentOperator(ctx: AssignmentOperatorContext) {
        logEntry("AssignmentOperator")
    }

    override fun exitAssignmentOperator(ctx: AssignmentOperatorContext) {
        logExit("AssignmentOperator")
    }

    override fun enterPrefixUnaryOperation(ctx: PrefixUnaryOperationContext) {
        logEntry("PrefixUnaryOperation")
    }

    override fun exitPrefixUnaryOperation(ctx: PrefixUnaryOperationContext) {
        logExit("PrefixUnaryOperation")
    }

    override fun enterPostfixUnaryOperation(ctx: PostfixUnaryOperationContext) {
        logEntry("PostfixUnaryOperation")
    }

    override fun exitPostfixUnaryOperation(ctx: PostfixUnaryOperationContext) {
        logExit("PostfixUnaryOperation")
    }

    override fun enterAnnotatedLambda(ctx: AnnotatedLambdaContext) {
        logEntry("AnnotatedLambda")
    }

    override fun exitAnnotatedLambda(ctx: AnnotatedLambdaContext) {
        logExit("AnnotatedLambda")
    }

    override fun enterMemberAccessOperation(ctx: MemberAccessOperationContext) {
        logEntry("MemberAccessOperation")
    }

    override fun exitMemberAccessOperation(ctx: MemberAccessOperationContext) {
        logExit("MemberAccessOperation")
    }

    override fun enterFunctionLiteral(ctx: FunctionLiteralContext) {
        logEntry("FunctionLiteral")
    }

    override fun exitFunctionLiteral(ctx: FunctionLiteralContext) {
        logExit("FunctionLiteral")
    }

    override fun enterArrayAccess(ctx: ArrayAccessContext) {
        logEntry("ArrayAccess")
    }

    override fun exitArrayAccess(ctx: ArrayAccessContext) {
        logExit("ArrayAccess")
    }

    override fun enterObjectLiteral(ctx: ObjectLiteralContext) {
        logEntry("ObjectLiteral")
    }

    override fun exitObjectLiteral(ctx: ObjectLiteralContext) {
        logExit("ObjectLiteral")
    }

    override fun enterWhen(ctx: WhenContext) {
        logEntry("When")
    }

    override fun exitWhen(ctx: WhenContext) {
        logExit("When")
    }

    override fun enterWhenEntry(ctx: WhenEntryContext) {
        logEntry("WhenEntry")
    }

    override fun exitWhenEntry(ctx: WhenEntryContext) {
        logExit("WhenEntry")
    }

    override fun enterWhenCondition(ctx: WhenConditionContext) {
        logEntry("WhenCondition")
    }

    override fun exitWhenCondition(ctx: WhenConditionContext) {
        logExit("WhenCondition")
    }

    override fun visitTerminal(node: TerminalNode?) {
        val symbol = node?.symbol?.text ?: ""
        if (log) {
            (0..level).forEach { print(INDENT) }
            println("=== $symbol")
        }
    }

    override fun visitErrorNode(node: ErrorNode?) {
        super.visitErrorNode(node)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> MutableList<Any>.peek(): T? {
        return if (size > 0) last() as T else null
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> MutableList<Any>.pop(): T {
        return removeAt(size - 1) as T
    }

    private fun MutableList<Any>.popUntil(value: String): List<Any> {
        val result = mutableListOf<Any>()
        while (!context.isEmpty() && value != last()) {
            val last = removeLast()
            if (last !is String || !last.startsWith(PREFIX)) {
                result += last
            }
        }
        removeLast()
        result.reverse()
        return result
    }

    private fun MutableList<Any>.removeLast(): Any {
        return removeAt(size - 1)
    }

    private fun <T> MutableList<T>.popWhile(predicate: (T) -> Boolean): List<T> {
        val items = takeLastWhile(predicate)
        var i = items.size + 1
        while (i > 0) {
            removeAt(size - i)
            i--
        }

        return items
    }
}
