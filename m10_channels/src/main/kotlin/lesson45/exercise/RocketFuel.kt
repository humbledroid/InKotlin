package lesson45.exercise

/* ****************************************************************************

   In the exercise for the last lesson, you might have included the call to
   `currentCoroutineContext().cancelChildren()` to shut down the process
   gracefully after all seven rockets were launched.

   Remove that call, if you included it. Then, close the channels properly
   after all seven rockets are launched. When you're done, the process should
   still terminate successfully with exit code 0. Depending on how you wrote
   the code that generates the fuel, you might also have to cancel a coroutine
   manually.

   ************************************************************************* */
