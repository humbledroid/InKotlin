package lesson37.exercise.solution

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import lesson37.exercise.solution.Length.HALF
import lesson37.exercise.solution.Length.QUARTER
import javax.sound.midi.MidiSystem
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   "The Entertainer" is one of Scott Joplin's most famous tunes. In this
   exercise you'll play the opening line from this song, including its octave
   harmony.

   Create an object named `Entertainer` that has a shared flow for the notes
   of the song. Then give that object a function named `start()`, which will
   emit the notes of the song:

   D - Quarter Note
   E - Quarter Note
   C - Quarter Note
   A - Half Note
   B - Quarter Note
   G - Half Note

   In the `start()` function, you'll want to add a one-second delay before and
   after the notes, to ensure the instrument has a chance to warm up and cool
   down.

   Finally, create a `main()` function that includes two subscribers of your
   shared flow. The first subscriber should play the note on the piano as it
   is, and the second subscriber should play the note an octave higher, using
   the `octaveUp()` function.

   If you've got headphones or a speaker on your computer, use the `PlayPiano`
   and listen to the song! Otherwise, if your device doesn't support MIDI, you
   can just use the `LogPiano`.

   ************************************************************************* */

data class Note(val pitch: Pitch, val length: Length) {
    fun octaveUp() = copy(pitch = pitch.copy(number = pitch.number + 12))
}

enum class Length(val duration: Duration) {
    QUARTER(0.25.seconds), HALF(0.5.seconds)
}

data class Pitch(val label: String, val number: Int) {
    companion object {
        val E = Pitch("E", 64)
        val D = Pitch("D", 62)
        val C = Pitch("C", 60)
        val B = Pitch("B", 59)
        val A = Pitch("A", 57)
        val G = Pitch("G", 55)
    }
}

interface Piano : AutoCloseable {
    suspend fun play(note: Note)
}

class PlayPiano : Piano {
    private val synth = MidiSystem.getSynthesizer().apply { open() }
    private val instrument = synth.channels[0]

    override suspend fun play(note: Note) {
        instrument.noteOn(note.pitch.number, 127)
        delay(note.length.duration)
        instrument.noteOff(note.pitch.number)
    }

    override fun close() {
        synth.close()
    }
}

class LogPiano : Piano {
    override suspend fun play(note: Note) {
        println("🎹 Playing $note")
        delay(note.length.duration)
    }

    override fun close() {}
}

object Entertainer {
    private val _notes = MutableSharedFlow<Note>()
    val notes = _notes.asSharedFlow()

    suspend fun start() {
        delay(1.seconds)
        _notes.emit(Note(D, QUARTER))
        _notes.emit(Note(E, QUARTER))
        _notes.emit(Note(C, QUARTER))
        _notes.emit(Note(A, HALF))
        _notes.emit(Note(B, QUARTER))
        _notes.emit(Note(G, HALF))
        delay(1.seconds)
    }
}

fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)
    val piano = PlayPiano()

    scope.launch {
        Entertainer.notes.collect { piano.play(it) }
    }
    scope.launch {
        Entertainer.notes.collect { piano.play(it.octaveUp()) }
    }

    Entertainer.start()
    piano.close()
}
