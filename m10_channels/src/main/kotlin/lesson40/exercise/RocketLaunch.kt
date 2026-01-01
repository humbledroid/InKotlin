package lesson40.exercise

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Button & Booster Co. is an aerospace company launching rockets to different
   planets. In this exercise, you'll create a workflow that helps them prepare
   for each mission.

   Create a three-stage workflow that assembles, fuels, and launches rockets to
   randomly chosen destinations. The core models are provided below, including
   `Rocket`, `FuelUnits`, `Destination`, and an abstract `Worker` class that
   you can base your worker objects on.

   - Stage 1: An `Assembler` object that receives a random `Destination`, and
     after 1 second, creates a `Rocket` for that destination.
   - Stage 2: A `Fueler` object that receives the `Rocket` from Stage 1, and
     fuels it up. The `fuelingTime` property indicates how long it must
     take to add the fuel. Then, create a copy of the Rocket with the required
     amount of fuel and send it down to the final worker.
   - Stage 3: A `Launcher` object that receives the `Rocket` from Stage 2, and
     sends the rocket to its destination. After the destination's `travelTime`
     has passed, print out that it arrived.

   After your workflow is built out, create a `main()` function that kicks off
   the workflow three times, each with a random `Destination`.

   Be sure to add log messages during each stage, so that your output looks
   something like this:

   🏭 Assembling rocket for mission to MARS.
   🏭 Assembling rocket for mission to VENUS.
   ⛽ Fueling rocket with 100,000 units of fuel over 100ms.
   🚀 Launching rocket to MARS, traveling for 750ms.
   🏭 Assembling rocket for mission to SATURN.
   ⛽ Fueling rocket with 40,000 units of fuel over 40ms.
   ✨ Mission to MARS was successful!
   🚀 Launching rocket to VENUS, traveling for 250ms.
   ✨ Mission to VENUS was successful!
   ⛽ Fueling rocket with 1,000,000 units of fuel over 1s.
   🚀 Launching rocket to SATURN, traveling for 5s.
   ✨ Mission to SATURN was successful!

   It's fine if the process doesn't terminate when you run your solution.
   We'll address that in a lesson later in this unit.

   ************************************************************************* */

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

abstract class Worker<E : Any>(
    private val pollingFrequency: Duration = 500.milliseconds
) {
    val queue: Queue<E> = ConcurrentLinkedQueue()

    suspend fun start() {
        while (currentCoroutineContext().isActive) {
            when (val element: E? = queue.poll()) {
                null -> delay(pollingFrequency)
                else -> process(element)
            }
        }
    }

    abstract suspend fun process(element: E)

    protected fun simulateWork(duration: Duration) {
        Thread.sleep(duration.inWholeMilliseconds)
    }
}
