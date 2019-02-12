public class KnightBoard{
  private int  rows, cols;
  private int[][] board;
  @throws IllegalArgumentException when either parameter is negative.
  public KnightBoard(int startingRows,int startingCols){
    if (startingCols < 0 || startingRows < 0) {
      throw new IllegalArgumentException("rows of cols less than zero");
    }
    rows = startingRows; cols = startingCols;
    board = new int[rows][cols];
  }
  public String toString(){
    String out = "";
    for (int r = 0; r < rows; r++){
      for (int c = 0; c < cols; c++){
        if()
      }
    }
  }

  @throws IllegalStateException when the board contains non-zero values.
  @throws IllegalArgumentException when either parameter is negative
   or out of bounds.
  public boolean solve(int startingRow, int startingCol)

  @throws IllegalStateException when the board contains non-zero values.
  @throws IllegalArgumentException when either parameter is negative
   or out of bounds.
  public int countSolutions(int startingRow, int startingCol)
}
