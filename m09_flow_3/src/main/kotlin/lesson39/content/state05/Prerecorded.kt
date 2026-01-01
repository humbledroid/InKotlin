package lesson39.content.state05

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking {
    val latestEpisode = flow {
        (0..100 step 10).forEach { progress ->
            emit("Episode 39 - $progress%")
            delay(500.milliseconds)
        }
    }.shareIn(this, SharingStarted.Lazily)

    delay(1.seconds)

    val larry = launch {
        latestEpisode.collect { println("🦁 Larry is watching: $it") }
    }

    val peter = launch {
        delay(2.5.seconds)
        latestEpisode.collect { println("🐼 Peter is watching: $it") }
    }

    val frank = launch {
        delay(4.seconds)
        latestEpisode.collect { println("🐸 Frank is watching: $it") }
    }
}
