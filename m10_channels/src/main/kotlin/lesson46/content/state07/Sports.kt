package lesson46.content.state07

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = Channel<String>(BUFFERED)

    flow {
        launch {
            try {
                channel.send("⚽")
                channel.send("⚾")
                channel.send("🏀")
                channel.send("🏈")
            } finally {
                channel.close()
            }
        }
        channel.consumeEach { emit(it) }
    }.collect {
        println("Let's play $it")
    }
}
