package lesson22.content.state04

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/* ****************************************************************************

   See src/main/resources/META-INF/services/readme.txt for instructions about
   how to register these handlers with the Service Loader.

   ************************************************************************* */

class MyCustomHandler : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println("⚠️⚠️⚠️ - MyCustomHandler")
    }
}

class MyCustomHandler2 : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println("🔥🔥🔥 - MyCustomHandler2")
    }
}
