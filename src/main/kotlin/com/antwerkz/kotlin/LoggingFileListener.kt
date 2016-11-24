package com.antwerkz.kotlin

import com.antwerkz.kotlin.KotlinParser.AccessModifierContext
import com.antwerkz.kotlin.KotlinParser.AdditiveExpressionContext
import com.antwerkz.kotlin.KotlinParser.AdditiveOperationContext
import com.antwerkz.kotlin.KotlinParser.AnnotatedLambdaContext
import com.antwerkz.kotlin.KotlinParser.AnnotationContext
import com.antwerkz.kotlin.KotlinParser.AnnotationUseSiteTargetContext
import com.antwerkz.kotlin.KotlinParser.AnnotationsContext
import com.antwerkz.kotlin.KotlinParser.AnonymousInitializerContext
import com.antwerkz.kotlin.KotlinParser.ArrayAccessContext
import com.antwerkz.kotlin.KotlinParser.AssignmentOperatorContext
import com.antwerkz.kotlin.KotlinParser.AtomicExpressionContext
import com.antwerkz.kotlin.KotlinParser.BlockContext
import com.antwerkz.kotlin.KotlinParser.CallSuffixContext
import com.antwerkz.kotlin.KotlinParser.CallableReferenceContext
import com.antwerkz.kotlin.KotlinParser.CatchBlockContext
import com.antwerkz.kotlin.KotlinParser.ClassBodyContext
import com.antwerkz.kotlin.KotlinParser.ClassDeclarationContext
import com.antwerkz.kotlin.KotlinParser.ClassModifierContext
import com.antwerkz.kotlin.KotlinParser.CompanionObjectContext
import com.antwerkz.kotlin.KotlinParser.ComparisonContext
import com.antwerkz.kotlin.KotlinParser.ComparisonOperationContext
import com.antwerkz.kotlin.KotlinParser.ConjunctionContext
import com.antwerkz.kotlin.KotlinParser.ConstructorDelegationCallContext
import com.antwerkz.kotlin.KotlinParser.ConstructorInvocationContext
import com.antwerkz.kotlin.KotlinParser.DeclarationContext
import com.antwerkz.kotlin.KotlinParser.DelegationSpecifierContext
import com.antwerkz.kotlin.KotlinParser.DisjunctionContext
import com.antwerkz.kotlin.KotlinParser.DoWhileLoopContext
import com.antwerkz.kotlin.KotlinParser.ElvisExpressionContext
import com.antwerkz.kotlin.KotlinParser.EnumClassBodyContext
import com.antwerkz.kotlin.KotlinParser.EnumEntriesContext
import com.antwerkz.kotlin.KotlinParser.EnumEntryContext
import com.antwerkz.kotlin.KotlinParser.EqualityComparisonContext
import com.antwerkz.kotlin.KotlinParser.EqualityOperationContext
import com.antwerkz.kotlin.KotlinParser.ExplicitDelegationContext
import com.antwerkz.kotlin.KotlinParser.ExpressionContext
import com.antwerkz.kotlin.KotlinParser.ExtensionFunctionTypeNoReceiverContext
import com.antwerkz.kotlin.KotlinParser.FinallyBlockContext
import com.antwerkz.kotlin.KotlinParser.ForLoopContext
import com.antwerkz.kotlin.KotlinParser.FunctionBodyContext
import com.antwerkz.kotlin.KotlinParser.FunctionDeclarationContext
import com.antwerkz.kotlin.KotlinParser.FunctionLiteralContext
import com.antwerkz.kotlin.KotlinParser.FunctionParameterContext
import com.antwerkz.kotlin.KotlinParser.FunctionTypeNoReceiverContext
import com.antwerkz.kotlin.KotlinParser.FunctionType_paramOrTypeContext
import com.antwerkz.kotlin.KotlinParser.GetterContext
import com.antwerkz.kotlin.KotlinParser.HierarchyModifierContext
import com.antwerkz.kotlin.KotlinParser.IdentifierContext
import com.antwerkz.kotlin.KotlinParser.IfExpressionContext
import com.antwerkz.kotlin.KotlinParser.ImportHeaderContext
import com.antwerkz.kotlin.KotlinParser.InOperationContext
import com.antwerkz.kotlin.KotlinParser.InfixFunctionCallContext
import com.antwerkz.kotlin.KotlinParser.IsOperationContext
import com.antwerkz.kotlin.KotlinParser.JumpContext
import com.antwerkz.kotlin.KotlinParser.KotlinFileContext
import com.antwerkz.kotlin.KotlinParser.LabelDefinitionContext
import com.antwerkz.kotlin.KotlinParser.LabelReferenceContext
import com.antwerkz.kotlin.KotlinParser.LiteralConstantContext
import com.antwerkz.kotlin.KotlinParser.LoopContext
import com.antwerkz.kotlin.KotlinParser.MemberAccessOperationContext
import com.antwerkz.kotlin.KotlinParser.MemberDeclarationContext
import com.antwerkz.kotlin.KotlinParser.MembersContext
import com.antwerkz.kotlin.KotlinParser.ModifierContext
import com.antwerkz.kotlin.KotlinParser.ModifierKeywordContext
import com.antwerkz.kotlin.KotlinParser.ModifiersContext
import com.antwerkz.kotlin.KotlinParser.MultipleVariableDeclarationsContext
import com.antwerkz.kotlin.KotlinParser.MultiplicativeExpressionContext
import com.antwerkz.kotlin.KotlinParser.MultiplicativeOperationContext
import com.antwerkz.kotlin.KotlinParser.NamedInfixContext
import com.antwerkz.kotlin.KotlinParser.ObjectDeclarationContext
import com.antwerkz.kotlin.KotlinParser.ObjectLiteralContext
import com.antwerkz.kotlin.KotlinParser.OptionalProjectionContext
import com.antwerkz.kotlin.KotlinParser.PackageHeaderContext
import com.antwerkz.kotlin.KotlinParser.ParameterContext
import com.antwerkz.kotlin.KotlinParser.PostfixUnaryExpressionContext
import com.antwerkz.kotlin.KotlinParser.PostfixUnaryOperationContext
import com.antwerkz.kotlin.KotlinParser.PreambleContext
import com.antwerkz.kotlin.KotlinParser.PrefixUnaryExpressionContext
import com.antwerkz.kotlin.KotlinParser.PrefixUnaryOperationContext
import com.antwerkz.kotlin.KotlinParser.PrimaryConstructorContext
import com.antwerkz.kotlin.KotlinParser.PropertyDeclarationContext
import com.antwerkz.kotlin.KotlinParser.RangeExpressionContext
import com.antwerkz.kotlin.KotlinParser.SecondaryConstructorContext
import com.antwerkz.kotlin.KotlinParser.SetterContext
import com.antwerkz.kotlin.KotlinParser.SimpleUserTypeContext
import com.antwerkz.kotlin.KotlinParser.SimpleUserType_typeParamContext
import com.antwerkz.kotlin.KotlinParser.StatementContext
import com.antwerkz.kotlin.KotlinParser.StatementsContext
import com.antwerkz.kotlin.KotlinParser.StringLiteralContext
import com.antwerkz.kotlin.KotlinParser.SupertypesSpecifiersContext
import com.antwerkz.kotlin.KotlinParser.ToplevelObjectContext
import com.antwerkz.kotlin.KotlinParser.TryExpressionContext
import com.antwerkz.kotlin.KotlinParser.TypeArgumentsContext
import com.antwerkz.kotlin.KotlinParser.TypeConstraintContext
import com.antwerkz.kotlin.KotlinParser.TypeConstraintsContext
import com.antwerkz.kotlin.KotlinParser.TypeContext
import com.antwerkz.kotlin.KotlinParser.TypeDescriptorContext
import com.antwerkz.kotlin.KotlinParser.TypeOperationContext
import com.antwerkz.kotlin.KotlinParser.TypeParameterContext
import com.antwerkz.kotlin.KotlinParser.TypeParametersContext
import com.antwerkz.kotlin.KotlinParser.TypeRHSContext
import com.antwerkz.kotlin.KotlinParser.UnescapedAnnotationContext
import com.antwerkz.kotlin.KotlinParser.UserTypeContext
import com.antwerkz.kotlin.KotlinParser.ValueArgumentContext
import com.antwerkz.kotlin.KotlinParser.ValueArgumentsContext
import com.antwerkz.kotlin.KotlinParser.ValueParametersContext
import com.antwerkz.kotlin.KotlinParser.VariableDeclarationEntryContext
import com.antwerkz.kotlin.KotlinParser.VarianceAnnotationContext
import com.antwerkz.kotlin.KotlinParser.WhenConditionContext
import com.antwerkz.kotlin.KotlinParser.WhenContext
import com.antwerkz.kotlin.KotlinParser.WhenEntryContext
import com.antwerkz.kotlin.KotlinParser.WhileLoopContext
import com.antwerkz.kotlin.model.KotlinClass
import com.antwerkz.kotlin.model.KotlinFile
import com.antwerkz.kotlin.model.Parameter
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.Stack

