package miscellaneous.chess.model;

import miscellaneous.chess.utils.ImmutablePoint;

public interface ReadableBoard {
    Player getOccupier(int x, int y);

    default Player getOccupier(ImmutablePoint pos) { return getOccupier(pos.x, pos.y); }
    default boolean isOccupied(int x, int y) { return getOccupier(x, y) != null; }
    default boolean isOccupied(ImmutablePoint pos) { return isOccupied(pos.x, pos.y); }
    default boolean isLegal(ImmutablePoint pos, Player currentPlayer) {
        return ChessBoard.isLegal(pos) && (!isOccupied(pos) || getOccupier(pos) != currentPlayer); 
    }
}