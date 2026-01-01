package lesson50.content.state10

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class TelevisionStationTest {
    @Test
    fun `broadcasts an episode`() = runTest {
        TelevisionStation.broadcast()

        TelevisionStation.signal.test {
            assertThat(awaitItem()).isEqualTo("Episode 37 - 0%")
            assertThat(awaitItem()).isEqualTo("Episode 37 - 10%")
            assertThat(awaitItem()).isEqualTo("Episode 37 - 20%")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
