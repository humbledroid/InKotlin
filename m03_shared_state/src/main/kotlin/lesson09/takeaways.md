# Key Takeaways 🔑

1. **Shared mutable state** refers to variables and properties that can be accessed and updated by _multiple coroutines_ at the same time.
2. A **race condition** occurs when coroutines running in parallel try to update the _same shared variable or property_.
3. When our code runs _sequentially_ - one line at a time - then it's safe to read and update our state, but when our code runs in _parallel_, there's a risk of race conditions.

The rest of this unit is focused on how we can manage this risk.
