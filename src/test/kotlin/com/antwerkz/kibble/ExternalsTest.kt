package com.antwerkz.kibble

import org.eclipse.jgit.api.Git
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import java.io.File

class ExternalsTest {
    @DataProvider(name = "repos")
    fun repos(): Array<Array<Any>> {
        return arrayOf(
                arrayOf("javabot", "git@github.com:evanchooly/javabot.git", listOf("src/main/kotlin/", "src/test/kotlin/")),
//                                                                    https://github.com/square/kotlinpoet/pull/456
                arrayOf("kotlinpoet", "git@github.com:square/kotlinpoet.git", listOf(/*"src/main/java/", */"src/test/java/"))
        )
    }

    @Test(dataProvider = "repos")
    fun parse(name: String, repo: String, sources: List<String>) {
        val gitDir = File("/tmp/$name.git")
        if (!gitDir.exists()) {
            Git.cloneRepository().setURI(repo).setDirectory(gitDir).call()
        }

        Kibble.parse(sources.map { File(gitDir, it) })
    }
}