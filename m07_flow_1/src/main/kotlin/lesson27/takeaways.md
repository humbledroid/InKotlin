# Key Takeaways 🔑

1. Flows have a lot in common with collections and sequences - especially sequences.
2. **Collection operators** allow us to manipulate a collection by transforming and filtering its contents, producing a new collection at each step.
3. **Sequence operators** have the same result as their collection operator counterparts, but they work by processing each item in the sequence before moving onto the next item.
4. You can build out a sequence, and then run it multiple times.
5. **Intermediate operators** are those that are called upon a sequence and return a sequence.
6. A **terminal operator** is called upon a sequence but does _not_ return a sequence. On a sequence, the terminal operator is what actually causes the sequence to run.
7. The `sequence {}` builder function allows us to generate values dynamically. This can include looping over a range, or looping forever.
8. The coroutine-based sequence builder function is not allowed to call just any suspending function. It can only call members of its receiver object - `yield()` and `yieldAll()`. Functions like this are said to have **restricted suspension**.
