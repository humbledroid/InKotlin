package neetcode.revision

import java.util.*

class CarFleetRevision {
    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
        val stack = Stack<Double>()

        val pairSortedByDistanceTravelled = position.zip(speed).sortedByDescending { it.first }

        for((p, s) in pairSortedByDistanceTravelled){
            val remainingTimeToTarget = (target - p) / s.toDouble()
            stack.push(remainingTimeToTarget)
            if (stack.size >= 2 && stack[stack.size - 1] <= stack[stack.size - 2]) {
                stack.pop()
            }
        }
        return stack.size
    }
}

fun main() {
    val carFleetRevision = CarFleetRevision()
    println(carFleetRevision.carFleet(target = 10, position = intArrayOf(4,1,0,7), speed = intArrayOf(2,2,1,1)))
}