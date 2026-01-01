package lesson26.content.state04.adapter

import kotlinx.coroutines.suspendCancellableCoroutine
import lesson26.content.state04.thirdparty.ApprovalCallback
import kotlin.coroutines.resume
import lesson26.content.state04.thirdparty.ApprovalSystem as BaseApprovalSystem

class ApprovalSystem(private val system: BaseApprovalSystem = BaseApprovalSystem()) {
    suspend fun requestApproval(
        customerId: String,
        estimate: Int
    ) = suspendCancellableCoroutine { continuation ->
        system.requestApproval(customerId, estimate, object : ApprovalCallback {
            override fun onApproved() = continuation.resume(ApprovalState.APPROVED)
            override fun onDeclined() = continuation.resume(ApprovalState.DECLINED)
            override fun onError() = continuation.resume(ApprovalState.ERROR)
        })
    }
}

enum class ApprovalState { APPROVED, DECLINED, ERROR }
