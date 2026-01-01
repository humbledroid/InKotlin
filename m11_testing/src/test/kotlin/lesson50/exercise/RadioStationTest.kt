@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson50.exercise

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Implement each of the test functions below.

   The corresponding main source file for this test can be found here:
   /main/lesson50/exercise/RadioStation.kt.

   ************************************************************************* */

class RadioStationTest {
    private val songs: List<Song> = listOf(
        Song("Maple Leaf Rag", 1.5.seconds),
        Song("The Entertainer", 2.seconds),
        Song("The Easy Winners", 1.5.seconds),
    )

    @Test
    fun `plays the songs in order from first to last`() = runTest {
    }

    @Test
    fun `plays the first song after the last song is done`() = runTest {
    }

    @Test
    fun `plays each song for the duration of the song`() = runTest {
    }
}
