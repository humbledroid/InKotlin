package lesson50.content.state06

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
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
        subject.test {
            assertThat(receive()).isEqualTo(LocalDate.parse("2025-06-02"))
            assertThat(receive()).isEqualTo(LocalDate.parse("2025-06-03"))
            assertThat(receive()).isEqualTo(LocalDate.parse("2025-06-04"))
            assertThat(receive()).isEqualTo(LocalDate.parse("2025-06-05"))
            assertThat(receive()).isEqualTo(LocalDate.parse("2025-06-06"))
        }
    }
}

suspend fun <T> Flow<T>.test(block: suspend Channel<T>.() -> Unit) {
    val flow = this
    val channel = Channel<T>()
    coroutineScope {
        launch { flow.collect { channel.send(it) } }
        launch { block(channel) }
    }
}
