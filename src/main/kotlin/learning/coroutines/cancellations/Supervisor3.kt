package learning.coroutines.cancellations

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    runBlocking {
        /**
         * Never pass SuperVisorJob or Job context element
         * to a coroutine builder, as it ends up breaking the parent
         * child relationship,
         *
         * in this case the main doesn't wait at all, as runBlocking {}
         * return immediately, because, in this case all runBlocking
         * has no children, and all coroutines are tied to new SuperVisorJob
         *
         */
        launch(SupervisorJob()) {
            println("Peter's Order")

            launch { prepare("🥩") }
            launch { prepare("🥔") }
            launch { prepare("🫛") }
            launch { prepare("🍪") }
        }
    }
}

suspend fun prepare(name: String) {
    delay(500.milliseconds)
    println(name)
}