package lesson42.content.state02

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
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
            println("🧁➡️🎁 🕒 Trying to hand off #${cupcake.id} to the boxer.")
            outbound.send(decorated)
            println("🧁➡️🎁 ☑️ Successfully handed off #${cupcake.id} to the boxer.")
        }
    }
}

class Boxer(
    val inbound: ReceiveChannel<Cupcake>,
    val outbound: SendChannel<Box>
) {
    suspend fun start() {
        for (cupcake in inbound) {
            println("🎁 Boxing $cupcake")
            simulateWork(250.milliseconds)
            val box = Box(cupcake)
            println("🎁➡️🔍 🕒 Trying to hand off #${cupcake.id} to the inspector.")
            outbound.send(box)
            println("🎁➡️🔍 ☑️ Successfully handed off #${cupcake.id} to the inspector.")
        }
    }
}

class Inspector(
    val inbound: ReceiveChannel<Box>,
    val toDecorator: SendChannel<Cupcake>,
    val toBoxer: SendChannel<Cupcake>
) {
    val rng = Random(123)

    suspend fun start() {
        for (box in inbound) {
            when {
                rng.nextInt(100) < 20 -> {
                    println("⛔ Decoration failed: Cupcake #${box.cupcake.id}")
                    println("🔍➡️🧁 🕒 Trying to hand off #${box.cupcake.id} to the decorator.")
                    toDecorator.send(box.cupcake)
                    println("🔍➡️🧁 ☑️ Successfully handed off #${box.cupcake.id} to the decorator.")
                }

                rng.nextInt(100) < 10 -> {
                    println("⛔ Box failed: Cupcake #${box.cupcake.id}")
                    println("🔍➡️🎁 🕒 Trying to hand off #${box.cupcake.id} to the boxer.")
                    toBoxer.send(box.cupcake)
                    println("🔍➡️🎁 ☑️ Successfully handed off #${box.cupcake.id} to the boxer.")
                }

                else                  -> println("✅ Passed: $box")
            }
        }
    }
}

fun main() = runBlocking(Dispatchers.Default) {
    val countChannel = Channel<Int>(capacity = 1)
    val bakedChannel = Channel<Cupcake>(capacity = 1)
    val decoratedChannel = Channel<Cupcake>(capacity = 1)
    val boxChannel = Channel<Box>(capacity = 1)

    launch { Baker(countChannel, bakedChannel).start() }
    launch { Decorator(bakedChannel, decoratedChannel).start() }
    launch { Boxer(decoratedChannel, boxChannel).start() }
    launch { Inspector(boxChannel, bakedChannel, decoratedChannel).start() }
    countChannel.send(12)
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
