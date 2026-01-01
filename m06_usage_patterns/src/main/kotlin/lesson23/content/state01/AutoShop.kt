package lesson23.content.state01

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    runBlocking(Dispatchers.Default) {
        val income = listOf(
            process(WorkOrder("🚙", Symptom.SQUEAKING_SOUND)),
            process(WorkOrder("🚗", Symptom.WILL_NOT_START)),
            process(WorkOrder("🚚", Symptom.BURNING_SMELL)),
        ).awaitAll().sum()

        println("-----------------------------")
        println($$"Total income for today: $$${income}")
    }
}

fun CoroutineScope.process(workOrder: WorkOrder) = async {
    var order = workOrder
    println("${order.car} - Received order 🆕")

    val diagnosisTask = async { order.diagnose() }
    order = diagnosisTask.await()

    val repairTask = async { order.repair() }
    order = repairTask.await()

    val testDriveTask = async { order.testDrive() }
    order = testDriveTask.await()

    val billTask = async { order.billCustomer() }
    order = billTask.await()

    println("${order.car} - Completed order ✅")

    return@async order.cost
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

    val cost = coroutineScope {
        async(Dispatchers.IO) {
            println("$car - Fixing problem: $diagnosis 🔧")
            simulateBlockingWork(diagnosis.timeToRepair)

            return@async when (diagnosis) {
                Diagnosis.OIL_LEAK        -> 125
                Diagnosis.WORN_BRAKE_PADS -> 750
                Diagnosis.DEAD_BATTERY    -> 250
            }
        }.await()
    }
    println("$car - Finished fixing problem: $diagnosis 🔧☑️")

    return copy(cost = this.cost + cost)
}

suspend fun WorkOrder.testDrive(): WorkOrder {
    delay(1.5.seconds)
    return copy(cost = cost + 25)
}

suspend fun WorkOrder.billCustomer(): WorkOrder {
    delay(0.5.seconds)
    println($$"$$car - Billed customer for $$$cost 💵☑️")
    return this
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
