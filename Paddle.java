import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

//Name: Robert Oller
//Title: Homework2
//Class Name: Paddle.java
//Instructor: Mr. Selgrad
//Description: Controls the player controlled object 'paddle'
//Date: 6/18/2018

public class Paddle extends GameObject
{

	public Paddle(int x, int y, int width, int height, int xVel, int yVel, Color color)
	{
		super(x, y, width, height, xVel, yVel, color);
		this.yVel = 0;
	}
	
	//Updates the x and y positions according to the x and y velocities
	private void moveIt()
	{
		x += xVel;
		y += yVel;
	}
	
//	This takes keyboard input from the user to move the paddle left or right within the x boundaries
	private void controls(GameContainer container)
	{
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_LEFT) && (x >= 0))
		{
			xVel = -10;
		}
		else if(input.isKeyDown(Input.KEY_RIGHT) && ((x + width) <= 1680))
		{
			xVel = 10;
		}
		else
		{
			xVel = 0;
		}
	}

	//Draws and sets the color according to the given parameters
	@Override
	public void drawIt(Graphics painter)
	{
		painter.setColor(new Color(200, 0, 0));
		painter.fillRect(x, y, width, height);
	}

	@Override
	protected void onHit(GameObject other)
	{
		
	}

	public void update(GameContainer container, int SCREEN_WIDTH, int SCREEN_HEIGHT)
	{
		moveIt();
		controls(container);	
	}

}
