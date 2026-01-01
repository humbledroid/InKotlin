package lesson24.content.state01

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val stayOpenFor: Duration = 4.seconds
val stayClosedFor: Duration = 2.seconds

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
        println("🌙 $day: Closing shop for today.")
    }
}
