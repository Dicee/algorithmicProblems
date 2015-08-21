package miscellaneous.game2048;

public interface Game2048 {
	boolean move(Direction dir);
	Tile getTile(int x, int y);
	GameState getState();
}
