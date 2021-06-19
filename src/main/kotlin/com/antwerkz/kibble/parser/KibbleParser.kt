package com.antwerkz.kibble.parser

import com.antwerkz.kibble.KibbleContext
import com.antwerkz.kibble.parser.KotlinParser.AdditiveExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.AdditiveOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.AnnotatedDelegationSpecifierContext
import com.antwerkz.kibble.parser.KotlinParser.AnnotatedLambdaContext
import com.antwerkz.kibble.parser.KotlinParser.AnnotationContext
import com.antwerkz.kibble.parser.KotlinParser.AnnotationUseSiteTargetContext
import com.antwerkz.kibble.parser.KotlinParser.AnonymousFunctionContext
import com.antwerkz.kibble.parser.KotlinParser.AnonymousInitializerContext
import com.antwerkz.kibble.parser.KotlinParser.AsExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.AsOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.AssignableExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.AssignableSuffixContext
import com.antwerkz.kibble.parser.KotlinParser.AssignmentAndOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.AssignmentContext
import com.antwerkz.kibble.parser.KotlinParser.BlockContext
import com.antwerkz.kibble.parser.KotlinParser.CallSuffixContext
import com.antwerkz.kibble.parser.KotlinParser.CallableReferenceContext
import com.antwerkz.kibble.parser.KotlinParser.CatchBlockContext
import com.antwerkz.kibble.parser.KotlinParser.ClassBodyContext
import com.antwerkz.kibble.parser.KotlinParser.ClassDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.ClassMemberDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.ClassMemberDeclarationsContext
import com.antwerkz.kibble.parser.KotlinParser.ClassModifierContext
import com.antwerkz.kibble.parser.KotlinParser.ClassParameterContext
import com.antwerkz.kibble.parser.KotlinParser.ClassParametersContext
import com.antwerkz.kibble.parser.KotlinParser.CollectionLiteralContext
import com.antwerkz.kibble.parser.KotlinParser.CompanionObjectContext
import com.antwerkz.kibble.parser.KotlinParser.ComparisonContext
import com.antwerkz.kibble.parser.KotlinParser.ComparisonOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.ConjunctionContext
import com.antwerkz.kibble.parser.KotlinParser.ConstructorDelegationCallContext
import com.antwerkz.kibble.parser.KotlinParser.ConstructorInvocationContext
import com.antwerkz.kibble.parser.KotlinParser.ControlStructureBodyContext
import com.antwerkz.kibble.parser.KotlinParser.DeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.DelegationSpecifierContext
import com.antwerkz.kibble.parser.KotlinParser.DelegationSpecifiersContext
import com.antwerkz.kibble.parser.KotlinParser.DirectlyAssignableExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.DisjunctionContext
import com.antwerkz.kibble.parser.KotlinParser.DoWhileStatementContext
import com.antwerkz.kibble.parser.KotlinParser.ElvisContext
import com.antwerkz.kibble.parser.KotlinParser.ElvisExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.EnumClassBodyContext
import com.antwerkz.kibble.parser.KotlinParser.EnumEntriesContext
import com.antwerkz.kibble.parser.KotlinParser.EnumEntryContext
import com.antwerkz.kibble.parser.KotlinParser.EqualityContext
import com.antwerkz.kibble.parser.KotlinParser.EqualityOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.ExclContext
import com.antwerkz.kibble.parser.KotlinParser.ExplicitDelegationContext
import com.antwerkz.kibble.parser.KotlinParser.ExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.FileAnnotationContext
import com.antwerkz.kibble.parser.KotlinParser.FinallyBlockContext
import com.antwerkz.kibble.parser.KotlinParser.ForStatementContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionBodyContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionLiteralContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionModifierContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionTypeContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionTypeParametersContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionValueParameterContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionValueParameterWithOptionalTypeContext
import com.antwerkz.kibble.parser.KotlinParser.FunctionValueParametersContext
import com.antwerkz.kibble.parser.KotlinParser.GenericCallLikeComparisonContext
import com.antwerkz.kibble.parser.KotlinParser.GetterContext
import com.antwerkz.kibble.parser.KotlinParser.IdentifierContext
import com.antwerkz.kibble.parser.KotlinParser.IfExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.ImportAliasContext
import com.antwerkz.kibble.parser.KotlinParser.ImportHeaderContext
import com.antwerkz.kibble.parser.KotlinParser.ImportListContext
import com.antwerkz.kibble.parser.KotlinParser.InOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.IndexingSuffixContext
import com.antwerkz.kibble.parser.KotlinParser.InfixFunctionCallContext
import com.antwerkz.kibble.parser.KotlinParser.InfixOperationContext
import com.antwerkz.kibble.parser.KotlinParser.InheritanceModifierContext
import com.antwerkz.kibble.parser.KotlinParser.IsOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.JumpExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.KotlinFileContext
import com.antwerkz.kibble.parser.KotlinParser.LabelContext
import com.antwerkz.kibble.parser.KotlinParser.LambdaLiteralContext
import com.antwerkz.kibble.parser.KotlinParser.LambdaParameterContext
import com.antwerkz.kibble.parser.KotlinParser.LambdaParametersContext
import com.antwerkz.kibble.parser.KotlinParser.LineStringContentContext
import com.antwerkz.kibble.parser.KotlinParser.LineStringExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.LineStringLiteralContext
import com.antwerkz.kibble.parser.KotlinParser.LiteralConstantContext
import com.antwerkz.kibble.parser.KotlinParser.LoopStatementContext
import com.antwerkz.kibble.parser.KotlinParser.MemberAccessOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.MemberModifierContext
import com.antwerkz.kibble.parser.KotlinParser.ModifierContext
import com.antwerkz.kibble.parser.KotlinParser.ModifiersContext
import com.antwerkz.kibble.parser.KotlinParser.MultiAnnotationContext
import com.antwerkz.kibble.parser.KotlinParser.MultiLineStringContentContext
import com.antwerkz.kibble.parser.KotlinParser.MultiLineStringExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.MultiLineStringLiteralContext
import com.antwerkz.kibble.parser.KotlinParser.MultiVariableDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.MultiplicativeExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.MultiplicativeOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.NavigationSuffixContext
import com.antwerkz.kibble.parser.KotlinParser.NullableTypeContext
import com.antwerkz.kibble.parser.KotlinParser.ObjectDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.ObjectLiteralContext
import com.antwerkz.kibble.parser.KotlinParser.PackageHeaderContext
import com.antwerkz.kibble.parser.KotlinParser.ParameterContext
import com.antwerkz.kibble.parser.KotlinParser.ParameterModifierContext
import com.antwerkz.kibble.parser.KotlinParser.ParameterModifiersContext
import com.antwerkz.kibble.parser.KotlinParser.ParameterWithOptionalTypeContext
import com.antwerkz.kibble.parser.KotlinParser.ParametersWithOptionalTypeContext
import com.antwerkz.kibble.parser.KotlinParser.ParenthesizedAssignableExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.ParenthesizedDirectlyAssignableExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.ParenthesizedExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.ParenthesizedTypeContext
import com.antwerkz.kibble.parser.KotlinParser.ParenthesizedUserTypeContext
import com.antwerkz.kibble.parser.KotlinParser.PlatformModifierContext
import com.antwerkz.kibble.parser.KotlinParser.PostfixUnaryExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.PostfixUnaryOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.PostfixUnarySuffixContext
import com.antwerkz.kibble.parser.KotlinParser.PrefixUnaryExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.PrefixUnaryOperatorContext
import com.antwerkz.kibble.parser.KotlinParser.PrimaryConstructorContext
import com.antwerkz.kibble.parser.KotlinParser.PrimaryExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.PropertyDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.PropertyDelegateContext
import com.antwerkz.kibble.parser.KotlinParser.PropertyModifierContext
import com.antwerkz.kibble.parser.KotlinParser.QuestContext
import com.antwerkz.kibble.parser.KotlinParser.RangeExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.RangeTestContext
import com.antwerkz.kibble.parser.KotlinParser.ReceiverTypeContext
import com.antwerkz.kibble.parser.KotlinParser.ReificationModifierContext
import com.antwerkz.kibble.parser.KotlinParser.SafeNavContext
import com.antwerkz.kibble.parser.KotlinParser.ScriptContext
import com.antwerkz.kibble.parser.KotlinParser.SecondaryConstructorContext
import com.antwerkz.kibble.parser.KotlinParser.SemiContext
import com.antwerkz.kibble.parser.KotlinParser.SemisContext
import com.antwerkz.kibble.parser.KotlinParser.SetterContext
import com.antwerkz.kibble.parser.KotlinParser.ShebangLineContext
import com.antwerkz.kibble.parser.KotlinParser.SimpleIdentifierContext
import com.antwerkz.kibble.parser.KotlinParser.SimpleUserTypeContext
import com.antwerkz.kibble.parser.KotlinParser.SingleAnnotationContext
import com.antwerkz.kibble.parser.KotlinParser.StatementContext
import com.antwerkz.kibble.parser.KotlinParser.StatementsContext
import com.antwerkz.kibble.parser.KotlinParser.StringLiteralContext
import com.antwerkz.kibble.parser.KotlinParser.SuperExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.ThisExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.TopLevelObjectContext
import com.antwerkz.kibble.parser.KotlinParser.TryExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.TypeAliasContext
import com.antwerkz.kibble.parser.KotlinParser.TypeArgumentsContext
import com.antwerkz.kibble.parser.KotlinParser.TypeConstraintContext
import com.antwerkz.kibble.parser.KotlinParser.TypeConstraintsContext
import com.antwerkz.kibble.parser.KotlinParser.TypeContext
import com.antwerkz.kibble.parser.KotlinParser.TypeModifierContext
import com.antwerkz.kibble.parser.KotlinParser.TypeModifiersContext
import com.antwerkz.kibble.parser.KotlinParser.TypeParameterContext
import com.antwerkz.kibble.parser.KotlinParser.TypeParameterModifierContext
import com.antwerkz.kibble.parser.KotlinParser.TypeParameterModifiersContext
import com.antwerkz.kibble.parser.KotlinParser.TypeParametersContext
import com.antwerkz.kibble.parser.KotlinParser.TypeProjectionContext
import com.antwerkz.kibble.parser.KotlinParser.TypeProjectionModifierContext
import com.antwerkz.kibble.parser.KotlinParser.TypeProjectionModifiersContext
import com.antwerkz.kibble.parser.KotlinParser.TypeReferenceContext
import com.antwerkz.kibble.parser.KotlinParser.TypeTestContext
import com.antwerkz.kibble.parser.KotlinParser.UnaryPrefixContext
import com.antwerkz.kibble.parser.KotlinParser.UnescapedAnnotationContext
import com.antwerkz.kibble.parser.KotlinParser.UserTypeContext
import com.antwerkz.kibble.parser.KotlinParser.ValueArgumentContext
import com.antwerkz.kibble.parser.KotlinParser.ValueArgumentsContext
import com.antwerkz.kibble.parser.KotlinParser.VariableDeclarationContext
import com.antwerkz.kibble.parser.KotlinParser.VarianceModifierContext
import com.antwerkz.kibble.parser.KotlinParser.VisibilityModifierContext
import com.antwerkz.kibble.parser.KotlinParser.WhenConditionContext
import com.antwerkz.kibble.parser.KotlinParser.WhenEntryContext
import com.antwerkz.kibble.parser.KotlinParser.WhenExpressionContext
import com.antwerkz.kibble.parser.KotlinParser.WhenSubjectContext
import com.antwerkz.kibble.parser.KotlinParser.WhileStatementContext
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

