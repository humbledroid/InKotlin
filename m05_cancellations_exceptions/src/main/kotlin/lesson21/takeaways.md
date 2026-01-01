# Key Takeaways 🔑

1. A **Supervisor** prevents an exception from being handed any further up the coroutine hierarchy.
2. An exception thrown in any one of a supervisor's children will not affect any of its other children, nor will it affect any parent of the supervisor.
3. The `supervisorScope { }` function is an easy way to place a supervisor within a coroutine hierarchy. It works much like the `coroutineScope { }` function, but adds supervisor functionality to it.
4. `SupervisorJob()` is a context element that can be used with `CoroutineScope()` to give supervisor functionality to that scope. This is often used by frameworks that provide their own coroutine scopes, such as the `viewModelScope` in Compose apps.
5. Don't pass `SupervisorJob()` to a coroutine builder. The child coroutines will be attached to the `SupervisorJob` rather than the inherited job, which severs the natural parent-child relationship.
