# Key Takeaways 🔑

1. When coroutines need to be tied to the lifecycle of an object, that object should have a dedicated `CoroutineScope`, and the scope should be cancelled whenever the object is at the end of its lifecycle.
2. Many coroutine-aware frameworks provide their own `CoroutineScope` that's tied to the lifecycle of the objects that they manage. For example, _Compose_ provides a `viewModelScope` that will cancel whenever the `ViewModel` is cleared.
3. When you need coroutines to outlast the object that creates them, use an external scope, such as an application-wide scope.
4. For background work that needs to run for the entire duration of your application, use an application-wide scope.
