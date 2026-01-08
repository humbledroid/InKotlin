package learning.coroutines.callbacks

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import learning.coroutines.callbacks.thirdparty.ApprovalCallback
import kotlin.coroutines.resume
import learning.coroutines.callbacks.thirdparty.ApprovalSystem as BaseApprovalSystem


class ApprovalSystem(private val approvalSystem: BaseApprovalSystem = BaseApprovalSystem()) {
    suspend fun requestApproval(customerId: String, estimate: Int) = suspendCancellableCoroutine { continuation ->
        approvalSystem.requestApproval(customerId, estimate, object: ApprovalCallback {
            override fun onApproved() {
                continuation.resume(ApprovalStatus.APPROVED)
            }

            override fun onDeclined() {
                continuation.resume(ApprovalStatus.DECLINED)
            }

            override fun onError() {
                continuation.resume(ApprovalStatus.ERROR)
            }

        })
    }

}


enum class ApprovalStatus {
    APPROVED, DECLINED, ERROR
}



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
                val status = system.requestApproval(customerId, estimate)
                when (status) {
                    ApprovalStatus.APPROVED -> println("Approved!")
                    ApprovalStatus.DECLINED -> println("Declined!")
                    ApprovalStatus.ERROR -> println("Error!")
                }
            }
        }
    }

    println("Received responses from all customers.")
}
