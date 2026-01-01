package lesson43.exercise

/* ****************************************************************************

   Button & Booster Co. is responding to emergency calls! Instead of a 25%
   chance of needing to redirect a rocket to a random destination, there's now
   a signal that the planets can send when there's an emergency.

   Update your solution for the previous exercise with the changes below.

   Create a new channel for emergencies at particular `Destination`s.

   Then, in your `main()` function, launch a coroutine that repeats five times.
   On each iteration, delay for 2 seconds, then pick a random destination where
   an emergency requires a rocket mission. Send that destination through the
   new emergency channel.

   Finally, update `MissionControl` to use a select expression, so that it can
   listen to both its current inbound channel and the new emergency channel.

   - When a new emergency arrives, store it as the active emergency.
   - When a rocket arrives, if there's an active emergency, redirect that
     rocket to go to that new destination and immediately clear the active
     emergency. Otherwise, just send it to its original destination.
   - If a new emergency arrives while there's already an existing emergency,
     the new emergency replaces the previous emergency.
   - As before, make sure there's enough fuel for a redirected rocket to get
     to the new destination.

   ************************************************************************* */
