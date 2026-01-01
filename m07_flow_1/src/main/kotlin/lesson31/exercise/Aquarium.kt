package lesson31.exercise

import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

/* ****************************************************************************

   Fiona is spending the day at the aquarium, fascinated by the marine life!
   Her best friend wanted to join her, but since she couldn't make it, she
   asked Fiona to text her a message of each kind of animal she sees.

   Unfortunately, the cell phone signal in the aquarium is a bit sketchy, so
   her text messages don't always go through.

   In this exercise, you'll help ensure that all of Fiona's text messages are
   delivered to her friend.

   - Loop over the space-separated `exhibits` with a regular for-loop or a
     collection-based `forEach {}` operator.
   - For each exhibit, _use a flow_ to send a message of the animal as many
     times as needed until it goes through.
   - Whenever `sendTextMessage()` throws an exception, print the `message` of
     the exception.
   - Whenever `sendTextMessage()` succeeds, print the `message` of the
     resulting `MessageConfirmation` object.

   ************************************************************************* */

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

fun main() {
    TODO("Implement this function")
}
