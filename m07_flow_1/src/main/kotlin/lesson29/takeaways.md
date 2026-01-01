# Key Takeaways 🔑

1. Flows have many of the same operators that collections and sequences have.
2. Flow operators are called upon a `Flow` object and return a `Flow` object.
3. The intermediate flow operators are _non-suspending_ functions that accept a _suspending_ lambda. In other words, we can call them from anywhere, and inside their lambdas they can call other suspending functions.
4. Intermediate flow operators act as _both_ a collector _and_ an emitter.
5. The term **upstream** describes a flow or operator that comes _before_ a particular operator, and **downstream** describes those that come _after_.
6. The `transform { }` operator accepts an element and can emit zero or more elements.
7. Flows support a few event-based operators, including `onStart()`, `onCompletion`, `onEmpty()`, and of course, `onEach()`.
8. The `sample {}` flow operator can be used to receive values from a flow less frequently than the emitter is providing them.
