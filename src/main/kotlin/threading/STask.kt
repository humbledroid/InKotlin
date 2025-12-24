package threading

enum class Sport { HIKE, RUN, TOURING_BICYCLE, E_TOURING_BICYCLE }
data class Summary(val sport: Sport, val distance: Int)

val cookie = "connect.sid=s%3A5tZo5g4m50m_qPwLXUwfJcCKigv_54Su.hqvBtb92IVB8113h04yYxwbK8Kur%2F9wFIGFXv9hbu30\n"


data class Item(val order:Int, val da: String)

fun main(){
    val l = listOf(
        Item(7, "U"),
        Item(5, "C"),
        Item(3, "U"),
        Item(12, "J"),
        Item(24, "C"),
        Item(9, "O"),
        Item(2, "N"),
        Item(15, "P"),
        Item(20, "U"),
        Item(15, "X"),
        Item(23, "V"),
        Item(27, "S"),
        Item(13, "J"),
        Item(10, "K"),
        Item(29, "O"),
        Item(19, "J"),
        Item(22, "X"),
        Item(11, "N"),
        Item(17, "Z"),
        Item(18, "B"),
        Item(25, "W"),
        Item(14, "C"),
        Item(26,"6"),
        Item(21,"7"),
        Item(4,"Z"),
        Item(8,"X"),
        Item(1,"O"),
        Item(6,"B"),
        Item(28,"Z"),
    ).sortedBy { it.order }.forEach {
        print(it.da)
    }
}