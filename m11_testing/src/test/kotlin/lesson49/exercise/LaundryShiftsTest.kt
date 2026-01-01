@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson49.exercise

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

/* ****************************************************************************

   Implement the test functions below. The corresponding main source code can
   be found here: /main/kotlin/lesson49/exercise/LaundryShifts.kt.

   The `PrintingLog` class below can be used to print log messages _and_ store
   them for assertions.

   ************************************************************************* */

class LaundryShiftsTest {
    @Nested
    inner class WorkShiftTest {
        @Test
        fun `disables the security system at the start of the shift`() = runTest {
        }

        @Test
        fun `enables the security system after the shift`() = runTest {
        }

        @Test
        fun `employee works on laundry every half second`() = runTest {
        }
    }

    @Nested
    inner class SecuritySystemTest {
        @Test
        fun `logs current status every half second`() = runTest {
        }
    }

    @Nested
    inner class DeliveryTruckTest {
        @Test
        fun `delivers garment after specified driving time`() = runTest {
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
