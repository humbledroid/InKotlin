package neetcode.rec

fun main() {
    fun countDown(n: Int) {
        if (n == 0) {
            println("Done, lets go!!!")
            return
        }
        println(n)
        countDown(n - 1)
    }

    countDown(4)
}


