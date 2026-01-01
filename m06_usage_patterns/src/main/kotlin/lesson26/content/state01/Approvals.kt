package lesson26.content.state01

import lesson26.content.state01.thirdparty.ApprovalCallback
import lesson26.content.state01.thirdparty.ApprovalSystem

data class ApprovalRequest(val customerId: String, val estimate: Int)

val requests = listOf(
    ApprovalRequest(customerId = "795a91da", estimate = 824),
    ApprovalRequest(customerId = "3d0df54d", estimate = 950),
    ApprovalRequest(customerId = "2677c99f", estimate = 972),
    ApprovalRequest(customerId = "03dee696", estimate = 1022),
    ApprovalRequest(customerId = "09a8c736", estimate = 1057)
)

fun main() {
    val system = ApprovalSystem()

    requests.forEach { (customerId, estimate) ->
        system.requestApproval(customerId, estimate, object : ApprovalCallback {
            override fun onApproved() {
                println("✅ Customer $customerId approved the repairs!")
            }

            override fun onDeclined() {
                println("⛔ Customer $customerId declined repairs.")
            }

            override fun onError() {
                println("💥 Customer $customerId could not be contacted due to a system error.")
            }
        })
    }

    println("Received responses from all customers.")
}
