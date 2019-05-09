import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class ReadyScreen extends JFrame{
	private ImageIcon startImage = new ImageIcon("start.gif");
	
	ReadyScreen(){
		setTitle("SpaceDefence Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		
		JButton startBtn = new JButton("START");//시작 버튼
		startBtn.setSize(100,50);
		startBtn.setLocation(240, 200);
		startBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				GameBoard g = new GameBoard();
				GameStart.readyJf.setVisible(false);
			}
		});
		c.add(startBtn);
		
		JLabel startLa = new JLabel(startImage);//시작화면 레이블
		startLa.setBounds(0, 0, startImage.getIconWidth(), startImage.getIconHeight());//위치 크기
		c.add(startLa);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(startImage.getIconWidth()+15, startImage.getIconHeight()+45);
		setVisible(true);
	}
}

public class GameStart {
	static JFrame readyJf;
	public static void main(String[] args) {
		readyJf = new ReadyScreen();
	}
}