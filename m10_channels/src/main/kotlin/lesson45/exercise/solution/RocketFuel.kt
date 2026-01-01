package lesson45.exercise.solution

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   In the exercise for the last lesson, you might have included the call to
   `currentCoroutineContext().cancelChildren()` to shut down the process
   gracefully after all seven rockets were launched.

   Remove that call, if you included it. Then, close the channels properly
   after all seven rockets are launched. When you're done, the process should
   still terminate successfully with exit code 0. Depending on how you wrote
   the code that generates the fuel, you might also have to cancel a coroutine
   manually.

   ************************************************************************* */

suspend fun main(): Unit = coroutineScope {
    val pipeline = Channel<FuelUnits>(RENDEZVOUS)
    val depot = fuelDepot(300_000, pipeline)
    val destinations: List<Destination> = listOf(VENUS, MERCURY, MARS, MERCURY, MERCURY, MARS, VENUS)

    val generator = launch {
        while (currentCoroutineContext().isActive) {
            delay(513.milliseconds)
            depot.send(GenerateFuel(FuelUnits(10_000)))
        }
    }

    destinations.forEachIndexed { index, destination ->
        val id = index + 1
        delay(1.seconds)
        println("🚀 Preparing mission #$id to $destination")
        depot.send(RequestFuel(destination.fuelRequired))
        val fuel = pipeline.receive()
        println("✨ Received $fuel fuel units to launch mission #$id to $destination")
    }

    generator.cancel()
    depot.close()
    pipeline.close()
}

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.fuelDepot(startingAmount: Int, pipeline: SendChannel<FuelUnits>) = actor<FuelMessage> {
    var available = FuelUnits(startingAmount)

    for (message in channel) {
        when (message) {
            is GenerateFuel -> {
                available += message.amount
                println("⛽ Fuel increased to $available")
            }

            is RequestFuel  -> {
                println("🔍 Checking reserves: ${message.amount} / $available")
                if (message.amount > available) throw OutOfFuelException()
                available -= message.amount
                println("⛽ Fuel decreased to $available")
                pipeline.send(message.amount)
            }
        }
    }
}

sealed interface FuelMessage
data class GenerateFuel(val amount: FuelUnits) : FuelMessage
data class RequestFuel(val amount: FuelUnits) : FuelMessage

class OutOfFuelException : Exception()

@JvmInline
value class FuelUnits(val value: Int) {
    override fun toString() = "%,d".format(value)
    operator fun compareTo(other: FuelUnits) = value.compareTo(other.value)
    operator fun plus(delta: FuelUnits) = FuelUnits(value + delta.value)
    operator fun minus(delta: FuelUnits) = FuelUnits(value - delta.value)
}

enum class Destination(val fuelRequired: FuelUnits) {
    MERCURY(FuelUnits(50_000)),
    VENUS(FuelUnits(40_000)),
    MARS(FuelUnits(100_000))
}
