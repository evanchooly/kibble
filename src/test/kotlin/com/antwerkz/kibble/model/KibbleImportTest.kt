package com.antwerkz.kibble.model

import org.testng.Assert
import org.testng.annotations.Test

class KibbleImportTest {
    @Test
    fun sorting() {
        val file = KibbleFile()
        val list = sortedSetOf(
                KibbleImport(KibbleType(file, "Hudson", "org.jelly", alias = "Jenkins")),
                KibbleImport(KibbleType(file, "JTable", "javax.swing")),
                KibbleImport(KibbleType(file, "quixote", alias = "Jim")),
                KibbleImport(KibbleType(file, "Bob", "org.apocalypse")),
                KibbleImport(KibbleType(file, "String", "java.lang"))
        ).toList()
        Assert.assertEquals(list, listOf(
                KibbleImport(KibbleType(file, "String", "java.lang")),
                KibbleImport(KibbleType(file, "JTable", "javax.swing")),
                KibbleImport(KibbleType(file, "Bob", "org.apocalypse")),
                KibbleImport(KibbleType(file, "Hudson", "org.jelly", alias = "Jenkins")),
                KibbleImport(KibbleType(file, "quixote", alias = "Jim"))))
    }

    @Test
    fun aliases() {
        val file = KibbleFile()
        Assert.assertEquals(
                KibbleImport(KibbleType(file, "Bar", "com.foo"))
                        .toSource()
                        .toString(),
                "import com.foo.Bar\n")


        Assert.assertEquals(
                KibbleImport(KibbleType(file, "Bar", "com.foo", alias = "Harry"))
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
        val from = KibbleType.from(file, type, alias = alias)
        val fqcn = from.fqcn

        file.addImport(type, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn == fqcn && it.type.alias == alias}, 1)

        file.addImport(type)
        Assert.assertEquals(file.imports.count { it.type.fqcn == fqcn && it.type.alias == alias}, 1)
    }

    private fun tryImport(file: KibbleFile, type: Class<*>, alias: String? = null) {
        val fqcn = KibbleImport(KibbleType.from(file, type.name, alias = alias)).type.fqcn

        file.addImport(type, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn == fqcn && it.type.alias == alias }, 1)

        file.addImport(type, alias)
        Assert.assertEquals(file.imports.count { it.type.fqcn == fqcn && it.type.alias == alias }, 1)
    }
}