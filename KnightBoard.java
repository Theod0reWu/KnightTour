public class KnightBoard{
  private int  rows, cols;
  private int[][] board;
  public KnightBoard(int startingRows,int startingCols){
    if (startingCols <= 0 || startingRows <= 0) {
      throw new IllegalArgumentException("rows of cols less than zero");
    }
    rows = startingRows; cols = startingCols;
    board = new int[rows][cols];
  }
  public String toString(){
    String out = "";
    for (int r = 0; r < rows; r++){
      for (int c = 0; c < cols; c++){
        if(rows*cols > 9 && board[r][c] < 10){
          out += "_"+board[r][c] +" ";
        }
        else{
          out += board[r][c] + " ";
        }
      }
      out+="\n";
    }
    return out;
  }

  //@throws IllegalStateException when the board contains non-zero values.
  //@throws IllegalArgumentException when either parameter is negative
  // or out of bounds.
  public boolean solve(int startingRow, int startingCol){
      if (startingCol + startingRow < 0){throw new IllegalArgumentException("invalid input");}
      return help(startingRow, startingCol, 1);
  }
  public boolean help(int row, int col, int level){
    if (level == rows * cols) {return true;}
    for (int i = 1; i < 9; i++){
      if (canMove(row, col, i)){
        
      }
    }
  }
  public boolean canMove(int row, int col, int direction){
    switch (direction){
      case 8:
      return col - 2 >= 0 && row + 1 < rows && board[col-2][row+1] == 0;
      case 7:
      return col - 1 >= 0 && row + 2 < rows && board[col-1][row+2] == 0;
      case 6:
      return col + 1 < cols && row + 2 < rows && board[col+1][row+2] == 0;
      case 5:
      return col + 2 < cols && row + 1 < rows && board[col+2][row+1] == 0;
      case 4:
      return col + 2 < cols && row - 1 >= 0 && board[col+2][row-1] == 0;
      case 3:
      return col + 1 < cols && row - 2 >= 0 && board[col+1][row-2] == 0;
      case 2:
      return col - 1 >= 0 && row - 2 >= 0 && board[col-1][row-2] == 0;
      case 1:
      return col - 2 >= 0 && row - 1 >= 0 && board[col-2][row-1] == 0;
    }
    throw new IllegalArgumentException("directions is not 1-8 inclusive");
  }
  //@throws IllegalStateException when the board contains non-zero values.
//  @throws IllegalArgumentException when either parameter is negative
   //or out of bounds.
  public int countSolutions(int startingRow, int startingCol){return 1;}
  public static void main(String[] args){
    KnightBoard board = new KnightBoard(5,5);
    System.out.println(board);
    for (int i = 1; i < 9; i++){
        System.out.println(board.canMove(0,2,i));
    }
  }

}
