package lesson41.content.state03

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val countChannel = Channel<Int>()
val bakedChannel = Channel<Cupcake>()

object Baker {
    private val rng = Random(0)

    suspend fun start() {
        val count = countChannel.receive()
        println("🥣 Baking $count cupcakes")
        simulateWork(1.seconds)
        val cupcakes = List(count) { Cupcake(it, Batter.entries.random(rng)) }
        cupcakes.forEach { bakedChannel.send(it) }
    }
}

fun main() = runBlocking {
    launch { Baker.start() }
    countChannel.send(12)
    println(bakedChannel.receive())
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
