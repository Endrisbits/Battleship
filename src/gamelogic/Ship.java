package gamelogic;

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

    Ship(shipTypes type) {
        this.type = type;
        this.health = type.nrOfCells;
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