package lesson47.content.state01

import kotlinx.coroutines.delay
import kotlin.time.Duration

suspend fun activity(emoji: String, description: String, duration: Duration) {
    println("$emoji Checking $description...")
    delay(duration)
    println("$emoji The $description passed. ☑️")
}
