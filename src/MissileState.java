import java.awt.Image;
	
public class MissileState extends State{
	private GameBoard game;
	
	public MissileState(GameBoard game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dy = -2;
	}

	@Override
	public void move() {//미사일이 화면을 넘어가면 제거
		super.move();
		if(y < -50)
			game.removeState(this);
	}

	@Override
	public void handleCollision(State other) {//미사일에 적이 맞으면 제거
		if(other instanceof EnemyState) {
			game.removeState(this);
			game.removeState(other);
		}
		if(other instanceof EnemyBoss) {
			game.removeState(this);
			game.removeState(other);
		}
	}
}
