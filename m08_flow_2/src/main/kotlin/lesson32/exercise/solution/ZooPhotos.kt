package lesson32.exercise.solution

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.Duration.Companion.milliseconds

/* ****************************************************************************

   Fiona is at the zoo again, taking photos of the animals, and uploading her
   photos to the cloud. Thankfully, the cell phone signal is more reliable than
   last time, but it's still a little slow - she's able to take the photos much
   faster than they're getting uploaded.

   Rather than waiting for each photo to upload before moving to the next
   animal, she wants to take all the photos she can, and let them upload in the
   background.

   For this exercise:

   - Flow over the space-separated `animals` string, printing out that she took
     a photo of an animal - e.g., "Photographing 🐷" - and then emit it so that
     the downstream collector can simulate uploading it.
   - When collecting the photo...
       - First, pause for 250 milliseconds to simulate initializing the upload.
       - Then print out that it's uploading - e.g., "Uploading photo of 🐷".
       - Then pause for 1 second to simulate uploading it.
       - Finally, print out that the upload is "Done".
       - Optionally, during the 1 second, print out a dot every 100ms to show
         the progress - e.g., "Uploading photo of 🐷 .......... Done!".
   - Rather than waiting for each photo to upload before taking the next photo,
     ensure that all the photos can be taken up front. Because the collector
     spends 250ms initializing _before_ any upload begins, you should see all
     the "Photographing..." lines printed **before** any "Uploading..." line.

   ************************************************************************* */

private const val animals = "🐶 🐱 🐭 🐹 🐰 🦊 🐻 🐼 🐨 🐯 🦁 🐺 🦝 🐷 🐮 🐸 🐵 🐔 🦄 🐲"

suspend fun main() {
    flow {
        animals.split(" ").forEach {
            println("Photographing $it")
            emit(it)
        }
    }
        .flowOn(Dispatchers.Default)
        .collect(::upload)
}

suspend fun upload(photo: String) {
    delay(250.milliseconds) // Initializing upload...
    print("Uploading photo of $photo ")
    repeat(10) {
        print(".")          // Upload in progress...
        delay(100.milliseconds)
    }
    println(" Done!")
}
