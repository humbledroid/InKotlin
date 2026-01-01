package lesson35.content.state01

fun main() {
    val andrew = Player("🧑🏻‍🦰", listOf(TWO_POINTER, REBOUND, ASSIST))
    val brian = Player("🧔🏽‍♂️", listOf(STEAL, STEAL, FOUL))
    val cornelius = Player("👨🏾‍🦲", listOf(THREE_POINTER, FREE_THROW, TWO_POINTER))

    listOf(andrew, brian, cornelius)
        .flatMap { it.events }
        .forEach { println("Event: $it") }
}

class Player(val label: String, val events: List<BasketballEvent>)

enum class BasketballEvent {
    FREE_THROW, TWO_POINTER, THREE_POINTER, REBOUND, ASSIST, STEAL, FOUL
}
