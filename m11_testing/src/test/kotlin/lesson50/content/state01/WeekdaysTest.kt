package lesson50.content.state01

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test

class WeekdaysTest {
    @Test
    fun `emits only weekdays`() = runTest {
        val start = LocalDate.parse("2025-06-01")
        val end = LocalDate.parse("2025-06-07")

        val subject = weekdays(start, end)

        subject.collect { item ->
            assertThat(item).isEqualTo(LocalDate.parse("2025-06-02"))
        }
    }
}
