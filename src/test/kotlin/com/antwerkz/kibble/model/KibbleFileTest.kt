package com.antwerkz.kibble.model

import com.antwerkz.kibble.Kibble
import org.intellij.lang.annotations.Language
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File
import java.sql.ResultSet

class KibbleFileTest {
    @Test(expectedExceptions = arrayOf(IllegalArgumentException::class))
    fun constructorProperties() {
        KibbleFile().addProperty("name").constructorParam = true
    }

    @Test
    fun imports() {
        val file = KibbleFile()
        file.pkgName = "com.antwerkz.kibble"

        file.addImport(ResultSet::class.java, "aliasName")
        file.addImport(String::class.java, "anotherAlias")
        assertImport(file, "java.lang.String", "anotherAlias")
        Assert.assertEquals(file.toSource().toString().trim(),
                """package com.antwerkz.kibble

import java.lang.String as anotherAlias
import java.sql.ResultSet as aliasName""")
    }

    @Test
    fun importOrdering() {
        val file = KibbleFile()

        file.addImport("java.util.ArrayList")
        file.addImport("javax.annotation.Generated")
        file.addImport("java.util.HashMap", "HMap")

        val iterator = file.imports.iterator()
        Assert.assertEquals(iterator.next().type.fqcn(), "java.util.ArrayList")
        val next = iterator.next()
        Assert.assertEquals(next.type.fqcn(), "java.util.HashMap")
        Assert.assertEquals(next.alias, "HMap")
        Assert.assertEquals(iterator.next().type.fqcn(), "javax.annotation.Generated")
    }

    @Test
    fun outputFile() {
        val file = KibbleFile("Foo.kt")
        Assert.assertEquals(file.outputFile(File("/tmp/")), File("/tmp/Foo.kt"))

        file.pkgName = "com.antwerkz.kibble"
        Assert.assertEquals(file.outputFile(File("/tmp/")), File("/tmp/com/antwerkz/kibble/Foo.kt"))
    }

    @Test
    fun resolve() {
        @Language("kotlin")
        val source = """
            package com.antwerkz.testing

            import com.foo.Bar
            import com.zorg.Flur

            class Main {
                val s: Second = Second()
                val t: Third = Third.HI
                val b: Bar = Bar()
                val f: com.zorg.Flur = com.zorg.Flur()
                val g: Generic<Int>
            }

            class Second""".trimIndent()

        @Language("kotlin")
        val source2 = """
            package com.antwerkz.testing

            enum class Third {
                HI
            }

            class Generic<T>""".trimIndent()

        val file = Kibble.parseSource(source)
        Kibble.parseSource(source2, file.context)
        val props = file.classes[0].properties.iterator()

        check(props.next(), "Second", "com.antwerkz.testing.Second")
        check(props.next(), "com.antwerkz.testing.Third", "com.antwerkz.testing.Third")
        check(props.next(), "com.foo.Bar", "com.foo.Bar")
        check(props.next(), "com.zorg.Flur", "com.zorg.Flur")
        check(props.next(), "com.antwerkz.testing.Generic<Int>", "com.antwerkz.testing.Generic")

    }

    @Test
    fun resolveClassesInFile() {
        @Language("kotlin")
        val source = """package com.antwerkz.testing

class Main {
    val s: Second = Second()
}
class Second""".trim()

        val file = Kibble.parseSource(source)
        val props = file.classes[0].properties.iterator()

        check(props.next(), "com.antwerkz.testing.Second", "com.antwerkz.testing.Second")
    }

    @Test
    fun resolveClassesInAnotherFile() {

        val sourceFile1 = createTempFile("source1", ".kt").also {
            it.writeText("""package com.antwerkz.testing

class Main {
    val s: Second = Second()
}""")
        }
        val sourceFile2 = createTempFile("source2", ".kt").also {
            it.writeText("""package com.antwerkz.testing

class Second""".trim())
        }

        val files = Kibble.parse(listOf(sourceFile1, sourceFile2))
        val props = files[0].classes[0].properties.iterator()

        check(props.next(), "com.antwerkz.testing.Second", "com.antwerkz.testing.Second")
    }

    private fun check(property: KibbleProperty, expectedName: String, fqcn: String) {
        val type = property.type!!
        Assert.assertEquals(type.toString(), expectedName)
        Assert.assertEquals(type.fqcn(), fqcn)
    }

    @Test
    fun normalize() {
        val file = KibbleFile("test.kt")

        val list = file.resolve(KibbleType.from("java.util.List"))
        Assert.assertEquals(list.toString(), "List")
        Assert.assertEquals(list.resolved, "List")
        Assert.assertNotNull(file.imports.firstOrNull { "List" == it.alias || "List" == it.type.className })
        assertImport(file, "java.util.List")

        val resolve = file.resolve(list)
        Assert.assertEquals(resolve.toString(), "List")
        Assert.assertEquals(resolve.resolved, "List")
        assertImport(file, "java.util.List")

        val bareList = file.resolve(KibbleType.from("List"))
        Assert.assertEquals(bareList.toString(), "List")
        Assert.assertEquals(bareList.resolved, "List")
        assertImport(file, "java.util.List")

        val set = file.resolve(KibbleType.from("java.util.Set"))
        Assert.assertEquals(set.toString(), "Set")
        Assert.assertEquals(set.resolved, "Set")
        assertImport(file, "java.util.Set")

        file.addImport(java.awt.List::class.java, "awtList")
        val awt = file.resolve(KibbleType.from("java.awt.List"))
        Assert.assertEquals(awt.toString(), "awtList")
        Assert.assertEquals(awt.resolved, "awtList")
        assertImport(file, "java.awt.List", "awtList")

        val awt2 = file.resolve(KibbleType.from("awtList"))
        Assert.assertEquals(awt2.toString(), "awtList")
        Assert.assertEquals(awt2.resolved, "awtList")

        val entry = file.resolve(KibbleType.from("Map.Entry"))
        Assert.assertEquals(entry.toString(), "Map.Entry")
        Assert.assertEquals(entry.resolved, "Map.Entry")

        val bob = file.resolve(KibbleType.from("Bob"))
        Assert.assertEquals(bob.toString(), "Bob")
        Assert.assertEquals(bob.resolved, "Bob")
        assertNoImport(file, "Bob", null)
    }


    private fun assertImport(file: KibbleFile, fqcn: String, alias: String? = null) {
        Assert.assertEquals(file.imports.filter {
            it.type.fqcn() == fqcn
        }.size, 1, "Should have found an import for $fqcn")

        alias?.let {
            Assert.assertEquals(file.imports
                    .filter { it.alias == alias }
                    .size, 1, "Should have found an import for $alias")
        }
    }

    private fun assertNoImport(file: KibbleFile, fqcn: String, alias: String? = null) {
        Assert.assertEquals(file.imports.filter {
            it.type.fqcn() == fqcn
        }.size, 0, "Should not have found an import for $fqcn")

        alias?.let {
            Assert.assertEquals(file.imports
                    .filter { it.alias == alias }
                    .size, 0, "Should not have found an import for $alias")
        }
    }
}