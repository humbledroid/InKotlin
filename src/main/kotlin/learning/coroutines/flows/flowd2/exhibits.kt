package learning.coroutines.flows.flowd2

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import learning.coroutines.flows.flowd2.CellService.sendTextMessage
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

private const val exhibits = "🐢 🦦 🦈 🐬 🐋 🐟 🐠 🐡 🦐 🦞"

object CellService {
    private val random = Random(1)
    suspend fun sendTextMessage(message: String): MessageConfirmation {
        delay(500.milliseconds)
        println("Sending:   $message")
        if (random.nextInt(0, 3) == 0) throw ServiceInterruptionException()
        return MessageConfirmation("Delivered: $message")
    }
}

data class MessageConfirmation(val message: String)
class ServiceInterruptionException() : Exception("Message not sent.")

suspend fun main() {
    exhibits.split(' ').forEach { item ->
        flow {
            emit(sendTextMessage(item))
        }.catch { e ->
            println("Error: $e")
            throw e
        }.retry {
            it is ServiceInterruptionException
        }.collect { confirmation ->
            println(confirmation.message)
        }
    }
}