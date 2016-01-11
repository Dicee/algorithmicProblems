package miscellaneous.chess.pieces;

import static com.dici.collection.CollectionUtils.unionList;

import java.util.List;

import miscellaneous.chess.model.Piece;
import miscellaneous.chess.moves.DiagonalMove;
import miscellaneous.chess.moves.MoveWithLength;
import miscellaneous.chess.moves.VerticalMove;

public class Rook implements Piece {
    @Override 
    public List<MoveWithLength> getMaximalMoves() { return unionList(DiagonalMove.allMaximalMoves(), VerticalMove.allMaximalMoves()); }
}
