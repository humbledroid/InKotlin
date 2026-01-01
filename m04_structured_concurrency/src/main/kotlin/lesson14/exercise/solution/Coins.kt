package lesson14.exercise.solution

/* ****************************************************************************

    A port of Coins.bas to Kotlin.

   ************************************************************************* */

fun main() {
    var total = 0
    var addMore = true
    println("Welcome to the Coin Counter!")

    while (addMore) {
        println("Enter a coin value to insert (1, 5, 10, or 25):")
        val coin = readLine()?.toIntOrNull()

        when (coin) {
            1, 5, 10, 25 -> {
                total += coin

                println("The current total is: $total")
                println("Add another coin? (yes/no)")
                val again = readLine()?.lowercase()

                addMore = again == "yes"
            }

            else -> {
                println("That's not a valid coin value. Please enter 1, 5, 10, or 25.")
            }
        }
    }

    println("Final total: $total")
    println("Thanks for using the Coin Counter!")
}
