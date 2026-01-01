@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson43.content.state08

import kotlinx.coroutines.*
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

suspend fun main(): Unit = coroutineScope {
    println("Who's going to finish first?")

    val job = launch {
        val duration = Random.nextDouble(5.0).seconds
        println("launch is working for $duration")
        delay(duration)
    }

    val deferred = async {
        val duration = Random.nextDouble(5.0).seconds
        println("async is working for $duration")
        delay(duration)
        duration
    }

    select {
        job.onJoin { println("launch finished first") }
        deferred.onAwait { println("async finished first at $it") }
        onTimeout(Random.nextLong(5000)) { println("I got tired of waiting...") }
    }
}
