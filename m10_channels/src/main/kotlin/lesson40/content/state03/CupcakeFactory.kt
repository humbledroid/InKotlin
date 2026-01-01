package lesson40.content.state03

import kotlinx.coroutines.*
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue
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

suspend fun main(): Unit = coroutineScope {
    launch { Baker.start() }
    launch { Decorator.start() }
    launch { Boxer.start() }
    launch { Inspector.start() }

    Baker.queue.add(12)
}

object Baker {
    val queue: Queue<Int> = ConcurrentLinkedQueue()
    private val dispatcher = Dispatchers.Default.limitedParallelism(1)
    private val rng = Random(0)

    suspend fun start() {
        while (currentCoroutineContext().isActive) {
            when (val count: Int? = queue.poll()) {
                null -> delay(500.milliseconds)
                else -> {
                    val baked = bake(count)
                    Decorator.queue.addAll(baked)
                }
            }
        }
    }

    private suspend fun bake(count: Int) = withContext(dispatcher) {
        println("🥣 Baking $count cupcakes")
        simulateWork(1.seconds)
        List(count) { Cupcake(it, Batter.entries.random(rng)) }
    }
}

object Decorator {
    val queue: Queue<Cupcake> = ConcurrentLinkedQueue()
    private val dispatcher = Dispatchers.Default.limitedParallelism(1)
    private val rng = Random(1)

    suspend fun start() {
        while (currentCoroutineContext().isActive) {
            when (val cupcake: Cupcake? = queue.poll()) {
                null -> delay(500.milliseconds)
                else -> {
                    val decorated = decorate(cupcake)
                    Boxer.queue.add(decorated)
                }
            }
        }
    }

    private suspend fun decorate(cupcake: Cupcake): Cupcake = withContext(dispatcher) {
        println("🧁 Decorating $cupcake")
        simulateWork(1.seconds)
        cupcake.copy(
            frosting = Frosting.entries.random(rng),
            topping = Topping.entries.random(rng)
        )
    }
}

object Boxer {
    val queue: Queue<Cupcake> = ConcurrentLinkedQueue()
    private val dispatcher = Dispatchers.Default.limitedParallelism(1)

    suspend fun start() {
        while (currentCoroutineContext().isActive) {
            when (val cupcake: Cupcake? = queue.poll()) {
                null -> delay(500.milliseconds)
                else -> {
                    val boxed = box(cupcake)
                    Inspector.queue.add(boxed)
                }
            }
        }
    }

    private suspend fun box(cupcake: Cupcake): Box = withContext(dispatcher) {
        println("🎁 Boxing $cupcake")
        simulateWork(250.milliseconds)
        Box(cupcake)
    }
}

object Inspector {
    val queue: Queue<Box> = ConcurrentLinkedQueue()
    private val dispatcher = Dispatchers.Default.limitedParallelism(1)
    private val rng = Random(2)

    suspend fun start() {
        while (currentCoroutineContext().isActive) {
            when (val box: Box? = queue.poll()) {
                null -> delay(500.milliseconds)
                else -> inspect(box)
            }
        }
    }

    suspend fun inspect(box: Box): Unit = withContext(dispatcher) {
        println("🔍 Inspecting product")

        when {
            rng.nextInt(100) < 20 -> {
                println("⛔ Decoration failed: Cupcake #${box.cupcake.id}")
                Decorator.queue.add(box.cupcake)
            }

            rng.nextInt(100) < 10 -> {
                println("⛔ Box failed: Cupcake #${box.cupcake.id}")
                Boxer.queue.add(box.cupcake)
            }

            else                  -> println("✅ Passed: $box")
        }

    }
}

private fun simulateWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}
