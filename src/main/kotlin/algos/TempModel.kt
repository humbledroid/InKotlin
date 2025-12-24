package algos

import java.io.*
import java.math.*
import java.security.*
import java.text.*
import java.util.*
import java.util.concurrent.*
import java.util.function.*
import java.util.regex.*
import java.util.stream.*
import kotlin.collections.*
import kotlin.comparisons.*
import kotlin.io.*
import kotlin.jvm.*
import kotlin.jvm.functions.*
import kotlin.jvm.internal.*
import kotlin.ranges.*
import kotlin.sequences.*
import kotlin.text.*


fun getMinimumDifference(a: Array<String>, b: Array<String>): Array<Int> {
    // Write your code here
    val result = ArrayList<Int>()
    val mMap = mutableMapOf<Char, Int>()
    val tMap = mutableMapOf<Char, Int>()
    for (i in 0 until a.size) {
        val firstString = a[i]
        val secondString = b[i]
        if(firstString.length != secondString.length){
            result.add(-1)
        }else{
            firstString.forEach { c ->
                if(mMap.containsKey(c)){
                    val currentCount = mMap[c]!!
                    mMap[c] = currentCount+1
                }else{
                    mMap[c] = 1
                }
            }

            secondString.forEach { c ->
                if(tMap.containsKey(c)){
                    val currentCount = mMap[c]!!
                    tMap[c] = currentCount+1
                }else{
                    tMap[c] = 1
                }
            }
            val va = mMap.toSortedMap().values.toList()
            val ta = tMap.toSortedMap().values.toList()
            var maxDiff = 0

            for (i in 0 until va.size) {
                val diff = va[i]-ta[i]
                if(maxDiff > diff){
                    maxDiff = diff
                }
            }

            result.add(maxDiff)

        }


        /**
         * abcccccc -> [a:1, b: 1, c: 6]
         * abaabbbc -> [a:3, b: 4, c: 1]
         */
    }
    return result.toTypedArray()
}

fun main(args: Array<String>) {
    val aCount = readLine()!!.trim().toInt()

    val a = Array<String>(aCount, { "" })
    for (i in 0 until aCount) {
        val aItem = readLine()!!
        a[i] = aItem
    }

    val bCount = readLine()!!.trim().toInt()

    val b = Array<String>(bCount, { "" })
    for (i in 0 until bCount) {
        val bItem = readLine()!!
        b[i] = bItem
    }

    val result = getMinimumDifference(a, b)

    println(result.joinToString("\n"))
}
