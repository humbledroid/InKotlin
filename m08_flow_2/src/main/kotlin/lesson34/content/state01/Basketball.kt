package lesson34.content.state01

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val homeTeam = team("🔵 Home", 100)
    val awayTeam = team("🔴 Away", 200)

    runBlocking {
        homeTeam.collect { goal -> println("${goal.team} scored ${goal.points} points!") }
        awayTeam.collect { goal -> println("${goal.team} scored ${goal.points} points!") }
    }
}

fun team(name: String, rngSeed: Int) = flow {
    val rng = Random(rngSeed)
    repeat(rng.nextInt(5, 10)) {
        delay(rng.nextInt(500).milliseconds)
        val randomValue = rng.nextInt(100)
        emit(
            when {
                randomValue < 10 -> ThreePointer(name)
                randomValue < 25 -> FreeThrow(name)
                else             -> TwoPointer(name)
            }
        )
    }
}

sealed class Goal(val team: String, val points: Int)
class FreeThrow(team: String) : Goal(team, 1)
class TwoPointer(team: String) : Goal(team, 2)
class ThreePointer(team: String) : Goal(team, 3)
