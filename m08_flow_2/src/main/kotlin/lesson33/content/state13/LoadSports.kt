package lesson33.content.state13

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.runBlocking
import java.io.FileNotFoundException
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    flow {
        listOf("⚽", "⚾", "🏀", "🏈").forEach { label ->
            delay(500.milliseconds)
            println("Let's play $label")
            emit(label)
        }
    }
        .transformLatest { label ->
            emit(SportsFile.loadSport(label))
        }
        .collect { sport ->
            println("Now playing ${sport.name} ${sport.label}")
            (1..10).forEach {
                delay(250.milliseconds)
                println("${sport.label}: Your team scored a ${sport.scoreLabel}! Now at ${sport.score * it}.")
            }
        }
}

data class Sport(val label: String, val name: String, val score: Int, val scoreLabel: String)

object SportsFile {
    suspend fun loadSport(label: String): Sport {
        println("Loading details for $label...")
        delay(1.seconds) // simulate loading time
        println("...details loaded for $label!")
        return when (label) {
            "⚽"  -> Sport(label, "Soccer", 1, "goal")
            "⚾"  -> Sport(label, "Baseball", 1, "run")
            "🏀"  -> Sport(label, "Basketball", 2, "field goal")
            "🏈"  -> Sport(label, "Football", 6, "touchdown")
            else -> throw FileNotFoundException()
        }
    }
}