open class LoggingFileListener(): KotlinParserBaseListener() {
    companion object {
        private val INDENT = "  "
    }

    var level = 0
    val file = KotlinFile()
    var currentClass: KotlinClass? = null
    var context = Stack<Any>()
    var terminals = mutableListOf<String>()
    var parameters = mutableListOf<Parameter>()


    fun enter(name: String) {
        (0..level).forEach { print(INDENT) }
        println("-> $name")
        level += 1
    }

    fun exit(name: String) {
        level -= 1
        (0..level).forEach { print(INDENT) }
        println("<- $name")
    }

    override fun enterKotlinFile(ctx: KotlinFileContext) {
        enter("KotlinFile")
    }

    override fun exitKotlinFile(ctx: KotlinFileContext) {
        exit("KotlinFile")
    }

    override fun enterPreamble(ctx: PreambleContext) {
        enter("Preamble")
    }

    override fun exitPreamble(ctx: PreambleContext) {
        exit("Preamble")
    }

    override fun enterPackageHeader(ctx: PackageHeaderContext) {
        enter("PackageHeader")
    }

    override fun exitPackageHeader(ctx: PackageHeaderContext) {
        exit("PackageHeader")
    }

    override fun enterImportHeader(ctx: ImportHeaderContext) {
        enter("ImportHeader")
    }

