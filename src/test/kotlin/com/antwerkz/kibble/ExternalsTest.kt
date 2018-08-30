package com.antwerkz.kibble

import org.eclipse.jgit.api.Git
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import java.io.File

class ExternalsTest {
    @DataProvider(name = "repos")
    fun repos(): Array<Array<String>> {
        return arrayOf(
                arrayOf("javabot", "git@github.com:evanchooly/javabot.git", "src/main/kotlin/")
        )
    }

    @Test(dataProvider = "repos")
    fun parse(name: String, repo: String, source: String) {
        val gitDir = File("/tmp/$name.git")
        if (!gitDir.exists()) {
            val git = Git.cloneRepository()
                    .setURI(repo)
                    .setDirectory(gitDir)
                    .call()
            println("repo = [${repo}], source = [${source}]")
            println("git = ${git}")
        }

        Kibble.parse(listOf(File(gitDir, source)))
    }
}