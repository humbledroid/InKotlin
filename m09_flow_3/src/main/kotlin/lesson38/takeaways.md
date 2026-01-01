# Key Takeaways 🔑

1. The second kind of hot flow is `StateFlow`, which extends `SharedFlow`, giving it a `value` property that can be used to get its _latest value_ on demand.
2. Use the `MutableStateFlow()` function to create a mutable state flow, being sure to provide a default value.
3. Use the `.asStateFlow()` function to get a read-only version of a mutable state flow.
4. It's possible to get the `value` of a state flow from regular, _non-suspending_ code.
5. It's possible to call `emit()` to emit values into a mutable state flow, but it's more common to simply use the setter of the `value` property.
6. `MutableStateFlow` is thread-safe, but all the rules of _shared mutable state_ still apply - so when updating the value based on its previous value, use the `update()`, `updateAndGet()` or `getAndUpdate()` functions, which handle the update _atomically_.
7. The replay cache of a state flow always has a _single_ value. This means that new subscribers always receive the _latest value_ from the replay cache.
8. Whenever emitting values into a mutable state flow, it'll call `equals()` to compare the previous value with the latest value, and will only emit an update when the value has _changed_. This is known as **equality-based conflation**.
9. Because of equality-based conflation, state flows work best with types that are _not mutated_, such as simple types, immutable data classes, or read-only collection types.
