import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements KeyListener, Runnable{
	private boolean playing = true;//게임 운영 체크
	private ArrayList states = new ArrayList();//우주선, 미사일, 적을 넣기 위한 ArrayList
	private State starship;//우주선
	private ImageIcon enemyImageIcon = new ImageIcon("Enemy.png");//각 이미지를 담기위한 변수 선언
	private Image enemyImage = enemyImageIcon.getImage();
	private ImageIcon enemyBossImageIcon = new ImageIcon("EnemyBoss.png");//각 이미지를 담기위한 변수 선언
	private Image enemyBossImage = enemyBossImageIcon.getImage();
	private ImageIcon missileImageIcon = new ImageIcon("Missile.png");
	private Image missileImage = missileImageIcon.getImage();
	private ImageIcon starshipImageIcon = new ImageIcon("Starship.png");
	private Image starshipImage = starshipImageIcon.getImage();
	private ImageIcon backgroundImageIcon = new ImageIcon("Spacebackground.png");
	private Image backgroundImage = backgroundImageIcon.getImage();
	private ImageIcon startImageIcon = new ImageIcon("Start.gif");
	private Image startImage = startImageIcon.getImage();
	private static int score = 0;//Score 변수
	private int level = 0;
	private JLabel laScore = new JLabel();//Score 레이블
	private Thread th;
	private JButton reBtn;

	public GameBoard() {
		JFrame jf = new JFrame("SpaceDefence Game");
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.add(this);
		jf.setSize(800, 600);
		jf.setVisible(true);
		jf.setResizable(false);//윈도우창 사이즈 조절 불가 설정
		
		laScore.setForeground(Color.WHITE);//Score 레이블 설정
		laScore.setFont(new Font("Gothic", Font.ITALIC, 30));
		laScore.setLocation(50, 50);
		this.add(laScore);
		//게임하는 도중에 나타나는 리플레이
		reBtn = new JButton("REPLAY");
		reBtn.setSize(100,50);
		reBtn.setLocation(240, 200);
		reBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				GameStart.readyJf = new ReadyScreen();
				score = 0;
				jf.setVisible(false);
			}
		});
		this.add(reBtn);
		
		this.setFocusable(true);
		this.requestFocus();
		this.initStates_one();
		addKeyListener(this);//keyListener 등록
		th = new Thread(this);
		th.start();
	}
	
	private void initStates_one() {//우주선 생성, 적 생성
		starship = new StarShipState(this, starshipImage, 370, 500);
		states.add(starship);
		for(int j = 0; j < 3; j++) {//3행
			for(int i = 0; i < 12; i++) {//12열
				State enemy = new EnemyState(this, enemyImage, 100+(i*50), (50)+j*30);//x는 50픽셀씩, y는 30픽셀씩 간격을 줌
				states.add(enemy);
			}
		}
	}
	private void initStates_two() {//우주선 생성, 적 생성
		starship = new StarShipState(this, starshipImage, 370, 500);
		states.add(starship);
		for(int j = 0; j < 5; j++) {//5행
			for(int i = 0; i < 12; i++) {//12열
				State enemy = new EnemyState(this, enemyImage, 100+(i*50), (50)+j*30);//x는 50픽셀씩, y는 30픽셀씩 간격을 줌
				states.add(enemy);
			}
		}
	}
	private void initStates_three() {//우주선 생성, 적 생성
		starship = new StarShipState(this, starshipImage, 370, 500);
		states.add(starship);
		for(int j = 0; j < 7; j++) {//7행
			for(int i = 0; i < 12; i++) {//12열
				State enemy = new EnemyState(this, enemyImage, 100+(i*50), (50)+j*30);//x는 50픽셀씩, y는 30픽셀씩 간격을 줌
				states.add(enemy);
			}
		}
	}
	private void initStates_boss() {//우주선 생성, 보스 생성
		starship = new StarShipState(this, starshipImage, 270, 400);
		states.add(starship);
		State enemyBoss = new EnemyBoss(this, enemyBossImage, 100, 50);//x는 50픽셀씩, y는 30픽셀씩 간격을 줌
		states.add(enemyBoss);
	}
	
	private void startGame() {//이용해서 추가해야함
		states.clear();//객체 모두 제거
		if(level == 1)
			initStates_two();
		else if(level == 2)
			initStates_three();
		else
			initStates_boss();
	}
	
	public void endGame() {
		System.exit(0);
	}
	
	public void removeState(State state) {
		if(state instanceof EnemyState)
			score += 200;//적이 제거되면 200점씩 증가
		if(state instanceof EnemyBoss)
			score += 5000;//보스가 제거되면 5000점 증가
		states.remove(state);
	}
	
	public void missile() {
		MissileState missile = new MissileState(this, missileImage, starship.getX()+15, starship.getY()-30);
		states.add(missile);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);//배경화면 설정
		for(int i=0; i<states.size(); i++) {//배열로 생성된 객체들 그려줌
			State state = (State)states.get(i);
			state.draw(g);
		}
	}
	
	@Override
	public void run() {
		while(playing) {
			for(int i=0; i<states.size(); i++) {//모든 객체 이동 가능하게 해줌
				State state = (State)states.get(i);
				state.move();
			}
			for(int i=0; i<states.size(); i++) {//충돌 검사
				for(int j=i+1; j<states.size(); j++) {//우주선이 인덱스 0이므로 +1
					State me = (State)states.get(i);
					State other = (State)states.get(j);
					
					if(me.checkCollision(other)) {//미사일과 적이 충돌할 시에 적 제거(적과 우주선이 충돌할 때도 검사함)
						laScore.setText("Score: "+ score);//Score 표시
						me.handleCollision(other);
						other.handleCollision(me);
					}
				}
			}
			repaint();
			try {
				Thread.sleep(10);
			}catch(InterruptedException e) {
				return;
			}
			if(states.size() == 1) {
				level++;          
				startGame();
			}
			if(level == 4)
				endGame();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			starship.setDx(-3);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			starship.setDx(+3);
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			missile();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			starship.setDx(0);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			starship.setDx(0);
	}
	@Override
	public void keyTyped(KeyEvent e) {}
}