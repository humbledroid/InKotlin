package lesson41.content.state06

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class Baker(
    val inbound: ReceiveChannel<Int>,
    val outbound: SendChannel<Cupcake>
) {
    private val rng = Random(0)

    suspend fun start() {
        for (count in inbound) {
            println("🥣 Baking $count cupcakes")
            simulateWork(1.seconds)
            val cupcakes = List(count) { Cupcake(it, Batter.entries.random(rng)) }
            cupcakes.forEach { outbound.send(it) }
        }
    }
}

class Decorator(
    val inbound: ReceiveChannel<Cupcake>,
    val outbound: SendChannel<Cupcake>
) {
    val rng = Random(1)

    suspend fun start() {
        for (cupcake in inbound) {
            println("🧁 Decorating $cupcake")
            simulateWork(1.seconds)
            val decorated = cupcake.copy(
                frosting = Frosting.entries.random(rng),
                topping = Topping.entries.random(rng)
            )
            outbound.send(decorated)
        }
    }
}

fun main() = runBlocking {
    val countChannel = Channel<Int>()
    val bakedChannel = Channel<Cupcake>()
    val decoratedChannel = Channel<Cupcake>()

    launch { Baker(countChannel, bakedChannel).start() }
    launch { Decorator(bakedChannel, decoratedChannel).start() }
    countChannel.send(12)
    for (element in decoratedChannel) println(element)
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
