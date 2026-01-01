package lesson36.exercise.solution

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   This lesson introduced the high-level concepts for hot flows, without yet
   showing how to write them. But, this exercise will give you a starting point
   for the rest of the exercises in this unit.

   Whenever you're feeling down, Scott Joplin, the King of Ragtime, is there to
   help put a pep in your step!

   Write a cold flow that will iterate over the following songs with the
   indicated delay, as a simulation of playing each song:

       - Maple Leaf Rag     - 1.5 seconds
       - The Entertainer    - 2.0 seconds
       - The Easy Winners   - 1.5 seconds
       - Elite Syncopations - 1.0 seconds
       - Peacherine Rag     - 1.0 seconds

   For each song in this album, print out the name of the song that's playing.
   For example: `🎹 Now playing: Maple Leaf Rag`

   ************************************************************************* */

data class Song(val title: String, val duration: Duration)

val songs = listOf(
    Song("Maple Leaf Rag", 1.5.seconds),
    Song("The Entertainer", 2.seconds),
    Song("The Easy Winners", 1.5.seconds),
    Song("Elite Syncopations", 1.seconds),
    Song("Peacherine Rag", 1.seconds),
)

val album = flow {
    songs.forEach { song ->
        emit(song.title)
        delay(song.duration)
    }
}

fun main() {
    runBlocking {
        album.collect { println("🎹 Now playing: $it") }
    }
}
