# minesolver

This little side project isn't supposed to be any machine learning thingy. I've been curious as to 
how often my current strategies to solve minesweeper games can actually solve a board, given that
there is no random chance required to win. I'm sure someone else out there has done this same thing,
but I wanted to see for myself how often I could win with what I know, as well as how often one
could win with only a subset of the strategies on different board sizes.

## Recreating the Game

I use the standard grid height, grid width, and number of mines for each mode (currently just
expert). The number of mines left is based on the number of user flags, whether they are correct
or incorrect. Once the user/solver makes their first move, the rest of the 99 mines are generated
_outside_ the 3x3 square centered around the first move (the generated locations are unique and
random).

## Statistics

## Definitions of Strategies

Each strategy is encoded as one pattern and transformed into 4 based on 90 degree rotations.

All of these strategies can be seen in `resources/patterns.dat` but they are nameless and a bit
harder to read.

Key:

Symbol | Meaning
-------|--------
🥚 | irrelevant but visible
? | irrelevant but not visible
🚩 | flag
‼  | unsafe/mine
✔ | safe/not a mine

#### CornerOne

🥚 |‼
---|---
 1| 🥚
 
 #### TwinTwos
 
 ✔ | ‼ | ‼ |✔
  ---|---|---|---
  1|2|2|1
  
#### FlatThree

? |‼ | ‼|‼ | ?  
---|---|---|---|---
?|🥚|3|🥚|?
  
WIP
