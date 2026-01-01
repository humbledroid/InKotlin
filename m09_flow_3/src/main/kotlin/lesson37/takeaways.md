# Key Takeaways 🔑

1. `SharedFlow` is one kind of hot flow.
2. There are types for both _mutable_ and _read-only_ shared flows.
3. To create a flow into which you can emit values, create a mutable shared flow with a factory function named `MutableSharedFlow()`.
4. With a shared flow, each active collector receives the _same_ new values as they're emitted. That is, all the collectors _share_ the same flow.
5. _Collectors_ of a hot flow are usually referred to as **subscribers**.
6. Hot flows don't complete, so subscribers will _suspend_ on their `collect()` call until they're cancelled.
7. Code from multiple threads can emit values into the same mutable shared flow.
8. Any code with access to a mutable shared flow can emit values into it - aim to limit its visibility to only the code that needs to do that.
9. It's common practice to keep a mutable shared flow _private_ within a class, named with a leading underscore; and then introduce a read-only _public_ shared flow, named without the leading underscore.
10. Use the `.asSharedFlow()` function to convert a `MutableSharedFlow` into a `ReadonlySharedFlow` that can't be cast back to the mutable type in the calling code.
11. By default, a shared flow _suspends_, waiting on _all_ subscribers to collect a value before moving onto the next value. This means one slow subscriber can cause delays for other subscribers.
12. To help mitigate against slow subscribers, specify the buffer size of a mutable shared flow by passing an argument to the `extraBufferCapacity` parameter or `replay` parameter.
13. Use the `onBufferOverflow` parameter to indicate how a mutable shared flow should operate when its buffer is full. It uses the same options as the `buffer()` operator that we saw in the last unit.
14. Shared flows have a **replay cache**, which stores recently-emitted values, so that new subscribers can get caught up. To specify the number of elements to store in that cache, use the `replay` parameter of the `MutableSharedFlow()` function.
15. A shared flow instance itself can't be cancelled, but you can cancel the _coroutines that are emitting into it_, or the _coroutines that are collecting from it_. A subscriber is unsubscribed by cancelling the coroutine that's running the call to `collect()`.
