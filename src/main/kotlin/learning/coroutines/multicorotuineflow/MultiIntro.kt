package learning.coroutines.multicorotuineflow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * this will error out saying Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
 */
fun main() = runBlocking {
    flow {
        launch {
            emit("⚽")
            emit("⚾")
            emit("🏀")
            emit("🏈")
        }
    }.collect {
        println("Let's play $it")
    }
}
