# Key Takeaways 🔑

1. In some cases, it's possible to avoid shared mutable state altogether.
2. A **side effect** is any activity that a function performs other than accepting arguments, running a calculation, and returning a result. For our purposes, we're mainly considering side effects related to _state_. So a function creates a side effect if it changes the value of a variable or property that's _outside_ its own function body.
3. Changing the state of a local variable - that is, a variable declared within its own function body - is not considered a side effect.
4. We can often remove side effects from our functions and coroutines by separating the code that _performs a calculation_ from the code that _updates state_. Instead of updating the state directly, it _returns_ the value that it was going to set.

There are occasions when it might not be feasible to avoid shared mutable state, such as when an application maintains state that a user might update over time.
