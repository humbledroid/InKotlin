package algos

class InsertionSort {
    fun insertionSort(arr: IntArray) {
        for (i in arr.indices){
            var j = i
            while(j>0 && arr[j] > arr[j-1]){
                swap(arr,j,j-1)
                j--
            }
        }
    }

    private fun swap(arr: IntArray, i: Int, j: Int) {
        val tmp = arr[i]
        arr[i] = arr[j]
        arr[j] = tmp
    }
}

fun main() {
    val a = intArrayOf(10,4,2,5,7,1,3,8,9)
    val insertionSort = InsertionSort()
    insertionSort.insertionSort(a)

    a.forEach(::println)
}