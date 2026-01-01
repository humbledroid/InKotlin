@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson50.exercise.solution

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Implement each of the test functions below.

   The corresponding main source file for this test can be found here:
   /main/lesson50/exercise/solution/RadioStation.kt.

   ************************************************************************* */

class RadioStationTest {
    private val songs: List<Song> = listOf(
        Song("Maple Leaf Rag", 1.5.seconds),
        Song("The Entertainer", 2.seconds),
        Song("The Easy Winners", 1.5.seconds),
    )

    private fun TestScope.createRadioStation() =
        RadioStation(songs, StandardTestDispatcher(testScheduler))

    @Test
    fun `plays the songs in order from first to last`() = runTest {
        createRadioStation().use { station ->
            station.signal.test {
                assertThat(awaitItem().title).isEqualTo("Maple Leaf Rag")
                assertThat(awaitItem().title).isEqualTo("The Entertainer")
                assertThat(awaitItem().title).isEqualTo("The Easy Winners")
            }
        }
    }

    @Test
    fun `plays the first song after the last song is done`() = runTest {
        createRadioStation().use { station ->
            station.signal.test {
                skipItems(3)
                assertThat(awaitItem().title).isEqualTo("Maple Leaf Rag")
            }
        }
    }

    @Test
    fun `plays each song for the duration of the song`() = runTest {
        createRadioStation().use { station ->
            station.signal.test {
                awaitItem()
                assertThat(currentTime.milliseconds).isEqualTo(0.seconds)
                awaitItem()
                assertThat(currentTime.milliseconds).isEqualTo(1.5.seconds)
                awaitItem()
                assertThat(currentTime.milliseconds).isEqualTo(3.5.seconds)
            }
        }
    }
}
