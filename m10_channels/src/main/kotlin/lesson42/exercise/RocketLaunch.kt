package lesson42.exercise

/* ****************************************************************************

   Button & Booster Co. is growing their operations to include two additional
   launchpads so that they can run more space missions at one time.

   However, they also need to accommodate emergencies, when they suddenly need
   to change a rocket's destination at the last moment. On some occasions, the
   rocket might need to get more fuel in order to reach the new destination.

   Update your solution from the previous exercise so that it includes a total
   of three `Launcher` workers, to represent the new launchpads.

   Then add a new `MissionControl` worker between the `Fueler` and `Launcher`
   workers. `MissionControl` should redirect the rockets to a different random
   destination 25% of the time. If the rocket doesn't have enough fuel to get
   to the new destination, send it back to the `Fueler` worker. Add log
   messages for mission control like these:

   🧑‍🚀 Mission Control needs to retarget #1 from MARS to SATURN.
   🔁 More fuel needed for #1 to retarget from MARS to SATURN.

   Finally, now that there are more launch pads, update the total number of
   missions to ten.

   ************************************************************************* */
