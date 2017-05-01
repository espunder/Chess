package brain;

import chess.*;

import java.util.*;

public class II {

    public static String deepMind (String color, ArrayList<ChessItem> items){
        // массив оценочных значений. сюда добавляются пары "строка(координата хода)"-"число (оценка ситуации на доске после этого хода)"
        HashMap<String,Integer> values = new HashMap<>();
        
        //создаем список-копию всех наших фигур, для того чтобы 
        //а.) не было исключения ConcurentModificationException и 
        //б.) мы могли вернуть координаты фигур, которыми ПРОБУЕМ ходить в начальное положение. 
        //у меня в игре нет доски, все данные о местоположении фигур хранятся в самих объектах фигур.
        ArrayList<ChessItem> abstractItems = copyOfItems(items);

        String step = "";//step  - строка с координатой самого выгодного хода, который в итоге должен вернуть наш метод
        for(ChessItem item : abstractItems)
        {
            if (item.getColor().equals(color)){
                for (int x = 1; x < 9; x++) {
                    for (int y = 1; y < 9; y++){
                        if (item.hasMove(x, y)) { 
                            // для всех фигур нашего цвета пробуем сделать ход на все клетки поля. если ход допустим, то..
                            step = ((char)(item.getX()+96)) + String.valueOf(item.getY()) + "-" + ((char)(x+96)) + String.valueOf(y) ; //e2-e4
                            Chess.chessBoard.setLocalItem(item);//"кладем в руку фигуру, которой ходим"
                            if (Chess.canMove((byte)x,(byte)y)) {//еще одна проверка на возможность хода этой фигурой, проверка на вскрытый шах и проч.
                                item.toMove(x, y);//h7-h5. делаем ход.
                                //String step2 = deepMind2(item.getColor().equals("white")?"black":"white",abstractItems);
                                //Chess.chessBoard.setLocalItem(ChessBoard.isItem(ChessBoard.charToX(step2.charAt(0)),step2.charAt(1)));
                                //ChessBoard.isItem(ChessBoard.charToX(step2.charAt(0)),step2.charAt(1)).toMove(ChessBoard.charToX(step2.charAt(3)),step2.charAt(4));//за белую власть
                                int m = Values.values(color);//оценка
                                values.put(step, m);//кладем пару в наш массив
                                ((Figure) item).recover();//делаем возврат измененных данных фигур ( короч откат сделанного хода, возврат предыдущего состояния доски)
                                if (m == 1000) return step;//если можем ставить мат - сразу возвращаем координаты хода для мата
                            }
                            step = "";
                        }
                    }
                }
            }
        }

        //выбираем пару с наивцысшей оценкой, лучший ход.
        int max = values.values().iterator().next();
        for (Map.Entry<String, Integer> pair : values.entrySet()){
            if (pair.getValue() >= max){
                max = pair.getValue();
                step = pair.getKey();
            }
        }
        return step;
    }
//    public static String deepMind2 (String color, ArrayList<ChessItem> items){
//        HashMap<String,Integer> values = new HashMap<>();
//        ArrayList<ChessItem> abstractItems = copyOfItems(items);
//
//        String step = "";
//        for(ChessItem item : abstractItems)
//        {
//            if (item.getColor().equals(color)){
//                for (int x = 1; x < 9; x++) {
//                    for (int y = 1; y < 9; y++){
//                        if (item.hasMove(x, y)) {
//                            step = ((char)(item.getX()+96)) + String.valueOf(item.getY()) + "-" + ((char)(x+96)) + String.valueOf(y) ; //e2-e4
//                            Chess.chessBoard.setLocalItem(item);
//                            item.toMove(x, y);
//                            int m = Values.values(color);
//                            values.put(step,m);
//                            //Chess.chessBoard.setLocalItem(null);
//                            ((Figure)item).recover();
//                            if (m==1000) return step;
//                            step = "";
//                        }
//                    }
//                }
//            }
//        }
//
//
//        int max = values.values().iterator().next();
//        for (Map.Entry<String, Integer> pair : values.entrySet()){
//            if (pair.getValue() >= max){
//                max = pair.getValue();
//                step = pair.getKey();
//            }
//        }
//        return step;
//    }



    static ArrayList<ChessItem> copyOfItems(ArrayList<ChessItem> items){
        ArrayList<ChessItem> abstractItems = new ArrayList<>();
        for (ChessItem item : items){
           abstractItems.add(new Figure(item));
        }
        return abstractItems;
    }

}

class Figure extends ChessItem {

    private ChessItem item;

    private int x,y;
    private String color;
    private int hadStep;

    public Figure(ChessItem item) {
        this.item = item;
        this.x = item.getX();
        this.y = item.getY();
        this.color = item.getColor();
        this.hadStep = item.getHadStep();
    }

    public int getHadStep() {
        return item.getHadStep();
    }

    public String getClassName(){
        return item.getClass().getSimpleName();
    }

    public void setHadStep(int hadStep) {
        item.setHadStep(hadStep);
    }


    public void recover(){
        item.setX(x);
        item.setY(y);
        item.setColor(color);
        item.setHadStep(hadStep);
        if (!removedItems.isEmpty()){
            ChessBoard.items.add(removedItems.get(0));
            removedItems.clear();
        }
    }
    @Override
    public int getX() {
        return item.getX();
    }

    @Override
    public void setX(int x) {
        item.setX(x);
    }

    @Override
    public int getY() {
        return item.getY();
    }

    @Override
    public void setY(int y) {
        item.setY(y);
    }

    @Override
    public String getColor() {
        return item.getColor();
    }

    @Override
    public boolean hasMove(int x, int y)
    {
        return item.hasMove(x,y);
    }

    @Override
    public void toMove(int x, int y) {
        item.toMove(x,y);
    }
}
