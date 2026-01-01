# Key Takeaways 🔑

1. When running your code with the debugger, open the _Layout Settings_ button at the top right of the debug tool window, and click _Coroutines_.
2. The coroutine debug panel shows coroutine names, IDs, states, the thread, the dispatcher, and the job. It also expands to show the stack trace and child coroutines.
3. It shows all _active_ coroutines - those that have been created, those that are running, and those that are suspended. It does _not_ show a parent coroutine who has finished its own work, and is simply waiting for its children to complete.
4. The coroutines in the debug panel can be shown grouped by _dispatcher_ rather than job, but it requires editing the IDE's registry, and disabling a flag named `coroutine.panel.show.jobs.hierarchy`.
5. The coroutine debug panel is most helpful when your coroutines are created in multiple places across your project, or when they're created behind the scenes with functions like `produceIn()`.
6. You can use the plus operator to add a _context element_ to a _scope_.
7. To keep an eye on the coroutine context while using step debugging, add `currentCoroutineContext()` to the _Evaluate Expression_ box, and optionally add it as an expression to _watch_.
8. To focus the stack trace on only the code from the current project, Right-click in the stack trace area, and choose _Hide Frames from Libraries_.
9. When paused on a breakpoint, the step debugger will _stay pinned to the current coroutine_ as long as you move forward with the step buttons.
10. However, when pressing the _Resume Program_ button, it's possible that the next breakpoint will be hit by a _different_ coroutine.
