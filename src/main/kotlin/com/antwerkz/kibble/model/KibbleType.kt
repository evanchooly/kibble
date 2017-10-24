package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

/**
 * Specifies the type information of a property or parameter
 *
 * @property className the class name of this type
 * @property pkgName the package name of this type
 * @property typeParameters the type parameters of this type
 * @property nullable does this type support null values?
 * @property alias the type name alias
 * @property imported true if this type has been imported.  if true, only the className/alias will be used when generating the source code
 */
open class KibbleType internal constructor(override val file: KibbleFile, val className: String, val pkgName: String? = null,
                                           override var typeParameters: List<TypeParameter> = listOf(),
                                           val nullable: Boolean = false, val alias: String? = null,
                                           private val imported: Boolean = false) : GenericCapable, Comparable<KibbleType> {

    companion object {
        /**
         * Creates a KibbleType from ths string
         *
         * @return the new KibbleType
         */
        internal fun from(file: KibbleFile, type: String, alias: String? = null): KibbleType {
            val parsed = if (type.contains(".") || type.contains("<")) {
                Kibble.parseSource("val temp: $type").properties[0].type!!
            } else {
                KibbleType(file, type)
            }
            return alias?.let { KibbleType(parsed, alias) } ?: parsed
        }

        internal fun from(file: KibbleFile, kt: KtTypeReference?): KibbleType? {
            return kt?.typeElement.let {
                when (it) {
                    is KtUserType -> extractType(file, it)
                    is KtNullableType -> extractType(file, it.innerType as KtUserType, true)
                    is KtFunctionType -> KibbleFunctionType(file, it)
                    else -> it?.let { throw IllegalArgumentException("unknown type $it") }
                }
            }
        }

        internal fun from(file: KibbleFile, kt: KtImportDirective): KibbleType {
            val fqName = kt.importedFqName!!
            return KibbleType(file, fqName.shortName().identifier, fqName.parent().asString(), alias = kt.aliasName)
        }

        internal fun extractType(file: KibbleFile, typeElement: KtUserType, nullable: Boolean = false): KibbleType {
            val value = (typeElement.qualifier?.text?.let { "$it." } ?: "") +
                    (typeElement.referencedName ?: "")
            val parameters = GenericCapable.extractFromTypeProjections(file, typeElement.typeArguments)
            val raw = value.substringBefore("<")
            var pkgName = if (raw.contains(".")) {
                raw.split(".")
                        .dropLastWhile { it[0].isUpperCase() }
                        .filter { it != "" }
                        .joinToString(".")
            } else null
            if (pkgName == "") pkgName = null

            val className = pkgName?.let { raw.substring(it.length + 1) } ?: raw

            return KibbleType(file, className, pkgName, parameters, nullable)
        }
    }

    internal constructor(type: KibbleType, imported: Boolean) : this(type.file, type.className, type.pkgName, type.typeParameters,
            imported = imported)

    internal constructor(type: KibbleType, alias: String) : this(type.file, type.className, type.pkgName, type.typeParameters,
            type.nullable, alias)

    /**
     * Gives the expression of this type for use in the source complete with type parameters
     */
    val value: String by lazy {
        val list = mutableListOf<String>()
        if (!imported && pkgName != null) {
            list.add(pkgName)
        }
        list.add(alias ?: className)
        var base = list.joinToString(".") +
                (if (typeParameters.isNotEmpty()) typeParameters.joinToString(prefix = "<", postfix = ">") else "")
        if (nullable) base += "?"

        base
    }


    /**
     * Gives the fully qualified class name for this type
     */
    val fqcn: String by lazy { (pkgName?.let { "$pkgName." } ?: "") + className }

    /**
     * @return the string/source form of this type
     */
    override fun toString() = value

    override fun compareTo(other: KibbleType): Int {
        return fqcn.compareTo(other.fqcn)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KibbleType

        if (className != other.className) return false
        if (pkgName != other.pkgName) return false
        if (typeParameters != other.typeParameters) return false
        if (nullable != other.nullable) return false
        if (alias != other.alias) return false
        if (imported != other.imported) return false

        return true
    }

    override fun hashCode(): Int {
        var result = className.hashCode()
        result = 31 * result + (pkgName?.hashCode() ?: 0)
        result = 31 * result + typeParameters.hashCode()
        result = 31 * result + nullable.hashCode()
        result = 31 * result + (alias?.hashCode() ?: 0)
        result = 31 * result + imported.hashCode()
        return result
    }
}