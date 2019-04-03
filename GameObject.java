import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

//Name: Robert Oller
//Title: Homework2
//Class Name: GameObject
//Instructor: Mr. Selgrad
//Description: Acts as the interface for the ball, paddle, and brick classes
//Date: 6/18/2018

public abstract class GameObject
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int xVel;
	protected int yVel;
	protected Color color;
	
	public GameObject other;

	public GameObject(int x, int y, int width, int height, int xVel, int yVel, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xVel = xVel;
		this.yVel = yVel;
		this.color = color;
	}
	
	public abstract void drawIt(Graphics painter);
	
	//Runs through objects seeing if they've collided and returns true if they had
	public boolean CheckBoxCollision(GameObject other)
	{	
		  if (this.x <= other.x + other.width && other.x <= this.x + this.width)
		  {
			  if (this.y <= other.y + other.height && other.y <= this.y + this.height)
		  				return true;
		  		else
		  		{
		  			return false;
		  		}
		  }
		  else
		  {
			  return false;
		  }
	}
	
	protected abstract void onHit(GameObject other);

}
