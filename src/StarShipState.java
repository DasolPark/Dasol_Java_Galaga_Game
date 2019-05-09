import java.awt.Image;

public class StarShipState extends State{
	private GameBoard game;
	
	public StarShipState(GameBoard game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dx = 0;
		dy = 0;
	}
	
	@Override
	public void move() {//우주선이 창을 넘어가지 못 하도록 함
		if((dx < 0) && (x < 10))
			return;
		if((dx > 0) && (x > 740))
			return;
		super.move();
	}

	@Override
	public void handleCollision(State other) {
		if(other instanceof EnemyState) {
			game.endGame();
		}
		if(other instanceof EnemyBoss) {
			game.endGame();
		}
	}
}
