package battleship.gamelogic;

enum shipTypes {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer",2);

    String label;
    int nrOfCells;

    shipTypes(String s, int nrOfCells) {
        this.label = s;
        this.nrOfCells = nrOfCells;
    }
}
public class Ship {
    shipTypes type;
    int health;
    Square start;
    Square end;
    GameField gameField;

    Ship(Square start, Square end, GameField gameField, shipTypes type) {
        this.type = type;
        this.health = type.nrOfCells;
        this.start = start;
        this.end = end;
        this.gameField = gameField;
    }

    public boolean isSunken() {
        if (this.health==0) {
            return true;
        } else {
            return false;
        }
    }

    void registerHit () {
        this.health--;
    }
}