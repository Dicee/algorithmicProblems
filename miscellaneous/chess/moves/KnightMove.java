package miscellaneous.chess.moves;

import static com.dici.check.Check.notNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import miscellaneous.chess.model.Move;
import miscellaneous.chess.model.Player;
import miscellaneous.chess.model.ReadableBoard;
import miscellaneous.chess.utils.Delta;
import miscellaneous.chess.utils.ImmutablePoint;

public class KnightMove implements Move {
    public static List<KnightMove> allPossibleMoves() {
        return Stream.of(Orientation.values()).map(orientation -> new KnightMove(orientation)).collect(toList());
    }
    
    private final Orientation orientation;

    public KnightMove(Orientation orientation) { this.orientation = notNull(orientation); }

    @Override
    public Delta delta() { return new Delta(orientation.dx, orientation.dy); }

    @Override
    public List<Move> getAllowedSubMoves(ImmutablePoint origin, Player currentPlayer, ReadableBoard board) {
        return board.isLegal(move(origin), currentPlayer) ? singletonList(this) : emptyList();
    }

    public static enum Orientation {
        STAND_RIGHT(-1, 2),           // . 
                                      // .
                                      // . .
                                     
        STAND_LEFT(1, 2),             //   . 
                                      //   .
                                      // . .
                         
        LIE_RIGHT(2, -1),              // .     
                                       // . . .
                                
        LIE_LEFT(-2, -1),              //     . 
                                       // . . .
                                
        STAND_RIGHT_REV(-1, -2),       // . . 
                                       // .
                                       // .
                                
        STAND_LEFT_REV(1, -2),         // . . 
                                       //   .
                                       //   .
                                
        LIE_RIGHT_REV(2, 1),           // . . .     
                                       // . 
                                
        LIE_LEFT_REV(-2, 1);           // . . . 
                                       //     .
        public final int dx, dy;
        
        private Orientation(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}