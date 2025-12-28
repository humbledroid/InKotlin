package learning.coroutines.statemgmt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun main() {
    val mutex = Mutex()
    val difficulty = DifficultyLevel.NORMAL
    var score = 0L

    runBlocking {
        for (action in playerActions()) {
            launch(Dispatchers.Default) {
                recordAction(action)
                val points = action.pointsFor(difficulty)
                mutex.withLock { score += points }
                animatePointsOnScreen(points)
            }
        }
    }

    println(String.format("%,d", score))
}

fun recordAction(action: Action) {
    // Simulate some work
}

fun animatePointsOnScreen(points: Long) {
    // Simulate some work
}
