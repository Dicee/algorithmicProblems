package miscellaneous.chess.model;

import static com.dici.math.MathUtils.isBetween;
import miscellaneous.chess.pieces.Bishop;
import miscellaneous.chess.pieces.King;
import miscellaneous.chess.pieces.Knight;
import miscellaneous.chess.pieces.Pawn;
import miscellaneous.chess.pieces.Queen;
import miscellaneous.chess.pieces.Rook;
import miscellaneous.chess.utils.ImmutablePoint;

import com.dici.check.Check;

public class ChessBoard implements ReadableBoard {
    public static final int BOARD_SIZE = 8;
    
    public static boolean isLegal(ImmutablePoint pos) { return isLegal(pos.x, pos.y); }
    public static boolean isLegal(int x, int y) {
        // MathUtils.isBetween is right-exclusive 
        return isBetween(0, x, BOARD_SIZE + 1) && isBetween(0, y, BOARD_SIZE + 1);
    }
    
    private final Cell[][] cells = new Cell[BOARD_SIZE][BOARD_SIZE];

    public ChessBoard() {
        for (Player player : Player.values()) {
            int baseRow  = player == Player.BLACK ? 0 : BOARD_SIZE - 1;
            int pawnsRow = baseRow + (player == Player.BLACK ? 1 : -1);
            
            setRooks       (player, baseRow );
            setKnights     (player, baseRow );
            setBishops     (player, baseRow );
            setKingAndQueen(player, baseRow );
            setPawns       (player, pawnsRow);
        }
    }
    
    private void setRooks(Player player, int row) {
        cells[row][0]              = new Cell(player, new Rook());
        cells[row][BOARD_SIZE - 1] = new Cell(player, new Rook());
    }
    
    private void setKnights(Player player, int row) {
        cells[row][1]              = new Cell(player, new Knight());
        cells[row][BOARD_SIZE - 2] = new Cell(player, new Knight());
    }
    
    private void setBishops(Player player, int row) {
        cells[row][2]              = new Cell(player, new Bishop());
        cells[row][BOARD_SIZE - 3] = new Cell(player, new Bishop());
    }

    private void setKingAndQueen(Player player, int row) {
        cells[row][3]              = new Cell(player, new Queen());
        cells[row][BOARD_SIZE - 4] = new Cell(player, new King());
    }
    
    private void setPawns(Player player, int row) {
        for (int j = 0; j < BOARD_SIZE; j++) cells[row][j] = new Cell(player, new Pawn());
    }
    
    @Override
    public Player getOccupier(int x, int y) {
        Check.isTrue(isLegal(x, y), "Illegal position : (" + x + ", " + y + ")");
        return cells[x][y].player;
    }
}