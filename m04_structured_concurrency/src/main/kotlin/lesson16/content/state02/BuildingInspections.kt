package lesson16.content.state02

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking {
        checkStructuralIntegrity()
        checkElectricalSafety()
        checkFireSafety()
    }
    println("🏠 Safety inspection is complete.")
}

fun CoroutineScope.checkStructuralIntegrity() {
    launch {
        launch { activity("🔧", "support beams", 1.seconds) }
        launch { activity("🔧", "stairs", 3.seconds) }
        launch { activity("🔧", "railings", 2.seconds) }
    }.invokeOnCompletion { println("🔧 Structural integrity approved. ✅") }
}

fun CoroutineScope.checkElectricalSafety() {
    launch {
        launch { activity("🔌", "electrical outlets", 3.seconds) }
        launch { activity("🔌", "light switches", 2.seconds) }
        launch { activity("🔌", "circuit breaker", 4.seconds) }
    }.invokeOnCompletion { println("🔌 Electrical safety cleared. ✅") }
}

fun CoroutineScope.checkFireSafety() {
    launch {
        launch { activity("🧯", "smoke detector batteries", 5.seconds) }
        launch { activity("🧯", "fire extinguisher", 6.seconds) }
    }.invokeOnCompletion { println("🧯 Fire safety check passed. ✅") }
}

suspend fun activity(emoji: String, description: String, duration: Duration) {
    println("$emoji Checking $description...")
    delay(duration)
    println("$emoji The $description passed. ☑️")
}
