package lesson34.content.state04

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val rng = Random(1)

    val team = listOf("👩🏻", "👨🏾‍🦲", "🧑🏼‍🦰", "👨🏼‍🦱", "👩🏾‍🦱", "👨🏽‍🦳", "👩🏼", "🧑🏿", "👩🏽‍🦰", "👨🏻", "🧔🏻‍♂️")
    val players = flow {
        repeat(10) {
            delay(rng.nextInt(500).milliseconds)
            emit(team.random(rng))
        }
    }
    val shots = flow {
        repeat(10) {
            delay(rng.nextInt(500).milliseconds)
            emit(if (rng.nextInt(100) < 25) Miss() else Goal())
        }
    }

    runBlocking {
        players.zip(shots) { player, shot -> player to shot }
            .collect { (player, shot) ->
                val result = if (shot is Goal) "it's good!" else "and misses."
                println("🎙️ Player $player takes a shot... $result")
            }
    }
}

sealed interface Shot
class Goal() : Shot
class Miss() : Shot
