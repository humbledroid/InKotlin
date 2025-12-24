package algos

class MergeSort {

    fun sortArray(array: IntArray): IntArray{
        if(array.size<2){
            return array
        }
        val mid = array.size / 2

        val left = IntArray(mid)
        val right = IntArray(array.size-mid)

        for (i in 0 until mid){
            left[i] = array[i]
        }
        for(i in mid until array.size){
            right[i-mid] = array[i]
        }
        sortArray(left)
        sortArray(right)
        merge(array, left, right)
        return array
    }

    fun merge(original: IntArray, left: IntArray, right: IntArray, ) {
        var k = 0
        var i = 0
        var j = 0
        val nL = left.size
        val nR = right.size
        while(i < nL && j < nR){
            if(left[i] <= right[j]){
                original[k] = left[i]
                i++
            }else{
                original[k] = right[j]
                j++
            }
            k++
        }

        while (i<nL){
            original[k] = left[i]
            i++
            k++
        }

        while (j<nR){
            original[k] = right[j]
            j++
            k++
        }
    }
}


fun main(){
    val mergeSort = MergeSort()
    mergeSort.sortArray(intArrayOf(4,6,3,1,99,11,2,5,101)).forEach {
        println(it)
    }
}