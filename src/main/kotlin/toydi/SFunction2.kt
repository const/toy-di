package toydi

interface SFunction2<A, B, R> : SSystem {
    fun apply(arg1 : SProvider<A>, arg2 : SProvider<B>) : Unowned<SProvider<R>>
}