package brain;

import chess.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class II {

    public static String deepMind (String color){
        HashMap<String,Integer> values = new HashMap<>();
        ArrayList<ChessItem> abstractItems = ChessBoard.items;
        int hadStep;
        String step = "";
        while(ChessBoard.items.iterator().hasNext())
        {
            ChessItem item = ChessBoard.items.iterator().next();
            if (item.getColor().equals(color)){
                for (int x = 1; x < 9; x++) {
                    for (int y = 1; y < 9; y++){
                        if (item.hasMove(x, y)) {
                            hadStep = item.hadStep;
                            step = (char)item.getX() + item.getY() + "-" + (char)x + y ; //e2-e4
                            Chess.chessBoard.setLocalItem(item);
                            item.toMove(x, y);
                            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                            values.put(step,Values.values(color));
                            step = "";
                            ChessBoard.items = abstractItems;
                            item.hadStep = hadStep;
                            Chess.chessBoard.setLocalItem(null);
                        }
                    }
                }
            }
        }


        int max = values.values().iterator().next();
        for (Map.Entry<String, Integer> pair : values.entrySet()){
            if (pair.getValue() >= max){
                max = pair.getValue();
                step = pair.getKey();
            }
        }
        return step;
    }
}
