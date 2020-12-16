package toydi

object SProviders {
    fun <T> of(value : T) : SProvider<T> = object : SProvider<T> {
        override val result = value
        override fun close() {
            // do nothing
        }
    }

    fun <T : AutoCloseable> ofCloseable (value : T) : SProvider<T> = object : SProvider<T> {
        override val result = value
        override fun close() {
            result.close()
        }
    }

    fun <T : AutoCloseable> getCloseable(action : () -> T) : SProvider<T> {
        return ofCloseable(action.invoke())
    }

    fun <T : AutoCloseable> get(action : () -> T) : SProvider<T> {
        return of(action.invoke())
    }

}