package lesson41.content.state01

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val channel = Channel<Int>()

object Baker {
    suspend fun start() {
        val count = channel.receive()
        println("🥣 Baking $count cupcakes")
    }
}

fun main() = runBlocking {
    launch { Baker.start() }
    channel.send(12)
}
