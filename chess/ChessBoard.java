package chess;


import java.util.ArrayList;
import java.util.Collections;

public class ChessBoard {
    static King wKing = new King("white", charToX('e'), 1);//e1
    static King bKing = new King("black", charToX('e'), 8);//e8
    static Queen wQueen = new Queen("white", charToX('d'), 1);//d1
    static Queen bQueen = new Queen("black", charToX('d'), 8);
    static Rook wRook1 = new Rook("white", charToX('a'), 1);
    static Rook wRook2 = new Rook("white", charToX('h'), 1);
    static Rook bRook1 = new Rook("black", charToX('a'), 8);
    static Rook bRook2 = new Rook("black", charToX('h'), 8);
    static Knight wKnight1 = new Knight("white", charToX('b'), 1);
    static Knight wKnight2 = new Knight("white", charToX('g'), 1);
    static Knight bKnight1 = new Knight("black", charToX('b'), 8);//b8
    static Knight bKnight2 = new Knight("black", charToX('g'), 8);
    static Bishop wBishop1 = new Bishop("white", charToX('c'), 1);
    static Bishop wBishop2 = new Bishop("white", charToX('f'), 1);//f1
    static Bishop bBishop1 = new Bishop("black", charToX('c'), 8);
    static Bishop bBishop2 = new Bishop("black", charToX('f'), 8);
    static Pawn wPawn1 = new Pawn("white", charToX('a'), 2);
    static Pawn wPawn2 = new Pawn("white", charToX('b'), 2);
    static Pawn wPawn3 = new Pawn("white", charToX('c'), 2);
    static Pawn wPawn4 = new Pawn("white", charToX('d'), 2);
    static Pawn wPawn5 = new Pawn("white", charToX('e'), 2);//e2
    static Pawn wPawn6 = new Pawn("white", charToX('f'), 2);//f2
    static Pawn wPawn7 = new Pawn("white", charToX('g'), 2);//g2
    static Pawn wPawn8 = new Pawn("white", charToX('h'), 2);
    static Pawn bPawn1 = new Pawn("black", charToX('a'), 7);
    static Pawn bPawn2 = new Pawn("black", charToX('b'), 7);
    static Pawn bPawn3 = new Pawn("black", charToX('c'), 7);
    static Pawn bPawn4 = new Pawn("black", charToX('d'), 7);//d7
    static Pawn bPawn5 = new Pawn("black", charToX('e'), 7);//e7
    static Pawn bPawn6 = new Pawn("black", charToX('f'), 7);
    static Pawn bPawn7 = new Pawn("black", charToX('g'), 7);
    static Pawn bPawn8 = new Pawn("black", charToX('h'), 7);

    static ArrayList<ChessItem> items = initializeArray(); // список фигур

    private static ArrayList<ChessItem> initializeArray(){ // наполняем список

        items = new ArrayList<>();
        Collections.addAll( items,
                wKing,bKing,wQueen,bQueen,wRook1,wRook2,bRook1,bRook2,
                wKnight1,wKnight2,bKnight1,bKnight2,wBishop1,wBishop2,bBishop1,bBishop2,
                wPawn1,wPawn2,wPawn3,wPawn4,wPawn5,wPawn6,wPawn7,wPawn8,
                bPawn1,bPawn2,bPawn3,bPawn4,bPawn5,bPawn6,bPawn7,bPawn8);
        return items;
    }

    private ChessItem item; //локальная переменная


    public static int charToX(char ch){ // меняет букву на цифру на оси Х ( a,b,c,d,e,f,g,h)
        return ((int)ch-96);
    }

    public void stepFromTo(String text){
        int x = charToX(text.charAt(0));
        int y = Integer.parseInt(text.substring(1,2));

        setLocalItem(null);
        for (ChessItem item:items)
        {
            if ((item.getX() == x) && (item.getY() == y)){
                setLocalItem(item);
                break;
            }
        }
    }

    public static boolean hasItem(int x, int y){ // есть ли фигура на координатах
        for (ChessItem item:items)
        {
            if ((item.getX() == x) && (item.getY() == y))
                return true;
        }
        return false;
    }

    public static ChessItem isItem(int x, int y){ // возвращает ссылку на объект(фигуру), находящуюся на координатах
        for (ChessItem item:items)
        {
            if ((item.getX() == x) && (item.getY() == y))
                return item;
        }
        return null;
    }

    public static void setItem(ChessItem chessitem, int x, int y){ // меняет координаты фигуры на новые ( в списке Array)

        for (int i =0 ; i < items.size(); i++)
        {
            if (items.get(i).getX()==chessitem.getX() && items.get(i).getY()==chessitem.getY()) {
                chessitem.setX(x);
                chessitem.setY(y);
                chessitem.hadSteps++;
                items.set(i,chessitem);
                break;
            }
        }
    }

    public ChessItem getLocalItem(){
        return item;
    }

    public void setLocalItem(ChessItem item){
        this.item = item;
    }

}
