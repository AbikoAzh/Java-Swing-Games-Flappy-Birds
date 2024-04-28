package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {

	// this to use it in Main Method
	public static FlappyBird flappybird;
	
	public final int Width=1100, Height=700;
	// here we have panel
	public Render render;
	
	public int ticks, ymotion, score;
	
	public Rectangle bird;
	
	public boolean gameOver, started;
	
	public ArrayList<Rectangle> columns;
	
	public Random random;
	
	public FlappyBird() {
		JFrame frame = new JFrame();
		Timer timer = new Timer(20,this);
		
		render = new Render();
		random = new Random();
		
		frame.add(render);
		frame.setResizable(false);
		frame.setSize(Width, Height);
		frame.setVisible(true);
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Flappy Bird");
		
		bird = new Rectangle(Width / 2 -10 , Height /2 -10 ,20,20);
		columns = new ArrayList<Rectangle>();
		
		addcolumn(true);
		addcolumn(true);
		addcolumn(true);
		addcolumn(true);
		
		timer.start();
	}
	
	public void paintColumn(Graphics g, Rectangle column){
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	public void addcolumn(boolean start){
		int space = 300;
		int width = 100;
		int height = 50 + random.nextInt(300);
//		System.out.println(height);
		
		if(start){
//			System.out.println("addcolumn here in true");
			columns.add(new Rectangle(Width + width + columns.size() * 300, Height - height - 120,width,height));
			columns.add(new Rectangle(Width + width + (columns.size()-1) * 300, 0,width, Height - height - space));
//			System.out.println("addcolumn here in true columns = " + columns);
		}else{
//			System.out.println("addcolumn here in false");
			columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, Height - height - 120,width,height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x, 0,width, Height - height - space));
//			System.out.println("addcolumn here in false columns = " + columns);
		}
	}
	
	public void jump(){
		if(gameOver){
			bird = new Rectangle(Width / 2 -10 , Height /2 -10 ,20,20);
			columns.clear();
			ymotion = 0;
			score =0;
			
			addcolumn(true);
			addcolumn(true);
			addcolumn(true);
			addcolumn(true);
			
			gameOver = false;
		}
		
		if(!started){
			started = true;
		}
		else if (!gameOver){
			if(ymotion > 0){
				ymotion = 0;
			}
			ymotion -=50;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int speed =10;
		ticks ++;
		
		if(started){
			
			for(int i=0; i<columns.size(); i++){
				Rectangle column = columns.get(i);
				column.x -=speed;}
			
			if(ticks % 2 == 0 && ymotion < 15){
				ymotion =+2;}
			
			for(int i=0; i<columns.size(); i++){
				Rectangle column = columns.get(i);
				
				if(column.x + column.width < 0){
					columns.remove(column);
//					System.out.println("remove");
					
					if(column.y == 0){
//						System.out.println("addcolumn false");
						addcolumn(false);}
				}
			}
			
			bird.y +=ymotion;
			
			for(Rectangle column : columns){
				
				if(column.y==0 && bird.x+bird.width/2 > column.x+column.width/2-10 && bird.x+bird.width/2 < column.x+column.width/2 +10){
					score++;}
				
				if(column.intersects(bird)){
					gameOver = true;
					
					if(bird.x <= column.x){
						bird.x = column.x - bird.width;
					}else{
						if(column.y !=  0 ){
							bird.y = column.y - bird.height;
						}else if(bird.y < column.height){
							bird.y = column.height;
						}
					}
					
				}
			}
			
			if(bird.y > Height - 120 || bird.y < 0){
				gameOver = true;}

			if(bird.y + ymotion >= Height - 120){
				bird.y = Height - 120 - bird.height;}
		}
				
		render.repaint();
	}
	
	public void repaint(Graphics g) {
		//background
		g.setColor(Color.cyan);
		g.fillRect(0, 0, Width, Height);
		
		//ground
		g.setColor(Color.orange);
		g.fillRect(0, Height -120, Width,120);
		
		//grass
		g.setColor(Color.green);
		g.fillRect(0, Height -120, Width,20);
		
		//bird
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		for(Rectangle column : columns){
			paintColumn(g, column);
		}
		
		g.setColor(Color.red);
		g.setFont(new Font("Arial",1,20));
		g.drawString("Developed By Abubaker Azhari", Width-710, Height-57);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,100));
		
		if(!started){
			g.drawString("Click to Start!", Width/4, Height/2-50);
		}
		if(gameOver){
			g.drawString("Game Over", Width/4, Height/2-50);
		}
		
		if(!gameOver && started){
			g.drawString(String.valueOf(score),Width/2-25,100);
		}
		
	}
	

	public static void main(String[] args) {
		flappybird = new FlappyBird();
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			jump();
		}
	}
}