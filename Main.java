import java.util.*;
class GameState
{
    GameState prev;
    byte[] state;
    GameState(GameState p)
    {
        prev = p;
        state = new byte[2];
    }
    public String toString()
    {
        String current = "";
        current += "X position: " + state[0];
        current += " Y position: " + state[1];
        return current;
    }
}
class StateComparator implements Comparator<GameState>
{
    public int compare(GameState a, GameState b)
    {
        for(int i = 0; i < 2; i++)
        {
            if(a.state[i] < b.state[i])
            {
                return -1;
            }
            else if(a.state[i] > b.state[i])
            {
                return 1;
            }
        }
        return 0;
    }
}
public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello");
        byte state = new byte[22]; // Encoding the game for the 22 positions, 2 for each piece.
        // Update state every time a move is performed.
        GameState test = new GameState(null);
        GameState test2 = new GameState(null);
        test.state[0] = 10;
        test.state[1] = 10;
        test2.state[0] = 5;
        test2.state[1] = 5;
        StateComparator comp = new StateComparator();
        TreeSet<GameState> ts = new TreeSet<GameState>(comp);
        ts.add(test);
        ts.add(test2);
        System.out.println(ts); // Should have the 10's second, and the 5's first
    }
}
