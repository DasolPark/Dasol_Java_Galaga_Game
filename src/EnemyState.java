import java.awt.Image;

public class EnemyState extends State{
	private GameBoard game;
	
	public EnemyState(GameBoard game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dx = -2;
	}

	@Override
	public void move() {//벽에 끝까지 가면 방향 바꿈
		if(((dx < 0) && (x < 10)) || ((dx > 0) && (x > 750))) {
			dx = -dx;
			y += 10;
		}
		super.move();
	}
}