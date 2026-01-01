# Key Takeaways 🔑

1. When code is executed on a different schedule from the code that called it, we say it runs **asynchronously**.
2. When code runs on a single thread, there's no risk of race conditions.
3. When we force coroutines running asynchronously to run part of their instructions on the same schedule as other coroutines, it's known as **synchronizing**.
4. The point at which the tasks are synchronized is called a **synchronization point**.
5. When we mitigate race conditions by forcing critical sections to run on a single thread, it's referred to as **thread confinement**.
6. `newSingleThreadContext()` creates a dispatcher that has a thread pool with a single thread, but it requires manually closing the thread pool, and isn't available when your Kotlin code is targeting JavaScript or WebAssembly.
7. We can use the `withContext()` function to run part of our coroutine's instructions using a particular context. This is frequently used to change which dispatcher the coroutine is using for some of its instructions.
8. We can call a function named `limitedParallelism()` on a dispatcher in order to get a view of that dispatcher that limits the number of threads that will run at the same time. This approach is generally preferred over `newSingleThreadContext()`.
