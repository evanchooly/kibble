package com.antwerkz.kibble.model

import org.testng.Assert
import org.testng.annotations.Test

class KibbleImportTest {
    @Test
    fun sorting() {
        val list = sortedSetOf(
                KibbleImport(KibbleType("Hudson", "org.jelly", alias = "Jenkins")),
                KibbleImport(KibbleType("JTable", "javax.swing")),
                KibbleImport(KibbleType("quixote", alias = "Jim")),
                KibbleImport(KibbleType("Bob", "org.apocalypse")),
                KibbleImport(KibbleType("String", "java.lang"))
        ).toList()
        Assert.assertEquals(list, listOf(
                KibbleImport(KibbleType("String", "java.lang")),
                KibbleImport(KibbleType("JTable", "javax.swing")),
                KibbleImport(KibbleType("Bob", "org.apocalypse")),
                KibbleImport(KibbleType("Hudson", "org.jelly", alias = "Jenkins")),
                KibbleImport(KibbleType("quixote", alias = "Jim"))))
    }

    @Test
    fun aliases() {
        Assert.assertEquals(
                KibbleImport(KibbleType("Bar", "com.foo"))
                        .toSource()
                        .toString(),
                "import com.foo.Bar\n")


        Assert.assertEquals(
                KibbleImport(KibbleType("Bar", "com.foo", alias = "Harry"))
                        .toSource()
                        .toString(),
                "import com.foo.Bar as Harry\n")
    }


    @Test
    fun duplicateImport() {
        tryImport(KibbleFile("Foo.kt"), "com.foo.Bar")
        tryImport(KibbleFile("Foo.kt"), "com.foo.Bar", "Second")
        tryImport(KibbleFile("Foo.kt"), String::class.java)
        tryImport(KibbleFile("Foo.kt"), String::class.java, "Second")
    }

    private fun tryImport(file: KibbleFile, type: String, alias: String? = null) {
        val from = KibbleType.from(type, alias = alias)
        val fqcn = from.fqcn

        file.addImport(type, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn == fqcn && it.type.alias == alias}, 1)

        file.addImport(type)
        Assert.assertEquals(file.imports.count { it.type.fqcn == fqcn && it.type.alias == alias}, 1)
    }

    private fun tryImport(file: KibbleFile, type: Class<*>, alias: String? = null) {
        val fqcn = KibbleImport(KibbleType.from(type.name, alias = alias)).type.fqcn

        file.addImport(type, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn == fqcn && it.type.alias == alias }, 1)

        file.addImport(type, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn == fqcn && it.type.alias == alias }, 1)
    }
}