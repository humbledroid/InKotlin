# Key Takeaways 🔑

1. By using multiple threads, a process can do _more than one thing at the same time_.
2. The **dispatcher** determines which thread a coroutine runs on.
3. When calling a coroutine builder, we can pass a dispatcher as its first argument.
4. `Dispatchers.Default` is a great choice for most coroutines that do _CPU-bound work_, because its thread pool is
   sized according to the number of CPU cores.
5. `Dispatchers.IO` is a great choice for _blocking network calls and disk operations_, due to its large thread pool
   with up to 64 threads on most machines.
