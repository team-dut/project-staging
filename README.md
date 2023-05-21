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

- Build system: Maven

- CI system: GitHub action

- `.exe` file creation: Launch4J.
    - This is really a fun decision, because:
      - The result binary size will be so sky-high
      - The main developer is against that decision because he follows "do-it-yourself" principle: install the code, then build
        > "In that way, the JVM can compile the code that is optimized for your machine"
    
# Game rule

> Wait, really? Do you even need to see this?

- The game rule is *simple*:
  - You are the Pacman, which is the only thing that is yellow on the game screen.
    - Your mission is to eat all the foods on the map ASAP, that is a `Game Clear` for you.
    - You have to ignore the ghosts, or else, `Game Over`
      - There is some large foods, which will put all ghosts in "weak" state for 7 seconds.
      - When the ghost is eaten, they will return to base for a randomized time before going back to the game. Around 20-35 seconds to be exact.
    
  - There are five types of `Ghost`
    > *There are actually three:*
    - Red: most aggressive of all ghosts, will hunt you to the end of the world. 
    - Blue: most peaceful of all ghosts, will move very slow, *slow*. Or is it?
    - Cyan: most normal ghost type.
    
    > *Something I regret making it. Has very, very, very low chance to appear anyway*
    - RGB: *Run. Now.*
    - `Shielded` mutation: the ghost will have one more life when eaten. [Shielded ghost concept video - trigger warning.](https://www.youtube.com/shorts/b-RShSpLF5w)

# Code design

- We used some design patterns throughout the making of the game:
  - `Singleton`: there should be only one start window, one map design window.
  - `Builder`: there is `Discord rich presence` and `Leaderboard` extension, so that fits here.
  - `Observer`: for game events handling - restart, player lose, etc.
  > This pattern is subject to be removed or changed because of the burden in maintaining it!

- UML: TBA

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
