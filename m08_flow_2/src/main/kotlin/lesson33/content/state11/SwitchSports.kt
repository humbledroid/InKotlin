package lesson33.content.state11

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking {
    flow {
        listOf("⚽", "⚾", "🏀", "🏈").forEach { sport ->
            delay(1.5.seconds)
            println("Let's play $sport")
            emit(sport)
        }
    }.collectLatest { sport ->
        (1..10).forEach {
            delay(250.milliseconds)
            println("$sport: Your team scored $it points!")
        }
    }
}