    override fun exitImportHeader(ctx: ImportHeaderContext) {
        exit("ImportHeader")
    }

    override fun enterToplevelObject(ctx: ToplevelObjectContext) {
        enter("ToplevelObject")
    }

    override fun exitToplevelObject(ctx: ToplevelObjectContext) {
        exit("ToplevelObject")
    }

    override fun enterMemberDeclaration(ctx: MemberDeclarationContext) {
        enter("MemberDeclaration")
    }

    override fun exitMemberDeclaration(ctx: MemberDeclarationContext) {
        exit("MemberDeclaration")
    }

    override fun enterOptionalProjection(ctx: OptionalProjectionContext) {
        enter("OptionalProjection")
    }

    override fun exitOptionalProjection(ctx: OptionalProjectionContext) {
        exit("OptionalProjection")
    }

    override fun enterTypeParameters(ctx: TypeParametersContext) {
        enter("TypeParameters")
    }

    override fun exitTypeParameters(ctx: TypeParametersContext) {
        exit("TypeParameters")
    }

    override fun enterTypeParameter(ctx: TypeParameterContext) {
        enter("TypeParameter")
    }

    override fun exitTypeParameter(ctx: TypeParameterContext) {
        exit("TypeParameter")
    }

    override fun enterTypeArguments(ctx: TypeArgumentsContext) {
        enter("TypeArguments")
    }

    override fun exitTypeArguments(ctx: TypeArgumentsContext) {
        exit("TypeArguments")
    }

    override fun enterTypeConstraints(ctx: TypeConstraintsContext) {
        enter("TypeConstraints")
    }

    override fun exitTypeConstraints(ctx: TypeConstraintsContext) {
        exit("TypeConstraints")
    }

    override fun enterTypeConstraint(ctx: TypeConstraintContext) {
        enter("TypeConstraint")
    }

    override fun exitTypeConstraint(ctx: TypeConstraintContext) {
        exit("TypeConstraint")
    }

    override fun enterType(ctx: TypeContext) {
        enter("Type")
    }

    override fun exitType(ctx: TypeContext) {
        exit("Type")
    }

    override fun enterTypeDescriptor(ctx: TypeDescriptorContext) {
        enter("TypeDescriptor")
    }

    override fun exitTypeDescriptor(ctx: TypeDescriptorContext) {
        exit("TypeDescriptor")
    }

    override fun enterUserType(ctx: UserTypeContext) {
        enter("UserType")
    }

    override fun exitUserType(ctx: UserTypeContext) {
        exit("UserType")
    }

    override fun enterSimpleUserType_typeParam(ctx: SimpleUserType_typeParamContext) {
        enter("SimpleUserType_typeParam")
    }

    override fun exitSimpleUserType_typeParam(ctx: SimpleUserType_typeParamContext) {
        exit("SimpleUserType_typeParam")
    }

