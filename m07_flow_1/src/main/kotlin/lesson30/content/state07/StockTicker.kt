package lesson30.content.state07

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.none
import kotlinx.coroutines.flow.takeWhile
import java.text.NumberFormat
import java.util.*
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)

fun stockTicker(startPrice: Int) = flow {
    var price = startPrice
    while (true) {
        delay(500.milliseconds)
        val delta = Random.nextInt(-100, 100)
        price = (price + delta).coerceAtLeast(1) // avoid negative
        emit(price)
    }
}

suspend fun main() {
    val match = stockTicker(150_00)
        .takeWhile { it >= 148_00 }
        .map { currencyFormat.format(it / 100.0) }
        .none { it.startsWith("$14") }

    println(match)
}
