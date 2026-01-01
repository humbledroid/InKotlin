package lesson34.exercise

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.milliseconds

/* ****************************************************************************

   The music festival is here! While the band is playing, the lights are
   flashing, and attendees are getting their wristbands and raffle tickets.

   In this exercise, you'll need to properly put together the following flows:

   - `guitar` and `drums`
   - `wristbands` and `raffleTickets`
   - `colorControls` and `brightnessControls`

   For the guitar and drums, put the notes from each of those flows into a
   single flow. Then print out the note in the collector. The output from
   this flow should include notes from both the guitar and the drums.

   For the wristbands and raffle tickets, put the corresponding wristband and
   raffle ticket together in an `Attendee` object. For any given attendee,
   the number on the wristband is the same number that's on the raffle ticket.
   Print out the `Attendee` object in the collector.

   For the lighting controls, create a `LightingSnapshot` that indicates the
   current state of the lighting whenever _either_ the color or brightness
   changes. Print out that snapshot in the collector.

   ************************************************************************* */

suspend fun main() {
    println("MUSIC -----------------------------------------")
    // TODO: Put together the guitar and drums flows

    println("ATTENDANCE ------------------------------------")
    // TODO: Put together the wristbands and raffle tickets

    println("LIGHTING --------------------------------------")
    // TODO: Put together the color controls and brightness controls
}

// Music

val guitar: Flow<Note> = flow {
    repeat(4) {
        emit(GuitarNote.POWER_CHORD_1); delay(500.milliseconds)
        emit(GuitarNote.POWER_CHORD_4); delay(250.milliseconds)
        emit(GuitarNote.POWER_CHORD_5); delay(250.milliseconds)
    }
}

val drums: Flow<Note> = flow {
    repeat(4) {
        emit(DrumNote.BOOM); delay(250.milliseconds)
        emit(DrumNote.TAP); delay(250.milliseconds)
        emit(DrumNote.BOOM); delay(250.milliseconds)
        emit(DrumNote.TAP); delay(250.milliseconds)
    }
}

interface Note
enum class GuitarNote : Note { POWER_CHORD_1, POWER_CHORD_4, POWER_CHORD_5 }
enum class DrumNote : Note { BOOM, TAP }

// Attendance

val wristbands = flow {
    repeat(5) { id -> emit(Wristband(id)) }
}

val raffleTickets = flow {
    repeat(5) { id -> emit(RaffleTicket(id)) }
}

data class Attendee(val wristband: Wristband, val raffleTicket: RaffleTicket)
data class Wristband(val number: Int)
data class RaffleTicket(val number: Int)

// Lighting

val colorControls = flow {
    LightingColor.entries.forEach { emit(it); delay(500.milliseconds) }
}

val brightnessControls = flow {
    delay(250.milliseconds)
    LightingBrightness.entries.forEach { emit(it); delay(500.milliseconds) }
}

data class LightingSnapshot(val color: LightingColor, val brightness: LightingBrightness)
enum class LightingColor { BLUE, YELLOW, RED, ORANGE, PURPLE, GREEN }
enum class LightingBrightness { LOW, MEDIUM, HIGH }