    override fun enterSimpleUserType(ctx: SimpleUserTypeContext) {
        enter("SimpleUserType")
    }

    override fun exitSimpleUserType(ctx: SimpleUserTypeContext) {
        exit("SimpleUserType")
    }

    override fun enterVariableDeclarationEntry(ctx: VariableDeclarationEntryContext) {
        enter("VariableDeclarationEntry")
    }

    override fun exitVariableDeclarationEntry(ctx: VariableDeclarationEntryContext) {
        exit("VariableDeclarationEntry")
    }

    override fun enterFunctionType_paramOrType(ctx: FunctionType_paramOrTypeContext) {
        enter("FunctionType_paramOrType")
    }

    override fun exitFunctionType_paramOrType(ctx: FunctionType_paramOrTypeContext) {
        exit("FunctionType_paramOrType")
    }

    override fun enterExtensionFunctionTypeNoReceiver(ctx: ExtensionFunctionTypeNoReceiverContext) {
        enter("ExtensionFunctionTypeNoReceiver")
    }

    override fun exitExtensionFunctionTypeNoReceiver(ctx: ExtensionFunctionTypeNoReceiverContext) {
        exit("ExtensionFunctionTypeNoReceiver")
    }

    override fun enterFunctionTypeNoReceiver(ctx: FunctionTypeNoReceiverContext) {
        enter("FunctionTypeNoReceiver")
    }

    override fun exitFunctionTypeNoReceiver(ctx: FunctionTypeNoReceiverContext) {
        exit("FunctionTypeNoReceiver")
    }

    override fun enterMultipleVariableDeclarations(ctx: MultipleVariableDeclarationsContext) {
        enter("MultipleVariableDeclarations")
    }

    override fun exitMultipleVariableDeclarations(ctx: MultipleVariableDeclarationsContext) {
        exit("MultipleVariableDeclarations")
    }

    override fun enterPropertyDeclaration(ctx: PropertyDeclarationContext) {
        enter("PropertyDeclaration")
    }

    override fun exitPropertyDeclaration(ctx: PropertyDeclarationContext) {
        exit("PropertyDeclaration")
    }

    override fun enterGetter(ctx: GetterContext) {
        enter("Getter")
    }

    override fun exitGetter(ctx: GetterContext) {
        exit("Getter")
    }

    override fun enterSetter(ctx: SetterContext) {
        enter("Setter")
    }

    override fun exitSetter(ctx: SetterContext) {
        exit("Setter")
    }

    override fun enterModifiers(ctx: ModifiersContext) {
        enter("Modifiers")
    }

    override fun exitModifiers(ctx: ModifiersContext) {
        exit("Modifiers")
    }

    override fun enterModifier(ctx: ModifierContext) {
        enter("Modifier")
    }

    override fun exitModifier(ctx: ModifierContext) {
        exit("Modifier")
    }

    override fun enterModifierKeyword(ctx: ModifierKeywordContext) {
        enter("ModifierKeyword")
    }

    override fun exitModifierKeyword(ctx: ModifierKeywordContext) {
        exit("ModifierKeyword")
    }

    override fun enterHierarchyModifier(ctx: HierarchyModifierContext) {
        enter("HierarchyModifier")
    }

    override fun exitHierarchyModifier(ctx: HierarchyModifierContext) {
        exit("HierarchyModifier")
    }

    override fun enterClassModifier(ctx: ClassModifierContext) {
        enter("ClassModifier")
    }

    override fun exitClassModifier(ctx: ClassModifierContext) {
        exit("ClassModifier")
    }

    override fun enterAccessModifier(ctx: AccessModifierContext) {
        enter("AccessModifier")
    }

    override fun exitAccessModifier(ctx: AccessModifierContext) {
        exit("AccessModifier")
    }

    override fun enterVarianceAnnotation(ctx: VarianceAnnotationContext) {
        enter("VarianceAnnotation")
    }

    override fun exitVarianceAnnotation(ctx: VarianceAnnotationContext) {
        exit("VarianceAnnotation")
    }

    override fun enterAnnotations(ctx: AnnotationsContext) {
        enter("Annotations")
    }

    override fun exitAnnotations(ctx: AnnotationsContext) {
        exit("Annotations")
    }

    override fun enterAnnotation(ctx: AnnotationContext) {
        enter("Annotation")
    }

    override fun exitAnnotation(ctx: AnnotationContext) {
        exit("Annotation")
    }

