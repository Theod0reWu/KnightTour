import java.util.Arrays;
public class KnightBoard{
  private int  rows, cols;
  private int[][] board, hboard;
  private static int[] deltaRow = new int[]{0,-1,-2,-2,-1,1,2,2,1}; //row changes for 8 directions
  private static int[] deltaCol = new int[]{0,-2,-1,1,2,2,1,-1,-2}; //col changes for 8 directions
  public KnightBoard(int startingRows,int startingCols){
    if (startingCols <= 0 || startingRows <= 0) {
      throw new IllegalArgumentException("rows of cols less than or equal to zero");
    }
    rows = startingRows; cols = startingCols;
    board = new int[rows][cols];
    hboard = genHboard();
  }
  private int[][] genHboard(){ //works for board > size 3
    int[][] hb = new int[rows][cols];
    for (int r = 0; r < rows; r++){
      for (int c =0 ; c < cols ; c++){
        if ((r==0 || r == rows - 1) && (c==0 || c ==cols - 1)) {hb[r][c] = 2;}
        else if ((r == 0 || r == 1) && (c == 1 - r || c == cols - 2 + r)){hb[r][c] = 3;} //top four three's
        else if (r == rows - 2 && (c == 0 || c == cols - 1)) {hb[r][c] =3;} // bottom three's
        else if (r == rows - 1 && (c == 1 || c == cols - 2)) {hb[r][c] = 3;}
        else if ((r == 1 || r == rows - 2) && (c == 1 || c == cols - 2)) {hb[r][c] = 4;} // fours in the middle
        else if (r == 0 || r == rows - 1 || c == 0 || c == cols - 1) {hb[r][c] = 4;} //fours along the border
        else if (r == 1 || r == rows -2 || c == 1 || c == cols - 2) {hb[r][c] = 6;} // sixes
        else {hb[r][c] = 8;}
      }
    }
    return hb;
  }
  public String toString(){
    String out = ""; String un = "";
    if (rows*cols > 9){un = "_";}
    if (rows*cols > 99) {un = "__";}
    for (int r = 0; r < rows; r++){
      for (int c = 0; c < cols; c++){
        if(board[r][c] < 10){
          out += un+board[r][c] +" ";
        }
        else if (board[r][c] < 100 && rows * cols > 99){
          out += "_" + board[r][c] + " ";
        }
        else{
          out += board[r][c] + " ";
        }
      }
      out+="\n";
    }
    return out;
  }
  public String toStringH(){
    String out = "";
    for (int r = 0; r < rows; r++){
      for (int c = 0; c < cols; c++){
          out += hboard[r][c] +" ";
      }
      out+="\n";
    }
    return out;
  }
  public boolean solve(int startingRow, int startingCol){
      if (startingCol + startingRow < 0 || board[startingRow][startingCol] != 0){throw new IllegalArgumentException("invalid input or board is not clear");}
      //if (rows % 2 ==1 && cols % 2 ==1) {return false;}
      //board[startingRow][startingCol] = 1;
      return moreHelp(startingRow, startingCol, 1);
  }
  public class tile implements Comparable<tile>{
    private int direction;
    private int value;
    tile(int d, int v){direction = d; value = v;}
    public int compareTo(tile t){
      return value - t.value;
    }
    public int getValue(){return value;}
    public int getDirection(){return direction;}
    public String toString(){return direction +":" + value;}
  }
  public boolean moreHelp(int row, int col, int level){
    board[row][col] = level;
    if (level == rows * cols) {return true;}
    tile[] tiles = new tile[8];
    for (int i = 1; i < 9; i++){
      int r = deltaRow[i] + row; int c = deltaCol[i] + col;
      if (canMove(r, c, i)){
        hboard[r][c]--;
        tiles[i-1] = new tile(i,hboard[r][c]);}
      else{tiles[i-1] = new tile(i,-1);}
    }
    Arrays.sort(tiles);
    //System.out.println(this.toStringH());
    //for (tile t: tiles){System.out.println(t);}
    for (tile t: tiles){
      int r = deltaRow[t.getDirection()] + row; int c = deltaCol[t.getDirection()] + col;
      if (t.getValue() >= 0){
        if (board[r][c] != 0){throw new IllegalArgumentException("board is not clear");}
        if (moreHelp(r,c,level+1)){
          return true;
        }
        board[r][c] = 0;
        for (tile s: tiles){
          if (s.getValue() >= 0){
            r = deltaRow[s.getDirection()] + row; c = deltaCol[s.getDirection()] + col;
            hboard[r][c]++;
          }
        }
      }
    }
    board[row][col] = 0;
    return false;
  }
  public boolean help(int row, int col, int level){
    board[row][col] = level;
    if (level == rows * cols) {return true;}
    for (int i = 1; i < 9; i++){
      int r = deltaRow[i] + row; int c = deltaCol[i] + col;
      //System.out.println(i+"|"+r+":"+c +" "+ canMove(r, c, i)  );
      if (canMove(r, c, i)){
        if (board[r][c] != 0){throw new IllegalArgumentException("board is not clear");}
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
  public int countSolutions(int startingRow, int startingCol){
    if (startingCol + startingRow < 0 || board[startingRow][startingCol] != 0){throw new IllegalArgumentException("invalid input or board is not clear");}
      board[startingRow][startingCol] = 1;
      return countingHelp(startingRow, startingCol, 2,0);
  }
  public int countingHelp(int row, int col, int level, int solutions){
    if (level == rows * cols + 1) {return solutions+1;}
    for (int i = 1; i < 9; i++){
      int r = deltaRow[i] + row; int c = deltaCol[i] + col;
      if (canMove(r, c, i)){
        if (board[r][c] != 0){throw new IllegalArgumentException("board is not clear");}
        board[r][c] = level;
        int s = countingHelp(r,c,level+1,solutions);
        if (s > solutions){
          solutions = s;
        }
        board[r][c] = 0;
      }
    }
    board[row][col] = 0;
    return solutions;
  }
  public static void main(String[] args){
    
    for(int i  = 1; i < 100; i++){
      try{
        if (i != 38 && i != 46 && i != 54 && i != 55 && i != 56 && i != 61 ){
          KnightBoard board = new KnightBoard(i,i);
          boolean b = board.solve(0,0);
          System.out.println(i + ":" + b);
          //if (b == false){break;}
          System.out.println(board);
        }
      }
      catch(StackOverflowError e){
        System.out.println(i);
      }
    }
    
    //KnightBoard b = new KnightBoard(99,99);
    //System.out.println(b.solve(0,98));

  }

}
