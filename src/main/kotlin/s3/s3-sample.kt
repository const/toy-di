package s3

import domain.DirectoryNode
import toydi.SProvider
import toydi.SProviders
import toydi.SSystemBase
import toydi.subSystemOf
import java.io.File

class SubDirectories(path: SProvider<String>) : SSystemBase(), SProvider<List<String>> {
    override val result: List<String> = listOf(*File(path.result).listFiles()!!)
        .filter { it.isDirectory }.map { it.path }
}

class DirectoryTree3(path: SProvider<String>) : SSystemBase(), SProvider<DirectoryNode> {
    override val result: DirectoryNode;

    init {
        val childDirectories = SubDirectories(path).subSystemOf(this).result
        val createdChildren = childDirectories.map {
            DirectoryTree3(SProviders.of(it)).subSystemOf(this).result
        }
        result = resource(DirectoryNode(path.result, createdChildren))
    }
}

fun main() {
    DirectoryTree3(SProviders.of("./src/main")).use {
        it.result.print()
    }
}


