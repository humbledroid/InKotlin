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
        val result = IntArray(nums.size) { 0 }
        for (i in nums.indices) {
            var product = 1
            for (j in nums.indices) {
                if (i == j) {
                    continue
                }
                product *= nums[j]
            }
            result[i] = product
        }

        return result
    }
}

fun main(args: Array<String>) {
    val productOfArrayExceptSelf = ProductOfArrayExceptSelf()
    println(productOfArrayExceptSelf.productExceptSelf(intArrayOf(0,0)).joinToString(","))
}