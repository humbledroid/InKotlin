package learning.coroutines.statemgmt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.plusAssign

@OptIn(ExperimentalAtomicApi::class)
fun main() {
    val difficulty = DifficultyLevel.NORMAL
    val score = AtomicLong(0L)

    runBlocking {
        for (action in playerActions()) {
            launch(Dispatchers.Default) {
                recordAction(action)
                val points = action.pointsFor(difficulty)
                score += points
                animatePointsOnScreen(points)
            }
        }
    }

    println(String.format("%,d", score.load()))
}