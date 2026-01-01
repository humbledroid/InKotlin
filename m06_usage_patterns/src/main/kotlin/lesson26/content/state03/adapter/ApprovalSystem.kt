package lesson26.content.state03.adapter

import kotlinx.coroutines.suspendCancellableCoroutine
import lesson26.content.state03.thirdparty.ApprovalCallback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import lesson26.content.state03.thirdparty.ApprovalSystem as BaseApprovalSystem

class ApprovalSystem(private val system: BaseApprovalSystem = BaseApprovalSystem()) {
    suspend fun requestApproval(
        customerId: String,
        estimate: Int
    ) = suspendCancellableCoroutine { continuation ->
        system.requestApproval(customerId, estimate, object : ApprovalCallback {
            override fun onApproved() = continuation.resume(ApprovalState.APPROVED)
            override fun onDeclined() = continuation.resume(ApprovalState.DECLINED)
            override fun onError() = continuation.resumeWithException(ApprovalException())
        })
    }
}

enum class ApprovalState { APPROVED, DECLINED }
class ApprovalException : Exception()
