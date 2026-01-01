package lesson44.exercise.solution

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import lesson44.exercise.solution.Destination.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Button & Booster Co. is testing out their new fuel depot. Rocket missions
   constantly need fuel from the depot, but there are only so many fuel units
   available at any given time. Thankfully, fuel units are recovered
   automatically from nearby energy generators at a rate of 10,000 units every
   513 milliseconds.

   Create an actor to represent the fuel depot, with an internal state
   representing its available fuel supply.

   Launch a coroutine to increase its fuel supply by 10,000 fuel units every
   513 milliseconds.

   In your `main()` function, every 1 second, prepare to launch a rocket to the
   next destination by requesting the required amount of fuel units from the
   depot.

   If the depot does not have enough fuel units for the rocket, then the
   simulation failed, and an `OutOfFuelException` is thrown. Otherwise, the
   fuel units are removed from the available supply, and sent to the rocket
   station.

   The rocket can only launch after receiving the fuel units from the depot.

   The rockets must be launched in this order, including a one-second delay
   between each:

   VENUS, MERCURY, MARS, MERCURY, MERCURY, MARS, VENUS

   After you've successfully launched all the rockets, you can call
   `currentCoroutineContext().cancelChildren()` to gracefully shut down.

   Once you've written your code, use it to figure out the minimum number of
   fuel units the depot must begin with in order to successfully launch all
   the rockets.

   ************************************************************************* */

suspend fun main(): Unit = coroutineScope {
    val pipeline = Channel<FuelUnits>(RENDEZVOUS)
    val depot = fuelDepot(300_000, pipeline)
    val destinations: List<Destination> = listOf(VENUS, MERCURY, MARS, MERCURY, MERCURY, MARS, VENUS)

    launch {
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

    currentCoroutineContext().cancelChildren()
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
