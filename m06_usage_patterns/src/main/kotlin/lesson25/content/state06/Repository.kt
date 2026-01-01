package lesson25.content.state06

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    suspend fun save() {
        withContext(ioDispatcher) {
            // call service...
        }
    }
}
