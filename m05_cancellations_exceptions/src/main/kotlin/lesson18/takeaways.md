# Key Takeaways 🔑

1. A big benefit of structured concurrency is that it helps us properly **cancel** our coroutines when the work is no longer needed.
2. We can cancel a coroutine by calling the `cancel()` function on the `Job` object for that coroutine.
3. When a coroutine is cancelled, it _automatically cancels its children_, but its parents and siblings are unaffected. I'll refer to this as the _Coroutine Cancellation Protocol_.
