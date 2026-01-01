package lesson49.exercise

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Below are some classes adapted from the exercise for Lesson 24.

   Write the tests for the `WorkShift`, `SecuritySystem`, and `DeliveryTruck`
   classes. The test file already has test functions stubbed out for you in
   /test/kotlin/lesson49/exercise/LaundryShiftsTest.kt. Just implement the test
   functions that are there.

   Feel free to modify the code here in the main source file, too, as long as
   it doesn't affect the core functionality.

   ************************************************************************* */

class WorkShift(
    private val employeeName: String,
    private val securitySystem: SecuritySystem,
    private val log: (String) -> Unit
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun onStart() {
        securitySystem.status = SecuritySystem.Status.INACTIVE
        log("☀️ $employeeName is starting work for the day.")
    }

    fun work() = scope.launch {
        while (true) {
            log("🫧 $employeeName is working on the laundry.")
            delay(0.5.seconds)
        }
    }

    fun onStop() {
        scope.cancel()
        log("🌙️ $employeeName is done working for the day.")
        securitySystem.status = SecuritySystem.Status.ACTIVE
    }
}

class SecuritySystem(
    private val scope: CoroutineScope,
    private val log: (String) -> Unit
) {
    var status: Status = Status.ACTIVE

    fun monitor() {
        scope.launch {
            while (true) {
                delay(0.5.seconds)
                log("${status.label} Security system is $status")
            }
        }
    }

    enum class Status(val label: String) {
        ACTIVE("🔒"), INACTIVE("🔓")
    }
}

class DeliveryTruck(
    private val scope: CoroutineScope,
    private val shipment: Shipment,
    private val log: (String) -> Unit
) {
    fun start() = scope.launch {
        delay(shipment.drivingTime)
        log("🚚 Delivered ${shipment.garment} ✅")
    }
}

data class Shipment(val drivingTime: Duration, val garment: String)
