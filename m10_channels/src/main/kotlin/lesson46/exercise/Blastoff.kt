package lesson46.exercise

import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

/* ****************************************************************************

   Button & Booster Co. is launching a mission to each planet, but needs to
   ensure that all approvals are met - the system checks need to succeed,
   there needs to be enough fuel, and the weather needs to be fair.

   From within a `channelFlow`, launch three coroutines - one to perform each
   check. Each coroutine should call its corresponding object to perform the
   check (e.g., `WeatherCheck.perform()`) and send the specific `Approval`
   indicating whether the approval is granted.

   Then, in the `main()` function, loop over the `Destination` entries, and
   request approval for each one. If all three approvals succeed, then proceed
   to blastoff. Otherwise, the mission is cancelled.

   If one approval fails, there's no need to proceed with the other approvals.

   The output will be random each time, but it should print output that looks
   something like this:

   ✨ Preparing for mission to MERCURY.
   ⛅ Weather Check 🚫
   ⛔ Mission to MERCURY was cancelled.

   ✨ Preparing for mission to VENUS.
   ⛽ Fuel Check ✅
   ⛅ Weather Check ✅
   🖥️ System Check ✅
   🚀 Mission to VENUS is ready for blastoff!

   ✨ Preparing for mission to MARS.
   ⛅ Weather Check ✅
   🖥️ System Check 🚫
   ⛔ Mission to MARS was cancelled.

   ...

   ************************************************************************* */

abstract class Check(
    private val minMs: Int,
    private val maxMs: Int,
    private val chanceOfSuccess: Int
) {
    private val rng = Random.Default

    suspend fun perform(): Boolean {
        delay(rng.nextInt(minMs, maxMs).milliseconds)
        return rng.nextInt(100) < chanceOfSuccess
    }
}

object SystemCheck : Check(minMs = 500, maxMs = 2000, chanceOfSuccess = 85)
object FuelCheck : Check(minMs = 450, maxMs = 1750, chanceOfSuccess = 80)
object WeatherCheck : Check(minMs = 550, maxMs = 1500, chanceOfSuccess = 65)

enum class Destination { MERCURY, VENUS, MARS, JUPITER, SATURN, URANUS, NEPTUNE }

sealed class Approval {
    abstract val granted: Boolean
    override fun toString() = if (granted) "✅" else "🚫"
}

class WeatherApproval(override val granted: Boolean) : Approval()
class FuelApproval(override val granted: Boolean) : Approval()
class SystemApproval(override val granted: Boolean) : Approval()
