package battleship.gamelogic;

import battleship.gamelogic.Ship;

enum squareStates {
    O('O'),
    X('X'),
    M('M'),
    EMPTY('~');

    public char label;

    squareStates(char label) {
        this.label = label;
    }
};

class Square {
    private final int row;
    private final int col;
    private squareStates state;
    private Ship occupyingShip = null;
    private boolean alreadyHit = false;

    Square(int row, int col){ //row must represent the difference to GameField.startingRow
        this.row = row;
        this.col = col;
        this.state = squareStates.EMPTY;
    }

    Square(int row, int col, squareStates s){ //row must represent the difference to GameField.startingRow
        this.row = row;
        this.col = col;
        this.state = s;
    }

    squareStates getState() {
        return this.state;
    }

    public Ship getOccupyingShip() {
        return this.occupyingShip;
    }

    public void occupyWith(Ship s) {
        this.occupyingShip = s;
        this.state = squareStates.O;
    }

    public void registerHit() {
        if(this.isOccupied()) {
            if(!this.alreadyHit) {
                this.state = squareStates.X;
                this.getOccupyingShip().registerHit();
            }
        }
        else {
            this.state = squareStates.M;
        }
        this.alreadyHit = true;
    }

    boolean isOccupied () {
        return this.occupyingShip!=null;
    }

}
