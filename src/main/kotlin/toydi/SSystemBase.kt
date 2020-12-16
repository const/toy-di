package toydi

open class SSystemBase : SSystem, OwnershipScope {
    private val dependent = ArrayList<AutoCloseable>()
    override fun <T : AutoCloseable> resource(resource : T) : T {
        dependent.add(resource)
        return resource
    }
    override fun close() {dependent.reversed().forEach{ it.close() }}
}
fun <T : SSystemBase> T.subSystemOf(parentSystem : SSystemBase) : T {
    return parentSystem.resource(this)
}