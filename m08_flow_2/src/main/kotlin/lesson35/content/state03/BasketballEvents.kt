package lesson35.content.state03

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() {
    val andrew = Player("🧑🏻‍🦰", flow {
        println("Loading events for 🧑🏻‍🦰...")
        delay(500.milliseconds); emit(TWO_POINTER)
        delay(750.milliseconds); emit(REBOUND)
        delay(500.milliseconds); emit(ASSIST)
        println("All events loaded for 🧑🏻‍🦰")
    })
    val brian = Player("🧔🏽‍♂️", flow {
        println("Loading events for 🧔🏽‍♂️...")
        delay(250.milliseconds); emit(STEAL)
        delay(750.milliseconds); emit(STEAL)
        delay(750.milliseconds); emit(FOUL)
        println("All events loaded for 🧔🏽‍♂️")
    })
    val cornelius = Player("👨🏾‍🦲", flow {
        println("Loading events for 👨🏾‍🦲...")
        delay(750.milliseconds); emit(THREE_POINTER)
        delay(125.milliseconds); emit(FREE_THROW)
        delay(500.milliseconds); emit(TWO_POINTER)
        println("All events loaded for 👨🏾‍🦲")
    })

    flow {
        emit(andrew)
        delay(1.seconds)
        emit(brian)
        delay(1.seconds)
        emit(cornelius)
    }
        .flatMapConcat { it.events }
        .collect { println("Event: $it") }
}

class Player(val label: String, val events: Flow<BasketballEvent>)

enum class BasketballEvent {
    FREE_THROW, TWO_POINTER, THREE_POINTER, REBOUND, ASSIST, STEAL, FOUL
}
