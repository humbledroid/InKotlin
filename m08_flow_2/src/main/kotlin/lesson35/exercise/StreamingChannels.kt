package lesson35.exercise

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   After working hard all week, Murphy, Benny, and Inigo are ready to watch
   their favorite TV shows. But each of them has very different viewing habits!

   - Murphy is known as "Multitasking Murphy" because he likes to watch
     multiple shows at one time. He's got three devices in front of him, and
     he watches three different channels at once!
   - Benny is known as "Binging Benny", because likes to watch through
     everything. He's only got one TV, so he just watches all the shows on one
     channel before moving on to the next.
   - Inigo's friends call him "Indecisive Inigo" because he can't decide which
     channel he wants to watch. He flips through the channels, finally settling
     on the drama channel.

   For each of these three individuals, start with the `allChannels` flow, and
   use the right operators to match the person's viewing habits. The collector
   for each person should simply call the `watch()` function with the show.

   When you've completed this successfully, your output should look like this:

   Multitasking Murphy  --------------------------
    📺 Watching 'Shadow Team: Zero'
    📺 Watching 'Zed-14 and the Droidbots'
    📺 Watching 'Granny Smith Mysteries'
    📺 Watching 'Cuddle Pet Adventures'
    📺 Watching 'Brick Steel Undercover'
    📺 Watching 'Johnny Stealth'
    📺 Watching 'Spies in the Skies'
    📺 Watching 'Objection, Your Honor'
    📺 Watching 'Emergent: K-9 Unit'
   Binging Benny ---------------------------------
    📺 Watching 'Shadow Team: Zero'
    📺 Watching 'Brick Steel Undercover'
    📺 Watching 'Spies in the Skies'
    📺 Watching 'Zed-14 and the Droidbots'
    📺 Watching 'Cuddle Pet Adventures'
    📺 Watching 'Johnny Stealth'
    📺 Watching 'Granny Smith Mysteries'
    📺 Watching 'Objection, Your Honor'
    📺 Watching 'Emergent: K-9 Unit'
   Indecisive Inigo ------------------------------
    📺 Watching 'Shadow Team: Zero'
    📺 Watching 'Zed-14 and the Droidbots'
    📺 Watching 'Granny Smith Mysteries'
    📺 Watching 'Objection, Your Honor'
    📺 Watching 'Emergent: K-9 Unit'

   ************************************************************************* */

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() {
    println("Multitasking Murphy  --------------------------")
    // TODO: Implement for Murphy

    println("Binging Benny ---------------------------------")
    // TODO: Implement for Benny

    println("Indecisive Inigo ------------------------------")
    // TODO: Implement for Inigo
}

val actionChannel = flow {
    broadcast(Show("Shadow Team: Zero", 750.milliseconds))
    broadcast(Show("Brick Steel Undercover", 500.milliseconds))
    broadcast(Show("Spies in the Skies", 750.milliseconds))
}

val cartoonChannel = flow {
    broadcast(Show("Zed-14 and the Droidbots", 500.milliseconds))
    broadcast(Show("Cuddle Pet Adventures", 300.milliseconds))
    broadcast(Show("Johnny Stealth", 500.milliseconds))
}

val dramaChannel = flow {
    broadcast(Show("Granny Smith Mysteries", 1.5.seconds))
    broadcast(Show("Objection, Your Honor", 1.seconds))
    broadcast(Show("Emergent: K-9 Unit", 1.seconds))
}

suspend fun FlowCollector<Show>.broadcast(show: Show) {
    emit(show)
    delay(show.duration)
}

val allChannels = flow {
    emit(actionChannel)
    delay(125.milliseconds)
    emit(cartoonChannel)
    delay(125.milliseconds)
    emit(dramaChannel)
}

fun watch(show: Show) {
    println(" 📺 Watching '${show.title}'")
}

data class Show(val title: String, val duration: Duration)
