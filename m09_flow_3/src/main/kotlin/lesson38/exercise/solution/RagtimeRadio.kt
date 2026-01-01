package lesson38.exercise.solution

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Using the list of songs from the exercise for Lesson 36, create an object
   named `RadioStation` with a `signal` state flow. The radio station needs to
   emit a random song into its state flow, being sure to delay the proper
   amount of time for the song.

   Then, create a `Playlist` object, which also has a state flow that holds a
   list of strings. Give this object a function - `add(songTitle: String)` -
   which can be used to add the song title to the list in its state flow.

   Finally, create a `main()` function that does these things:

   1. Whenever the `RadioStation` starts playing another song, print out the
   name of the song - e.g., `🎹 Now playing: Maple Leaf Rag`.
   2. Whenever the `Playlist` gets a new song, print out the full list of songs
   in the playlist - e.g., `📃 Playlist: [Elite Syncopations, Peacherine Rag]`
   3. Whenever the user hits the Enter key in the console, add the radio's
   currently-playing song to the playlist.

   ************************************************************************* */

data class Song(val title: String, val duration: Duration)

val songs: List<Song> = listOf(
    Song("Maple Leaf Rag", 1.5.seconds),
    Song("The Entertainer", 2.seconds),
    Song("The Easy Winners", 1.5.seconds),
    Song("Elite Syncopations", 1.seconds),
    Song("Peacherine Rag", 1.seconds),
)

object RadioStation {
    private val seed = Random(1)
    private val _signal = MutableStateFlow(Song("Starting up!", 0.seconds))
    val signal = _signal.asStateFlow()

    suspend fun broadcast() {
        while (currentCoroutineContext().isActive) {
            val song = songs.random(seed)
            _signal.value = song
            delay(song.duration)
        }
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

    val station = launch {
        RadioStation.broadcast()
    }

    while (readln().trim() != "exit") {
        Playlist.add(RadioStation.signal.value.title)
    }

    radio.cancel()
    playlist.cancel()
    station.cancel()
}
