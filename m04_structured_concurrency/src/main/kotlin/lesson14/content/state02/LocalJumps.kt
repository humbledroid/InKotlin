package lesson14.content.state02

import kotlin.random.Random
import kotlin.random.nextInt

fun main() {
    println("▶️ Let's play!")
    playGame()
    println("👋 Thanks for playing.")
}

fun playGame() {
    val range = 1..10
    val secret = Random.nextInt(range)
    val guesses = List(11) { Random.nextInt(1..11) }
    val history = mutableListOf<Int>()

    for ((index, guess) in guesses.withIndex()) {
        if (guess in history) {
            println("⚠️ You already guessed $guess. Try again.")
            continue
        } else {
            history.add(guess)
        }

        if (guess == secret) {
            println("🎉 $guess is correct! You guessed it!")
            return
        }

        if (guess !in range) {
            throw IllegalStateException("🚫 Invalid guess: $guess. Ending game. It was $secret.")
        }

        if (index == 10) {
            println("🛑 Too many guesses!")
            break
        }

        println("❌ Wrong guess: $guess. Try again.")
    }

    println("🏁 Game over. It was $secret.")
}
