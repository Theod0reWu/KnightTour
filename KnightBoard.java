public class KnightBoard{
  private int  rows, cols;
  private int[][] board;
  private static int[] deltaRow = new int[]{0,-1,-2,-2,-1,1,2,2,1}; //row changes for 8 directions
  private static int[] deltaCol = new int[]{0,-2,-1,1,2,2,1,-1,-2}; //col changes for 8 directions
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
  public boolean solve(int startingRow, int startingCol){
      if (startingCol + startingRow < 0){throw new IllegalArgumentException("invalid input");}
      board[startingRow][startingCol] = 1;
      return help(startingRow, startingCol, 2);
  }
  public boolean help(int row, int col, int level){
    if (level == rows * cols + 1) {return true;}
    for (int i = 1; i < 9; i++){
      int r = deltaRow[i] + row; int c = deltaCol[i] + col;
      //System.out.println(i+"|"+r+":"+c +" "+ canMove(r, c, i)  );
      if (canMove(r, c, i)){
        board[r][c] = level;
        //System.out.println(this);
        //System.out.println(level);
        if (help(r,c,level+1)){
          return true;
        }
        board[r][c] = 0;
      }
    }
    board[row][col] = 0;
    return false;
  }
  public boolean canMove(int row, int col, int direction){
    //*2***3*
    //1*****4
    //***k***
    //8*****5
    //*7***6*
    switch (direction){
      case 8:
      return col>= 0 && row < rows && board[row][col] == 0;
      case 7:
      return col >= 0 && row < rows && board[row][col] == 0;
      case 6:
      return col < cols && row < rows && board[row][col] == 0;
      case 5:
      return col < cols && row < rows && board[row][col] == 0;
      case 4:
      return col < cols && row >= 0 && board[row][col] == 0;
      case 3:
      return col < cols && row >= 0 && board[row][col] == 0;
      case 2:
      return col >= 0 && row >= 0 && board[row][col] == 0;
      case 1:
      return col >= 0 && row >= 0 && board[row][col] == 0;
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
    //for (int i = 1; i < 9; i++){
    //    System.out.println(board.canMove(0,2,i));
    //}
    System.out.println(board.solve(0,0));
    System.out.println(board);
  }

}
