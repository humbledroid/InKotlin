package lesson50.content.state11

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class TelevisionStationTest {
    @Test
    fun `broadcasts an episode`() = runTest {
        TelevisionStation.signal.test {
            TelevisionStation.broadcast()

            assertThat(awaitItem()).isEqualTo("Episode 37 - 0%")
            assertThat(awaitItem()).isEqualTo("Episode 37 - 10%")
            assertThat(awaitItem()).isEqualTo("Episode 37 - 20%")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
