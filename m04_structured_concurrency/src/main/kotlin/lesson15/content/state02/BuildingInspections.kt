package lesson15.content.state02

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking<Unit> {
    val scope = this

    scope.launch { delay(2.seconds); println("🔧 Structural integrity approved.") }
    scope.launch { delay(2.seconds); println("🔌 Electrical safety cleared.") }
    scope.launch { delay(2.seconds); println("🧯 Fire safety check passed.") }
}
