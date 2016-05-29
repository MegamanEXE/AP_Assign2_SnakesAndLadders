
public class Position {
	short number;		//2 bytes integer is enough
	
	boolean special;	//True if either a snake or a ladder
	boolean isSnake, isLadder;
	short goesTo;
	
	public Position()
	{
		number = -1;
		special = false;
		goesTo = -1;
		isSnake = isLadder = false;
	}
	
	public Position(short argNumber)
	{
		number = argNumber;
		special = false;
		goesTo = -1;	
		isSnake = isLadder = false;
	}
	
	public void setPosition(boolean is_a_snake, short arg_goesTo)
	{
		special = true;
		goesTo = arg_goesTo;
		
		if(is_a_snake){
			isSnake = true;
		}
		else{
			isLadder = true;
		}
	}
	
	
	
}
