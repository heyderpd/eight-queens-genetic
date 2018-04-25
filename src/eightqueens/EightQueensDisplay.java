package eightqueens;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class EightQueensDisplay {

	private JFrame frame;
	private ChessPanel chessPanel;

	public EightQueensDisplay(EightQueensCromosome solution) {
		this.frame = new JFrame();
		this.frame.setTitle("Eight Queens Solution");
		this.frame.setSize(600, 600);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.chessPanel = new ChessPanel(solution);
		this.frame.add(chessPanel);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		
		this.frame.setVisible(true);
	}
	
	private class ChessPanel extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private EightQueensCromosome solution;
		
		public ChessPanel(EightQueensCromosome solution) {
			this.solution = solution;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			
			int width = (int) Math.ceil(this.getSize().width / 8);
			int height = (int) Math.ceil(this.getSize().height / 8);
			
			drawSquares(g, width, height);
			drawQueens(g, width, height);
		}
		
		private void drawSquares(Graphics g, int width, int height) {
			for (int column = 1; column <= 8; column++) {
				for (int row = 1; row <= 8; row++) {
					drawSquare(g, width, height, column, row);
				}
			}
		}
		
		private void drawQueens(Graphics g, int width, int height) {
			List<Integer> positions = this.solution.getPositions();
			for(int i = 0; i < positions.size(); i++) {
				drawQueen(g, width, height, i + 1, positions.get(i));
			}
		}
		
		private void drawQueen(Graphics g, int width, int height, int column, int row) {
			g.setColor(Color.RED);
			g.fillOval(width * (column - 1) + width/4, height * (row - 1) + height/4, width/2, height/2);
		}
		
		private void drawSquare(Graphics g, int width, int height, int column, int row) {
			g.setColor(getSquareColor(column, row));
			g.fillRect(width * (column - 1), height * (row - 1), width, height);
		}
		
		private Color getSquareColor(int column, int row) {
			if ((column + row) % 2 == 1) {
				return Color.BLACK;
			}
			else {
				return Color.WHITE;
			}
		}
		
	}
	
}
