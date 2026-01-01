package lesson16.content.state04

import kotlinx.coroutines.coroutineScope
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

suspend fun checkStructuralIntegrity() {
    coroutineScope {
        launch { activity("🔧", "support beams", 1.seconds) }
        launch { activity("🔧", "stairs", 3.seconds) }
        launch { activity("🔧", "railings", 2.seconds) }
    }
    println("🔧 Structural integrity approved. ✅")
}

suspend fun checkElectricalSafety() {
    coroutineScope {
        launch { activity("🔌", "electrical outlets", 3.seconds) }
        launch { activity("🔌", "light switches", 2.seconds) }
        launch { activity("🔌", "circuit breaker", 4.seconds) }
    }
    println("🔌 Electrical safety cleared. ✅")
}

suspend fun checkFireSafety() {
    coroutineScope {
        launch { activity("🧯", "smoke detector batteries", 5.seconds) }
        launch { activity("🧯", "fire extinguisher", 6.seconds) }
    }
    println("🧯 Fire safety check passed. ✅")
}

suspend fun activity(emoji: String, description: String, duration: Duration) {
    println("$emoji Checking $description...")
    delay(duration)
    println("$emoji The $description passed. ☑️")
}
