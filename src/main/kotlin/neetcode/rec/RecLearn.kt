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

    fun sum(input: List<Int>): Int {
        if(input.isEmpty()) return 0 // Base case
        return input.first() + sum(input.drop(1))
                               // this is the smaller sub problem that needs to be solved
    }

    println(sum(listOf(3,7,2)))

    /**
     * sum([3, 7, 2])
     *   → 3 + sum([7, 2])
     *     → 7 + sum([2])
     *       → 2 + sum([])
     *         → 0   // base case!
     *       ← 2 + 0 = 2
     *     ← 7 + 2 = 9
     *   ← 3 + 9 = 12
     */
}


