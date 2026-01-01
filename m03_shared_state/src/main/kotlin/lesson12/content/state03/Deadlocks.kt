package lesson12.content.state03

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val mutex1 = Mutex()
    val mutex2 = Mutex()

    runBlocking {
        launch(Dispatchers.IO) {
            mutex1.withLock {
                delay(500.milliseconds)
                mutex2.withLock {
                    delay(500.milliseconds)
                    println("1 -> 2")
                }
            }
        }
        launch(Dispatchers.IO) {
            mutex2.withLock {
                delay(250.milliseconds)
                mutex1.withLock {
                    delay(500.milliseconds)
                    println("2 -> 1")
                }
            }
        }
    }
}
