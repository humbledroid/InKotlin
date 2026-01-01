package lesson29.content.state06

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.take
import java.text.NumberFormat
import java.util.*
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)

fun stockTicker(startPrice: Int) = flow {
    var price = startPrice
    while (true) {
        delay(500.milliseconds)
        val delta = Random.nextInt(-100, 100)
        price = (price + delta).coerceAtLeast(1)
        emit(price)
    }
}

@OptIn(FlowPreview::class)
suspend fun main() {
    stockTicker(150_00)
        .sample(3.seconds)
        .map { currencyFormat.format(it / 100.0) }
        .take(100)
        .collect { println("📈 $it") }
}
