# Key Takeaways 🔑

1. Exceptions can be caught _within_ the coroutine where they were thrown, in which case the try-catch mechanism works as usual.
2. When an exception is _not_ caught in the coroutine where it was thrown, the _Coroutine Exception Protocol_ is started, where the coroutine and its children are cancelled, and the exception is handed up to its parent. That process repeats until it works up to the root coroutine.
3. A try-catch around a coroutine builder does not catch an exception thrown _within_ the coroutine. It only catches exceptions related to _building_ the coroutine, not those _related_ to running the coroutine.
4. With `async()`, an exception within the coroutine will be rethrown when you call `await()`, but catching it will not prevent the exception from being handed up to its parent.
