package neetcode

class BinarySearch {
    fun search(nums: IntArray, target: Int): Int {
        var start = 0
        var end = nums.size - 1

        while (start <= end) {
            val mid = start + (end - start) / 2
            if(target == nums[mid]) {
                return mid
            }
            if(target > nums[mid]) {
                start = mid + 1
            }else{
                end = mid - 1
            }
        }
        return -1
    }
}

fun  main(){
    val binarySearch = BinarySearch()
    println(binarySearch.search(intArrayOf(-1,0,2,4,6,8), 4))
}