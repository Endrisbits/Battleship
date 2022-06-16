# A console version of the game *Battleship*
This  is a console version of the game *Battleship*. There are two players who can place 5 types of different ships in a 10x10 grid without overlap. Every player then takes their turn to hit one square of the opponent's field. 

Each player has no information about the placement of the ships of the opponent. After a hit they get to know if they did hit a ship or if they missed. They are also informed if the hit did end up sinking an enemy ship. The first player to sink all the enemies ships wins the game. 

## The Implementation
There is a GameLogic class that deals with the game logic, including adding the ships at the start of the game and managing the user inputs and switching the player's turns. It has references to both players and to both GameFields as well as a state to keep track of the turns.

The GameField class is a collection of mostly data objects. Each GameField has a matrix of squares and an owner which is one of the players. Used some exceptions for dealing with incorrect or malformed input cases with a simple message to inform the user.

