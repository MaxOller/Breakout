import java.io.IOException;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

//Name: Robert Oller
//Title: Homework2
//Class Name: GameMain.java
//Instructor: Mr. Selgrad
//Description: Imitates the game "Breakout", keeps score and gives the player 3 lives
//Date: 6/18/2018

public class GameMain extends BasicGame
{
	public final static int SCREEN_WIDTH = 1680;
	public final static int SCREEN_HEIGHT = 1050;
	
	public int x;
	public int y;
	public int width;
	public int height;
	public int xVel;
	public int yVel;
	public Color color;
	
	private int lives = 3;
	public static int score = 0;
	
	static
	{
		try
		{
			System.load("C:\\Users\\oller\\eclipse-workspace\\Oller_Homework2\\LWJGL 2.9.3\\lwjgl64.dll");
		}
		catch(UnsatisfiedLinkError e)
		{
			System.err.println("Native code library failed to load. \n" + e);
		}
	}
	
//	Creates objects for the paddle, ball, and brick(s)
	public Paddle paddle = new Paddle(SCREEN_WIDTH / 2 - 70, SCREEN_HEIGHT - 15, 150, 15, 0, 0, Color.white);
	Ball ball = new Ball(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 40, 20, 20, 0, 0, Color.white);
	Brick[] brick = new Brick[144];

	public GameMain(String title)
	{
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics painter) throws SlickException
	{
		//Renders a backdrop
		painter.setColor(new Color(40, 40, 40));
		painter.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
				
		//Renders the paddle and ball objects in the respective classes
		paddle.drawIt(painter);
		ball.drawIt(painter);
		
		//Goes through the drawIt method in the ball class for every ball
		for(int i = 0; i < brick.length; i++)
		{
			brick[i].drawIt(painter);
		}
		
		//Renders the score and lives
		drawNumbers(painter);
		//Renders a string based on whether the player has won or lost
		win(painter);
		lose(painter);
	}
	
	private void bot()
	{
		if(paddle.x + paddle.width / 2 < ball.x && (paddle.x + paddle.width) <= SCREEN_WIDTH)
		{
			paddle.x += ball.xVel;
		}
		else if(paddle.x + paddle.width / 2 > ball.x &&(paddle.x) >= 0)
		{
			paddle.x += ball.xVel;
		}
		else
		{
			paddle.x = paddle.x;
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
		y = 200;
		
		for(int i = 0; i < brick.length; i++)
		{
			Color c = setBrickColor(y);
			brick[i] = new Brick(x, y, 70, 15, 0, 0, c);
			if(x < SCREEN_WIDTH - 70)
			{
				x += 70;
			}
			else
			{
				x = 0;
				y += 15;
			}
		}	
	}

	@Override
	public void update(GameContainer container, int count) throws SlickException
	{
//		Calls on the update methods of each object
		paddle.update(container, SCREEN_WIDTH, SCREEN_HEIGHT);
		ball.update(container, brick, paddle, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		miss();
		controls(container);
		//bot();
	}
	
	//Provides a set of controls for the player to use (reset and exit)
	private void controls(GameContainer container)
	{
		Input input = container.getInput();
		
		//Resets the ball, paddle, bricks, lives, and score when the R key is pressed
		if(input.isKeyPressed(Input.KEY_R))
		{
			lives = 3;
			score = 0;
			
			paddle.x = SCREEN_WIDTH / 2 - 70;
			
			ball.xVel = 0;
			ball.yVel = 0;
			
			reInit();
			follow();
		}
		
		//Exits the game if the esc key is pressed
		if(input.isKeyPressed(Input.KEY_ESCAPE))
		{
			System.exit(0);
		}
	}
	
	//Used to reset all the bricks to original positions
	private void reInit()
	{
		x = 0;
		y = 200;
		
		for(int i = 0; i < brick.length; i++)
		{
			Color c = setBrickColor(y);
			brick[i] = new Brick(x, y, 70, 15, 0, 0, c);
			if(x < SCREEN_WIDTH - 70)
			{
				x += 70;
			}
			else
			{
				x = 0;
				y += 15;
			}
		}	
	}
	
	//Renders the current amount of lives and score
	private void drawNumbers(Graphics painter)
	{
		painter.setColor(Color.white);
		painter.drawString("Lives: " + lives, 10, 25);
		painter.drawString("Score: " + score, 10, 40);
	}
	
	//Resets the ball to where the paddle is with zero velocity if it exits through the bottom, subtracts a life, and sets the ball to follow the paddle
	private void miss()
	{
		if(ball.y >= SCREEN_HEIGHT)
		{	 
			ball.xVel = 0;
			ball.yVel = 0;
			lives--;
		}
		
		follow();
	}
	
	//When lives equals 0, the game displays a 'game over' message
	private void lose(Graphics painter)
	{
		if(lives == 0)
		{
			painter.setColor(Color.red);
			painter.drawString("You Lost", SCREEN_WIDTH / 2 - 30, 15);
			painter.drawString("Press R to play again, or ESC to exit", SCREEN_WIDTH / 2 - 155, 30);
		}
	}
	
	//When the score reaches the max amount of 14400, the game displays a 'player won' message
	private void win(Graphics painter)
	{
		if(score == 14400)
		{
			painter.setColor(Color.green);
			painter.drawString("You Won!!!", SCREEN_WIDTH / 2 - 30, 15);
			painter.drawString("Press R to play again, or ESC to exit", SCREEN_WIDTH / 2 - 155, 30);
		}
	}
	
	//Makes the ball follow the paddle until the ball is launched
		private void follow()
		{
			if(ball.xVel == 0 && ball.yVel == 0)
			{
				ball.x = paddle.x + (paddle.width / 2 - 10);
				ball.y = paddle.y - 21;
			}
		}
	
	//Returns a color for each brick depending on its y position
	private Color setBrickColor(int y)
	{
		if(y == 200)
		{
			return Color.red;
		}
		else if(y == 215)
		{
			return Color.orange;
		}
		else if(y == 230)
		{
			return Color.yellow;
		}
		else if(y == 245) 
		{
			return Color.green;
		}
		else if(y == 260)
		{
			return Color.blue;
		}
		else if(y == 275)
		{
			return Color.magenta;
		}
		else
		{
			return Color.white;
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		try
		{
			AppGameContainer myGame = new AppGameContainer(new GameMain("Breakout?"));
			myGame.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			myGame.setTargetFrameRate(60);
			myGame.start();
		}
		catch(SlickException exception)
		{
			System.out.println(exception.toString());
		}
	}
}
