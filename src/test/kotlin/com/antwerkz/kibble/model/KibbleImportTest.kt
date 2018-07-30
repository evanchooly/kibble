package com.antwerkz.kibble.model

import org.testng.Assert
import org.testng.annotations.Test

class KibbleImportTest {
    @Test
    fun sorting() {
        val list = sortedSetOf(
                KibbleImport(KibbleType("org.jelly", "Hudson"), "Jenkins"),
                KibbleImport(KibbleType("javax.swing", "JTable")),
                KibbleImport(KibbleType(className = "quixote")),
                KibbleImport(KibbleType("org.apocalypse", "Bob")),
                KibbleImport(KibbleType("java.lang", "String"))
        ).toList()
        Assert.assertEquals(list, listOf(
                KibbleImport(KibbleType("java.lang", "String")),
                KibbleImport(KibbleType("javax.swing", "JTable")),
                KibbleImport(KibbleType("org.apocalypse", "Bob")),
                KibbleImport(KibbleType("org.jelly", "Hudson"), "Jenkins"),
                KibbleImport(KibbleType(className = "quixote"))))
    }

    @Test
    fun aliases() {
        Assert.assertEquals(
                KibbleImport(KibbleType("com.foo", "Bar"))
                        .toSource()
                        .toString(),
                "import com.foo.Bar\n")


        Assert.assertEquals(
                KibbleImport(KibbleType("com.foo", "Bar"), "Harry")
                        .toSource()
                        .toString(),
                "import com.foo.Bar as Harry\n")
    }


    @Test
    fun starImport() {
        tryImport("com.foo.*")
    }

    @Test
    fun duplicateImport() {
        tryImport("com.foo.Bar")
        tryImport("com.foo.Bar", "Second")
        tryImport(String::class.java)
        tryImport(String::class.java, "Second")
    }

    private fun tryImport(type: String, alias: String? = null) {
        val from = KibbleType.from(type)
        val fqcn = from.fqcn()
        val file = KibbleFile("Foo.kt")

        file.addImport(from, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn() == fqcn && it.alias == alias}, 1)

        file.addImport(type)
        Assert.assertEquals(file.imports.count { it.type.fqcn() == fqcn && it.alias == alias}, 1)
    }

    private fun tryImport(type: Class<*>, alias: String? = null) {
        val fqcn = KibbleImport(KibbleType.from(type.name), alias).type.fqcn()
        val file = KibbleFile("Foo.kt")

        file.addImport(type, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn() == fqcn && it.alias == alias }, 1)

        file.addImport(type, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn() == fqcn && it.alias == alias }, 1)
    }
}