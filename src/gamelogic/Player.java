package gamelogic;

class Player {
    private final int numberOfShips = 5;
    private GameField ownField;
    public GameField opponentField;
    private String name;
    private int addedShipsCounter = 0;
    Ship[] ships = new Ship[numberOfShips];

    public Player(String name, GameField ownField, GameField opponentField) {
        this.name = name;
        this.ownField = ownField;
        this.opponentField = opponentField;
    }

    public GameField getOwnGameField() {
        return this.ownField;
    }

    public GameField getOpponentField() {
        return this.opponentField;
    }

    public String getName() {
        return this.name;
    }

    public boolean allShipsSunk() {//player whom ships may be sunk.
        for(Ship ship : this.ships) {
            if (!ship.isSunken()) {
                return false;
            }
        }
        return true;
    }

    public void placeShip(int row1, int col1, int row2, int col2, shipTypes type) throws Exception {
        if(!(this.ownField.hasCell(row1, col1) && this.ownField.hasCell(row2, col2))) { //out of bounds check
            throw new Exception("Error! Wrong ship location! Try again: ");
        }
        int length = type.nrOfCells;
        if(row1==row2) {//Added Horizontally
            if(col2-col1 != (length-1)) {
                throw new Exception("Error! Wrong length of the " + type.label + "! Try again:");
            }  else {//check if the fields are empty
                for(int j = col1; j <=col2; j++) {
                    if (this.ownField.getCell(row1,j).isOccupied()) {
                        throw new Exception("Error! You placed it too close to another one. Try again:");
                    }
                }
                Ship ship = new Ship(type);
                this.addShip(ship);
                for(int j = col1; j <=col2; j++) {
                    this.ownField.getCell(row1,j).occupyWith(ship);
                }
            }
        }
        else if(col1==col2) {
            if (row2 - row1 != (length - 1)) {
                throw new Exception("Error! Wrong length of the " + type.label + "! Try again:");
            }
            else {//check if the fields are empty
                for (int i = row1; i <=row2; i++) {
                    if (this.ownField.getCell(i,col1).isOccupied()) {
                        throw new Exception("Error! You placed it too close to another one. Try again:");
                    }
                }
                Ship ship = new Ship(type);
                this.addShip(ship);
                for(int i = row1; i <=row2; i++) {
                    this.ownField.getCell(i,col1).occupyWith(ship);
                }
            }
        }
        else {
            throw new Exception("Error! Wrong ship location! Try again:");
        }
    }

    private void addShip(Ship refShip) throws Exception {
        if(addedShipsCounter>=ships.length) throw new Exception("All Ships already added!");
        else {
            if(refShip == null) throw new Exception("Provided ship is null!");
            else{
                this.ships[addedShipsCounter] = refShip;
                addedShipsCounter++;
            }
        }
    }
}
