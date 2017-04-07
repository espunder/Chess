package brain;

import chess.*;

import java.util.HashMap;

public class Values {
    private static HashMap<String, Integer> mapValues = new HashMap<>();
    static {
        mapValues.put("King",100);
        mapValues.put("Queen",60);
        mapValues.put("Rook",35);
        mapValues.put("Bishop",20);
        mapValues.put("Knight",18);
        mapValues.put("Pawn",5);
    }

    public static int values (String color){
        int value = 0;
        Chess.schahimat(Chess.chessBoard);
        if (Chess.itisMAT>0) return 1000;
        if (Chess.itisPAT>0) return -100;
        for (ChessItem item : ChessBoard.items){
            if (item.getColor().equals(color))
            {
                value += mapValues.get(item.getClass().getSimpleName());
                if (item.getClass().getSimpleName().equals("King")){
                    if(Chess.kingUnderAttack(item, item.getX(), item.getY()))
                        value-=3;
            }
            }
            else
            {
                value -= mapValues.get(item.getClass().getSimpleName());
                if (item.getClass().getSimpleName().equals("King")){
                    if(Chess.kingUnderAttack(item, item.getX(), item.getY()))
                        value+=3;
            }
            }
    }
    return value;
    }

  /*  private static boolean kingUnderAttack(int x, int y, String color) {
            for (ChessItem item : ChessBoard.items)
            {
                if (!item.getColor().equals(color) && !item.getClass().getSimpleName().equals("King"))
                {
                        if (item.hasMove(x, y)) return true;
                }

            }
            return false;
    }
*/
  /*  public static void main(String[] args) {
        System.out.println(values("white"));
    }
*/
}
