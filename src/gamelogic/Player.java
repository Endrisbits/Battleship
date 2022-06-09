package battleship.gamelogic;

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

    public void addShip(Ship refShip) throws Exception {
        if(addedShipsCounter>=ships.length) throw new Exception("All Ships already added!");
        else {
            this.ships[addedShipsCounter] = refShip;
            addedShipsCounter++;
        }
    }

    public String getName() {
        return this.name;
    }
}
