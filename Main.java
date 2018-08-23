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
        System.out.println("TreeSet:");
        System.out.println(set + "\n");
        // System.out.println(a);
        Board bo = new Board();
        System.out.println(bo);
        if(bo.validBoard())
        {
            System.out.println("valid board");
        }
        else
        {
            System.out.println("invalid board");
        }
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
		// TODO: Create some sort of relative position in order to color grid correctly.
        pieces[0] = new Piece(1, 3, 2, 3, 1, 4, 2, 4, 0);
        pieces[1] = new Piece(1, 5, 1, 6, 2, 6, 1);
        pieces[2] = new Piece(2, 5, 3, 5, 3, 6, 2);
        pieces[3] = new Piece(3, 7, 3, 8, 4, 8, 3);
        pieces[4] = new Piece(4, 7, 5, 7, 5, 8, 4);
        pieces[5] = new Piece(6, 7, 7, 7, 6, 8, 5);
        pieces[6] = new Piece(5, 4, 5, 5, 5, 6, 4, 5, 6);
        pieces[7] = new Piece(6, 4, 6, 5, 6, 6, 7, 5, 7);
        pieces[8] = new Piece(8, 5, 8, 6, 7, 6, 8);
        pieces[9] = new Piece(6, 2, 6, 3, 5, 3, 9);
        pieces[10] = new Piece(3, 1, 6, 1, 5, 2, 10);
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
    Board(Board b)
    {
        // Note: Deep Copy
        this.grid = new boolean[10][10];
        this.pieces = new Piece[11];
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                this.grid[i][j] = b.grid[i][j];
            }
        }
        for(int i = 0; i < 11; i++)
        {
            this.pieces[i] = b.pieces[i];
        }
    }
    boolean validBoard()
    {
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
        for(int i = 0; i < pieces.length; i++)
        {
            Piece current = pieces[i];
            for(int j = i+1; j < pieces.length; j++)
            {
                Piece comp = pieces[j];
                if(current.threeBlock && comp.threeBlock)
                {
                    // Case where the two are both 3 blocks
                    for(int h = 0; h < 6; h+= 2)
                    {
                        int x = current.threeCords[h];
                        int y = current.threeCords[h+1];
                        for(int k = 0; k < 6; k+= 2)
                        {
                            int compX = comp.threeCords[k];
                            int compY = comp.threeCords[k+1];
                            if(x == compX && y == compY)
                                return false;
                        }
                    }
                }
                else if(!current.threeBlock && !comp.threeBlock)
                {
                    // case where the two are both 4 blocks.
                    for(int h = 0; h < 8; h+= 2)
                    {
                        int x = current.fourCords[h];
                        int y = current.fourCords[h+1];
                        for(int k = 0; k < 8; k+= 2)
                        {
                            int compX = comp.fourCords[k];
                            int compY = comp.fourCords[k+1];
                            if(x == compX && y == compY)
                                return false;
                        }
                    }
                }
                else if(current.threeBlock && !comp.threeBlock)
                {
                    for(int h = 0; h < 6; h+= 2)
                    {
                        int x = current.threeCords[h];
                        int y = current.threeCords[h+1];
                        for(int k = 0; k < 8; k += 2)
                        {
                            int compX = comp.fourCords[k];
                            int compY = comp.fourCords[k+1];
                            if(x == compX && y == compY)
                                return false;
                        }
                    }
                }
                else
                {
                    for(int h = 0; h < 8; h+= 2)
                    {
                        int x = current.fourCords[h];
                        int y = current.fourCords[h+1];
                        for(int k = 0; k < 6; k+= 2)
                        {
                            int compX = comp.threeCords[k];
                            int compY = comp.threeCords[k+1];
                            if(x == compX && y == compY)
                                return false;
                        }
                    }
                }
            }
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
    byte[] threeCords;
    byte[] fourCords;
    boolean threeBlock;
    int number;
    // 3 block piece
    Piece(int a, int b, int c, int d, int e, int f, int n)
    {
        this.number = n;
        this.threeBlock = true;
        threeCords = new byte[6];
        threeCords[0] = (byte)a;
        threeCords[1] = (byte)b;
        threeCords[2] = (byte)c;
        threeCords[3] = (byte)d;
        threeCords[4] = (byte)e;
        threeCords[5] = (byte)f;
    }
    // 4 block piece
    Piece(int a, int b, int c, int d, int e, int f, int g, int h, int n)
    {
        this.number = n;
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
    int getPieceNumber()
    {
        return this.number;
    }
    @Override
	public String toString()
	{
        StringBuilder sb = new StringBuilder();
		return sb.toString();
    }
    void movePiece(int movement, boolean horizontal)
    {
        // TODO: Come back to this soon.
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
        board = new Board();
        if(_prev != null)
        {
            for(int i = 0; i < 22; i++)
            {
                state[i] = _prev.state[i];
            }
            for(int i = 0; i < 10; i++)
            {
                for(int j = 0; j < 10; j++)
                {
                    this.board.grid[i][j] = _prev.board.grid[i][j];
                }
            }
            for(int i = 0; i < 11; i++)
            {
                this.board.pieces[i] = _prev.board.pieces[i];
            }
        }
        // Add in new move.
        valid = true;
    }
    // TODO: Change switching state algo.
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
    public boolean invalidState()
    {
    	// TODO: Write function for both static standpoint and object standpoint.
    	GameState current = this;
    	return false; // Placeholder for now
    }
    public static boolean invalidState(GameState gs)
    {
        // Draw the board for the input state, and if valid return true.
    	// Change the center x, y based off the bytes.
    	// TODO: Gabe's Idea, write one board(static) and update it with the byte state array.
        if(gs.board.validBoard())
            return true;
        return false; // If GameState is false, move backwards or cannot make move.
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
    TreeSet<GameState> closedSet;
    TreeSet<GameState> openSet;
    static StateComparator comp = new StateComparator();
    BFS(GameState init, GameState desired)
    {
        closedSet = new TreeSet<GameState>(comp);
        openSet = new TreeSet<GameState>();
        // implement BFS for GameState
    }
    public TreeSet<GameState> getPath()
    {
        return this.openSet;
    }
}
