package lesson10.content.state04

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main() {
    val difficulty = DifficultyLevel.NORMAL
    var score = 0L

    runBlocking {
        val deferredValues = mutableListOf<Deferred<Long>>()

        for (action in playerActions()) {
            deferredValues.add(async(Dispatchers.Default) { action.pointsFor(difficulty) })
        }

        for (deferred in deferredValues) {
            score += deferred.await()
        }
    }

    println(String.format("%,d", score))
}

fun playerActions() = sequence {
    val randomizer = Random(seed = 0)
    repeat(27_262) { yield(Action.entries.random(randomizer)) }
}

fun Action.pointsFor(difficulty: DifficultyLevel) =
    (points * difficulty.multiplier).toLong()

enum class Action(val points: Long) {
    JUMP_OVER_ONE_ENEMY(10),
    JUMP_OVER_TWO_ENEMIES(30),
    JUMP_OVER_THREE_ENEMIES(50),
    DEFEAT_ENEMY(30),
    PICK_UP_ITEM(30),
    FINISH_LEVEL(70)
}

enum class DifficultyLevel(val multiplier: Double) {
    EASY(0.5), NORMAL(1.0), CHALLENGING(2.0)
}
