package lesson50.content.state07

import app.cash.turbine.awaitComplete
import app.cash.turbine.awaitItem
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import lesson50.content.state06.test
import org.junit.jupiter.api.Test

class WeekdaysTest {
    @Test
    fun `emits only weekdays`() = runTest {
        val start = LocalDate.parse("2025-06-01")
        val end = LocalDate.parse("2025-06-07")

        val subject = weekdays(start, end)
        subject.test {
            assertThat(awaitItem()).isEqualTo(LocalDate.parse("2025-06-02"))
            assertThat(awaitItem()).isEqualTo(LocalDate.parse("2025-06-03"))
            assertThat(awaitItem()).isEqualTo(LocalDate.parse("2025-06-04"))
            assertThat(awaitItem()).isEqualTo(LocalDate.parse("2025-06-05"))
            assertThat(awaitItem()).isEqualTo(LocalDate.parse("2025-06-06"))
            awaitComplete()
        }
    }
}
