package lesson30.exercise

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
