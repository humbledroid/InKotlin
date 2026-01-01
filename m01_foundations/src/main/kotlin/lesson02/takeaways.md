# Key Takeaways 🔑

1. In Kotlin, instead of saying that a coroutine is _paused_, we say that it's **suspended**.
2. Ultimately, what causes a coroutine to suspend is a function named `suspendCoroutineUninterceptedOrReturn()`.
3. A function that calls `suspendCoroutineUninterceptedOrReturn()` - either directly or indirectly - is able to _suspend_ a coroutine, and is therefore known as a **suspending function**.
4. Suspending functions must be declared with the `suspend` modifier.
5. Regular code execution cannot be suspended - _only_ a coroutine can be suspended.
6. Because of that, _only_ a coroutine can execute a suspending function.
7. Like regular named functions, a lambda can also suspend a coroutine, as long as its type is declared with the `suspend` keyword, such as suspend `(String) -> Int`.
8. A coroutine builder _creates_ a coroutine and _submits_ a suspending lambda for that coroutine to run.
