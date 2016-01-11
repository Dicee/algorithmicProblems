package miscellaneous.chess.model;

import static com.dici.collection.CollectionUtils.unionList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import miscellaneous.chess.utils.ImmutablePoint;

public interface Piece {
    default List<? extends Move> getMaximalMoves() { return emptyList(); }
    
    default List<? extends Move> specialRuleAllowedMoves(ImmutablePoint origin, Player currentPlayer, ReadableBoard board, boolean isFirstTurn) { 
        return emptyList(); 
    }
    
    default List<Move> getAllowedMoves(ImmutablePoint origin, Player currentPlayer, ReadableBoard board, boolean isFirstTurn) {
        List<Move> actualAllowedMoves = 
                getMaximalMoves().stream()
                                 .flatMap(move -> move.getAllowedSubMoves(origin, currentPlayer, board).stream())
                                 .collect(toList());
        return unionList(actualAllowedMoves, specialRuleAllowedMoves(origin, currentPlayer, board, isFirstTurn));
    }
}