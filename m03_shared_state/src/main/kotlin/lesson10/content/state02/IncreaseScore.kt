package lesson10.content.state02

fun main() {
    var score = 0L

    fun increaseScore(action: Action, difficulty: DifficultyLevel) {
        var points: Number = (action.points * difficulty.multiplier)
        points = points.toLong()
        score += points
    }

    increaseScore(Action.FINISH_LEVEL, DifficultyLevel.CHALLENGING)
}

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
