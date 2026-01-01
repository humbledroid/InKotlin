package lesson24.content.state05

import kotlinx.coroutines.*
import java.util.Collections.synchronizedList
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val stayOpenFor: Duration = 4.seconds
val stayClosedFor: Duration = 2.seconds

val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
val jobs: MutableList<Job> = synchronizedList(mutableListOf<Job>())

fun main() {
    appScope.launch {
        while (true) {
            println("  🔴 Recording")
            delay(0.5.seconds)
        }
    }

    runBlocking {
        runDay("Thursday")
        runDay("Friday")

        jobs.joinAll()
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
        scope.cancel()
        processReports()
        println("🌙 $day: Closing shop for today.")
    }

    fun processReports() = appScope.launch {
        val count = if (day == "Friday") 12 else 4
        repeat(count) {
            println("📝 $day: Processing reports...")
            delay(0.25.seconds)
        }
        println("📝 $day: Reports are done")
    }.also { jobs.add(it) }
}
