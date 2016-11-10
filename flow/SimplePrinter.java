package flow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimplePrinter {
	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	public static int sWIDTH = 630;
	public static int sHEIGHT = 630;
	public static int squareSize = 30;
	public static JFrame window;
	public static Grid grid;
	public static Map<Integer, Color> colors;
	
	
	public static class Grid extends JPanel {
		private static final long serialVersionUID = 1L;
		private List<Point> fillCells;

		public Grid() {
			fillCells = new ArrayList<>();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Point fillCell = fillCells.get(0);
			for(int h = 0; h < fillCells.size(); h++) {
				fillCell = fillCells.get(h);
				g.setColor(colors.get(new Integer(fillCell.value)));
				int cellY = squareSize + (fillCell.fil * squareSize);
				int cellX = squareSize + (fillCell.col * squareSize);
				if(fillCell.value == 0)
					continue;
				
				if(fillCell.is_node || fillCell.is_end_node)
					g.fillOval(cellX, cellY, squareSize, squareSize);
				else {
					if(fillCell.direction_fil == 0)
						g.fillRect(cellX, cellY + squareSize/4, squareSize, squareSize/2);
					else if(fillCell.direction_col == 0)
						g.fillRect(cellX + squareSize/4, cellY, squareSize/2, squareSize);
				}
			}
			g.setColor(Color.BLACK);
			g.drawRect(squareSize, squareSize, sWIDTH, sHEIGHT);

			for (int i = squareSize; i <= sWIDTH; i += squareSize) {
				g.drawLine(i, squareSize, i, sHEIGHT + squareSize);
			}

			for (int i = squareSize; i <= sHEIGHT; i += squareSize) {
				g.drawLine(squareSize, i, sWIDTH + squareSize, i);
			}
		}

		public void fillCell(Point p) {
			fillCells.add(p);
			
			repaint();
		}
		
		public void fillCellsFromList(List<Point> lp) {
			for(Point p : lp) {
				grid.fillCell(p);
			}
		}
	}

	public static void setUpWindowAndGrid() {
		grid = new Grid();
		window = new JFrame();
		window.setSize(new Dimension(WIDTH, HEIGHT));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(grid);
		window.setVisible(true);
	}

	public static void setUpColorMap() {
		colors = new HashMap<>();
		//colors.put(new Integer(0), Color.MAGENTA);
		colors.put(new Integer(1), Color.RED);
		colors.put(new Integer(2), Color.GREEN);
		colors.put(new Integer(3), Color.BLUE);
		colors.put(new Integer(4), Color.BLACK);
		colors.put(new Integer(5), Color.CYAN);
		colors.put(new Integer(6), Color.LIGHT_GRAY);
		colors.put(new Integer(7), Color.DARK_GRAY);
		colors.put(new Integer(8), Color.WHITE);
	}
	
	public static void setUpPrinterAndPrintStuff(Point[][] solutionMatrix) {
		setUpWindowAndGrid();
		setUpColorMap();
		
		squareSize = sWIDTH/Math.max(solutionMatrix.length, solutionMatrix[0].length);
		
		for(int i = solutionMatrix.length - 1; 0 <= i; i--)
			for(int j = 0; j < solutionMatrix[i].length; j++)
				grid.fillCell(solutionMatrix[i][j]);
		
	}
}