package lesson50.exercise

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlin.time.Duration

/* ****************************************************************************

   Implement the tests for this class. You can find the test functions stubbed
   out in /test/lesson50/exercise/RadioStationTest.kt. Do not make any changes
   to the `RadioStation` or `Song` classes below.

   ************************************************************************* */

class RadioStation(
    private val songs: List<Song>,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : AutoCloseable {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    private val _signal = flow {
        var index = 0
        while (currentCoroutineContext().isActive) {
            val song = songs[index]
            emit(song)
            delay(song.duration)
            if (++index > songs.lastIndex) index = 0
        }
    }

    val signal = _signal.shareIn(scope, SharingStarted.Eagerly)

    override fun close() {
        scope.cancel()
    }
}

data class Song(val title: String, val duration: Duration)
