# Key Takeaways 🔑

1. A **mutex** allows us to synchronize access to critical sections of our code without requiring the coroutine to
   change dispatchers. Mutex is short for **Mutual Exclusion**.
2. A mutex is created by calling the factory function, `Mutex()`.
3. To lock a critical section with a mutex, call its `.withLock {}` function, wrapping the braces around the critical
   section.
4. Generally, a mutex has a similar code footprint as the `limitedParallelism()` solution we looked at in the previous
   lesson.
5. It's possible to use multiple mutex objects, with one nested within the `withLock {}` block of the other. If you do
   this, be careful to avoid _deadlocks_ - always nest the locks in the same order.
