package miscellaneous.chess.pieces;

import java.util.List;

import miscellaneous.chess.model.Piece;
import miscellaneous.chess.moves.KnightMove;

public class Knight implements Piece {
    @Override public List<KnightMove> getMaximalMoves() { return KnightMove.allPossibleMoves(); }
}
