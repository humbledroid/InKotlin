package lesson26.content.state04.thirdparty

import kotlin.random.Random

interface ApprovalCallback {
    fun onApproved()
    fun onDeclined()
    fun onError()
}

class ApprovalSystem {
    fun requestApproval(
        customerId: String,
        estimate: Int,
        callback: ApprovalCallback
    ) {
        Thread {
            println($$"Sending approval request to customer $$customerId for $$$estimate.")
            Thread.sleep(estimate.toLong())

            val decision = Random(estimate).nextInt(0, 100)
            when (decision) {
                in 0..60  -> callback.onApproved()
                in 61..90 -> callback.onDeclined()
                else      -> callback.onError()
            }
        }.start()
    }
}
