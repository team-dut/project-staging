# Introduction

Team Dut presents: The OOP project!

Our take to conquer this course is to (re)implement the game. After many researches to
optimize our workload since we only have around 13 weeks to completely learn and code
the game, we decided to *reinvent the wheel* - create Pacman from scratch.

# Where to install?

- Fetch the zip from here: https://github.com/team-dut/project-staging/releases
- Extract it, you should see two files, `Game.jar` and `Game.exe`
  - For Windows users, use `Game.exe` - see `Known Issues` section.
  - Or else, `java -jar Game.jar`.
  - No matter what you use, keep the `resources` directory intact.

# Technical stack

For the game, we decided to complete the project with minimum possible dependency count,
which is: "if it exists in Java, use it, or else, install then use it". So here is our list of
used technologies.

- Programming language: Java version `1.8.0_372`, which is the minimum Long Time Support version. 

- Game window and window designs: Java AWT (with OpenGL rendering pipeline)

- CI system: GitHub action

- `.exe` file creation: Launch4J.

# Is there anything new?

- Not really at "usable" state, but it is in the project nonetheless.
  - There is a [Discord rich presence](https://discord.com/developers/docs/rich-presence/how-to) status to show your current status.
  - There is a leaderboard for the fastest clear, since we only use one map.
    - We planned to remove this by the way.
  - There is a map designer, which is a controversial choice to make.

# Known issues

- The game is very, very laggy in Windows, contrast to Linux.
  - Workaround: use the `.exe` built in GitHub release page.

- The game *sometimes* halts when eating the first ghost since the open of the game.
  - Workaround: we are unable to fix this issue, since this relates on how JVM works. We tried to reduce the lag time by using a threaded computation on that part.

# Credits

- [Iwatani Toru - 岩谷 徹](https://en.wikipedia.org/wiki/Toru_Iwatani), for creating original Pac-Man.
- StackOverflow community for helping me to optimize the render pipeline.
- You, for playing the game.
