@file:OptIn(ExperimentalAtomicApi::class, ExperimentalCoroutinesApi::class)

package lesson48.exercise.solution

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class BakeryPrepTest {
    @Test
    fun `The bakery prepares for the day`() = runTest {
        val expectations = listOf(
            0 at 0.milliseconds,
            1 at 500.milliseconds,
            2 at 800.milliseconds,
            5 at 1.seconds,
            7 at 1.2.seconds,
            8 at 2.seconds,
            11 at 3.seconds,
        )

        launch { main() }

        expectations.forEach {
            advanceTimeBy(it.duration.inWholeMilliseconds - currentTime)
            runCurrent()
            assertThat(callCount.load()).isEqualTo(it.expectedCallCount)
        }
    }
}

data class Expectation(val expectedCallCount: Int, val duration: Duration)

infix fun Int.at(duration: Duration) = Expectation(this, duration)