    override fun enterAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext) {
        enter("AnnotationUseSiteTarget")
    }

    override fun exitAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext) {
        exit("AnnotationUseSiteTarget")
    }

    override fun enterValueArgument(ctx: ValueArgumentContext) {
        enter("ValueArgument")
    }

    override fun exitValueArgument(ctx: ValueArgumentContext) {
        exit("ValueArgument")
    }

    override fun enterValueArguments(ctx: ValueArgumentsContext) {
        enter("ValueArguments")
    }

    override fun exitValueArguments(ctx: ValueArgumentsContext) {
        exit("ValueArguments")
    }

    override fun enterUnescapedAnnotation(ctx: UnescapedAnnotationContext) {
        enter("UnescapedAnnotation")
    }

    override fun exitUnescapedAnnotation(ctx: UnescapedAnnotationContext) {
        exit("UnescapedAnnotation")
    }

    override fun enterJump(ctx: JumpContext) {
        enter("Jump")
    }

    override fun exitJump(ctx: JumpContext) {
        exit("Jump")
    }

    override fun enterLabelReference(ctx: LabelReferenceContext) {
        enter("LabelReference")
    }

    override fun exitLabelReference(ctx: LabelReferenceContext) {
        exit("LabelReference")
    }

    override fun enterLabelDefinition(ctx: LabelDefinitionContext) {
        enter("LabelDefinition")
    }

    override fun exitLabelDefinition(ctx: LabelDefinitionContext) {
        exit("LabelDefinition")
    }

    override fun enterParameter(ctx: ParameterContext) {
        enter("Parameter")
    }

    override fun exitParameter(ctx: ParameterContext) {
        exit("Parameter")
    }

    override fun enterFunctionParameter(ctx: FunctionParameterContext) {
        enter("FunctionParameter")
    }

    override fun exitFunctionParameter(ctx: FunctionParameterContext) {
        exit("FunctionParameter")
    }

    override fun enterPrimaryConstructor(ctx: PrimaryConstructorContext) {
        enter("PrimaryConstructor")
    }

    override fun exitPrimaryConstructor(ctx: PrimaryConstructorContext) {
        exit("PrimaryConstructor")
    }

    override fun enterSecondaryConstructor(ctx: SecondaryConstructorContext) {
        enter("SecondaryConstructor")
    }

    override fun exitSecondaryConstructor(ctx: SecondaryConstructorContext) {
        exit("SecondaryConstructor")
    }

    override fun enterConstructorDelegationCall(ctx: ConstructorDelegationCallContext) {
        enter("ConstructorDelegationCall")
    }

    override fun exitConstructorDelegationCall(ctx: ConstructorDelegationCallContext) {
        exit("ConstructorDelegationCall")
    }

    override fun enterCallSuffix(ctx: CallSuffixContext) {
        enter("CallSuffix")
    }

    override fun exitCallSuffix(ctx: CallSuffixContext) {
        exit("CallSuffix")
    }

    override fun enterConstructorInvocation(ctx: ConstructorInvocationContext) {
        enter("ConstructorInvocation")
    }

    override fun exitConstructorInvocation(ctx: ConstructorInvocationContext) {
        exit("ConstructorInvocation")
    }

    override fun enterExplicitDelegation(ctx: ExplicitDelegationContext) {
        enter("ExplicitDelegation")
    }

    override fun exitExplicitDelegation(ctx: ExplicitDelegationContext) {
        exit("ExplicitDelegation")
    }

    override fun enterDelegationSpecifier(ctx: DelegationSpecifierContext) {
        enter("DelegationSpecifier")
    }

    override fun exitDelegationSpecifier(ctx: DelegationSpecifierContext) {
        exit("DelegationSpecifier")
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
        enter("ClassDeclaration")
    }

    override fun exitClassDeclaration(ctx: ClassDeclarationContext) {
        exit("ClassDeclaration")
    }

    override fun enterSupertypesSpecifiers(ctx: SupertypesSpecifiersContext) {
        enter("SupertypesSpecifiers")
    }

    override fun exitSupertypesSpecifiers(ctx: SupertypesSpecifiersContext) {
        exit("SupertypesSpecifiers")
    }

    override fun enterObjectDeclaration(ctx: ObjectDeclarationContext) {
        enter("ObjectDeclaration")
    }

    override fun exitObjectDeclaration(ctx: ObjectDeclarationContext) {
        exit("ObjectDeclaration")
    }

    override fun enterCompanionObject(ctx: CompanionObjectContext) {
        enter("CompanionObject")
    }

    override fun exitCompanionObject(ctx: CompanionObjectContext) {
        exit("CompanionObject")
    }

    override fun enterClassBody(ctx: ClassBodyContext) {
        enter("ClassBody")
    }

    override fun exitClassBody(ctx: ClassBodyContext) {
        exit("ClassBody")
    }

    override fun enterMembers(ctx: MembersContext) {
        enter("Members")
    }

    override fun exitMembers(ctx: MembersContext) {
        exit("Members")
    }

    override fun enterValueParameters(ctx: ValueParametersContext) {
        enter("ValueParameters")
    }

    override fun exitValueParameters(ctx: ValueParametersContext) {
        exit("ValueParameters")
    }

    override fun enterFunctionDeclaration(ctx: FunctionDeclarationContext) {
        enter("FunctionDeclaration")
    }

    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext) {
        exit("FunctionDeclaration")
    }

    override fun enterStatements(ctx: StatementsContext) {
        enter("Statements")
    }

    override fun exitStatements(ctx: StatementsContext) {
        exit("Statements")
    }

    override fun enterFunctionBody(ctx: FunctionBodyContext) {
        enter("FunctionBody")
    }

    override fun exitFunctionBody(ctx: FunctionBodyContext) {
        exit("FunctionBody")
    }

    override fun enterBlock(ctx: BlockContext) {
        enter("Block")
    }

    override fun exitBlock(ctx: BlockContext) {
        exit("Block")
    }

    override fun enterAnonymousInitializer(ctx: AnonymousInitializerContext) {
        enter("AnonymousInitializer")
    }

    override fun exitAnonymousInitializer(ctx: AnonymousInitializerContext) {
        exit("AnonymousInitializer")
    }

    override fun enterEnumClassBody(ctx: EnumClassBodyContext) {
        enter("EnumClassBody")
    }

    override fun exitEnumClassBody(ctx: EnumClassBodyContext) {
        exit("EnumClassBody")
    }

    override fun enterEnumEntries(ctx: EnumEntriesContext) {
        enter("EnumEntries")
    }

    override fun exitEnumEntries(ctx: EnumEntriesContext) {
        exit("EnumEntries")
    }

    override fun enterEnumEntry(ctx: EnumEntryContext) {
        enter("EnumEntry")
    }

    override fun exitEnumEntry(ctx: EnumEntryContext) {
        exit("EnumEntry")
    }

    override fun enterIfExpression(ctx: IfExpressionContext) {
        enter("IfExpression")
    }

    override fun exitIfExpression(ctx: IfExpressionContext) {
        exit("IfExpression")
    }

    override fun enterTryExpression(ctx: TryExpressionContext) {
        enter("TryExpression")
    }

    override fun exitTryExpression(ctx: TryExpressionContext) {
        exit("TryExpression")
    }

    override fun enterCatchBlock(ctx: CatchBlockContext) {
        enter("CatchBlock")
    }

    override fun exitCatchBlock(ctx: CatchBlockContext) {
        exit("CatchBlock")
    }

    override fun enterFinallyBlock(ctx: FinallyBlockContext) {
        enter("FinallyBlock")
    }

    override fun exitFinallyBlock(ctx: FinallyBlockContext) {
        exit("FinallyBlock")
    }

    override fun enterLoop(ctx: LoopContext) {
        enter("Loop")
    }

    override fun exitLoop(ctx: LoopContext) {
        exit("Loop")
    }

    override fun enterForLoop(ctx: ForLoopContext) {
        enter("ForLoop")
    }

    override fun exitForLoop(ctx: ForLoopContext) {
        exit("ForLoop")
    }

    override fun enterWhileLoop(ctx: WhileLoopContext) {
        enter("WhileLoop")
    }

    override fun exitWhileLoop(ctx: WhileLoopContext) {
        exit("WhileLoop")
    }

    override fun enterDoWhileLoop(ctx: DoWhileLoopContext) {
        enter("DoWhileLoop")
    }

    override fun exitDoWhileLoop(ctx: DoWhileLoopContext) {
        exit("DoWhileLoop")
    }

    override fun enterExpression(ctx: ExpressionContext) {
        enter("Expression")
    }

    override fun exitExpression(ctx: ExpressionContext) {
        exit("Expression")
    }

    override fun enterDisjunction(ctx: DisjunctionContext) {
        enter("Disjunction")
    }

    override fun exitDisjunction(ctx: DisjunctionContext) {
        exit("Disjunction")
    }

    override fun enterConjunction(ctx: ConjunctionContext) {
        enter("Conjunction")
    }

    override fun exitConjunction(ctx: ConjunctionContext) {
        exit("Conjunction")
    }

    override fun enterEqualityComparison(ctx: EqualityComparisonContext) {
        enter("EqualityComparison")
    }

    override fun exitEqualityComparison(ctx: EqualityComparisonContext) {
        exit("EqualityComparison")
    }

    override fun enterComparison(ctx: ComparisonContext) {
        enter("Comparison")
    }

    override fun exitComparison(ctx: ComparisonContext) {
        exit("Comparison")
    }

    override fun enterNamedInfix(ctx: NamedInfixContext) {
        enter("NamedInfix")
    }

    override fun exitNamedInfix(ctx: NamedInfixContext) {
        exit("NamedInfix")
    }

    override fun enterElvisExpression(ctx: ElvisExpressionContext) {
        enter("ElvisExpression")
    }

    override fun exitElvisExpression(ctx: ElvisExpressionContext) {
        exit("ElvisExpression")
    }

    override fun enterInfixFunctionCall(ctx: InfixFunctionCallContext) {
        enter("InfixFunctionCall")
    }

    override fun exitInfixFunctionCall(ctx: InfixFunctionCallContext) {
        exit("InfixFunctionCall")
    }

    override fun enterRangeExpression(ctx: RangeExpressionContext) {
        enter("RangeExpression")
    }

    override fun exitRangeExpression(ctx: RangeExpressionContext) {
        exit("RangeExpression")
    }

    override fun enterAdditiveExpression(ctx: AdditiveExpressionContext) {
        enter("AdditiveExpression")
    }

    override fun exitAdditiveExpression(ctx: AdditiveExpressionContext) {
        exit("AdditiveExpression")
    }

    override fun enterMultiplicativeExpression(ctx: MultiplicativeExpressionContext) {
        enter("MultiplicativeExpression")
    }

    override fun exitMultiplicativeExpression(ctx: MultiplicativeExpressionContext) {
        exit("MultiplicativeExpression")
    }

    override fun enterTypeRHS(ctx: TypeRHSContext) {
        enter("TypeRHS")
    }

    override fun exitTypeRHS(ctx: TypeRHSContext) {
        exit("TypeRHS")
    }

    override fun enterPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext) {
        enter("PrefixUnaryExpression")
    }

    override fun exitPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext) {
        exit("PrefixUnaryExpression")
    }

    override fun enterPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext) {
        enter("PostfixUnaryExpression")
    }

    override fun exitPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext) {
        exit("PostfixUnaryExpression")
    }

    override fun enterCallableReference(ctx: CallableReferenceContext) {
        enter("CallableReference")
    }

    override fun exitCallableReference(ctx: CallableReferenceContext) {
        exit("CallableReference")
    }

    override fun enterIdentifier(ctx: IdentifierContext) {
        enter("Identifier")
    }

    override fun exitIdentifier(ctx: IdentifierContext) {
        exit("Identifier")
    }

    override fun enterStringLiteral(ctx: StringLiteralContext) {
        enter("StringLiteral")
    }

    override fun exitStringLiteral(ctx: StringLiteralContext) {
        exit("StringLiteral")
    }

    override fun enterAtomicExpression(ctx: AtomicExpressionContext) {
        enter("AtomicExpression")
    }

    override fun exitAtomicExpression(ctx: AtomicExpressionContext) {
        exit("AtomicExpression")
    }

    override fun enterLiteralConstant(ctx: LiteralConstantContext) {
        enter("LiteralConstant")
    }

    override fun exitLiteralConstant(ctx: LiteralConstantContext) {
        exit("LiteralConstant")
    }

    override fun enterDeclaration(ctx: DeclarationContext) {
        enter("Declaration")
    }

    override fun exitDeclaration(ctx: DeclarationContext) {
        exit("Declaration")
    }

    override fun enterStatement(ctx: StatementContext) {
        enter("Statement")
    }

    override fun exitStatement(ctx: StatementContext) {
        exit("Statement")
    }

    override fun enterMultiplicativeOperation(ctx: MultiplicativeOperationContext) {
        enter("MultiplicativeOperation")
    }

    override fun exitMultiplicativeOperation(ctx: MultiplicativeOperationContext) {
        exit("MultiplicativeOperation")
    }

    override fun enterAdditiveOperation(ctx: AdditiveOperationContext) {
        enter("AdditiveOperation")
    }

    override fun exitAdditiveOperation(ctx: AdditiveOperationContext) {
        exit("AdditiveOperation")
    }

    override fun enterInOperation(ctx: InOperationContext) {
        enter("InOperation")
    }

    override fun exitInOperation(ctx: InOperationContext) {
        exit("InOperation")
    }

    override fun enterTypeOperation(ctx: TypeOperationContext) {
        enter("TypeOperation")
    }

    override fun exitTypeOperation(ctx: TypeOperationContext) {
        exit("TypeOperation")
    }

    override fun enterIsOperation(ctx: IsOperationContext) {
        enter("IsOperation")
    }

    override fun exitIsOperation(ctx: IsOperationContext) {
        exit("IsOperation")
    }

    override fun enterComparisonOperation(ctx: ComparisonOperationContext) {
        enter("ComparisonOperation")
    }

    override fun exitComparisonOperation(ctx: ComparisonOperationContext) {
        exit("ComparisonOperation")
    }

    override fun enterEqualityOperation(ctx: EqualityOperationContext) {
        enter("EqualityOperation")
    }

    override fun exitEqualityOperation(ctx: EqualityOperationContext) {
        exit("EqualityOperation")
    }

    override fun enterAssignmentOperator(ctx: AssignmentOperatorContext) {
        enter("AssignmentOperator")
    }

    override fun exitAssignmentOperator(ctx: AssignmentOperatorContext) {
        exit("AssignmentOperator")
    }

    override fun enterPrefixUnaryOperation(ctx: PrefixUnaryOperationContext) {
        enter("PrefixUnaryOperation")
    }

    override fun exitPrefixUnaryOperation(ctx: PrefixUnaryOperationContext) {
        exit("PrefixUnaryOperation")
    }

    override fun enterPostfixUnaryOperation(ctx: PostfixUnaryOperationContext) {
        enter("PostfixUnaryOperation")
    }

    override fun exitPostfixUnaryOperation(ctx: PostfixUnaryOperationContext) {
        exit("PostfixUnaryOperation")
    }

    override fun enterAnnotatedLambda(ctx: AnnotatedLambdaContext) {
        enter("AnnotatedLambda")
    }

    override fun exitAnnotatedLambda(ctx: AnnotatedLambdaContext) {
        exit("AnnotatedLambda")
    }

    override fun enterMemberAccessOperation(ctx: MemberAccessOperationContext) {
        enter("MemberAccessOperation")
    }

    override fun exitMemberAccessOperation(ctx: MemberAccessOperationContext) {
        exit("MemberAccessOperation")
    }

    override fun enterFunctionLiteral(ctx: FunctionLiteralContext) {
        enter("FunctionLiteral")
    }

    override fun exitFunctionLiteral(ctx: FunctionLiteralContext) {
        exit("FunctionLiteral")
    }

    override fun enterArrayAccess(ctx: ArrayAccessContext) {
        enter("ArrayAccess")
    }

    override fun exitArrayAccess(ctx: ArrayAccessContext) {
        exit("ArrayAccess")
    }

    override fun enterObjectLiteral(ctx: ObjectLiteralContext) {
        enter("ObjectLiteral")
    }

    override fun exitObjectLiteral(ctx: ObjectLiteralContext) {
        exit("ObjectLiteral")
    }

    override fun enterWhen(ctx: WhenContext) {
        enter("When")
    }

    override fun exitWhen(ctx: WhenContext) {
        exit("When")
    }

    override fun enterWhenEntry(ctx: WhenEntryContext) {
        enter("WhenEntry")
    }

    override fun exitWhenEntry(ctx: WhenEntryContext) {
        exit("WhenEntry")
    }

    override fun enterWhenCondition(ctx: WhenConditionContext) {
        enter("WhenCondition")
    }

    override fun exitWhenCondition(ctx: WhenConditionContext) {
        exit("WhenCondition")
    }

    override fun visitTerminal(node: TerminalNode?) {
        val symbol = node?.symbol?.text ?: ""
        (0..level).forEach { print(INDENT) }
        println("=== $symbol")
    }

    override fun visitErrorNode(node: ErrorNode?) {
        super.visitErrorNode(node)
    }
}
