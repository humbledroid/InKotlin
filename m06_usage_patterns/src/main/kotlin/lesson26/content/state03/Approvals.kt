package lesson26.content.state03

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import lesson26.content.state03.adapter.ApprovalException
import lesson26.content.state03.adapter.ApprovalState
import lesson26.content.state03.adapter.ApprovalSystem

data class ApprovalRequest(val customerId: String, val estimate: Int)

val requests = listOf(
    ApprovalRequest(customerId = "795a91da", estimate = 824),
    ApprovalRequest(customerId = "3d0df54d", estimate = 950),
    ApprovalRequest(customerId = "2677c99f", estimate = 972),
    ApprovalRequest(customerId = "03dee696", estimate = 1022),
    ApprovalRequest(customerId = "09a8c736", estimate = 1057)
)

suspend fun main() {
    val system = ApprovalSystem()

    coroutineScope {
        requests.forEach { (customerId, estimate) ->
            launch {
                try {
                    val result = system.requestApproval(customerId, estimate)
                    when (result) {
                        ApprovalState.APPROVED -> println("✅ Customer $customerId approved the repairs!")
                        ApprovalState.DECLINED -> println("⛔ Customer $customerId declined repairs.")
                    }
                } catch (_: ApprovalException) {
                    println("💥 Customer $customerId could not be contacted due to a system error.")
                }
            }
        }
    }

    println("Received responses from all customers.")
}
