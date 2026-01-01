package lesson50.content.state02

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.fail
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test

class WeekdaysTest {
    @Test
    fun `emits only weekdays`() = runTest {
        val start = LocalDate.parse("2025-06-01")
        val end = LocalDate.parse("2025-06-07")

        val subject = weekdays(start, end)

        subject.collectIndexed { index, item ->
            when (index) {
                0 -> assertThat(item).isEqualTo(LocalDate.parse("2025-06-02"))
                1 -> assertThat(item).isEqualTo(LocalDate.parse("2025-06-03"))
                2 -> assertThat(item).isEqualTo(LocalDate.parse("2025-06-04"))
                3 -> assertThat(item).isEqualTo(LocalDate.parse("2025-06-05"))
                4 -> assertThat(item).isEqualTo(LocalDate.parse("2025-06-06"))
                5 -> fail("The flow in this test should only emit 5 items.")
            }
        }
    }
}
