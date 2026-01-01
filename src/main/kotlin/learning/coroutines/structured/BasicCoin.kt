package learning.coroutines.structured

import kotlin.system.exitProcess

var total = 0
fun main(){
    println("Welcome to the Coin Counter!")
    askForInput()
}

fun askForInput(){
    println("Enter a coin value to insert (1, 5, 10, or 25):")
    val input = readln().toInt()

    when (input) {
        1 -> addPenny()
        5 -> addNickel()
        10 -> addDime()
        25 -> addQuarter()
        else -> invalidCoin()
    }
}

fun askAgain(){
    println("The current total is: $total")
    println("Add another coin? (yes/no)")

    if(readln().toString().equals("yes", ignoreCase = true)){
        askForInput()
    } else {
        exitProcess(0)
    }
}

fun addPenny() {
    total += 1
    askAgain()
}

fun addNickel() {
    total += 5
    askAgain()
}

fun addDime() {
    total += 10
    askAgain()
}

fun addQuarter() {
    total += 25
    askAgain()
}

fun invalidCoin() {
    println("That's not a valid coin value. Please enter 1, 5, 10, or 25.")
    askAgain()
}