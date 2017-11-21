package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble

import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtTypeProjection
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

/**
 * Specifies the type information of a property or parameter
 *
 * @property className the class name of this type
 * @property pkgName the package name of this type
 * @property typeParameters the type parameters of this type
 * @property nullable does this type support null values?
 */
open class KibbleType internal constructor(pkgName: String? = null, val className: String,
                                           override val typeParameters: MutableList<TypeParameter> = mutableListOf(),
                                           val nullable: Boolean = false) : GenericCapable, Comparable<KibbleType> {

    internal constructor(type: KibbleType) : this(type.pkgName, type.className, type.typeParameters, type.nullable)

    companion object {
        val AUTOIMPORTS = listOf("Byte", "Short", "Int", "Long", "Float", "Double",
                "Boolean", "String", "Integer", "List", "Map", "String", "MutableList", "MutableMap", "MutableString")
        private val AUTOIMPORTED = mutableSetOf<String>()

        internal fun from(type: String): KibbleType {
            return if (type.contains(".") || type.contains("<")) {
                Kibble.parseSource("val temp: $type").properties[0].type!!
            } else {
                KibbleType(className = type)
            }
        }

        internal fun from(type: KtTypeProjection): KibbleType {
            try {
                return if (type.text == "*") KibbleType(className = "*") else from(type.typeReference)!!
            } catch (e: KotlinNullPointerException) {
                println("type = ${type}")
                throw e
            }
        }

        internal fun from(kt: KtTypeReference?): KibbleType? {
            return kt?.typeElement.let {
                when (it) {
                    is KtUserType -> extractType(it)
                    is KtNullableType -> extractType(it.innerType as KtUserType, true)
                    is KtFunctionType -> KibbleFunctionType(it)
                    else -> it?.let { throw IllegalArgumentException("unknown type $it") }
                }
            }
        }

        internal fun from(kt: KtImportDirective): KibbleType {
            val fqName = kt.importedFqName!!
            return KibbleType(fqName.parent().asString(), fqName.shortName().identifier)
        }

        internal fun extractType(typeElement: KtUserType, nullable: Boolean = false): KibbleType {

            val parameters = GenericCapable.extractFromTypeProjections(typeElement.typeArguments)

            val value = (typeElement.qualifier?.text?.let { "$it." } ?: "") +
                    (typeElement.referencedName ?: "")
            var (className, pkgName) = extractPkgAndClassName(value.substringBefore("<"), typeElement.findFile())

            return KibbleType(pkgName, className, parameters, nullable)
        }

        private fun extractPkgAndClassName(raw: String, f: KtFile): Pair<String, String?> {
            if (isAutoImported(raw)) {
                return raw to null
            }
            var pkgName: String?
            val className: String
            if (raw.contains(".")) {
                val name = raw.split(".")
                        .dropLastWhile { it.isEmpty() || it[0].isUpperCase() }
                        .filter { it != "" }
                        .joinToString(".")
                pkgName = if (name != "") name else f.extractPackage()
                className = pkgName?.let { raw.substring(it.length + 1) } ?: raw
            } else {
                pkgName = f.findImport(raw) ?: f.extractPackage()
                className = raw
            }
            if (pkgName == "kotlin") {
                pkgName = null
            }
            return Pair(className, pkgName)
        }

        private fun isAutoImported(raw: String): Boolean {
            if (AUTOIMPORTED.contains(raw)) {
                return true
            }
            try {
                Class.forName("java.lang.$raw")
                AUTOIMPORTED.add(raw)
            } catch (ignore: Exception) {
            }
            return raw in AUTOIMPORTS || raw in AUTOIMPORTED
        }
    }

    var pkgName = pkgName
        set(value) {
            field = value
            fqcn = "$pkgName.$className"
        }
    /**
     * Gives the fully qualified class name for this type
     */
    var fqcn = (pkgName?.let { "$pkgName." } ?: "") + className
        private set

    internal var resolved = fqcn

    /**
     * @return the string/source form of this type
     */
    override fun toString(): String {
        val list = mutableListOf(resolved)
        var base = list.joinToString(".") +
                (if (typeParameters.isNotEmpty()) typeParameters.joinToString(prefix = "<", postfix = ">") else "")
        if (nullable) base += "?"
        return base
    }

    override fun compareTo(other: KibbleType): Int {
        return fqcn.compareTo(other.fqcn)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KibbleType

        if (pkgName != other.pkgName) return false
        if (className != other.className) return false
        if (typeParameters != other.typeParameters) return false
        if (nullable != other.nullable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pkgName?.hashCode() ?: 0
        result = 31 * result + className.hashCode()
        result = 31 * result + typeParameters.hashCode()
        result = 31 * result + nullable.hashCode()
        return result
    }

}