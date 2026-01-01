@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson43.exercise.solution

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.selects.select
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Button & Booster Co. is responding to emergency calls! Instead of a 25%
   chance of needing to redirect a rocket to a random destination, there's now
   a signal that the planets can send when there's an emergency.

   Update your solution for the previous exercise with the changes below.

   Create a new channel for emergencies at particular `Destination`s.

   Then, in your `main()` function, launch a coroutine that repeats five times.
   On each iteration, delay for 2 seconds, then pick a random destination where
   an emergency requires a rocket mission. Send that destination through the
   new emergency channel.

   Finally, update `MissionControl` to use a select expression, so that it can
   listen to both its current inbound channel and the new emergency channel.

   - When a new emergency arrives, store it as the active emergency.
   - When a rocket arrives, if there's an active emergency, redirect that
     rocket to go to that new destination and immediately clear the active
     emergency. Otherwise, just send it to its original destination.
   - If a new emergency arrives while there's already an existing emergency,
     the new emergency replaces the previous emergency.
   - As before, make sure there's enough fuel for a redirected rocket to get
     to the new destination.

   ************************************************************************* */

suspend fun main(): Unit = coroutineScope {
    val destinations = Channel<Destination>(BUFFERED)
    val unfueledRockets = Channel<Rocket>(BUFFERED)
    val fueledRockets = Channel<Rocket>(BUFFERED)
    val approvedRockets = Channel<Rocket>(BUFFERED)
    val emergencies = Channel<Destination>(BUFFERED)

    launch { Assembler(destinations, unfueledRockets).start() }
    launch { Fueler(unfueledRockets, fueledRockets).start() }
    launch { MissionControl(fueledRockets, emergencies, unfueledRockets, approvedRockets).start() }
    repeat(3) { launch { Launcher(approvedRockets).start() } }

    launch {
        repeat(5) {
            delay(2.seconds)
            val destination = Destination.random()
            println("⚠️ Emergency on $destination")
            emergencies.send(destination)
        }
    }

    repeat(10) {
        destinations.send(Destination.random())
    }
}

class Assembler(
    private val inbound: ReceiveChannel<Destination>,
    private val outbound: SendChannel<Rocket>
) {
    private var id = 0

    suspend fun start() {
        for (element in inbound) {
            val newId = ++id
            println("🏭 Assembling rocket #${newId} for mission to $element.")
            simulateWork(1.seconds)
            outbound.send(Rocket(newId, element))
        }
    }
}

class Fueler(
    private val inbound: ReceiveChannel<Rocket>,
    private val outbound: SendChannel<Rocket>
) {
    suspend fun start() {
        for (rocket in inbound) {
            val required = rocket.destination.fuelRequired
            println("⛽ Fueling rocket #${rocket.id} with $required units of fuel over ${required.fuelingTime}.")
            simulateWork(required.fuelingTime)
            outbound.send(rocket.copy(fuel = required))
        }
    }
}

class MissionControl(
    private val inbound: ReceiveChannel<Rocket>,
    private val emergency: ReceiveChannel<Destination>,
    private val toFueler: SendChannel<Rocket>,
    private val toLauncher: SendChannel<Rocket>,
) {
    var latestEmergency: Destination? = null

    suspend fun start() {
        while (currentCoroutineContext().isActive) {
            select {
                inbound.onReceive { checkRocket(it) }
                emergency.onReceive { latestEmergency = it }
            }
        }
    }

    private suspend fun checkRocket(rocket: Rocket) {
        val destination = rocket.destination
        val newDestination = latestEmergency
        latestEmergency = null

        if (newDestination != null && newDestination != destination) {
            val updated = rocket.copy(destination = newDestination)
            println("🧑‍🚀 Mission Control needs to retarget #${rocket.id} from $destination to $newDestination.")
            if (rocket.fuel < newDestination.fuelRequired) {
                println("🔁 More fuel needed for #${rocket.id} to retarget from $destination to $newDestination.")
                toFueler.send(updated)
            } else {
                toLauncher.send(updated)
            }
        } else {
            toLauncher.send(rocket)
        }
    }
}

class Launcher(
    private val inbound: ReceiveChannel<Rocket>
) {
    suspend fun start() {
        for (rocket in inbound) {
            val destination = rocket.destination
            println("🚀 Launching rocket #${rocket.id} to $destination, traveling for ${destination.travelTime}.")
            simulateWork(destination.travelTime)
            println("✨ Mission to $destination was successful for #${rocket.id}!")
        }
    }
}

data class Rocket(
    val id: Int,
    val destination: Destination,
    val fuel: FuelUnits = FuelUnits(0)
)

@JvmInline
value class FuelUnits(val value: Int) {
    val fuelingTime: Duration get() = (value / 1000).milliseconds
    override fun toString() = "%,d".format(value)
    operator fun compareTo(other: FuelUnits) = value.compareTo(other.value)
}

enum class Destination(val fuelRequired: FuelUnits, val travelTime: Duration) {
    MERCURY(FuelUnits(50_000), 500.milliseconds),
    VENUS(FuelUnits(40_000), 250.milliseconds),
    MARS(FuelUnits(100_000), 750.milliseconds),
    JUPITER(FuelUnits(500_000), 2.5.seconds),
    SATURN(FuelUnits(1_000_000), 5.seconds);

    companion object {
        private val rng: Random = Random(492)
        fun random() = entries.random(rng)
    }
}

fun simulateWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}
