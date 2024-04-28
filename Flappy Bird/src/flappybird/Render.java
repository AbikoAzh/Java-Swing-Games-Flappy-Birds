package flappybird;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Render extends JPanel{

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// we used this because repaint method isn't static.. and we have object of flappy bird static
		FlappyBird.flappybird.repaint(g);
	}
}