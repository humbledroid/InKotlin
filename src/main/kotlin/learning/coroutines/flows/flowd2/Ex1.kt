package learning.coroutines.flows.flowd2

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.File
import java.io.FileWriter
import kotlin.text.Charsets.UTF_8
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/* ****************************************************************************

   Fiona is spending the day at the zoo, discovering lots of amazing animals,
   and she's keeping a log of the exhibits she's visiting.

   As Fiona sees each animal:

   - She pauses for a moment to admire it.
   - She tells her mother about it.
   - She writes it down in her journal log.

   The zoo is closing soon, though! When that happens, she'll need to pack up
   and leave. Even though her visit will end abruptly, Fiona wants to make sure
   her notes are saved, so she can remember which animals she saw.

   In this exercise, you'll simulate Fiona's visit to the zoo.

   - Flow over the space-separated `animals` string, emitting one emoji at a
     time.
   - Pause for 500 milliseconds between each animal (to admire it, of course!)
   - For each animal, print it to the console _and_ write it to a file named
     `animals.txt`.
   - After five seconds, cancel the coroutine (the zoo is closing!)
   - Ensure that the contents of the `animals.txt` file are properly saved,
     even with the cancellation.

   Remember to handle cancellation cleanly, especially when working with
   external resources like files!

   ************************************************************************* */

private const val animals = "🐶 🐱 🐭 🐹 🐰 🦊 🐻 🐼 🐨 🐯 🦁 🐺 🦝 🐷 🐮 🐸 🐵 🐔 🦄 🐲"
fun main() = runBlocking(Dispatchers.Default) {
    val file = File("animals.txt")
    val writer = FileWriter(file, UTF_8, false)
    val animalJob = launch(Dispatchers.IO) {
        animals.split(' ').asFlow()
            .onEach {
                delay(500.milliseconds)
            }
            .onCompletion {
                writer.close()
            }.collect { item ->
                println(item)
                writer.write(item)
            }
    }

    delay(5.seconds)
    println("Cancelling...")
    animalJob.cancelAndJoin()
    println("File contents:")
    println(file.readText())
}