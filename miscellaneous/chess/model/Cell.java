package miscellaneous.chess.model;

import static com.dici.check.Check.notNull;

public class Cell {
    public Player player;
    public Piece piece;

    public Cell() {
        // empty cell
    }
    
    public Cell(Player player, Piece piece) {
        this.player = notNull(player);
        this.piece  = notNull(piece);
    }
}
