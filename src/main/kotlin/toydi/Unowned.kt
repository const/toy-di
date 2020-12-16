package toydi

class Unowned<T : AutoCloseable>(value : T) {
    var state : T? = value;
    fun ownedBy(scope : OwnershipScope) : T {
        return scope.resource(getOnce());
    }
    private fun getOnce(): T {
        val r = state ?:
            throw IllegalStateException("Object is already owned");
        state = null;
        return r
    }
    fun <R> useOnce(action : (T) -> R) : R {
        return getOnce().use(action)
    }
}