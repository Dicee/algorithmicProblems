package miscellaneous.chess.model;

public class ChessGame {
    private final ChessBoard board         = new ChessBoard();
    private       Player     currentPlayer = Player.WHITE;
    
    public void nextTurn() {
        
        currentPlayer = currentPlayer == Player.WHITE ? Player.BLACK : Player.WHITE;
    }
    
}
