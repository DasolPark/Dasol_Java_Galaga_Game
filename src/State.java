import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

//적, 미사일, 내 우주선의 중복되는 내용을 통합 관리
public class State {
	protected int x, y;//현재 위치 x, y좌표
	protected int dx, dy;//움직이는 x, y 거리
	private Image image;//각자 갖게될 이미지
	
	public State(Image image, int x, int y) {
		this.image = image; this.x = x; this.y = y;//갖게될 이미지, 위치 초기화
	}
	
	public int getWidth() {
		return image.getWidth(null);//null이 들어간 곳은 '이미지 그리기를 완료한  후 통보할 객체'를 넣어주면 된다. 여러번 사용될 것이므로 null		
	}
	
	public int getHeight() {
		return image.getHeight(null);
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);//null은 위와 같은 의미
	}
	
	public void move() {//객체를 움직이는 메소드
		x += dx; y += dy;
	}
	
	public void setDx(int dx) {//x이동 거리 설정
		this.dx = dx;
	}
	
	public void setDy(int dy) {//y이동 거리 설정
		this.dy = dy;
	}
	
	public int getDx() {//x이동거리 얻어옴
		return dx;
	}
	
	public int getDy() {//y이동거리 얻어옴
		return dy;
	}
	
	public int getX() {//x좌표 얻어옴
		return x;
	}
	
	public int getY() {//y좌표 얻어옴
		return y;
	}
	
	public boolean checkCollision(State other) {
		Rectangle myRect = new Rectangle();
		Rectangle otherRect = new Rectangle();
		myRect.setBounds(x, y, getWidth(), getHeight());//내 우주선과 미사일의 위치와 크기
		otherRect.setBounds(other.getX(), other.getY(), other.getWidth(), other.getHeight());//적의 위치와 크기
		
		return myRect.intersects(otherRect);//intersects메소드는 겹치는 부분을 체크하고 겹치면 true 겹치지 않으면 false 리턴
	}
	
	public void handleCollision(State other) {//미사일과 우주선에서 사용
	}
}