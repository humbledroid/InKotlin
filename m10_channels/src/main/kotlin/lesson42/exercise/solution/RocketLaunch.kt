package lesson42.exercise.solution

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Button & Booster Co. is growing their operations to include two additional
   launchpads so that they can run more space missions at one time.

   However, they also need to accommodate emergencies, when they suddenly need
   to change a rocket's destination at the last moment. On some occasions, the
   rocket might need to get more fuel in order to reach the new destination.

   Update your solution from the previous exercise so that it includes a total
   of three `Launcher` workers, to represent the new launchpads.

   Then add a new `MissionControl` worker between the `Fueler` and `Launcher`
   workers. `MissionControl` should redirect the rockets to a different random
   destination 25% of the time. If the rocket doesn't have enough fuel to get
   to the new destination, send it back to the `Fueler` worker. Add log
   messages for mission control like these:

   🧑‍🚀 Mission Control needs to retarget #1 from MARS to SATURN.
   🔁 More fuel needed for #1 to retarget from MARS to SATURN.

   Finally, now that there are more launch pads, update the total number of
   missions to ten.

   ************************************************************************* */

suspend fun main(): Unit = coroutineScope {
    val destinations = Channel<Destination>(BUFFERED)
    val unfueledRockets = Channel<Rocket>(BUFFERED)
    val fueledRockets = Channel<Rocket>(BUFFERED)
    val approvedRockets = Channel<Rocket>(BUFFERED)

    launch { Assembler(destinations, unfueledRockets).start() }
    launch { Fueler(unfueledRockets, fueledRockets).start() }
    launch { MissionControl(fueledRockets, unfueledRockets, approvedRockets).start() }
    repeat(3) { launch { Launcher(approvedRockets).start() } }

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
    private val toFueler: SendChannel<Rocket>,
    private val toLauncher: SendChannel<Rocket>,
) {
    private val rng = Random(123)

    suspend fun start() {
        for (rocket in inbound) {
            val destination = rocket.destination

            if (rng.nextInt(100) < 25) {
                val newDestination = Destination.entries.filter { it != destination }.random(rng)
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
