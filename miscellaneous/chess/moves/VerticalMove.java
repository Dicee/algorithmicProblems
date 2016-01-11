package miscellaneous.chess.moves;

import static com.dici.collection.CollectionUtils.listOf;

import java.util.List;

import miscellaneous.chess.model.ChessBoard;
import miscellaneous.chess.utils.Delta;

public final class VerticalMove extends MoveWithLength {
    public static List<VerticalMove> allMaximalMoves() { return allMovesFromLength(ChessBoard.BOARD_SIZE); }
    public static List<VerticalMove> allUnitMoves   () { return allMovesFromLength(1); }
    
    public static List<VerticalMove> allMovesFromLength(int length) {
        return listOf(new VerticalMove(length), new VerticalMove(-length));
    }
    
    public VerticalMove(int length) { super(length); }

    @Override protected MoveWithLength buildFromLength(int length) { return new VerticalMove(length); }
    @Override protected Delta normalizedDelta() { return new Delta(1, 0); }
}