class KibbleParser(private val context: KibbleContext) : KotlinParserBaseListener() {
    override fun exitKotlinFile(ctx: KotlinFileContext?) {
        super.exitKotlinFile(ctx)
    }
    override fun enterScript(ctx: ScriptContext?) {
        super.enterScript(ctx)
    }
    override fun exitScript(ctx: ScriptContext?) {
        super.exitScript(ctx)
    }
    override fun enterShebangLine(ctx: ShebangLineContext?) {
        super.enterShebangLine(ctx)
    }
    override fun exitShebangLine(ctx: ShebangLineContext?) {
        super.exitShebangLine(ctx)
    }
    override fun exitFileAnnotation(ctx: FileAnnotationContext?) {
        super.exitFileAnnotation(ctx)
    }
    override fun exitPackageHeader(ctx: PackageHeaderContext?) {
        super.exitPackageHeader(ctx)
    }
    override fun enterImportList(ctx: ImportListContext?) {
        super.enterImportList(ctx)
    }
    override fun exitImportList(ctx: ImportListContext?) {
        super.exitImportList(ctx)
    }
    override fun enterImportHeader(ctx: ImportHeaderContext?) {
        super.enterImportHeader(ctx)
    }
    override fun exitImportHeader(ctx: ImportHeaderContext?) {
        super.exitImportHeader(ctx)
    }
    override fun enterImportAlias(ctx: ImportAliasContext?) {
        super.enterImportAlias(ctx)
    }
    override fun exitImportAlias(ctx: ImportAliasContext?) {
        super.exitImportAlias(ctx)
    }
    override fun enterTopLevelObject(ctx: TopLevelObjectContext?) {
        super.enterTopLevelObject(ctx)
    }
    override fun exitTopLevelObject(ctx: TopLevelObjectContext?) {
        super.exitTopLevelObject(ctx)
    }
    override fun enterTypeAlias(ctx: TypeAliasContext?) {
        super.enterTypeAlias(ctx)
    }
    override fun exitTypeAlias(ctx: TypeAliasContext?) {
        super.exitTypeAlias(ctx)
    }
    override fun enterDeclaration(ctx: DeclarationContext?) {
        super.enterDeclaration(ctx)
    }
    override fun exitDeclaration(ctx: DeclarationContext?) {
        super.exitDeclaration(ctx)
    }
    override fun exitClassDeclaration(ctx: ClassDeclarationContext?) {
        super.exitClassDeclaration(ctx)
    }
    override fun enterPrimaryConstructor(ctx: PrimaryConstructorContext?) {
        super.enterPrimaryConstructor(ctx)
    }
    override fun exitPrimaryConstructor(ctx: PrimaryConstructorContext?) {
        super.exitPrimaryConstructor(ctx)
    }
    override fun enterClassBody(ctx: ClassBodyContext?) {
        super.enterClassBody(ctx)
    }
    override fun exitClassBody(ctx: ClassBodyContext?) {
        super.exitClassBody(ctx)
    }
    override fun enterClassParameters(ctx: ClassParametersContext?) {
        super.enterClassParameters(ctx)
    }
    override fun exitClassParameters(ctx: ClassParametersContext?) {
        super.exitClassParameters(ctx)
    }
    override fun enterClassParameter(ctx: ClassParameterContext?) {
        super.enterClassParameter(ctx)
    }
    override fun exitClassParameter(ctx: ClassParameterContext?) {
        super.exitClassParameter(ctx)
    }
    override fun enterDelegationSpecifiers(ctx: DelegationSpecifiersContext?) {
        super.enterDelegationSpecifiers(ctx)
    }
    override fun exitDelegationSpecifiers(ctx: DelegationSpecifiersContext?) {
        super.exitDelegationSpecifiers(ctx)
    }
    override fun enterDelegationSpecifier(ctx: DelegationSpecifierContext?) {
        super.enterDelegationSpecifier(ctx)
    }
    override fun exitDelegationSpecifier(ctx: DelegationSpecifierContext?) {
        super.exitDelegationSpecifier(ctx)
    }
    override fun enterConstructorInvocation(ctx: ConstructorInvocationContext?) {
        super.enterConstructorInvocation(ctx)
    }
    override fun exitConstructorInvocation(ctx: ConstructorInvocationContext?) {
        super.exitConstructorInvocation(ctx)
    }
    override fun enterAnnotatedDelegationSpecifier(ctx: AnnotatedDelegationSpecifierContext?) {
        super.enterAnnotatedDelegationSpecifier(ctx)
    }
    override fun exitAnnotatedDelegationSpecifier(ctx: AnnotatedDelegationSpecifierContext?) {
        super.exitAnnotatedDelegationSpecifier(ctx)
    }
    override fun enterExplicitDelegation(ctx: ExplicitDelegationContext?) {
        super.enterExplicitDelegation(ctx)
    }
    override fun exitExplicitDelegation(ctx: ExplicitDelegationContext?) {
        super.exitExplicitDelegation(ctx)
    }
    override fun enterTypeParameters(ctx: TypeParametersContext?) {
        super.enterTypeParameters(ctx)
    }
    override fun exitTypeParameters(ctx: TypeParametersContext?) {
        super.exitTypeParameters(ctx)
    }
    override fun enterTypeParameter(ctx: TypeParameterContext?) {
        super.enterTypeParameter(ctx)
    }
    override fun exitTypeParameter(ctx: TypeParameterContext?) {
        super.exitTypeParameter(ctx)
    }
    override fun enterTypeConstraints(ctx: TypeConstraintsContext?) {
        super.enterTypeConstraints(ctx)
    }
    override fun exitTypeConstraints(ctx: TypeConstraintsContext?) {
        super.exitTypeConstraints(ctx)
    }
    override fun enterTypeConstraint(ctx: TypeConstraintContext?) {
        super.enterTypeConstraint(ctx)
    }
    override fun exitTypeConstraint(ctx: TypeConstraintContext?) {
        super.exitTypeConstraint(ctx)
    }
    override fun enterClassMemberDeclarations(ctx: ClassMemberDeclarationsContext?) {
        super.enterClassMemberDeclarations(ctx)
    }
    override fun exitClassMemberDeclarations(ctx: ClassMemberDeclarationsContext?) {
        super.exitClassMemberDeclarations(ctx)
    }
    override fun enterClassMemberDeclaration(ctx: ClassMemberDeclarationContext?) {
        super.enterClassMemberDeclaration(ctx)
    }
    override fun exitClassMemberDeclaration(ctx: ClassMemberDeclarationContext?) {
        super.exitClassMemberDeclaration(ctx)
    }
    override fun enterAnonymousInitializer(ctx: AnonymousInitializerContext?) {
        super.enterAnonymousInitializer(ctx)
    }
    override fun exitAnonymousInitializer(ctx: AnonymousInitializerContext?) {
        super.exitAnonymousInitializer(ctx)
    }
    override fun enterCompanionObject(ctx: CompanionObjectContext?) {
        super.enterCompanionObject(ctx)
    }
    override fun exitCompanionObject(ctx: CompanionObjectContext?) {
        super.exitCompanionObject(ctx)
    }
    override fun enterFunctionValueParameters(ctx: FunctionValueParametersContext?) {
        super.enterFunctionValueParameters(ctx)
    }
    override fun exitFunctionValueParameters(ctx: FunctionValueParametersContext?) {
        super.exitFunctionValueParameters(ctx)
    }
    override fun exitFunctionValueParameter(ctx: FunctionValueParameterContext?) {
        super.exitFunctionValueParameter(ctx)
    }
    override fun exitFunctionDeclaration(ctx: FunctionDeclarationContext?) {
        super.exitFunctionDeclaration(ctx)
    }
    override fun enterFunctionBody(ctx: FunctionBodyContext?) {
        super.enterFunctionBody(ctx)
    }
    override fun exitFunctionBody(ctx: FunctionBodyContext?) {
        super.exitFunctionBody(ctx)
    }
    override fun enterVariableDeclaration(ctx: VariableDeclarationContext?) {
        super.enterVariableDeclaration(ctx)
    }
    override fun exitVariableDeclaration(ctx: VariableDeclarationContext?) {
        super.exitVariableDeclaration(ctx)
    }
    override fun enterMultiVariableDeclaration(ctx: MultiVariableDeclarationContext?) {
        super.enterMultiVariableDeclaration(ctx)
    }
    override fun exitMultiVariableDeclaration(ctx: MultiVariableDeclarationContext?) {
        super.exitMultiVariableDeclaration(ctx)
    }
    override fun exitPropertyDeclaration(ctx: PropertyDeclarationContext?) {
        super.exitPropertyDeclaration(ctx)
    }
    override fun enterPropertyDelegate(ctx: PropertyDelegateContext?) {
        super.enterPropertyDelegate(ctx)
    }
    override fun exitPropertyDelegate(ctx: PropertyDelegateContext?) {
        super.exitPropertyDelegate(ctx)
    }
    override fun enterGetter(ctx: GetterContext?) {
        super.enterGetter(ctx)
    }
    override fun exitGetter(ctx: GetterContext?) {
        super.exitGetter(ctx)
    }
    override fun enterSetter(ctx: SetterContext?) {
        super.enterSetter(ctx)
    }
    override fun exitSetter(ctx: SetterContext?) {
        super.exitSetter(ctx)
    }
    override fun enterParametersWithOptionalType(ctx: ParametersWithOptionalTypeContext?) {
        super.enterParametersWithOptionalType(ctx)
    }
    override fun exitParametersWithOptionalType(ctx: ParametersWithOptionalTypeContext?) {
        super.exitParametersWithOptionalType(ctx)
    }
    override fun enterFunctionValueParameterWithOptionalType(ctx: FunctionValueParameterWithOptionalTypeContext?) {
        super.enterFunctionValueParameterWithOptionalType(ctx)
    }
    override fun exitFunctionValueParameterWithOptionalType(ctx: FunctionValueParameterWithOptionalTypeContext?) {
        super.exitFunctionValueParameterWithOptionalType(ctx)
    }
    override fun enterParameterWithOptionalType(ctx: ParameterWithOptionalTypeContext?) {
        super.enterParameterWithOptionalType(ctx)
    }
    override fun exitParameterWithOptionalType(ctx: ParameterWithOptionalTypeContext?) {
        super.exitParameterWithOptionalType(ctx)
    }
    override fun exitParameter(ctx: ParameterContext?) {
        super.exitParameter(ctx)
    }
    override fun enterObjectDeclaration(ctx: ObjectDeclarationContext?) {
        super.enterObjectDeclaration(ctx)
    }
    override fun exitObjectDeclaration(ctx: ObjectDeclarationContext?) {
        super.exitObjectDeclaration(ctx)
    }
    override fun enterSecondaryConstructor(ctx: SecondaryConstructorContext?) {
        super.enterSecondaryConstructor(ctx)
    }
    override fun exitSecondaryConstructor(ctx: SecondaryConstructorContext?) {
        super.exitSecondaryConstructor(ctx)
    }
    override fun enterConstructorDelegationCall(ctx: ConstructorDelegationCallContext?) {
        super.enterConstructorDelegationCall(ctx)
    }
    override fun exitConstructorDelegationCall(ctx: ConstructorDelegationCallContext?) {
        super.exitConstructorDelegationCall(ctx)
    }
    override fun enterEnumClassBody(ctx: EnumClassBodyContext?) {
        super.enterEnumClassBody(ctx)
    }
    override fun exitEnumClassBody(ctx: EnumClassBodyContext?) {
        super.exitEnumClassBody(ctx)
    }
    override fun enterEnumEntries(ctx: EnumEntriesContext?) {
        super.enterEnumEntries(ctx)
    }
    override fun exitEnumEntries(ctx: EnumEntriesContext?) {
        super.exitEnumEntries(ctx)
    }
    override fun enterEnumEntry(ctx: EnumEntryContext?) {
        super.enterEnumEntry(ctx)
    }
    override fun exitEnumEntry(ctx: EnumEntryContext?) {
        super.exitEnumEntry(ctx)
    }
    override fun exitType(ctx: TypeContext?) {
        super.exitType(ctx)
    }
    override fun enterTypeReference(ctx: TypeReferenceContext?) {
        super.enterTypeReference(ctx)
    }
    override fun exitTypeReference(ctx: TypeReferenceContext?) {
        super.exitTypeReference(ctx)
    }
    override fun enterNullableType(ctx: NullableTypeContext?) {
        super.enterNullableType(ctx)
    }
    override fun exitNullableType(ctx: NullableTypeContext?) {
        super.exitNullableType(ctx)
    }
    override fun enterQuest(ctx: QuestContext?) {
        super.enterQuest(ctx)
    }
    override fun exitQuest(ctx: QuestContext?) {
        super.exitQuest(ctx)
    }
    override fun enterUserType(ctx: UserTypeContext?) {
        super.enterUserType(ctx)
    }
    override fun exitUserType(ctx: UserTypeContext?) {
        super.exitUserType(ctx)
    }
    override fun exitSimpleUserType(ctx: SimpleUserTypeContext?) {
        super.exitSimpleUserType(ctx)
    }
    override fun enterTypeProjection(ctx: TypeProjectionContext?) {
        super.enterTypeProjection(ctx)
    }
    override fun exitTypeProjection(ctx: TypeProjectionContext?) {
        super.exitTypeProjection(ctx)
    }
    override fun enterTypeProjectionModifiers(ctx: TypeProjectionModifiersContext?) {
        super.enterTypeProjectionModifiers(ctx)
    }
    override fun exitTypeProjectionModifiers(ctx: TypeProjectionModifiersContext?) {
        super.exitTypeProjectionModifiers(ctx)
    }
    override fun enterTypeProjectionModifier(ctx: TypeProjectionModifierContext?) {
        super.enterTypeProjectionModifier(ctx)
    }
    override fun exitTypeProjectionModifier(ctx: TypeProjectionModifierContext?) {
        super.exitTypeProjectionModifier(ctx)
    }
    override fun enterFunctionType(ctx: FunctionTypeContext?) {
        super.enterFunctionType(ctx)
    }
    override fun exitFunctionType(ctx: FunctionTypeContext?) {
        super.exitFunctionType(ctx)
    }
    override fun enterFunctionTypeParameters(ctx: FunctionTypeParametersContext?) {
        super.enterFunctionTypeParameters(ctx)
    }
    override fun exitFunctionTypeParameters(ctx: FunctionTypeParametersContext?) {
        super.exitFunctionTypeParameters(ctx)
    }
    override fun enterParenthesizedType(ctx: ParenthesizedTypeContext?) {
        super.enterParenthesizedType(ctx)
    }
    override fun exitParenthesizedType(ctx: ParenthesizedTypeContext?) {
        super.exitParenthesizedType(ctx)
    }
    override fun enterReceiverType(ctx: ReceiverTypeContext?) {
        super.enterReceiverType(ctx)
    }
    override fun exitReceiverType(ctx: ReceiverTypeContext?) {
        super.exitReceiverType(ctx)
    }
    override fun enterParenthesizedUserType(ctx: ParenthesizedUserTypeContext?) {
        super.enterParenthesizedUserType(ctx)
    }
    override fun exitParenthesizedUserType(ctx: ParenthesizedUserTypeContext?) {
        super.exitParenthesizedUserType(ctx)
    }
    override fun enterStatements(ctx: StatementsContext?) {
        super.enterStatements(ctx)
    }
    override fun exitStatements(ctx: StatementsContext?) {
        super.exitStatements(ctx)
    }
    override fun enterStatement(ctx: StatementContext?) {
        super.enterStatement(ctx)
    }
    override fun exitStatement(ctx: StatementContext?) {
        super.exitStatement(ctx)
    }
    override fun enterLabel(ctx: LabelContext?) {
        super.enterLabel(ctx)
    }
    override fun exitLabel(ctx: LabelContext?) {
        super.exitLabel(ctx)
    }
    override fun enterControlStructureBody(ctx: ControlStructureBodyContext?) {
        super.enterControlStructureBody(ctx)
    }
    override fun exitControlStructureBody(ctx: ControlStructureBodyContext?) {
        super.exitControlStructureBody(ctx)
    }
    override fun enterBlock(ctx: BlockContext?) {
        super.enterBlock(ctx)
    }
    override fun exitBlock(ctx: BlockContext?) {
        super.exitBlock(ctx)
    }
    override fun enterLoopStatement(ctx: LoopStatementContext?) {
        super.enterLoopStatement(ctx)
    }
    override fun exitLoopStatement(ctx: LoopStatementContext?) {
        super.exitLoopStatement(ctx)
    }
    override fun enterForStatement(ctx: ForStatementContext?) {
        super.enterForStatement(ctx)
    }
    override fun exitForStatement(ctx: ForStatementContext?) {
        super.exitForStatement(ctx)
    }
    override fun enterWhileStatement(ctx: WhileStatementContext?) {
        super.enterWhileStatement(ctx)
    }
    override fun exitWhileStatement(ctx: WhileStatementContext?) {
        super.exitWhileStatement(ctx)
    }
    override fun enterDoWhileStatement(ctx: DoWhileStatementContext?) {
        super.enterDoWhileStatement(ctx)
    }
    override fun exitDoWhileStatement(ctx: DoWhileStatementContext?) {
        super.exitDoWhileStatement(ctx)
    }
    override fun enterAssignment(ctx: AssignmentContext?) {
        super.enterAssignment(ctx)
    }
    override fun exitAssignment(ctx: AssignmentContext?) {
        super.exitAssignment(ctx)
    }
    override fun enterSemi(ctx: SemiContext?) {
        super.enterSemi(ctx)
    }
    override fun exitSemi(ctx: SemiContext?) {
        super.exitSemi(ctx)
    }
    override fun enterSemis(ctx: SemisContext?) {
        super.enterSemis(ctx)
    }
    override fun exitSemis(ctx: SemisContext?) {
        super.exitSemis(ctx)
    }
    override fun enterExpression(ctx: ExpressionContext?) {
        super.enterExpression(ctx)
    }
    override fun exitExpression(ctx: ExpressionContext?) {
        super.exitExpression(ctx)
    }
    override fun enterDisjunction(ctx: DisjunctionContext?) {
        super.enterDisjunction(ctx)
    }
    override fun exitDisjunction(ctx: DisjunctionContext?) {
        super.exitDisjunction(ctx)
    }
    override fun enterConjunction(ctx: ConjunctionContext?) {
        super.enterConjunction(ctx)
    }
    override fun exitConjunction(ctx: ConjunctionContext?) {
        super.exitConjunction(ctx)
    }
    override fun enterEquality(ctx: EqualityContext?) {
        super.enterEquality(ctx)
    }
    override fun exitEquality(ctx: EqualityContext?) {
        super.exitEquality(ctx)
    }
    override fun enterComparison(ctx: ComparisonContext?) {
        super.enterComparison(ctx)
    }
    override fun exitComparison(ctx: ComparisonContext?) {
        super.exitComparison(ctx)
    }
    override fun enterGenericCallLikeComparison(ctx: GenericCallLikeComparisonContext?) {
        super.enterGenericCallLikeComparison(ctx)
    }
    override fun exitGenericCallLikeComparison(ctx: GenericCallLikeComparisonContext?) {
        super.exitGenericCallLikeComparison(ctx)
    }
    override fun enterInfixOperation(ctx: InfixOperationContext?) {
        super.enterInfixOperation(ctx)
    }
    override fun exitInfixOperation(ctx: InfixOperationContext?) {
        super.exitInfixOperation(ctx)
    }
    override fun enterElvisExpression(ctx: ElvisExpressionContext?) {
        super.enterElvisExpression(ctx)
    }
    override fun exitElvisExpression(ctx: ElvisExpressionContext?) {
        super.exitElvisExpression(ctx)
    }
    override fun enterElvis(ctx: ElvisContext?) {
        super.enterElvis(ctx)
    }
    override fun exitElvis(ctx: ElvisContext?) {
        super.exitElvis(ctx)
    }
    override fun enterInfixFunctionCall(ctx: InfixFunctionCallContext?) {
        super.enterInfixFunctionCall(ctx)
    }
    override fun exitInfixFunctionCall(ctx: InfixFunctionCallContext?) {
        super.exitInfixFunctionCall(ctx)
    }
    override fun enterRangeExpression(ctx: RangeExpressionContext?) {
        super.enterRangeExpression(ctx)
    }
    override fun exitRangeExpression(ctx: RangeExpressionContext?) {
        super.exitRangeExpression(ctx)
    }
    override fun enterAdditiveExpression(ctx: AdditiveExpressionContext?) {
        super.enterAdditiveExpression(ctx)
    }
    override fun exitAdditiveExpression(ctx: AdditiveExpressionContext?) {
        super.exitAdditiveExpression(ctx)
    }
    override fun enterMultiplicativeExpression(ctx: MultiplicativeExpressionContext?) {
        super.enterMultiplicativeExpression(ctx)
    }
    override fun exitMultiplicativeExpression(ctx: MultiplicativeExpressionContext?) {
        super.exitMultiplicativeExpression(ctx)
    }
    override fun enterAsExpression(ctx: AsExpressionContext?) {
        super.enterAsExpression(ctx)
    }
    override fun exitAsExpression(ctx: AsExpressionContext?) {
        super.exitAsExpression(ctx)
    }
    override fun enterPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext?) {
        super.enterPrefixUnaryExpression(ctx)
    }
    override fun exitPrefixUnaryExpression(ctx: PrefixUnaryExpressionContext?) {
        super.exitPrefixUnaryExpression(ctx)
    }
    override fun enterUnaryPrefix(ctx: UnaryPrefixContext?) {
        super.enterUnaryPrefix(ctx)
    }
    override fun exitUnaryPrefix(ctx: UnaryPrefixContext?) {
        super.exitUnaryPrefix(ctx)
    }
    override fun enterPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext?) {
        super.enterPostfixUnaryExpression(ctx)
    }
    override fun exitPostfixUnaryExpression(ctx: PostfixUnaryExpressionContext?) {
        super.exitPostfixUnaryExpression(ctx)
    }
    override fun enterPostfixUnarySuffix(ctx: PostfixUnarySuffixContext?) {
        super.enterPostfixUnarySuffix(ctx)
    }
    override fun exitPostfixUnarySuffix(ctx: PostfixUnarySuffixContext?) {
        super.exitPostfixUnarySuffix(ctx)
    }
    override fun enterDirectlyAssignableExpression(ctx: DirectlyAssignableExpressionContext?) {
        super.enterDirectlyAssignableExpression(ctx)
    }
    override fun exitDirectlyAssignableExpression(ctx: DirectlyAssignableExpressionContext?) {
        super.exitDirectlyAssignableExpression(ctx)
    }
    override fun enterParenthesizedDirectlyAssignableExpression(ctx: ParenthesizedDirectlyAssignableExpressionContext?) {
        super.enterParenthesizedDirectlyAssignableExpression(ctx)
    }
    override fun exitParenthesizedDirectlyAssignableExpression(ctx: ParenthesizedDirectlyAssignableExpressionContext?) {
        super.exitParenthesizedDirectlyAssignableExpression(ctx)
    }
    override fun enterAssignableExpression(ctx: AssignableExpressionContext?) {
        super.enterAssignableExpression(ctx)
    }
    override fun exitAssignableExpression(ctx: AssignableExpressionContext?) {
        super.exitAssignableExpression(ctx)
    }
    override fun enterParenthesizedAssignableExpression(ctx: ParenthesizedAssignableExpressionContext?) {
        super.enterParenthesizedAssignableExpression(ctx)
    }
    override fun exitParenthesizedAssignableExpression(ctx: ParenthesizedAssignableExpressionContext?) {
        super.exitParenthesizedAssignableExpression(ctx)
    }
    override fun enterAssignableSuffix(ctx: AssignableSuffixContext?) {
        super.enterAssignableSuffix(ctx)
    }
    override fun exitAssignableSuffix(ctx: AssignableSuffixContext?) {
        super.exitAssignableSuffix(ctx)
    }
    override fun enterIndexingSuffix(ctx: IndexingSuffixContext?) {
        super.enterIndexingSuffix(ctx)
    }
    override fun exitIndexingSuffix(ctx: IndexingSuffixContext?) {
        super.exitIndexingSuffix(ctx)
    }
    override fun enterNavigationSuffix(ctx: NavigationSuffixContext?) {
        super.enterNavigationSuffix(ctx)
    }
    override fun exitNavigationSuffix(ctx: NavigationSuffixContext?) {
        super.exitNavigationSuffix(ctx)
    }
    override fun enterCallSuffix(ctx: CallSuffixContext?) {
        super.enterCallSuffix(ctx)
    }
    override fun exitCallSuffix(ctx: CallSuffixContext?) {
        super.exitCallSuffix(ctx)
    }
    override fun enterAnnotatedLambda(ctx: AnnotatedLambdaContext?) {
        super.enterAnnotatedLambda(ctx)
    }
    override fun exitAnnotatedLambda(ctx: AnnotatedLambdaContext?) {
        super.exitAnnotatedLambda(ctx)
    }
    override fun enterTypeArguments(ctx: TypeArgumentsContext?) {
        super.enterTypeArguments(ctx)
    }
    override fun exitTypeArguments(ctx: TypeArgumentsContext?) {
        super.exitTypeArguments(ctx)
    }
    override fun enterValueArguments(ctx: ValueArgumentsContext?) {
        super.enterValueArguments(ctx)
    }
    override fun exitValueArguments(ctx: ValueArgumentsContext?) {
        super.exitValueArguments(ctx)
    }
    override fun enterValueArgument(ctx: ValueArgumentContext?) {
        super.enterValueArgument(ctx)
    }
    override fun exitValueArgument(ctx: ValueArgumentContext?) {
        super.exitValueArgument(ctx)
    }
    override fun enterPrimaryExpression(ctx: PrimaryExpressionContext?) {
        super.enterPrimaryExpression(ctx)
    }
    override fun exitPrimaryExpression(ctx: PrimaryExpressionContext?) {
        super.exitPrimaryExpression(ctx)
    }
    override fun enterParenthesizedExpression(ctx: ParenthesizedExpressionContext?) {
        super.enterParenthesizedExpression(ctx)
    }
    override fun exitParenthesizedExpression(ctx: ParenthesizedExpressionContext?) {
        super.exitParenthesizedExpression(ctx)
    }
    override fun enterCollectionLiteral(ctx: CollectionLiteralContext?) {
        super.enterCollectionLiteral(ctx)
    }
    override fun exitCollectionLiteral(ctx: CollectionLiteralContext?) {
        super.exitCollectionLiteral(ctx)
    }
    override fun enterLiteralConstant(ctx: LiteralConstantContext?) {
        super.enterLiteralConstant(ctx)
    }
    override fun exitLiteralConstant(ctx: LiteralConstantContext?) {
        super.exitLiteralConstant(ctx)
    }
    override fun enterStringLiteral(ctx: StringLiteralContext?) {
        super.enterStringLiteral(ctx)
    }
    override fun exitStringLiteral(ctx: StringLiteralContext?) {
        super.exitStringLiteral(ctx)
    }
    override fun enterLineStringLiteral(ctx: LineStringLiteralContext?) {
        super.enterLineStringLiteral(ctx)
    }
    override fun exitLineStringLiteral(ctx: LineStringLiteralContext?) {
        super.exitLineStringLiteral(ctx)
    }
    override fun enterMultiLineStringLiteral(ctx: MultiLineStringLiteralContext?) {
        super.enterMultiLineStringLiteral(ctx)
    }
    override fun exitMultiLineStringLiteral(ctx: MultiLineStringLiteralContext?) {
        super.exitMultiLineStringLiteral(ctx)
    }
    override fun enterLineStringContent(ctx: LineStringContentContext?) {
        super.enterLineStringContent(ctx)
    }
    override fun exitLineStringContent(ctx: LineStringContentContext?) {
        super.exitLineStringContent(ctx)
    }
    override fun enterLineStringExpression(ctx: LineStringExpressionContext?) {
        super.enterLineStringExpression(ctx)
    }
    override fun exitLineStringExpression(ctx: LineStringExpressionContext?) {
        super.exitLineStringExpression(ctx)
    }
    override fun enterMultiLineStringContent(ctx: MultiLineStringContentContext?) {
        super.enterMultiLineStringContent(ctx)
    }
    override fun exitMultiLineStringContent(ctx: MultiLineStringContentContext?) {
        super.exitMultiLineStringContent(ctx)
    }
    override fun enterMultiLineStringExpression(ctx: MultiLineStringExpressionContext?) {
        super.enterMultiLineStringExpression(ctx)
    }
    override fun exitMultiLineStringExpression(ctx: MultiLineStringExpressionContext?) {
        super.exitMultiLineStringExpression(ctx)
    }
    override fun enterLambdaLiteral(ctx: LambdaLiteralContext?) {
        super.enterLambdaLiteral(ctx)
    }
    override fun exitLambdaLiteral(ctx: LambdaLiteralContext?) {
        super.exitLambdaLiteral(ctx)
    }
    override fun enterLambdaParameters(ctx: LambdaParametersContext?) {
        super.enterLambdaParameters(ctx)
    }
    override fun exitLambdaParameters(ctx: LambdaParametersContext?) {
        super.exitLambdaParameters(ctx)
    }
    override fun enterLambdaParameter(ctx: LambdaParameterContext?) {
        super.enterLambdaParameter(ctx)
    }
    override fun exitLambdaParameter(ctx: LambdaParameterContext?) {
        super.exitLambdaParameter(ctx)
    }
    override fun enterAnonymousFunction(ctx: AnonymousFunctionContext?) {
        super.enterAnonymousFunction(ctx)
    }
    override fun exitAnonymousFunction(ctx: AnonymousFunctionContext?) {
        super.exitAnonymousFunction(ctx)
    }
    override fun enterFunctionLiteral(ctx: FunctionLiteralContext?) {
        super.enterFunctionLiteral(ctx)
    }
    override fun exitFunctionLiteral(ctx: FunctionLiteralContext?) {
        super.exitFunctionLiteral(ctx)
    }
    override fun enterObjectLiteral(ctx: ObjectLiteralContext?) {
        super.enterObjectLiteral(ctx)
    }
    override fun exitObjectLiteral(ctx: ObjectLiteralContext?) {
        super.exitObjectLiteral(ctx)
    }
    override fun enterThisExpression(ctx: ThisExpressionContext?) {
        super.enterThisExpression(ctx)
    }
    override fun exitThisExpression(ctx: ThisExpressionContext?) {
        super.exitThisExpression(ctx)
    }
    override fun enterSuperExpression(ctx: SuperExpressionContext?) {
        super.enterSuperExpression(ctx)
    }
    override fun exitSuperExpression(ctx: SuperExpressionContext?) {
        super.exitSuperExpression(ctx)
    }
    override fun enterIfExpression(ctx: IfExpressionContext?) {
        super.enterIfExpression(ctx)
    }
    override fun exitIfExpression(ctx: IfExpressionContext?) {
        super.exitIfExpression(ctx)
    }
    override fun enterWhenSubject(ctx: WhenSubjectContext?) {
        super.enterWhenSubject(ctx)
    }
    override fun exitWhenSubject(ctx: WhenSubjectContext?) {
        super.exitWhenSubject(ctx)
    }
    override fun enterWhenExpression(ctx: WhenExpressionContext?) {
        super.enterWhenExpression(ctx)
    }
    override fun exitWhenExpression(ctx: WhenExpressionContext?) {
        super.exitWhenExpression(ctx)
    }
    override fun enterWhenEntry(ctx: WhenEntryContext?) {
        super.enterWhenEntry(ctx)
    }
    override fun exitWhenEntry(ctx: WhenEntryContext?) {
        super.exitWhenEntry(ctx)
    }
    override fun enterWhenCondition(ctx: WhenConditionContext?) {
        super.enterWhenCondition(ctx)
    }
    override fun exitWhenCondition(ctx: WhenConditionContext?) {
        super.exitWhenCondition(ctx)
    }
    override fun enterRangeTest(ctx: RangeTestContext?) {
        super.enterRangeTest(ctx)
    }
    override fun exitRangeTest(ctx: RangeTestContext?) {
        super.exitRangeTest(ctx)
    }
    override fun enterTypeTest(ctx: TypeTestContext?) {
        super.enterTypeTest(ctx)
    }
    override fun exitTypeTest(ctx: TypeTestContext?) {
        super.exitTypeTest(ctx)
    }
    override fun enterTryExpression(ctx: TryExpressionContext?) {
        super.enterTryExpression(ctx)
    }
    override fun exitTryExpression(ctx: TryExpressionContext?) {
        super.exitTryExpression(ctx)
    }
    override fun enterCatchBlock(ctx: CatchBlockContext?) {
        super.enterCatchBlock(ctx)
    }
    override fun exitCatchBlock(ctx: CatchBlockContext?) {
        super.exitCatchBlock(ctx)
    }
    override fun enterFinallyBlock(ctx: FinallyBlockContext?) {
        super.enterFinallyBlock(ctx)
    }
    override fun exitFinallyBlock(ctx: FinallyBlockContext?) {
        super.exitFinallyBlock(ctx)
    }
    override fun enterJumpExpression(ctx: JumpExpressionContext?) {
        super.enterJumpExpression(ctx)
    }
    override fun exitJumpExpression(ctx: JumpExpressionContext?) {
        super.exitJumpExpression(ctx)
    }
    override fun enterCallableReference(ctx: CallableReferenceContext?) {
        super.enterCallableReference(ctx)
    }
    override fun exitCallableReference(ctx: CallableReferenceContext?) {
        super.exitCallableReference(ctx)
    }
    override fun enterAssignmentAndOperator(ctx: AssignmentAndOperatorContext?) {
        super.enterAssignmentAndOperator(ctx)
    }
    override fun exitAssignmentAndOperator(ctx: AssignmentAndOperatorContext?) {
        super.exitAssignmentAndOperator(ctx)
    }
    override fun enterEqualityOperator(ctx: EqualityOperatorContext?) {
        super.enterEqualityOperator(ctx)
    }
    override fun exitEqualityOperator(ctx: EqualityOperatorContext?) {
        super.exitEqualityOperator(ctx)
    }
    override fun enterComparisonOperator(ctx: ComparisonOperatorContext?) {
        super.enterComparisonOperator(ctx)
    }
    override fun exitComparisonOperator(ctx: ComparisonOperatorContext?) {
        super.exitComparisonOperator(ctx)
    }
    override fun enterInOperator(ctx: InOperatorContext?) {
        super.enterInOperator(ctx)
    }
    override fun exitInOperator(ctx: InOperatorContext?) {
        super.exitInOperator(ctx)
    }
    override fun enterIsOperator(ctx: IsOperatorContext?) {
        super.enterIsOperator(ctx)
    }
    override fun exitIsOperator(ctx: IsOperatorContext?) {
        super.exitIsOperator(ctx)
    }
    override fun enterAdditiveOperator(ctx: AdditiveOperatorContext?) {
        super.enterAdditiveOperator(ctx)
    }
    override fun exitAdditiveOperator(ctx: AdditiveOperatorContext?) {
        super.exitAdditiveOperator(ctx)
    }
    override fun enterMultiplicativeOperator(ctx: MultiplicativeOperatorContext?) {
        super.enterMultiplicativeOperator(ctx)
    }
    override fun exitMultiplicativeOperator(ctx: MultiplicativeOperatorContext?) {
        super.exitMultiplicativeOperator(ctx)
    }
    override fun enterAsOperator(ctx: AsOperatorContext?) {
        super.enterAsOperator(ctx)
    }
    override fun exitAsOperator(ctx: AsOperatorContext?) {
        super.exitAsOperator(ctx)
    }
    override fun enterPrefixUnaryOperator(ctx: PrefixUnaryOperatorContext?) {
        super.enterPrefixUnaryOperator(ctx)
    }
    override fun exitPrefixUnaryOperator(ctx: PrefixUnaryOperatorContext?) {
        super.exitPrefixUnaryOperator(ctx)
    }
    override fun enterPostfixUnaryOperator(ctx: PostfixUnaryOperatorContext?) {
        super.enterPostfixUnaryOperator(ctx)
    }
    override fun exitPostfixUnaryOperator(ctx: PostfixUnaryOperatorContext?) {
        super.exitPostfixUnaryOperator(ctx)
    }
    override fun enterExcl(ctx: ExclContext?) {
        super.enterExcl(ctx)
    }
    override fun exitExcl(ctx: ExclContext?) {
        super.exitExcl(ctx)
    }
    override fun enterMemberAccessOperator(ctx: MemberAccessOperatorContext?) {
        super.enterMemberAccessOperator(ctx)
    }
    override fun exitMemberAccessOperator(ctx: MemberAccessOperatorContext?) {
        super.exitMemberAccessOperator(ctx)
    }
    override fun enterSafeNav(ctx: SafeNavContext?) {
        super.enterSafeNav(ctx)
    }
    override fun exitSafeNav(ctx: SafeNavContext?) {
        super.exitSafeNav(ctx)
    }
    override fun enterModifiers(ctx: ModifiersContext?) {
        super.enterModifiers(ctx)
    }
    override fun exitModifiers(ctx: ModifiersContext?) {
        super.exitModifiers(ctx)
    }
    override fun enterParameterModifiers(ctx: ParameterModifiersContext?) {
        super.enterParameterModifiers(ctx)
    }
    override fun exitParameterModifiers(ctx: ParameterModifiersContext?) {
        super.exitParameterModifiers(ctx)
    }
    override fun enterModifier(ctx: ModifierContext?) {
        super.enterModifier(ctx)
    }
    override fun exitModifier(ctx: ModifierContext?) {
        super.exitModifier(ctx)
    }
    override fun enterTypeModifiers(ctx: TypeModifiersContext?) {
        super.enterTypeModifiers(ctx)
    }
    override fun exitTypeModifiers(ctx: TypeModifiersContext?) {
        super.exitTypeModifiers(ctx)
    }
    override fun enterTypeModifier(ctx: TypeModifierContext?) {
        super.enterTypeModifier(ctx)
    }
    override fun exitTypeModifier(ctx: TypeModifierContext?) {
        super.exitTypeModifier(ctx)
    }
    override fun enterClassModifier(ctx: ClassModifierContext?) {
        super.enterClassModifier(ctx)
    }
    override fun exitClassModifier(ctx: ClassModifierContext?) {
        super.exitClassModifier(ctx)
    }
    override fun enterMemberModifier(ctx: MemberModifierContext?) {
        super.enterMemberModifier(ctx)
    }
    override fun exitMemberModifier(ctx: MemberModifierContext?) {
        super.exitMemberModifier(ctx)
    }
    override fun enterVisibilityModifier(ctx: VisibilityModifierContext?) {
        super.enterVisibilityModifier(ctx)
    }
    override fun exitVisibilityModifier(ctx: VisibilityModifierContext?) {
        super.exitVisibilityModifier(ctx)
    }
    override fun enterVarianceModifier(ctx: VarianceModifierContext?) {
        super.enterVarianceModifier(ctx)
    }
    override fun exitVarianceModifier(ctx: VarianceModifierContext?) {
        super.exitVarianceModifier(ctx)
    }
    override fun enterTypeParameterModifiers(ctx: TypeParameterModifiersContext?) {
        super.enterTypeParameterModifiers(ctx)
    }
    override fun exitTypeParameterModifiers(ctx: TypeParameterModifiersContext?) {
        super.exitTypeParameterModifiers(ctx)
    }
    override fun enterTypeParameterModifier(ctx: TypeParameterModifierContext?) {
        super.enterTypeParameterModifier(ctx)
    }
    override fun exitTypeParameterModifier(ctx: TypeParameterModifierContext?) {
        super.exitTypeParameterModifier(ctx)
    }
    override fun enterFunctionModifier(ctx: FunctionModifierContext?) {
        super.enterFunctionModifier(ctx)
    }
    override fun exitFunctionModifier(ctx: FunctionModifierContext?) {
        super.exitFunctionModifier(ctx)
    }
    override fun enterPropertyModifier(ctx: PropertyModifierContext?) {
        super.enterPropertyModifier(ctx)
    }
    override fun exitPropertyModifier(ctx: PropertyModifierContext?) {
        super.exitPropertyModifier(ctx)
    }
    override fun enterInheritanceModifier(ctx: InheritanceModifierContext?) {
        super.enterInheritanceModifier(ctx)
    }
    override fun exitInheritanceModifier(ctx: InheritanceModifierContext?) {
        super.exitInheritanceModifier(ctx)
    }
    override fun enterParameterModifier(ctx: ParameterModifierContext?) {
        super.enterParameterModifier(ctx)
    }
    override fun exitParameterModifier(ctx: ParameterModifierContext?) {
        super.exitParameterModifier(ctx)
    }
    override fun enterReificationModifier(ctx: ReificationModifierContext?) {
        super.enterReificationModifier(ctx)
    }
    override fun exitReificationModifier(ctx: ReificationModifierContext?) {
        super.exitReificationModifier(ctx)
    }
    override fun enterPlatformModifier(ctx: PlatformModifierContext?) {
        super.enterPlatformModifier(ctx)
    }
    override fun exitPlatformModifier(ctx: PlatformModifierContext?) {
        super.exitPlatformModifier(ctx)
    }
    override fun exitAnnotation(ctx: AnnotationContext?) {
        super.exitAnnotation(ctx)
    }
    override fun enterSingleAnnotation(ctx: SingleAnnotationContext?) {
        super.enterSingleAnnotation(ctx)
    }
    override fun exitSingleAnnotation(ctx: SingleAnnotationContext?) {
        super.exitSingleAnnotation(ctx)
    }
    override fun enterMultiAnnotation(ctx: MultiAnnotationContext?) {
        super.enterMultiAnnotation(ctx)
    }
    override fun exitMultiAnnotation(ctx: MultiAnnotationContext?) {
        super.exitMultiAnnotation(ctx)
    }
    override fun enterAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext?) {
        super.enterAnnotationUseSiteTarget(ctx)
    }
    override fun exitAnnotationUseSiteTarget(ctx: AnnotationUseSiteTargetContext?) {
        super.exitAnnotationUseSiteTarget(ctx)
    }
    override fun enterUnescapedAnnotation(ctx: UnescapedAnnotationContext?) {
        super.enterUnescapedAnnotation(ctx)
    }
    override fun exitUnescapedAnnotation(ctx: UnescapedAnnotationContext?) {
        super.exitUnescapedAnnotation(ctx)
    }
    override fun enterSimpleIdentifier(ctx: SimpleIdentifierContext?) {
        super.enterSimpleIdentifier(ctx)
    }
    override fun exitSimpleIdentifier(ctx: SimpleIdentifierContext?) {
        super.exitSimpleIdentifier(ctx)
    }
    override fun enterIdentifier(ctx: IdentifierContext?) {
        super.enterIdentifier(ctx)
    }
    override fun exitIdentifier(ctx: IdentifierContext?) {
        super.exitIdentifier(ctx)
    }
    override fun visitTerminal(node: TerminalNode?) {
        super.visitTerminal(node)
    }
    override fun visitErrorNode(node: ErrorNode?) {
        super.visitErrorNode(node)
    }
    override fun enterEveryRule(ctx: ParserRuleContext?) {
        super.enterEveryRule(ctx)
    }
    override fun exitEveryRule(ctx: ParserRuleContext?) {
        super.exitEveryRule(ctx)
    }

    override fun enterFileAnnotation(ctx: FileAnnotationContext) {
        super.enterFileAnnotation(ctx)
    }

    override fun enterAnnotation(ctx: AnnotationContext) {
        super.enterAnnotation(ctx)
    }

    override fun enterKotlinFile(ctx: KotlinFileContext) {
        println("ctx = ${ctx}")
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

    override fun enterPropertyDeclaration(ctx: PropertyDeclarationContext) {
        context.bookmark("property ${ctx.variableDeclaration().simpleIdentifier()}")
    }

    override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
        context.bookmark("class ${ctx.simpleIdentifier()}")
    }
}