package lesson25.content.state07

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.withContext

interface CoroutineDispatchers {
    val Default: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
}

object DefaultCoroutineDispatchers : CoroutineDispatchers {
    override val Default = Dispatchers.Default
    override val IO = Dispatchers.IO
    override val Main = Dispatchers.Main
}

object TestCoroutineDispatchers : CoroutineDispatchers {
    override val Default = StandardTestDispatcher()
    override val IO = StandardTestDispatcher()
    override val Main = StandardTestDispatcher()
}

// Repository needs to switch dispatchers
class Repository(private val dispatchers: CoroutineDispatchers = DefaultCoroutineDispatchers) {
    suspend fun save() {
        withContext(dispatchers.IO) {
            // call service...
        }
    }
}
