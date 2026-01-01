package lesson24.exercise.solution

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


/* ****************************************************************************

   Rick, Harry, and Matt are working shifts at George's Laundry Service. There
   are three issues that they need you need to fix:

    1) Each employee should only work during his own shift. Make the in-shift
       work stop when the shift ends (no work logs after closing).

    2) The first two shipments are delivered, but the third never arrives.
       Launch delivery work on a scope that outlives the shift and ensure the
       program waits for all deliveries before exiting.

    3) The security system correctly shows active/inactive, but it runs on
       GlobalScope. Move it to an appropriate app-wide scope instead.

   ************************************************************************* */

val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
val securitySystem = SecuritySystem(appScope)
val jobs: MutableList<Job> = mutableListOf()

fun main() {
    securitySystem.monitor()
    runBlocking {
        startShift("Rick", Shipment(0.5.seconds, "👚"))
        startShift("Harry", Shipment(1.5.seconds, "👖"))
        startShift("Matt", Shipment(5.0.seconds, "🧦"))
        jobs.joinAll()
    }
}

suspend fun startShift(employeeName: String, shipment: Shipment) {
    val shift = WorkShift(employeeName)

    shift.onStart()
    shift.work()
    delay(shift.shiftLength)
    shift.onStop()

    jobs.add(DeliveryTruck(appScope, shipment).start())
    delay(shift.timeToNextShift)
}

class WorkShift(
    val employeeName: String,
    val shiftLength: Duration = 2.seconds,
    val timeToNextShift: Duration = 2.seconds,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun onStart() {
        securitySystem.status = SecuritySystem.Status.INACTIVE
        println("☀️ $employeeName is starting work for the day.")
    }

    fun work() = scope.launch {
        while (true) {
            println("    🫧 $employeeName is working on the laundry.")
            delay(0.5.seconds)
        }
    }

    fun onStop() {
        scope.cancel()
        println("🌙️ $employeeName is done working for the day.")
        securitySystem.status = SecuritySystem.Status.ACTIVE
    }
}

class SecuritySystem(private val scope: CoroutineScope) {
    var status: Status = Status.ACTIVE

    fun monitor() {
        scope.launch {
            while (true) {
                delay(0.5.seconds)
                println("${" ".repeat(40)}${status.label} Security system is $status")
            }
        }
    }

    enum class Status(val label: String) {
        ACTIVE("🔒"), INACTIVE("🔓")
    }
}

class DeliveryTruck(
    private val scope: CoroutineScope,
    private val shipment: Shipment
) {
    fun start() = scope.launch {
        delay(shipment.drivingTime)
        println("    🚚 Delivered ${shipment.garment} ✅")
    }
}

data class Shipment(val drivingTime: Duration, val garment: String)
