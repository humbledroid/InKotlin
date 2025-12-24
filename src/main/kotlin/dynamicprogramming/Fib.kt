import kotlin.system.measureTimeMillis

fun main(){
//    val memo = mutableMapOf<Long, Long>()
//    val time = measureTimeMillis {
//        val value = fib(40, memo)
//        println("Value is $value")
//    }
    val list = mutableListOf(0L,1L)
    val n = 150
    for (i in 0..n){
        if(i>1){
            list.add(i, list[(i-1)]+list[(i-2)])
        }
    }

    println(list[n])

    //print(time/1000)
}

fun fib(n: Long, memo: MutableMap<Long, Long>): Long {
    if(n == 0L){
        return 0
    }else if(n == 1L){
        return 1
    }
    if(memo.containsKey(n)){
        return memo.getValue(n)
    }
    val value = fib(n-1, memo) + fib(n-2, memo)
    memo[n] = value
    return value
}

