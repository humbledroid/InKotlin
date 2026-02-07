package learning.coroutines.puttingflows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val homeTeam = team("<Blue> Home", 100)
    val awayTeam = team("<Red> Away", 200)

    runBlocking {
//        merge(homeTeam, awayTeam)
//            .collect { goal ->
//                println("${goal.} scored ${goal.points} points!")
//            }

        combine(homeTeam, awayTeam) { homeScore, awayScore ->
            Score(homeScore, awayScore)
        }.collect {
            println("The score is now ${it.team1} to ${it.team2}")
        }
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
                else -> TwoPointer(name)
            }
        )
    }
}.scan(0) { total, goal -> total+goal.points }

data class Score(val team1: Int=0, val team2: Int=0)

sealed class Goal(val team: String, val points: Int)
class FreeThrow(team: String) : Goal(team, 1)
class TwoPointer(team: String) : Goal(team, 2)
class ThreePointer(team: String) : Goal(team, 3)