@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson49.content.state06

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.time.Duration.Companion.seconds

class WorkDayTest {
    @Test
    fun `Hank keeps the garage clean`() = runTest {
        val log = PrintingLog()
        val dayOfWeek = "Monday"
        val subject = WorkDay(dayOfWeek, log, backgroundScope)

        launch { subject.keepTheGarageClean() }

        advanceTimeBy(7.seconds)
        runCurrent()
        assertThat(log.size).isEqualTo(7)
    }
}

class PrintingLog : (String) -> Unit {
    private val _log: MutableList<String> =
        Collections.synchronizedList(mutableListOf())

    val messages: List<String> get() = _log

    val size get() = messages.size

    override operator fun invoke(message: String) {
        println(message)
        _log.add(message)
    }
}
