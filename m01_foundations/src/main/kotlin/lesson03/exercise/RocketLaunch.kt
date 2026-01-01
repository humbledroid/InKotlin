package lesson03.exercise

/* **************************************************************************************

   Use the coroutine-based `sequence()` function to create an announcement system for a
   rocket launch!

   The system should count time from 60 down to 0, and...
   - when the time is 0, the announcement should be "BLAST OFF! 💥🚀"
   - otherwise, when the count is less than 5 seconds, it should be the time
   - otherwise, if the count is a multiple of 5, it should be "T-$time seconds to launch"
   - otherwise, the announcement should be "..."

    Hints:
    - You can use the `downTo` infix function like this: `for (time in 60 downTo 0)`
    - You can use `time % 5` or `time.rem(5)` to determine if it's a multiple of 5
   *********************************************************************************** */

fun main() {
    for (announcement in countDown()) {
        println(announcement)
    }
}

fun countDown() = sequence<String> {
    // TODO: Fill this in
}
