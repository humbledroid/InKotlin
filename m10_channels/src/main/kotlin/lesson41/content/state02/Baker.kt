package lesson41.content.state02

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val channel = Channel<Int>()

object Baker {
    private val rng = Random(0)

    suspend fun start() {
        val count = channel.receive()
        println("🥣 Baking $count cupcakes")
        simulateWork(1.seconds)
        val cupcakes = List(count) { Cupcake(it, Batter.entries.random(rng)) }
    }
}

fun main() = runBlocking {
    launch { Baker.start() }
    channel.send(12)
}

private fun simulateWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}

data class Cupcake(
    val id: Int,
    val batter: Batter,
    val frosting: Frosting? = null,
    val topping: Topping? = null
)

data class Box(val cupcake: Cupcake)

enum class Batter { VANILLA, CHOCOLATE, ALMOND }
enum class Frosting { VANILLA, CHOCOLATE }
enum class Topping { SPRINKLES, CHOCOLATE_DRIZZLE, CARAMEL_DRIZZLE }
