import java.util.Arrays;
public class KnightBoard{
  private int  rows, cols;
  private int[][] board, hboard;
  private int[] deltaRow = new int[]{0,-1,-2,-2,-1,1,2,2,1}; //row changes for 8 directions 7,8,1,2,3,4,5,6
  private int[] deltaCol = new int[]{0,-2,-1,1,2,2,1,-1,-2}; //col changes for 8 directions

  private static int[] deltaRow1 = new int[]{0,-2,-1,1,2,2,1,-1,-2}; //1,2,3,4,5,6,7,8
  private static int[] deltaCol1 = new int[]{0,1,2,2,1,-1,-2,-2,-1};
  public KnightBoard(int startingRows,int startingCols){
    if (startingCols <= 0 || startingRows <= 0) {
      throw new IllegalArgumentException("rows of cols less than or equal to zero");
    }
    rows = startingRows; cols = startingCols;
    board = new int[rows][cols];
    hboard = genHboard();
    moveOrder(21345678);
    //for (int l: deltaRow) {System.out.println(l);}
    //for (int i:deltaCol){System.out.println(i);}
  }
  private void moveOrder(int o){
    for (int i = 8; i > 0; i--){
      deltaRow[i] = deltaRow1[o%10];
      deltaCol[i] = deltaCol1[o%10];
      o/=10;
    }
  }
  private int[][] genHboard(){ //works for board > size 3
    int[][] hb = new int[rows][cols];
    for (int r = 0; r < rows; r++){
      for (int c =0 ; c < cols ; c++){
        if (r > 1 && r < rows-2 && c > 1 && c < cols-2){hb[r][c]=8;}
        else{
          for (int i = 1; i < 9; i++){
            if (canMove(r+deltaRow[i],c+deltaCol[i])){hb[r][c]++;}
          }
        }
      }
    }
    return hb;
  }
  public String toString(){
    String out = ""; String underscores = "_______________________";
    for (int r = 0; r < rows; r++){
      for (int c = 0; c < cols; c++){
        out += underscores.substring(0,(cols*rows/10 + "").length() - (board[r][c] + "").length()+1) + board[r][c] + " ";
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
  public boolean solve(int startingRow, int startingCol){
      if (startingCol + startingRow < 0 || board[startingRow][startingCol] != 0){throw new IllegalArgumentException("invalid input or board is not clear");}
      return moreHelp(startingRow, startingCol, 1);
  }
  public boolean moreHelp(int row, int col, int level){
    board[row][col] = level;
    if (level == rows * cols) {return true;}
    tile[] tiles = new tile[8];
    for (int i = 1; i < 9; i++){
      int r = deltaRow[i] + row; int c = deltaCol[i] + col;
      if (canMove(r, c)){
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
        for (int i = 1; i < 9; i++){
          int x = deltaRow[i] + r; int y = deltaCol[i] + c;
          if (canMove(x,y)) {hboard[x][y]++;}
        }
      }
    }
    board[row][col] = 0;
    return false;
  }
  public boolean canMove(int row, int col){
    //*2***3*
    //1*****4
    //***k***
    //8*****5
    //*7***6*
    return col>= 0 && col < cols && row >= 0 && row < rows && board[row][col] == 0;
  }
  public int countSolutions(int startingRow, int startingCol){
    if (startingCol + startingRow < 0 || board[startingRow][startingCol] != 0){throw new IllegalArgumentException("invalid input or board is not clear");}
      board[startingRow][startingCol] = 1;
      return countingHelp(startingRow, startingCol, 2,0);
  }
  public int countingHelp(int row, int col, int level, int solutions){
    if (level == rows * cols + 1) {System.out.println("pop"); return solutions+1;}
    for (int i = 1; i < 9; i++){
      int r = deltaRow[i] + row; int c = deltaCol[i] + col;
      if (canMove(r, c)){
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
    /*
    for(int i  = 1; i < 100; i++){
      try{
        if (i != 38 && i != 46 && i != 54 && i != 55 && i != 56 && i != 61 && i != 67){
          KnightBoard board = new KnightBoard(i,i);
          boolean b = board.solve(0,0);
          System.out.println(i + ":" + b);
          if (b== false) {System.out.println(board);}
        }
      }
      catch(StackOverflowError e){
        System.out.println(i);
      }
    }
    */
    KnightBoard b = new KnightBoard(2,11);
    System.out.println(b.toStringH());
    System.out.println(b.solve(0,0));
    System.out.println(b);
    //System.out.println(b.toStringH());
  }

}
