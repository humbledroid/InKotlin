package neetcode

class ProductOfArrayExceptSelf {
    fun productExceptSelf(nums: IntArray): IntArray {
        // failed attempt
//        val totalProduct = nums.fold(1) { acc, i -> acc * i }
//        println(totalProduct)
//        return buildList {
//            nums.forEach { currentNum ->
//                if(currentNum == 0) add(totalProduct) else add(totalProduct.div(currentNum).toInt())
//            }
//        }.toIntArray()
//        val result = IntArray(nums.size) { 0 }
//        for (i in nums.indices) {
//            var product = 1
//            for (j in nums.indices) {
//                if (i == j) {
//                    continue
//                }
//                product *= nums[j]
//            }
//            result[i] = product
//        }
//
//        return result

//        val countOfZero = nums.count { it == 0 }
          // val result = IntArray(nums.size) { 0 }
//        if (countOfZero > 1) return result
//
//        val totalNonZeroProduct = nums.filter { it != 0 }.fold(1) { acc, i -> acc * i }
//
//        if (countOfZero == 1) {
//            for (i in nums.indices) {
//                if (nums[i] == 0) {
//                    result[i] = totalNonZeroProduct
//                } else {
//                    result[i] = 0
//                }
//            }
//        } else {
//            for (i in nums.indices) {
//                if (nums[i] == 0) {
//                    result[i] = totalNonZeroProduct
//                } else {
//                    result[i] = totalNonZeroProduct / nums[i]
//                }
//            }
//        }
//

        val result = IntArray(nums.size)
        val prefix = IntArray(nums.size)
        val suffix = IntArray(nums.size)

        prefix[0] = 1
        suffix[nums.size - 1] = 1

        for (i in 1 until nums.size) {
            prefix[i] = prefix[i-1] * nums[i-1]
        }

        for (i in nums.size - 2 downTo 0) {
            suffix[i] = suffix[i+1] * nums[i+1]
        }

        for (i in nums.indices) {
            result[i] = prefix[i] * suffix[i]
        }

        return result
    }
}

fun main(args: Array<String>) {
    val productOfArrayExceptSelf = ProductOfArrayExceptSelf()
    println(productOfArrayExceptSelf.productExceptSelf(intArrayOf(-1, 0, 1, 2, 3)).joinToString(","))
}