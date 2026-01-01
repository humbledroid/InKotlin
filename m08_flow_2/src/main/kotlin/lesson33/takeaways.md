# Key Takeaways 🔑

1. When using `flowOn()` to change dispatchers, by default there will be a buffer with a capacity of 64 values sitting between the upstream and downstream flows.
2. This default value can be customized by setting the system property `kotlinx.coroutines.channels.defaultBuffer`.
3. The `buffer()` operator also splits a flow to run across multiple coroutines. Just pass it the capacity that you want the buffer to have.
4. You can switch dispatchers _and_ configure the buffer size by keeping `flowOn()` and `buffer()` adjacent to one another in the operator chain. The order doesn't matter, but they must be right next to each other.
5. The configuration of certain flow operators - including `flowOn()` and `buffer()` - are _merged together_ when they're adjacent to each other. This is called **Operator Fusion**.
6. Instead of buffering values, we can **conflate** the values - that is, simply replace the older value with the new value. To do this, we can pass the second argument of the `buffer()` operator, or use the `conflate()` operator as a shortcut. To favor the oldest value instead, pass a second argument of `DROP_LATEST` to the `buffer()` operator.
7. To prevent values from being emitted downstream until a new value hasn't been emitted for a while, use the `debounce()` operator.
8. To _interrupt_ and _replace_ the task of a running collector whenever a new value arrives, use the `collectLatest()` collector. The previous work will be abandoned, and the collector will start processing the new value.
9. To get the same behavior for a `map()` or `transform()` operation, use `mapLatest()` or `transformLatest()`.
