import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

//Name: Robert Oller
//Title: Homework2
//Class Name: 
//Instructor: Mr. Selgrad
//Description: Controls the array of objects 'brick'
//Date: 6/18/2018

public class Brick extends GameObject
{

	public Brick(int x, int y, int width, int height, int xVel, int yVel, Color color)
	{
		super(x, y, width, height, xVel, yVel, color);
		this.xVel = 0;
		this.yVel = 0;
	}

	//Draws and sets the color according to the given parameters
	@Override
	public void drawIt(Graphics painter)
	{
		painter.setColor(color);
		painter.fillRect(x, y, width, height);
	}

	//Moves the brick off screen and adds a point to the score if a brick is hit by the ball
	@Override
	protected void onHit(GameObject other)
	{
		this.x = 10000;
		this.y = 10000;
		GameMain.score += 100;
	}
}
