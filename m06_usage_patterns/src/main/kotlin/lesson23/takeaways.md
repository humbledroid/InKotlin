# Key Takeaways 🔑

1. Even though coroutines perform well compared to threads, they still make our code more challenging to understand, so be on the lookout for occasions when coroutines aren't actually necessary.
2. An important clue to identify unnecessary coroutines is a call to `async()` that's immediately followed by a call to `await()`, or a call to `launch()` that's immediately followed by a call to `join()`.
3. Another clue to unnecessary coroutines is when a coroutine is built only for the purpose of changing the context. In this case, simply call `withContext()` instead.

Keep in mind that these clues don't _necessarily_ mean that the coroutines should be eliminated - they're just a prompt for you to reconsider. For example:

- If a coroutine is built on an external scope (such as `appScope.launch()`) but you still need to suspend locally until it's complete, calling `join()` immediately is fine.
- Similarly, if you're launching many coroutines into a collection, it's fine to call `joinAll()` on that collection - in that case, the `join()` isn't technically immediate, because other coroutines are being added to the collection first.
