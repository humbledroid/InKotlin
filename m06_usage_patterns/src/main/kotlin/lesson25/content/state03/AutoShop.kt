package lesson25.content.state03

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking(Dispatchers.Default) {
        val job1 = processAsync(WorkOrder("🚙", Symptom.SQUEAKING_SOUND))
        val job2 = processAsync(WorkOrder("🚗", Symptom.WILL_NOT_START))
        val job3 = processAsync(WorkOrder("🚚", Symptom.BURNING_SMELL))

        val total = job1.await() + job2.await() + job3.await()
        println($$"Total is $$${total}")
    }
}

fun CoroutineScope.processAsync(order: WorkOrder) = async {
    order.diagnose().repair().cost
}

suspend fun WorkOrder.diagnose(): WorkOrder {
    println("$car - Diagnosing $symptom 🔍")
    delay(symptom.timeToDiagnose)

    val diagnosis = when (symptom) {
        Symptom.BURNING_SMELL   -> Diagnosis.OIL_LEAK
        Symptom.SQUEAKING_SOUND -> Diagnosis.WORN_BRAKE_PADS
        Symptom.WILL_NOT_START  -> Diagnosis.DEAD_BATTERY
    }
    println("$car - Diagnosis for $symptom is $diagnosis 🔍☑️")

    return copy(diagnosis = diagnosis, cost = cost + 150)
}

suspend fun WorkOrder.repair(): WorkOrder {
    val diagnosis = diagnosis ?: return this

    val cost = withContext(Dispatchers.IO) {
        println("$car - Fixing problem: $diagnosis 🔧")
        simulateBlockingWork(diagnosis.timeToRepair)

        when (diagnosis) {
            Diagnosis.OIL_LEAK        -> 125
            Diagnosis.WORN_BRAKE_PADS -> 750
            Diagnosis.DEAD_BATTERY    -> 250
        }
    }
    println("$car - Finished fixing problem: $diagnosis 🔧☑️")

    return copy(cost = this.cost + cost)
}

private fun simulateBlockingWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}

data class WorkOrder(
    val car: String,
    val symptom: Symptom,
    val diagnosis: Diagnosis? = null,
    val testDriveSuccess: Boolean? = null,
    val cost: Int = 0
)

enum class Symptom(val timeToDiagnose: Duration) {
    BURNING_SMELL(1.seconds),
    SQUEAKING_SOUND(0.5.seconds),
    WILL_NOT_START(1.5.seconds)
}

enum class Diagnosis(val timeToRepair: Duration) {
    OIL_LEAK(1.25.seconds),
    WORN_BRAKE_PADS(4.seconds),
    DEAD_BATTERY(2.5.seconds)
}
