@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson49.exercise.solution

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import lesson49.exercise.solution.SecuritySystem.Status.ACTIVE
import lesson49.exercise.solution.SecuritySystem.Status.INACTIVE
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Implement the test functions below. The corresponding main source code can
   be found here: /main/kotlin/lesson49/exercise/solution/LaundryShifts.kt.

   ************************************************************************* */

class LaundryShiftsTest {
    @Nested
    inner class WorkShiftTest {
        private lateinit var log: PrintingLog
        private lateinit var securitySystem: SecuritySystem
        private lateinit var shift: WorkShift

        fun TestScope.initialize() {
            log = PrintingLog()
            securitySystem = SecuritySystem(backgroundScope, ::println)
            shift = WorkShift("Rick", securitySystem, log, StandardTestDispatcher(testScheduler))
        }

        @Test
        fun `disables the security system at the start of the shift`() = runTest {
            initialize()
            securitySystem.status = ACTIVE
            shift.onStart()
            assertThat(securitySystem.status).isEqualTo(INACTIVE)
        }

        @Test
        fun `enables the security system after the shift`() = runTest {
            initialize()
            securitySystem.status = INACTIVE
            shift.onStop()
            assertThat(securitySystem.status).isEqualTo(ACTIVE)
        }

        @Test
        fun `employee works on laundry every half second`() = runTest {
            initialize()
            try {
                shift.work()

                for (i in 1..5) {
                    advanceTimeBy(0.5.seconds)
                    assertThat(log.size).isEqualTo(i)
                    runCurrent()
                }
            } finally {
                shift.onStop()
            }
        }
    }

    @Nested
    inner class SecuritySystemTest {
        @Test
        fun `logs current status every half second`() = runTest {
            val log = PrintingLog()
            val securitySystem = SecuritySystem(backgroundScope, log)

            securitySystem.monitor()

            securitySystem.status = ACTIVE
            advanceTimeBy(0.5.seconds)
            runCurrent()
            assertThat(log.messages)
                .containsExactly("🔒 Security system is ACTIVE")

            securitySystem.status = INACTIVE
            advanceTimeBy(0.5.seconds)
            runCurrent()
            assertThat(log.messages)
                .containsExactly("🔒 Security system is ACTIVE", "🔓 Security system is INACTIVE")
        }
    }

    @Nested
    inner class DeliveryTruckTest {
        @Test
        fun `delivers garment after specified driving time`() = runTest {
            val log = PrintingLog()
            val shipment = Shipment(5.0.seconds, "🧦")
            val truck = DeliveryTruck(backgroundScope, shipment, log)

            truck.start()

            assertThat(log.messages).isEmpty()

            advanceTimeBy(shipment.drivingTime)
            runCurrent()
            assertThat(log.messages)
                .containsExactly("🚚 Delivered ${shipment.garment} ✅")
        }
    }
}

class PrintingLog : (String) -> Unit {
    private val _log: MutableList<String> = Collections.synchronizedList(mutableListOf())

    val messages: List<String> get() = _log
    val size get() = messages.size

    override operator fun invoke(message: String) {
        println(message)
        _log.add(message)
    }
}
