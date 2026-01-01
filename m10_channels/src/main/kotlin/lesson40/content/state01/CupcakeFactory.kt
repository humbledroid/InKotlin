package lesson40.content.state01

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

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

fun main() {
    runBlocking {
        val produced = Baker.bake(12).map {
            val decorated = async { Decorator.decorate(it) }
            val boxed = async { Boxer.box(decorated.await()) }
            boxed
        }

        println("Produced ${produced.awaitAll().size} cupcakes!")
    }
}

object Baker {
    private val dispatcher = Dispatchers.Default.limitedParallelism(1)
    private val rng = Random(0)

    suspend fun bake(count: Int) = withContext(dispatcher) {
        println("🥣 Baking $count cupcakes")
        simulateWork(1.seconds)
        List(count) { Cupcake(it, Batter.entries.random(rng)) }
    }
}

object Decorator {
    private val dispatcher = Dispatchers.Default.limitedParallelism(1)
    private val rng = Random(1)

    suspend fun decorate(cupcake: Cupcake): Cupcake = withContext(dispatcher) {
        println("🧁 Decorating $cupcake")
        simulateWork(1.seconds)
        cupcake.copy(
            frosting = Frosting.entries.random(rng),
            topping = Topping.entries.random(rng)
        )
    }
}

object Boxer {
    private val dispatcher = Dispatchers.Default.limitedParallelism(1)

    suspend fun box(cupcake: Cupcake): Box = withContext(dispatcher) {
        println("🎁 Boxing $cupcake")
        simulateWork(250.milliseconds)
        Box(cupcake)
    }
}

private fun simulateWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}
