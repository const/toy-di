package s4

import domain.DirectoryNode
import toydi.*
import java.io.File

class MapList<T, E>(collection : SProvider<List<T>>, mapper : SFunction<T, E>)
        : SSystemBase(), SProvider<List<E>> {
    override val result : List<E> = collection.result.map {
        mapper.apply(SProviders.of(it)).ownedBy(this).result
    }
}

class TreeConfig<P, N : AutoCloseable>(path : SProvider<P>, list : SFunction<P, List<P>>, create : SFunction2<P, List<N>, N>) : SSystemBase(), SProvider<N> {
    override val result : N = create.apply(path,
        MapList(list.apply(path).ownedBy(this),
            object : SSystemBase(), SFunction<P, N> {
                override fun apply(argument: SProvider<P>) : Unowned<SProvider<N>> {
                    return Unowned(TreeConfig(argument, list, create))
                }
            }
        ).subSystemOf(this)
    ).ownedBy(this).result
}

class FsList : SSystemBase(), SFunction<String, List<String>> {
    override fun apply(argument: SProvider<String>): Unowned<SProvider<List<String>>> {
        return Unowned(SProviders.of(listOf(*File(argument.result).listFiles()!!)
                .filter { it.isDirectory }.map { it.path }))
    }
}

class DirectoryTree4(path : SProvider<String>)
        : SSystemBase(), SProvider<DirectoryNode> {
    override val result = TreeConfig(
        path,
        FsList().subSystemOf(this),
        object : SSystemBase(),
                 SFunction2<String, List<DirectoryNode>, DirectoryNode> {
            override fun apply(arg1: SProvider<String>,
                               arg2: SProvider<List<DirectoryNode>>
            ): Unowned<SProvider<DirectoryNode>> {
                return Unowned(SProviders.ofCloseable(
                    DirectoryNode(arg1.result, arg2.result)))
            }
        }
    ).subSystemOf(this).result;
}

fun main() {
    DirectoryTree4(SProviders.of("./src/main")).use {
        it.result.print()
    }
}