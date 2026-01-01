package lesson41.exercise.solution

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Update your solution from the previous exercise so that it uses channels
   instead of queues. The behavior and output should be identical.

   Your solution will likely eliminate the need for the abstract `Worker`
   class, although you'll still need the `simulateWork()` function.

   It's fine if the process doesn't terminate when you run your solution.
   We'll address that in a lesson later in this unit.

   ************************************************************************* */

suspend fun main(): Unit = coroutineScope {
    val destinations = Channel<Destination>()
    val unfueledRockets = Channel<Rocket>()
    val fueledRockets = Channel<Rocket>()

    launch { Assembler(destinations, unfueledRockets).start() }
    launch { Fueler(unfueledRockets, fueledRockets).start() }
    launch { Launcher(fueledRockets).start() }

    repeat(3) {
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
