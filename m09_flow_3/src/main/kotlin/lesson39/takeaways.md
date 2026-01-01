# Key Takeaways 🔑

1. It's possible to convert a cold flow into a `SharedFlow` or `StateFlow`.
2. To convert a cold flow into a `SharedFlow`, use the `shareIn()` function, providing a `CoroutineScope` and starting strategy.
3. A hot flow created with `shareIn()` will run the upstream cold flow on a _new coroutine_ that'll run on the provided scope.
4. The `Eagerly` starting strategy starts running the upstream cold flow _immediately_ when `shareIn()` is called, and it continues running, regardless of how many subscribers are attached.
5. The `Lazily` starting strategy waits to run the upstream cold flow until the _first subscriber arrives_, and it continues to run regardless of how many subscribers are attached.
6. The `WhileSubscribed()` strategy starts like `Lazily`, but also _stops_ the flow whenever the _last subscriber has unsubscribed_. If a new subscriber arrives after that, it'll start a fresh copy of the upstream flow.
7. The size of the replay cache can be configured via the third argument to `shareIn()`.
8. The replay cache is shared between the runs of the upstream flow, but you can use the `replayExpirationMillis` parameter of the `WhileSubscribed()` function to configure how long it lasts.
9. To convert a cold flow into a `StateFlow`, use the `stateIn()` function. Like `shareIn()`, it takes a coroutine scope and starting strategy, but it also requires the initial value of the state.
