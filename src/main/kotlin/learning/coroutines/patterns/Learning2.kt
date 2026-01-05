package learning.coroutines.patterns

import kotlinx.coroutines.*
import java.util.Collections.synchronizedList
import kotlin.time.Duration.Companion.seconds

val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

val stayOpenFor = 4.seconds
val stayClosedFor = 2.seconds

val job: MutableList<Job> = synchronizedList(mutableListOf<Job>())

fun main() {
    runBlocking {
        runDay("Thursday")
        runDay("Friday")

        job.joinAll()
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
    private fun processReports() = appScope.launch {
        val count = if(day == "Friday") 12 else 4
        repeat(count) {
            println("📝 $day: Processing reports...")
            delay(0.25.seconds)
        }
        println("📝 $day: Reports are done")
    }.also {
        job.add(it)
    }
    /**
     * So here the appScope might work for very short work, but something
     * that is long-running the app may close well before the coroutine launched
     * in the scope, completed the work.
     *
     * so we make a synchronizedList of jobs and push the job in that and in
     * main we wait for all those job to complete via .joinAll()
     */
}
