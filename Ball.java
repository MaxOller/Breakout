import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

//Name: Robert Oller
//Title: Homework2
//Class Name: Ball.java
//Instructor: Mr. Selgrad
//Description: Controls the specialized object 'ball'
//Date: 6/18/2018

public class Ball extends GameObject
{	
	private Sound paddleSound;
	private Sound wallSound;
	private Sound missSound;
	private Sound hitSound;
	
	public Ball(int x, int y, int width, int height, int xVel, int yVel, Color color)
	{
		super(x, y, width, height, xVel, yVel, color);
		
		try
		{
			wallSound = new Sound("res/wall.wav");
			missSound = new Sound("res/miss.wav");
			paddleSound = new Sound("res/paddle.wav");
			hitSound = new Sound("res/hit.wav");
		} 
		catch (SlickException e)
		{
			System.out.println("Sound file missing!");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	//Draws and sets the color according to the given parameters
	@Override
	public void drawIt(Graphics painter)
	{
		painter.setColor(new Color(200, 0, 0));
		painter.fillOval(x, y, width, height);
	}

	//Changes the sign of the respective velocity based on where it hit
	@Override
	protected void onHit(GameObject other)
	{
		if(this.x <= other.x + other.width || other.x <= this.x + this.width)
		{
			yVel *= -1;
		}
		else if(this.y <= other.y + other.height && other.y <= this.y + this.height)
		{
			xVel *= -1;
		}
	}
	
	//Launches the ball upwards from where it is initially placed in a random direction
	private void launch(GameContainer container)
	{
		Input input = container.getInput();
		
		if(xVel == 0 && yVel == 0)
		{
			if(input.isKeyPressed(Input.KEY_SPACE))
			{
				yVel = 10;
				xVel = (int) (Math.random() * 10);
			}
		}
	}

	//Updates the x and y positions according to the x and y velocities
	public void moveIt()
	{
		x += xVel;
		y += yVel;
	}

	//Bounces the ball if it comes in contact with the three screen edges, and plays a sound when the ball hits a wall or goes through the bottom
	private void checkBounds(int lowBoundX, int lowBoundY, int highBoundX, int highBoundY) throws SlickException
	{

		if(this.x <= lowBoundX)
		{
			xVel *= -1;
			wallSound.play();
		}
		else if(this.x + this.width >= highBoundX)
		{
			xVel *= -1;
			wallSound.play();
		}
		
		if(this.y <= lowBoundY)
		{
			yVel *= -1;
			wallSound.play();
		}
		else if(this.y + this.height >= highBoundY)
		{
			missSound.play();
		}
	}
	
	public void update(GameContainer container, Brick[] brick, Paddle paddle, int SCREEN_WIDTH, int SCREEN_HEIGHT) throws SlickException
	{
		launch(container);
		checkBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		moveIt();
		
		//Runs through each brick to see if it has collided with ball
		for(int i = 0; i < brick.length; i++)
		{
			if(this.CheckBoxCollision(brick[i]))
			{
				brick[i].onHit(brick[i]);
				this.onHit(brick[i]);
				hitSound.play();
			}
		}
		
		//Runs through the paddle to see if it has collided with ball
		if(this.CheckBoxCollision(paddle))
		{
			this.onHit(paddle);
			paddleSound.play();
		}
	}
}
