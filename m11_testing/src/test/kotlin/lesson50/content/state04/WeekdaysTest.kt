package lesson50.content.state04

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test

class WeekdaysTest {
    @Test
    fun `emits only weekdays`() = runTest {
        val start = LocalDate.parse("2025-06-01")
        val end = LocalDate.parse("2025-06-07")

        val subject = weekdays(start, end)
        val channel = Channel<LocalDate>(BUFFERED)

        launch { subject.collect { channel.send(it) } }

        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-02"))
        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-03"))
        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-04"))
        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-05"))
        assertThat(channel.receive()).isEqualTo(LocalDate.parse("2025-06-06"))
    }
}
