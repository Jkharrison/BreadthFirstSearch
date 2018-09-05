import java.util.Comparator;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Queue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Main
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("results.txt");
        if(!file.exists())
        {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        System.out.println("Welcome to my Java Breadth First Search project");
   	    GameState init = new GameState();
        LinkedList<GameState> result = BFS(init);
        LinkedList<GameState> reversedResult = new LinkedList<GameState>();
        System.out.println(result.size());
        for(int i = result.size() - 1; i >= 0; i--)
        {
            bw.write(result.get(i).stateToString());
            reversedResult.add(result.get(i));
        }
        bw.close();
        System.out.println("Algorithm is finished");
    }
    public static LinkedList<GameState> BFS(GameState init)
    {
    	StateComparator comp = new StateComparator();
    	TreeSet<GameState> visited = new TreeSet<GameState>(comp); // Closed set
    	Queue<GameState> candidates = new LinkedList<GameState>(); // Open Set(FIFO)
    	LinkedList<GameState> optimalPath = new LinkedList<GameState>(); // Result List. Should have 115 different GameStates.
    	candidates.add(init); // queue.add()
    	visited.add(init);
    	while(!candidates.isEmpty())
    	{
    		GameState subtree = candidates.poll(); // dequeue
    		if(subtree.state[0] == 4 && subtree.state[1] == -2)
    		{
    			optimalPath = subtree.getSuccessors();
    			System.out.println("Solution has been found, congratulations");
    			break;
    		}
    		for(int i = 0; i < 11; i++)
    		{
                GameState left = new GameState(subtree, -1, true, i);
    			if(left.validBoard() && !visited.contains(left))
    			{
    				candidates.add(left);
    				visited.add(left);
    			}
                GameState right = new GameState(subtree, 1, true, i);
    			if(right.validBoard() && !visited.contains(right))
    			{
    				candidates.add(right);
    				visited.add(right);
    			}
                GameState up = new GameState(subtree, -1, false, i);
    			if(up.validBoard() && !visited.contains(up))
    			{
    				candidates.add(up);
    				visited.add(up);
    			}
                GameState down = new GameState(subtree, 1, false, i);
    			if(down.validBoard() && !visited.contains(down))
    			{
    				candidates.add(down);
    				visited.add(down);
    			}
    		}
    	}
    	return optimalPath;
    }
}
class Board
{
    static Piece[] pieces = new Piece[11];
    static boolean[][] gridChecker = new boolean[10][10];
    Board()
    {
        pieces[0] = new Piece(3, 1, 3, 2, 4, 1, 4, 2, 0);
        pieces[1] = new Piece(5, 1, 6, 1, 6, 2, 1);
        pieces[2] = new Piece(5, 2, 5, 3, 6, 3, 2);
        pieces[3] = new Piece(7, 3, 8, 3, 8, 4, 3);
        pieces[4] = new Piece(7, 4, 7, 5, 8, 5, 4);
        pieces[5] = new Piece(7, 6, 7, 7, 8, 6, 5);
        pieces[6] = new Piece(4, 5, 5, 4, 5, 5, 6, 5, 6);
        pieces[7] = new Piece(4, 6, 5, 6, 5, 7, 6, 6, 7);
        pieces[8] = new Piece(5, 8, 6, 7, 6, 8, 8);
        pieces[9] = new Piece(2, 6, 3, 5, 3, 6, 9);
        pieces[10] = new Piece(1, 5, 1, 6, 2, 5, 10);
    }
}
class Piece
{
    byte[] threeCords;
    byte[] fourCords;
    boolean threeBlock;
    // 3 block piece
    Piece(int a, int b, int c, int d, int e, int f, int n)
    {
        this.threeBlock = true;
        threeCords = new byte[6];
        threeCords[0] = (byte)a;
        threeCords[1] = (byte)b;
        threeCords[2] = (byte)c;
        threeCords[3] = (byte)d;
        threeCords[4] = (byte)e;
        threeCords[5] = (byte)f;
    }
    Piece(int a, int b, int c, int d, int e, int f, int g, int h, int n)
    {
        this.threeBlock = false;
        fourCords = new byte[8];
        fourCords[0] = (byte)a;
        fourCords[1] = (byte)b;
        fourCords[2] = (byte)c;
        fourCords[3] = (byte)d;
        fourCords[4] = (byte)e;
        fourCords[5] = (byte)f;
        fourCords[6] = (byte)g;
        fourCords[7] = (byte)h;
    }
    @Override
	public String toString()
	{
        StringBuilder sb = new StringBuilder();
        if(this.threeBlock)
        {
        	for(int i = 0; i < 6; i+=2)
        	{
        		sb.append("(" + threeCords[i] + ", " + threeCords[i+1] + ") ");
        	}
        	sb.append("\n");
        }
        else
        {
        	for(int i = 0; i < 8; i++)
        	{
        		if(i % 2 == 0)
        			sb.append("(" + fourCords[i] + ", ");
        		else
        			sb.append(fourCords[i] + ")" );
        	}
        	sb.append("\n");
        }
		return sb.toString();
    }
}
class GameState
{
    GameState prev;
    byte[] state;
    static Board board;
    GameState()
    {
        this.prev = null;
        state = new byte[22];
        board = new Board();
    }
    GameState(GameState _prev)
    {
        prev = _prev;
        state = new byte[22]; // Should be all zeros from init.
        if(_prev != null)
        {
            for(int i = 0; i < 22; i++)
            {
                state[i] = _prev.state[i];
            }
        }
        else
            throw new RuntimeException("Use default constructor instead of this one");
    }
    GameState(GameState _prev, int movement, boolean horizontal, int piece)
    {
        prev = _prev;
        state = new byte[22];
        if(_prev!= null)
        {
            for(int i = 0; i < 22; i++)
            {
                state[i] = _prev.state[i];
            }
        }
        else
            throw new RuntimeException("Use default constructor instead of this one");
        if(horizontal)
            state[piece*2] += movement;
        else
            state[(piece*2) + 1] += movement;
    }
    boolean validBoard() // Loop through all locations on board, check if not drawing twice or overlapping.
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                board.gridChecker[i][j] = false; // Make sure all grid locations are false before validation.
            }
        }
        for(int i = 0; i < 10; i++) // Perimeter pieces.
    	{
    		board.gridChecker[0][i] = true;
    		board.gridChecker[9][i] = true;
    	}
    	for(int j = 0; j < 10; j++) // Perimeter pieces.
    	{
    		board.gridChecker[j][0] = true;
    		board.gridChecker[j][9] = true;
    	}
    	board.gridChecker[1][1] = true; // All of the non-perimeter pieces
        board.gridChecker[1][2] = true;
        board.gridChecker[1][7] = true;
        board.gridChecker[1][8] = true;
        board.gridChecker[2][1] = true;
        board.gridChecker[2][8] = true;
        board.gridChecker[3][4] = true;
        board.gridChecker[4][3] = true;
        board.gridChecker[4][4] = true;
        board.gridChecker[7][1] = true;
        board.gridChecker[7][8] = true;
        board.gridChecker[8][1] = true;
        board.gridChecker[8][2] = true;
        board.gridChecker[8][7] = true;
        board.gridChecker[8][8] = true;
        for(int i = 0; i < board.pieces.length; i++) // Loop through colored pieces.
        {
        	Piece current = board.pieces[i];
        	if(current.threeBlock)
        	{
        		for(int j = 0; j < 6; j+= 2)
        		{
        			int row = current.threeCords[j] + this.state[i * 2 + 1];
        			int col = current.threeCords[j + 1] + this.state[i * 2];
        			if(board.gridChecker[row][col] == false)
        				board.gridChecker[row][col] = true;
        			else if(board.gridChecker[row][col] == true)
        				return false;
        		}
        	}
        	else
        	{
        		for(int j = 0; j < 8; j += 2)
        		{

        			int row = current.fourCords[j] + (int)this.state[i * 2 + 1];
        			int col = current.fourCords[j + 1] + (int)this.state[i * 2];
        			if(board.gridChecker[row][col] == false)
        				board.gridChecker[row][col] = true;
        			else
        				return false;
        		}
        	}
        }
    	return true;
    }
    public String stateToString() // Method to print the state byte array for each respective GameState
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.state.length; i++)
        {
            if(i % 2 == 0)
            {
                sb.append("(");
                sb.append((int)this.state[i]);
                sb.append(", ");
            }
            else
            {
                sb.append((int)this.state[i]);
                sb.append(")");
            }
        }
        sb.append("\n");
        return sb.toString();
    }
    public LinkedList<GameState> getSuccessors() // Method to get all parents of state passed in.
    {
    	GameState current = this;
    	LinkedList<GameState> returnSet = new LinkedList<GameState>();
    	while(current.prev != null)
    	{
    		returnSet.add(current);
    		current = current.prev;
    	}
    	returnSet.add(current);
    	return returnSet;
    }
    public String printWithMoves() // Method to (x, y) locations from initial piece location and state moves.
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < board.pieces.length; i++)
        {
            Piece current = board.pieces[i];
            if(current.threeBlock)
            {
                for(int j = 0; j < 6; j+=2)
                {
                    sb.append("(" + (current.threeCords[j] + this.state[i * 2 + 1]) + ", ");
                    sb.append((current.threeCords[j + 1] + this.state[i * 2]) + ")");
                }
            }
            else
            {
                for(int j = 0; j < 8; j+= 2)
                {
                    sb.append("(" + (current.fourCords[j] + this.state[i * 2 + 1]) + ", ");
                    sb.append((current.fourCords[j + 1] + this.state[i * 2]) + ")");
                }
            }
        }
        sb.append("\n");
        return sb.toString();
    }
    @Override
    public String toString() // Helper method to print variables, useful for debugging.
    {
        StringBuilder sb = new StringBuilder();
        sb.append("State values: ");
        for(int i = 0; i < 21; i++)
            sb.append(this.state[i] + ", ");
        sb.append(this.state[21] + "\n");
        sb.append(board + "\n");
        return sb.toString();
    }
}
class StateComparator implements Comparator<GameState> // What the TreeSet using to sort GameState objects.
{
    public int compare(GameState a, GameState b)
    {
        for(int i = 0; i < 22; i++)
        {
            if(a.state[i] < b.state[i])
                return -1;
            else if(a.state[i] > b.state[i])
                return 1;
        }
        return 0;
    }
}
