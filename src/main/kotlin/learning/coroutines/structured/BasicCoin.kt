package learning.coroutines.structured

fun main() {
    println("Welcome to the Coin Counter!")
    var total = 0
    var again = true
    while (again) {
        println("Enter a coin value to insert (1, 5, 10, or 25):")
        when (val input = readln().toInt()) {
            1, 5, 10, 25 -> {
                total += input
                println("The current total is: $total")
                println("Add another coin? (yes/no)")

                again = readln().equals("yes", ignoreCase = true)
            }

            else -> {
                println("That's not a valid coin value. Please enter 1, 5, 10, or 25.")
            }
        }
    }

    println("Final total: $total")
    println("Thanks for using the Coin Counter!")
}
//
//fun askForInput() {
//    println("Enter a coin value to insert (1, 5, 10, or 25):")
//    val input = readln().toInt()
//    var again = true
//    while (again) {
//        when (input) {
//            1, 5, 10, 25 -> {
//                total += input
//                println("The current total is: $total")
//                println("Add another coin? (yes/no)")
//
//                again = readln().equals("yes", ignoreCase = true)
//            }
//
//            else -> {
//                println("That's not a valid coin value. Please enter 1, 5, 10, or 25.")
//            }
//        }
//    }
//}

//fun askAgain() {
//    println("The current total is: $total")
//    println("Add another coin? (yes/no)")
//
//    if (readln().toString().equals("yes", ignoreCase = true)) {
//        askForInput()
//    } else {
//        exitProcess(0)
//    }
//}
//
//fun addPenny() {
//    total += 1
//    askAgain()
//}
//
//fun addNickel() {
//    total += 5
//    askAgain()
//}
//
//fun addDime() {
//    total += 10
//    askAgain()
//}
//
//fun addQuarter() {
//    total += 25
//    askAgain()
//}
//
//fun invalidCoin() {
//
//}