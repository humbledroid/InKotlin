package lesson47.exercise.solution

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class HaikuTest {
    @Test
    fun `haiku has three lines`() = runTest {
        haiku()
        assertThat(callCount).isEqualTo(3)
    }
}
