1.I USE AI TO HELP ME FOR GUIDE A CORRECT JAVA STYLE
2.DETAILING TASK LOGIC:
  PROMPT : spawnObjects 
              public void spawnObjects()
              Spawns new objects (asteroids, enemies, and power-ups) at random positions. Uses this.random to make EXACTLY 6 calls to random.nextInt() and 1 random.nextBoolean.
              Random calls should be in the following order:
              1. Check if an asteroid should spawn (random.nextInt(100) < spawnRate)
              2. If spawning an asteroid, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
              3. Check if an enemy should spawn (random.nextInt(100) < spawnRate * ENEMY_SPAWN_RATE)
              4. If spawning an enemy, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
              5. Check if a power-up should spawn (random.nextInt(100) < spawnRate * POWER_UP_SPAWN_RATE)
              6. If spawning a power-up, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
              7. If spawning a power-up, spawn a ShieldPowerUp if random.nextBoolean(), else a HealthPowerUp.
              
              Failure to match random calls correctly will result in failed tests.
              
              Objects spawn at y = 0 (top of the screen).
              Objects may not spawn if there is a ship at the intended spawn location.
              This should NOT impact calls to random.
              
              can you explain to me the logic of this task in detail because i always get this error:
              
              testSpawning(game.GameModelTest): There should be an Asteroid at 8,0

  RESULT:THE GOAL
              The spawnObjects() method is responsible for spawning Asteroids, Enemies, and Power-Ups at the top of the screen (y = 0) in random horizontal (x) positions.
              
              You must:
              
              Use this.random to generate randomness.
              
              Make exactly 6 random.nextInt() calls and 1 random.nextBoolean() call, in a specific order.
              
              Check if there is a ship at the intended spawn location; if yes, don’t spawn, but you still must call random as if you were spawning it.
              
              📏 ORDER OF RANDOM CALLS (very important)
              Each call to random must happen exactly in this order:
              
              Step	Purpose	Call	Description
              1	Decide if asteroid should spawn	random.nextInt(100)	If < spawnRate, spawn it
              2	If spawning asteroid, pick x	random.nextInt(GAME_WIDTH)	Asteroid will spawn at (x, 0)
              3	Decide if enemy should spawn	random.nextInt(100)	If < spawnRate * ENEMY_SPAWN_RATE, spawn it
              4	If spawning enemy, pick x	random.nextInt(GAME_WIDTH)	Enemy will spawn at (x, 0)
              5	Decide if power-up should spawn	random.nextInt(100)	If < spawnRate * POWER_UP_SPAWN_RATE, spawn it
              6	If spawning power-up, pick x	random.nextInt(GAME_WIDTH)	Power-up will spawn at (x, 0)
              7	If power-up spawning, decide type	random.nextBoolean()	true → ShieldPowerUp, false → HealthPowerUp
              🚨 COMMON MISTAKES
              ❌ Mistake 1: Skipping a random call
              Even if you decide not to spawn (e.g., there's a ship there), you must still make the random calls in the correct order to not break test expectations.
              
              ❌ Mistake 2: Spawning out of order
              If you call random.nextBoolean() before you've done all 6 nextInt() calls, the test will fail.
              
              ❌ Mistake 3: Not checking for ship presence properly
              If the spawn location is already occupied by a ship, do not spawn, but you must have already made the correct random calls before.
              
              🧪 Your Test Failure
              There should be an Asteroid at 8,0
              
              This means:
              
              The test’s random object generated a spawn at (8, 0) for an Asteroid.
              
              But your code did not spawn it there.
              
              Most likely, your code:
              
              Didn't check random calls correctly.
              
              Or skipped the spawn due to a bug.
              
              Or didn’t place it at the exact location (8,0).


