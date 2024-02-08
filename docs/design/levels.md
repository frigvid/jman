> :warning: **This is subject to change.** Depending on both my mood, and the project's maturation.

As part of the task's requirements, levels must be saved in a plaintext file. We're allowed to keep the format as we choose, but I think going basic is probably for the best.

The level files will be plaintext, with a file extension of `.level`, because that's neat. There's no real reason for it.

Level structure data:

- `W` = Walls.
- `O` = Open space.
- `P` = Player's starting position.
- `E` = Exit.
  - This is multi-functional. Or should be. When the player hasn't collected all the points, it's a teleporter linked between each other. And if they have, either point serves as an exit to the next-level screen.
- `S` = Small dot/point.
- `B` = Big dot/power-up.
- `1` = Ghost spawn-point 1.
- `2` = Ghost spawn-point 2.

Then, you define the map something like the below. It both is, and isn't, human-readable. The text kind of bleeds into one another, but that's fine enough. A computer won't care.

```level
W W W W W W W W W W W W W W W W W W W
W S S S S S S S S W S S S S S S S S W
W B W W S W W W W W W W W W S W W B W
W S S S S S S S S S S S S S S S S S W
W S W W S W S W W W W W S W S W W S W
W S S S S W S S S W S S S W S S S S W
W W W W S W W W O W O W W W S W W W W
O O O W S W O O O O O O O W S W O O O
W W W W S W O W O W O W O W S W W W W
E O O O S O O W 1 O 2 W O O S O O O E
W W W W S W O W W W W W O W S W W W W
O O O W S W O O O O O O O W S W O O O
W W W W S W O W W W W W O W S W W W W
W S S S S S S S S W S S S S S S S S W
W B W W S W W W W W W W W W S W W B W
W S S W S S S S S P S S S S S W S S W
W W S W S W S W W W W W S W S W S W W
W S S S S W S S S W S S S W S S S S W
W S W W W W W W S W S W W W W W W S W
W S S S S S S S S S S S S S S S S S W
W W W W W W W W W W W W W W W W W W W
```
