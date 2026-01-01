package lesson15.content.state01

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    GlobalScope.launch { println("🔧 Structural integrity approved.") }
    GlobalScope.launch { println("🔌 Electrical safety cleared.") }
    GlobalScope.launch { println("🧯 Fire safety check passed.") }
}
