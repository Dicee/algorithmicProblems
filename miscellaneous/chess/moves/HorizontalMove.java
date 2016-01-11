package miscellaneous.chess.moves;

import static com.dici.collection.CollectionUtils.listOf;

import java.util.List;

import miscellaneous.chess.model.ChessBoard;
import miscellaneous.chess.utils.Delta;

public final class HorizontalMove extends MoveWithLength {
    public static List<HorizontalMove> allMaximalMoves() { return allMovesFromLength(ChessBoard.BOARD_SIZE); }
    public static List<HorizontalMove> allUnitMoves   () { return allMovesFromLength(1); }
    
    public static List<HorizontalMove> allMovesFromLength(int length) {
        return listOf(new HorizontalMove(length), new HorizontalMove(-length));
    }
    
    public HorizontalMove(int length) { super(length); }

    @Override protected MoveWithLength buildFromLength(int length) { return new HorizontalMove(length); }
    @Override protected Delta normalizedDelta() { return new Delta(0, 1); }
}