package toydi

interface OwnershipScope {
    /**
     * Register resource as an owned resource within scope
     *
     * @param resource a resource to be registered
     * @return a resource passed as parameter
     */
    fun <T : AutoCloseable> resource(resource : T) : T
}