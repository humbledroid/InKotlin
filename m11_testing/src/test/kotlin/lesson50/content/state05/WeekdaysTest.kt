package lesson50.content.state05

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test

class WeekdaysTest {
    @Test
    fun `emits only weekdays`() = runTest {
        val start = LocalDate.parse("2025-06-01")
        val end = LocalDate.parse("2025-06-07")

        val subject = weekdays(start, end)
        val channel = subject.produceIn(this)

        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-02"))
        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-03"))
        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-04"))
        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-05"))
        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-06"))
    }
}
