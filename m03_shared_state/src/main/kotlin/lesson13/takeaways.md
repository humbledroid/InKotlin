# Key Takeaways 🔑

1. **Atomics** are an easy way to avoid race conditions for simple values like counters and scores.
2. Numeric atomics, like `AtomicLong`, provide functions to increase or decrease their values safely, without having to explicitly lock critical sections with `withContext()` or `withLock()`, as we did with confinement and mutexes. This includes operator functions such as `plusAssign()`, which allow us to use an operator syntax like `+=`.
3. There are some additional functions on atomics, such as `load()` to read the value and `store()` to stomp out the existing value.
4. Atomics are generally useful for updates of simple values. Any critical sections that perform compound operations will need confinement or a mutex.
