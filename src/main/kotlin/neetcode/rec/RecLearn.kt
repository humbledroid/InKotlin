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

    fun factorial(n: Int): Int {
        if(n == 0) return 1

        return n * factorial(n - 1)
    }

    println(factorial(5))


    // memoization
    fun fib(n: Int, memo: MutableMap<Int, Int>): Int {
        if(n <= 1) return 1

        if(n in memo) return memo[n]!!

        val result = fib(n - 1, memo) + fib(n - 2, memo)
        memo[n] = result

        return result
    }

    println(fib(5, mutableMapOf<Int, Int>()))
}


