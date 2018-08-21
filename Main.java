import java.util.*;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;

class View extends JPanel implements MouseListener {
	Viz viz;
	Random rand;
	byte[] state;
	Graphics graphics;
	int size;

	View(Viz v) throws IOException
	{
		viz = v;
		rand = new Random(0);
		state = new byte[22];
		size = 48;
	}

	public void mousePressed(MouseEvent e)
	{
		state[rand.nextInt(22)] += (rand.nextInt(2) == 0 ? -1 : 1);

		for(int i = 0; i < 11; i++)
		System.out.print("(" + state[2 * i] + "," +
			state[2 * i + 1] + ") ");
		System.out.println();
		viz.repaint();
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	// Draw a block
	public void b(int x, int y)
	{
		graphics.fillRect(size * x, size * y, size, size);
	}

	// Draw a 3-block piece
	public void shape(int id, int red, int green, int blue,
		int x1, int y1, int x2, int y2, int x3, int y3)
	{
		graphics.setColor(new Color(red, green, blue));
		b(state[2 * id] + x1, state[2 * id + 1] + y1);
		b(state[2 * id] + x2, state[2 * id + 1] + y2);
		b(state[2 * id] + x3, state[2 * id + 1] + y3);
	}

	// Draw a 4-block piece
	public void shape(int id, int red, int green, int blue,
		int x1, int y1, int x2, int y2,
		int x3, int y3, int x4, int y4)
	{
		shape(id, red, green, blue, x1, y1, x2, y2, x3, y3);
		b(state[2 * id] + x4, state[2 * id + 1] + y4);
	}

	public void paintComponent(Graphics g)
	{
		// Draw the black squares
		graphics = g;
		g.setColor(new Color(0, 0, 0));
		for(int i = 0; i < 10; i++) { b(i, 0); b(i, 9); }
		for(int i = 1; i < 9; i++) { b(0, i); b(9, i); }
		b(1, 1); b(1, 2); b(2, 1);
		b(7, 1); b(8, 1); b(8, 2);
		b(1, 7); b(1, 8); b(2, 8);
		b(8, 7); b(7, 8); b(8, 8);
		b(3, 4); b(4, 4); b(4, 3);

		// Draw the pieces
		shape(0, 255, 0, 0, 1, 3, 2, 3, 1, 4, 2, 4);
		shape(1, 0, 255, 0, 1, 5, 1, 6, 2, 6);
		shape(2, 128, 128, 255, 2, 5, 3, 5, 3, 6);
		shape(3, 255, 128, 128, 3, 7, 3, 8, 4, 8);
		shape(4, 255, 255, 128, 4, 7, 5, 7, 5, 8);
		shape(5, 128, 128, 0, 6, 7, 7, 7, 6, 8);
		shape(6, 0, 128, 128, 5, 4, 5, 5, 5, 6, 4, 5);
		shape(7, 0, 128, 0, 6, 4, 6, 5, 6, 6, 7, 5);
		shape(8, 0, 255, 255, 8, 5, 8, 6, 7, 6);
		shape(9, 0, 0, 255, 6, 2, 6, 3, 5, 3);
		shape(10, 255, 128, 0, 5, 1, 6, 1, 5, 2);
	}
}
class Viz extends JFrame
{
	public Viz() throws Exception
	{
		View view = new View(this);
		view.addMouseListener(view);
		this.setTitle("Puzzle");
		this.setSize(482, 505);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
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
class Piece
{
    int x;
    int y;
    String color;
    Piece(int a, int b, String c)
    {
        this.x = a;
        this.y = b;
        this.color = c;
    }
    public String toString() // Helper method to print contents of the piece
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.x);
        sb.append(", ");
        sb.append(this.y);
        sb.append(", ");
        sb.append(this.color);
        return sb.toString();
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
    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello");
        new Viz();
        byte[] state = new byte[22]; // Encoding the game for the 22 positions, 2 for each piece. Initialize with 0's
        // Update state every time a move is performed.
        // TestComparator();
        // Piece start = new Piece(20, 40, "red");
        // System.out.println(start);
        // System.out.println(stateToString(state));
    }
    public static void TestComparator() // Simple Function to test sorting comparator for gamestates.
    {
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
    static String stateToString(byte[] b)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Byte.toString(b[0]));
        for(int i = 1; i < b.length; i++)
        {
            sb.append(", ");
            sb.append(Byte.toString(b[i]));
        }
        return sb.toString();
    }
}
