# Key Takeaways 🔑

1. The same code that used behind the coroutines debugging panel is accessible to us programmatically through the `kotlinx.coroutines.debug` library, which is versioned in tandem with the main coroutines library.
2. Typically, this library is added only to the **test** source set (i.e., in Gradle with `testImplementation()`)
3. To start probing your coroutines, call `DebugProbes.install()`.
4. `DebugProbes.dumpCoroutines()` will print the state of active coroutines in the console output. Instead of sending directly to the console, assign `DebugProbes.dumpCoroutinesInfo()` to a variable to get a list of `CoroutineInfo` objects describing our active coroutines.
5. As with the debug panel, a coroutine is not considered active when it's completed its main work and is only waiting for its children to finish.
6. When we call `DebugProbes.install()`, we're installing its Java agent _dynamically_, which is a deprecated ability that produces a warning (see JEP 451). Alternatively, you could load the agent by adding the `-javaagent` command line option, pointing to the **core** coroutines library (not the debug library). When debugging your app in IntelliJ IDEA, it adds that agent to the command line automatically.
7. The debugging library is often used to dump the active coroutines when a test times out. It includes a plugin for JUnit 4 and an extension for JUnit 5.
8. In JUnit 5, use the `@CoroutinesTimeout` annotation to get the timeout feature, and be sure to use `runBlocking` rather than `runTest`, since it uses real time rather than virtual time. 
9. BlockHound is a library that detects when a thread is blocked - that is, code that holds onto the thread while waiting for something - such as `Thread.sleep()`.
10. `Dispatchers.IO` is designed to be used with blocking code, but avoid blocking on `Dispatchers.Default` and `Dispatchers.Main`. BlockHound will call out when you do so.
11. BlockHound requires the `-XX:+AllowRedefinitionToAddDeleteMethods` JVM argument. If you plan to run your tests with Gradle, be sure to add that argument in your build file. If you're running tests in the IDE, make sure the test configuration has that argument. 
12. The BlockHound agent can be started with the coroutines integration by calling `BlockHound.install(CoroutinesBlockHoundIntegration())`.
