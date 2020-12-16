package domain

class DirectoryNode(private val path: String,
                    private val children: List<DirectoryNode>)
    : AutoCloseable {
    init {println("Created DirectoryNode: $path")}
    fun print() {
        println("Using DirectoryNode: $path")
        children.forEach { it.print() }
    }
    override fun close() {println("Closing DirectoryNode: $path")}
}