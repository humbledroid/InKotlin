package lesson16.content.state03

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking {
        checkStructuralIntegrity()
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

suspend fun activity(emoji: String, description: String, duration: Duration) {
    println("$emoji Checking $description...")
    delay(duration)
    println("$emoji The $description passed. ☑️")
}
