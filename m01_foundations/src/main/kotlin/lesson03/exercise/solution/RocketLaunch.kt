package lesson03.exercise.solution

fun main() {
    for (announcement in countDown()) {
        println(announcement)
    }
}

fun countDown() = sequence {
    for (time in 60 downTo 0) {
        when {
            time == 0        -> yield("BLAST OFF! 💥🚀")
            time <= 5        -> yield(time.toString())
            time.rem(5) == 0 -> yield("T-$time seconds to launch")
            else             -> yield("...")
        }
    }
}
