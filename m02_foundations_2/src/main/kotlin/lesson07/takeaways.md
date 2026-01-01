# Key Takeaways 🔑

1. A **coroutine context** is a bag of objects that pertain to the coroutine.
2. An object inside the context is known as a **context element**.
3. The context follows the coroutine around, and is accessible from any suspending function via a property named `coroutineContext`.
4. To add elements to a context, pass them as the first argument to a coroutine builder, separated with the `+` operator.
5. A nested coroutine _inherits_ context elements from its outer coroutine, but can choose to _override_ some of them by providing its own context elements.
6. Context elements that we've seen so far include dispatchers, jobs, and names.
