# Key Takeaways 🔑

1. Flows represent _multiple_ values, much like sequences or collections.
2. A simple flow can be built with the `flowOf()` function.
3. The terminal operator of a flow is called a **collector**.
4. The simplest collector is a suspending function named `collect()`, and it's analogous to the `forEach()` of a sequence.
5. Like a sequence, the flows presented in this lesson can be called by more than one collector.
6. `flowOf()` and `flow {}` are _not_ suspending functions, so flows can be created in regular, non-suspending functions. However, they can only be _collected_ from within a _suspending_ function.
7. An active flow comprises at least _two_ parts - an emitter and a collector. The execution path bounces back and forth between the emitter and collector.
8. So far, each flow we've seen has run on a single coroutine. Multi-coroutine flows will be covered in the next unit of this course.
