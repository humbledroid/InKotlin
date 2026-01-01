# Key Takeaways 🔑

1. When extracting common code out of coroutines, aim to extract it to a _suspending function_ rather than a regular function that wraps a coroutine builder.
2. When a suspending function needs to create a new coroutine, use the `coroutineScope {}` or `supervisorScope {}` functions rather than passing the `CoroutineScope` to it.
3. A longer-lived object, such as an application, can inject its `CoroutineScope` into a shorter-lived object, such as a `ViewModel`.
4. In real-world projects, you should almost always inject a dispatcher into an object that needs it. This will make life much easier when testing your coroutine-based code. You can either inject _individual_ dispatchers as needed, or create a type that groups _all_ dispatchers.
