package lesson36.content.state01

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

val latestEpisode = flow {
    (0..100 step 10).forEach { progress ->
        emit("Episode 36 - $progress%")
        delay(500.milliseconds)
    }
}

fun main() = runBlocking {
    val larry = launch {
        latestEpisode.collect { println("🦁 Larry is watching: $it") }
    }

    val peter = launch {
        delay(2.5.seconds)
        latestEpisode.collect { println("🐼 Peter is watching: $it") }
    }

    val frank = launch {
        delay(10.seconds)
        latestEpisode.collect { println("🐸 Frank is watching: $it") }
    }
}
