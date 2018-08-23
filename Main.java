import java.util.Comparator;
import java.util.TreeSet;
public class Main
{
    public static void main(String[] args) 
    {
        System.out.println("Welcome to my java project");
        StateComparator comp = new StateComparator();
        TreeSet<GameState> set = new TreeSet<GameState>(comp);
        // BFS solution = BFS(GameState init, GameState destination);
        // set = solution.getSet();  
        GameState a = new GameState(null); // What this state is.
        GameState b = new GameState(null); // Destination set.
        a.state[21] = 14;
        b.state[14] = 3;
        set.add(a);
        set.add(b);
        // System.out.println(a);
        Board bo = new Board();
        System.out.println(bo);
    }
}
class Board
{
    boolean grid[][];
    Piece[] pieces;
    Board()
    {
        pieces = new Piece[11]; // These will all be null at the beginning. Loop and initialize all.
		grid = new boolean[10][10]; // All are initially false.
		// TODO: Create some sort of relative position in order to color grid squards.
        pieces[0] = new Piece(3, 1, 0);
        pieces[1] = new Piece(5, 1, 1);
        pieces[2] = new Piece(5, 2, 2);
        pieces[3] = new Piece(7, 3, 3);
        pieces[4] = new Piece(7, 4, 4);
        pieces[5] = new Piece(7, 6, 5);
        pieces[6] = new Piece(4, 5, 6);
        pieces[7] = new Piece(4, 6, 7);
        pieces[8] = new Piece(5, 8, 8);
        pieces[9] = new Piece(2, 6, 9);
        pieces[10] = new Piece(1, 5, 10);
        for(int i = 0; i < 10; i++) // top and bottom black squares
        {
            grid[i][0] = true;
            grid[i][9] = true;
        }
        for(int j = 0; j < 10; j++) // left and right side black squares.
        {
            grid[0][j] = true;
            grid[9][j] = true;
        }
        // All the black squares that aren't on the perimeter.
        grid[1][1] = true;
        grid[1][2] = true;
        grid[1][7] = true;
        grid[1][8] = true;
        grid[2][1] = true;
        grid[2][8] = true;
        grid[3][4] = true;
        grid[4][3] = true;
        grid[4][4] = true;
        grid[7][8] = true;
        grid[8][1] = true;
        grid[8][2] = true;
        grid[8][7] = true;
        grid[8][8] = true;
    }
    boolean validBoard()
    {
        draw();
        // Insert Logic.
        // Checking the original black squares not on perimeter.
        if(grid[1][1] == false)
            return false;
        if(grid[1][2] == false)
            return false;
        if(grid[1][7] == false)
            return false;
        if(grid[1][8] == false)
            return false;
        if(grid[2][1] == false)
            return false;
        if(grid[2][8] == false)
            return false;
        if(grid[3][4] == false)
            return false;
        if(grid[4][3] == false)
            return false;
        if(grid[4][4] == false)
            return false;
        if(grid[7][8] == false)
            return false;
        if(grid[8][1] == false)
            return false;
        if(grid[8][2] == false)
            return false;
        if(grid[8][7] == false)
            return false;
        if(grid[8][8] == false)
            return false;
        if(grid[1][1] == false)
            return false;
        for(int i = 0; i < 10; i++)
        {
            // Just checking the original black squares from the beginning.
            if(grid[0][i] == false)
                return false;
            if(grid[9][i] == false)
                return false;
            if(grid[i][0] == false)
                return false;
            if(grid[i][9] == false)
                return false;
        }
        return true;
    }
    @Override
    public String toString() // String override to print contents of board.
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(this.grid[i][j] == true)
                {
                    sb.append("X ");
                }
                else
                {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
class Piece
{
    int x;
    int y;
    int leftTop;
    int leftMiddle;
    int leftBottom;
    int rightTop;
    int rightMiddle;
    int rightBottom;
    // width
    // height
    int number;
    Piece(int a, int b, int n)
    {
        this.x = a;
        this.y = b;
        this.number = n;
    }
    Piece(int a, int b, int n, int c, int d, int e, int f, int g, int h)
    {
        this.x = a;
        this.y = b;
        this.number = n;
        this.leftTop = c;
        this.leftMiddle = d;
        this.leftBottom = e;
        this.rightTop = f;
        this.rightMiddle = g;
        this.rightBottom = h;
    }
    @Override
	public String toString()
	{
        StringBuilder sb = new StringBuilder();
		return sb.toString();
	}
}
class GameState
{
    GameState prev;
    byte[] state;
    boolean valid;
    Board board;
    GameState()
    {
        this.prev = null;
        state = new byte[22];
        valid = true;
        board = new Board();
    }
    GameState(GameState _prev)
    {
        prev = _prev;
        state = new byte[22]; // Should be all zeros from init.
        valid = true;
        board = new Board(); // This board will have the relative piece locations.
    }
    GameState makeMove(GameState _prev, int movement, boolean horizontal, int piece)
    {
        assert(movement <= 1 && movement >= -1);
        if(movement > 1)
            throw new RuntimeException("Movement should not be more than 1");
        if(movement < -1)
            throw new RuntimeException("Move should not be less than -1");
        GameState current = new GameState(_prev);
        for(int i = 0; i < 22; i++)
        {
            current.state[i] = _prev.state[i];
        }
        if(horizontal)
        {
            current.state[piece] += movement;
        }
        else
        {
            current.state[piece+1] += movement;
        }
        return current;
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("State values: ");
        for(int i = 0; i < 21; i++)
        {
            sb.append(this.state[i] + ", ");
        }
        sb.append(this.state[21] + "\n");
        return sb.toString();
    }
    public static boolean invalidState(GameState gs)
    {
        // Draw the board for the input state, and if valid return true.
        if(gs.board.validBoard())
            return true;
        return false; // If gamestate is false, move backwards or cannot make move.
    }
}
class StateComparator implements Comparator<GameState>
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
class BFS
{
    TreeSet<GameState> set;
    static StateComparator comp = new StateComparator();
    BFS(GameState init, GameState desired)
    {
        set = new TreeSet<GameState>(comp);
        // implement BFS for GameState
    }
    public TreeSet<GameState> getPath()
    {
        return this.set;
    }
}