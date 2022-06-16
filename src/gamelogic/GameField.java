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

    public Square registerShot(int row, int col){
        Square refSquare = this.gameField[row][col];
        refSquare.registerHit();
        return refSquare;
    }

    public boolean hasCell(int row, int col) {
        return (row < this.rows && row >= 0) && (col < this.cols && col >= 0);
    }

    public Square getCell(int i, int j) throws IndexOutOfBoundsException{
        return gameField[i][j];
    }
}

