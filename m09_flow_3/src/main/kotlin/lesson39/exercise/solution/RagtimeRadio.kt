package lesson39.exercise.solution

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Copy and paste your solution from the previous exercise, and then update it
   so that the `RadioStation` object starts with a cold flow that emits the
   songs in round-robin fashion - that is, emit each song in turn, and when you
   get to the end of the list of songs, start over at the beginning again. That
   way the radio will always play a song.

   Then, convert that cold flow into a `StateFlow` using the `stateIn()`
   function.

   Make sure that the rest of the functionality from the last exercise
   continues to work - i.e., printing out the songs, printing out the playlist,
   and adding the currently-playing song to the playlist.

   ************************************************************************* */

data class Song(val title: String, val duration: Duration)

val songs: List<Song> = listOf(
    Song("Maple Leaf Rag", 1.5.seconds),
    Song("The Entertainer", 2.seconds),
    Song("The Easy Winners", 1.5.seconds),
    Song("Elite Syncopations", 1.seconds),
    Song("Peacherine Rag", 1.seconds),
)

object RadioStation : AutoCloseable {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _signal = flow {
        var index = 0
        while (currentCoroutineContext().isActive) {
            val song = songs[index]
            emit(song)
            delay(song.duration)
            if (++index > songs.lastIndex) index = 0
        }
    }

    val signal = _signal.stateIn(scope, SharingStarted.Lazily, Song("Starting up!", 0.seconds))

    override fun close() {
        scope.cancel()
    }
}

object Playlist {
    private val _items = MutableStateFlow(emptyList<String>())
    val items = _items.asStateFlow()

    fun add(songTitle: String) {
        _items.update { it + songTitle }
    }
}

suspend fun main() = coroutineScope {
    val radio = launch {
        RadioStation.signal.collect { println("🎹 Now playing: ${it.title}") }
    }

    val playlist = launch {
        Playlist.items.collect { println("📃 Playlist: $it") }
    }

    while (readln().trim() != "exit") {
        Playlist.add(RadioStation.signal.value.title)
    }

    radio.cancel()
    playlist.cancel()
    RadioStation.close()
}
