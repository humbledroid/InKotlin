package lesson19.content.state05

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

val manager = MessageManager()
val customerIds = listOf(100, 200, 300)

class MessageManager {
    private var initialized = false

    fun initialize() {
        initialized = true
    }

    fun sendMessage(message: String) {
        check(initialized) { "MessageManager must be initialized before sending messages." }
        println("📨 $message")
    }
}

suspend fun MessageManager.messageCustomer(customerId: Int, message: String) {
    try {
        println("Preparing message for $customerId")
        delay(1.seconds)
        sendMessage(message)
        println("Message sent to $customerId")
    } catch (_: IllegalStateException) {
        println("MessageManager might not be initialized. Initializing now.")
        initialize()
        sendMessage(message)
        println("Message sent to $customerId after initializing")
    }
}

fun main() = runBlocking {
    val runBlockingScope = this

    launch(Dispatchers.Default) {
        delay(500.milliseconds)
        runBlockingScope.cancel()
    }

    manager.initialize()
    customerIds.forEach { id -> manager.messageCustomer(id, "Your order is ready") }
}
