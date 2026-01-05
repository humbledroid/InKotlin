package learning.coroutines.patterns

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

val stayOpenFor = 4.seconds
val stayClosedFor = 2.seconds

fun main() {
    runBlocking {
        runDay("Monday")
        runDay("Tuesday")
        runDay("Wednesday")
        runDay("Thursday")
        runDay("Friday")
    }
}

suspend fun runDay(day: String) {
    val day = WorkDay(day)

    day.openForBusiness()
    day.keepTheGarageClean()
    delay(stayOpenFor)
    day.closeShop()
    delay(stayClosedFor)
}

class WorkDay(private val day: String) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun openForBusiness() {
        println("☀️ $day: We're open for business!")
    }

    fun keepTheGarageClean() = scope.launch {
        while (true) {
            delay(1.seconds)
            println("  🧹 $day: Hank is keeping the garage clean!")
        }
    }

    fun closeShop() {
        /**
         * make sure to cancel the scope, as soon as the scope is no longer supposed
         * otherwise it will keep on running the
         * while(true) block
         */
        processReports()
        scope.cancel()
        println("🌙 $day: Closing shop for today.")
    }

    /**
     * Use app wide scopes when something is supposed to run longer
     * than the span provided by local scope
     */
    fun processReports() = appScope.launch {
        repeat(4) {
            println("📝 $day: Processing reports...")
            delay(0.25.seconds)
        }
        println("📝 $day: Reports are done")
    }
}
