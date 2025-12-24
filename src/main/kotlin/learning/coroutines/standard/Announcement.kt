package learning.coroutines.standard

fun main() {
    for (announcement in countDown()){
        println(announcement)
    }
}

fun countDown() = sequence {
    for(time in 60 downTo 0){
        if(time == 0) yield("BLAST OFF! 💥🚀")
        else if(time < 5) yield(time.toString() )
        else if(time.rem(5) == 0) yield("T-$time seconds to launch")
        else yield("...")
    }
}