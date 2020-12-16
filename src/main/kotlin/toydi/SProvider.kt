package toydi

interface SProvider<T> : SSystem {
    val result : T
}