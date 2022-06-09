package gamelogic;

class GameField {
    static final char startingRow = 'A';
    private final int rows;
    private final int cols;
    private Player owner;

    private Square[][] gameField;

    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        gameField = new Square[rows][cols];
        this.setEmptyGameField();
    }

    private void setEmptyGameField() {
        for(int i=0; i < gameField.length; i++) {
            for(int j=0; j< gameField[i].length; j++) {
                this.gameField[i][j] = new Square(i,j);
            }
        }
    }

    public int getNumberOfRows() {
        return this.rows;
    }

    public int getNumberOfCols() {
        return this.cols;
    }

    public void setOwner(Player p) {
        this.owner = p;
    }

    public void printGameField(Player player) {
        boolean fogged = !(this.owner.equals(player));
        //Print header
        System.out.print(" ");
        for(int i = 1; i<=this.getNumberOfRows(); i++) {
            System.out.printf(" %d", i);
        }
        System.out.println();

        for (int i = 0; i < this.getNumberOfRows(); i++) {
            System.out.printf("%c ", (this.startingRow+i));
            for (int j = 0; j<this.getNumberOfCols(); j++) {
                squareStates state = this.gameField[i][j].getState();
                if (fogged && state == squareStates.O) {
                    state = squareStates.EMPTY;
                }
                System.out.printf("%c ", state.label);
            }
            System.out.println();
        }
    }

    public Ship placeShip(int row1, int col1, int row2, int col2, shipTypes type) throws Exception {
        if(row1 >= this.rows || row2>= this.rows || col1 >= this.cols || col2 >= this.cols) { //out of bounds check
            throw new Exception("Error! Wrong ship location! Try again: ");
        }
        int length = type.nrOfCells;
        if(row1==row2) {//Added Horizontally
            if(col2-col1 != (length-1)) {
                throw new Exception("Error! Wrong length of the " + type.label + "! Try again:");
            }  else {//check if the fields are empty
                for(int j = col1; j <=col2; j++) {
                    if (this.gameField[row1][j].isOccupied()) {
                        throw new Exception("Error! You placed it too close to another one. Try again:");
                    }
                }
                Square startSquare = this.gameField[row1][col1];
                Square endSquare = this.gameField[row1][col2];
                Ship ship = new Ship(startSquare, endSquare, this, type);
                for(int j = col1; j <=col2; j++) {
                    this.gameField[row1][j].occupyWith(ship);
                }
                return ship;
            }
        }
        else if(col1==col2) {
            if (row2 - row1 != (length - 1)) {
                throw new Exception("Error! Wrong length of the " + type.label + "! Try again:");
            }
            else {//check if the fields are empty
                for (int i = row1; i <=row2; i++) {
                    if (this.gameField[i][col1].isOccupied()) {
                        throw new Exception("Error! You placed it too close to another one. Try again:");
                    }
                }
                Square startSquare = this.gameField[row1][col1];
                Square endSquare = this.gameField[row2][col1];
                Ship ship = new Ship(startSquare, endSquare, this, type);
                for(int i = row1; i <=row2; i++) {
                    this.gameField[i][col1].occupyWith(ship);
                }
                return ship;
            }
        }
        else {
            throw new Exception("Error! Wrong ship location! Try again:");
        }
    }

    public Ship registerShot(int row, int col){
        Square refSquare = this.gameField[row][col];
        if(refSquare.isOccupied()) {
            refSquare.registerHit();
            return refSquare.getOccupyingShip();
        }
        else {
            refSquare.registerHit();
            return null;
        }
    }
}

