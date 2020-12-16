package toydi

interface SFunction<A, R> : SSystem {
    fun apply(argument : SProvider<A>) : Unowned<SProvider<R>>
}