package s2

interface MyRepository
interface EntityManager
interface DbUnitTests {
    fun inject(o : TestConfig) : TestConfig
}

annotation class System
annotation class Inject

class TestConfig {
    @Inject lateinit var repository : MyRepository;
    @Inject lateinit var entityManager : EntityManager;
}
class RepositoryTest(@System val testSystem : DbUnitTests) {
    val config = testSystem.inject(TestConfig())
    val repository = config.repository
    // ....
}