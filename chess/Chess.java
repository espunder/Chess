package chess;

import swing.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
* + МАТ,  ПАТ
* +взятие на проходе.
* +прохождение пешки
* +сделать вывод картинок из jar архива, чтобы работало через CMD
* ** Графика : **
* Сделать приветственное меню ( сохраниние, загрузка, новая игра)
* место отображения инфы о сделанных ходах
* +отображение важных моментов крупным планом ( шах, мат)
* -закрытие окна мат/шах вместе с окном программы без ошибок
* окончание игры - переход в меню
* ** ******** **
* -+перетаскивание фигур мышкой
* *************
* игра по сети
* */

public class Chess {

    public static ChessBoard chessBoard = new ChessBoard(); //создание объектов фигур, добавление этим объектам свойств и начальных коородинат, сохранение всех фигур в спиcок items
    public static Board mainBoard = new Board(); // создание площадки графичеcкого отображения (ContentPane) всех существующих на данный момент объектов фигур.
    public static JFrame mainFrame = new JFrame("Chess"); // создание окна

    private static Pawn vsyatienaprohodePewka = ChessBoard.bPawn1;
    private static int vsyatienaprohodePewkaY = 0;
    static ChessItem iAmAttackYou = null;
    public static String step = null;
    private static int stepscount = 0;
    private static int itisMAT = 0;
    private static int itisPAT = 0;

    private static FileOutputStream outputStream;


    private static void createLogsTxt(){
        String logs = "logs.txt";
        SimpleDateFormat dt = new SimpleDateFormat("d MMMMM yyyy , HH:mm"); // 9 ноября 2016 , 23:14
        String date = dt.format(new Date());
        try {
            outputStream = new FileOutputStream(logs);
            outputStream.write((date + "\r\n").getBytes("windows-1251"));
            outputStream = new FileOutputStream(logs, true);
        } catch (FileNotFoundException e) {
            System.out.println("Файл logs недоступен.");
        } catch (IOException e) {
            System.out.println("Без даты.");
        } catch (NullPointerException e) {
            System.out.println("logs == null");
        }


    }

    public static void writeLogs(String s) {
        try {
            outputStream.write(s.getBytes("windows-1251"));
            outputStream.write("\r\n".getBytes("windows-1251"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Лог не записан.");
        } catch (NullPointerException e) {
            System.out.println("NLP");
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        mainBoard.draw(ChessBoard.items); // заполнение фигурами площадки данных в соответствии с их координатами (инициализация доски)

        new StartHello();

        createLogsTxt();

        game();
    }

    public static void game(){

        System.out.println("ШАХМАТЫ.");
        writeLogs("ШАХМАТЫ.");
        System.out.println("Первыми ходят белые.");
        writeLogs("Первыми ходят белые.");

        while (true) {

            try {
                step = null;
                // в нити с слушателями клика мышки в это время строится String step, когда построится - main пойдет дальше.
                while (step == null) {
                    Thread.sleep(100);
                }

                if (isCorrectText(step))
                    chessBoard.stepFromTo(step);// находим фигуру, которая стоит на этом поле, добавляем ее в локальную переменную объекта chessBoard "chessBoard.getLocalItem()"
                else {
                    continue;
                }

                int x = ChessBoard.charToX(step.charAt(3));
                int y = Integer.parseInt(step.substring(4));


                if (canMove(x, y))//проверяем возможность хода
                {
                    if (chessBoard.getLocalItem().toString().equals("Pawn")) // добавляем маркет к пешке
                    {
                        vsyatienaprohodePewkaY = chessBoard.getLocalItem().getY();
                    }
                    writeLogs(step);
                    chessBoard.getLocalItem().toMove(x, y); // либо ест и ходит, либо просто ходит.

                    mainBoard.draw(ChessBoard.items);
                    stepscount++;

                    if (chessBoard.getLocalItem().toString().equals("Pawn") && (chessBoard.getLocalItem().getY() == 8 || chessBoard.getLocalItem().getY() == 1)) {
                        prohodnayaPawn((Pawn) chessBoard.getLocalItem());
                        mainBoard.draw(ChessBoard.items);
                    }

                    vsyatienaprohodePewka.markerPawn = 0;

                    if (chessBoard.getLocalItem().toString().equals("Pawn"))
                        vzyatieNaProhode(chessBoard.getLocalItem());//добавляем маяк, если пешка пошла на 2 клетки вперед.

                    schahimat(chessBoard);// ШАХ! (или МАТ)

                    if (itisMAT > 0) {
                        String text = "Игра окончена в пользу " + (chessBoard.getLocalItem().getColor().equals("black") ? "Чёрных " : "Белых ") + "\n"
                                + "на " + stepscount + " ходу.";
                        System.out.print(text);
                        writeLogs(text);
                        break;
                    }

                    if (itisPAT > 0) {
                        String text = "Игра окончена. Ничья." + "\n" +
                                (chessBoard.getLocalItem().getColor().equals("black") ? "Чёрные " : "Белые ") + "завели игру в ПАТ на " + stepscount + " ходу.";
                        System.out.print(text);
                        writeLogs(text);
                        break;
                    }

                } else continue;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка в main(). Попробуйте снова");
                writeLogs("Ошибка в main(). Попробуйте снова");
            }
        }
        mainBoard.endGame();
    }

    private static int getStepscount() {
        return stepscount;
    }

    public static boolean canMove(byte x, byte y) {
        if (chessBoard.getLocalItem() == null) // фигуры, которой собираются ходить, нет на указанной клетке
        {
            return false;
        } else if (getStepscount() % 2 == 0 && chessBoard.getLocalItem().getColor().equals("black"))// ход черных или белых
        {
            return false;
        } else if (getStepscount() % 2 != 0 && chessBoard.getLocalItem().getColor().equals("white"))// ход черных или белых
        {
            return false;
        } else if (!chessBoard.getLocalItem().hasMove(x, y)) {
            return false;
        } else if (attackKing((chessBoard.getLocalItem().getColor().equals("black") ? ChessBoard.bKing : ChessBoard.wKing), chessBoard.getLocalItem(), x, y)) // нельзя ставить короля под удар, проверка на вскрытый шах
        {
            return false;
        }

        return true;
    }

    private static boolean canMove(int x, int y) {
        if (chessBoard.getLocalItem() == null) // фигуры, которой собираются ходить, нет на указанной клетке
        {
            String text = "На поле " + step.substring(0, 1) + step.substring(1, 2) + " нет фигуры. Попробуйте снова.";
            System.out.println(text);
            writeLogs(text);
            return false;
        } else if (getStepscount() % 2 == 0 && chessBoard.getLocalItem().getColor().equals("black"))// ход черных или белых
        {
            System.out.println("Нельзя. Ход белых.");
            writeLogs("Нельзя. Ход белых.");
            return false;
        } else if (getStepscount() % 2 != 0 && chessBoard.getLocalItem().getColor().equals("white"))// ход черных или белых
        {
            System.out.println("Нельзя. Ход чёрных.");
            writeLogs("Нельзя. Ход чёрных.");
            return false;
        } else if (!chessBoard.getLocalItem().hasMove(x, y)) {
            System.out.println("Так ходить нельзя. Попробуйте снова");
            writeLogs("Так ходить нельзя. Попробуйте снова");
            return false;
        } else if (attackKing((chessBoard.getLocalItem().getColor().equals("black") ? ChessBoard.bKing : ChessBoard.wKing), chessBoard.getLocalItem(), x, y)) // нельзя ставить короля под удар, проверка на вскрытый шах
        {
            System.out.println("Ваш Король будет атакован. Так ходить нельзя. Попробуйте снова");
            writeLogs("Ваш Король будет атакован. Так ходить нельзя. Попробуйте снова");
            return false;
        }

        return true;
    }

    private static void vzyatieNaProhode(ChessItem pawn) {
        if (Math.abs(pawn.getY() - vsyatienaprohodePewkaY) == 2) {
            vsyatienaprohodePewka = (Pawn) pawn;
            vsyatienaprohodePewka.markerPawn = 1;
        }
    }

    private static void prohodnayaPawn(Pawn pawn) {
        System.out.println("Вы провели пешку до последней линии игрового поля. Выберите фигуру.");
        writeLogs("Вы провели пешку до последней линии игрового поля. Выберите фигуру.");
        ChooseFigure chooseFigure = new ChooseFigure(pawn.getColor());
        chooseFigure.start();
        try {
            chooseFigure.join();
        } catch (InterruptedException e) {
            writeLogs("prohodnayaPawn catch InterruptedException");
            System.out.println("prohodnayaPawn catch InterruptedException");
            e.printStackTrace();
        }
        switch (chooseFigure.s) {
            default:
            case "queen":
                ChessBoard.items.add(new Queen(pawn.getColor(), pawn.getX(), pawn.getY()));
                ChessBoard.items.remove(pawn);
                break;
            case "rook":
                ChessBoard.items.add(new Rook(pawn.getColor(), pawn.getX(), pawn.getY()));
                ChessBoard.items.remove(pawn);
                break;
            case "bishop":
                ChessBoard.items.add(new Bishop(pawn.getColor(), pawn.getX(), pawn.getY()));
                ChessBoard.items.remove(pawn);
                break;
            case "knight":
                ChessBoard.items.add(new Knight(pawn.getColor(), pawn.getX(), pawn.getY()));
                ChessBoard.items.remove(pawn);
                break;
        }
    }

    private static void schahimat(ChessBoard board) {
        if (board.getLocalItem().getColor().equals("white")) {
            if (ChessBoard.bKing.kingUnderAttack(ChessBoard.bKing.getX(), ChessBoard.bKing.getY())) {
                if (mat(ChessBoard.bKing)) {
                    ShahIMat.runShahIMat(false); // мат
                    System.out.println("ШАХ и МАТ. Победили БЕЛЫЕ!");
                    writeLogs("ШАХ и МАТ. Победили БЕЛЫЕ!");
                    return;
                }
                ShahIMat.runShahIMat(true); // шах
                System.out.println("ШАХ ЧЁРНЫМ!");
                writeLogs("ШАХ ЧЁРНЫМ!");
            } else {
                if (pat(ChessBoard.bKing)) {
                    System.out.println("ПАТ. У ЧЕРНЫХ нет возможных ходов. Ничья!");
                    writeLogs("ПАТ. У ЧЕРНЫХ нет возможных ходов. Ничья!");
                }
            }
        } else {
            if (ChessBoard.wKing.kingUnderAttack(ChessBoard.wKing.getX(), ChessBoard.wKing.getY())) {
                if (mat(ChessBoard.wKing)) {
                    ShahIMat.runShahIMat(false); // мат
                    System.out.println("ШАХ и МАТ. Победили ЧЁРНЫЕ!");
                    writeLogs("ШАХ и МАТ. Победили ЧЁРНЫЕ!");
                    return;
                }
                ShahIMat.runShahIMat(true); // шах
                System.out.println("ШАХ БЕЛЫМ!");
                writeLogs("ШАХ БЕЛЫМ!");
            } else {
                if (pat(ChessBoard.wKing)) {
                    System.out.println("ПАТ. У БЕЛЫХ нет возможных ходов. Ничья!");
                    writeLogs("ПАТ. У БЕЛЫХ нет возможных ходов. Ничья!");
                }
            }
        }
    }

    private static boolean mat(King king) {
        //исправить. считает что мат, когда королю некуда пойти, но его может спасти(закрыть) другая фигура.
        int[] kingStepsX = {king.getX() - 1, king.getX(), king.getX() + 1};
        int[] kingStepsY = {king.getY() - 1, king.getY(), king.getY() + 1};
        //1.)Король может уйти от шаха( съесть обидчика).
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (kingStepsX[i] > 0 && kingStepsX[i] < 9 && kingStepsY[j] > 0 && kingStepsY[j] < 9) {
                    if (king.hasMove(kingStepsX[i], kingStepsY[j])) return false;
                }
            }
        }
        //2.)Сюда попадаем, если король не может спастись самостоятельно).
        // Проверяем, может ли фигура цвета короля( т.к. его ход) спасти положение.
        //атаовать меня могут 2 фигуры одновременно максимум ( конь и еще одна, кроме короля)
        //спасение: съесть атакующую фигуру или встать между ей и мной.

        //2.а.) съесть атакующую фигуру
        if (king.kingUnderAttack(king.getX(), king.getY())) {
            for (ChessItem item : ChessBoard.items) {
                if (item.getColor().equals(king.getColor()) && !(item instanceof King)) {
                    if (item.hasMove(iAmAttackYou.getX(), iAmAttackYou.getY())) {
                        if (attackKing(king, item, iAmAttackYou.getX(), iAmAttackYou.getY())) {
                            itisMAT++;
                            return true;
                        } else return false;
                    }
                }
            }
            //2.б) встать между ей и мной.
            for (ChessItem item : ChessBoard.items) {
                if (item.getColor().equals(king.getColor()) && !(item instanceof King)) {
                    for (int x = 1; x < 9; x++) {
                        for (int y = 1; y < 9; y++) {
                            if (!(x == king.getX() && y == king.getY()) && iAmAttackYou.hasMove(x, y) && item.hasMove(x, y)) {
                                if (!attackKing(king, item, x, y)) return false;
                            }
                        }
                    }
                }
            }
            itisMAT++;
            return true;
        } else return false;
    }

    private static boolean pat(King king) {

        for (ChessItem item : ChessBoard.items) {
            if (item.getColor().equals(king.getColor())) {
                for (int x = 1; x < 9; x++) {
                    for (int y = 1; y < 9; y++) {
                        if (item.hasMove(x, y)) return false;
                    }
                }
            }
        }
        itisPAT++;
        return true;

    }

    private static boolean isCorrectText(String step) {
        char[] stepArr = step.toCharArray();
        return (!(stepArr[0] == stepArr[3] && stepArr[1] == stepArr[4]));
    }

    static String whoEated(int x, int y) {
        switch (ChessBoard.isItem(x, y).toString()) {
            case "King":
                return "Короля";
            case "Queen":
                return "Ферзя";
            case "Rook":
                return "Ладью";
            case "Knight":
                return "Коня";
            case "Bishop":
                return "Слона";
            case "Pawn":
                return "Пешку";
            default:
                return "фигуру";
        }
    }

    static boolean attackKing(King myKing, ChessItem myItem, int newItemX, int newItemY) {
        int hasitem = 0;
        int defaultX = myItem.getX();
        int defaultY = myItem.getY();

        if (ChessBoard.hasItem(newItemX, newItemY)) {
            hasitem++;
            ChessBoard.isItem(newItemX, newItemY).setX(-10);
            ChessBoard.isItem(-10, newItemY).setY(-1);
        }

        myItem.setX(newItemX);
        myItem.setY(newItemY);

        for (ChessItem item : ChessBoard.items) {
            if (!item.getColor().equals(myKing.getColor()) && !(item instanceof King)) {
                if (item.hasMove(myKing.getX(), myKing.getY())) {
                    myItem.setX(defaultX);
                    myItem.setY(defaultY);
                    if (hasitem == 1) {
                        ChessBoard.isItem(-10, -1).setX(newItemX);
                        ChessBoard.isItem(newItemX, -1).setY(newItemY);
                    }
                    return true;
                } else continue;
            } else continue;
        }
        myItem.setX(defaultX);
        myItem.setY(defaultY);
        if (hasitem == 1) {
            ChessBoard.isItem(-10, -1).setX(newItemX);
            ChessBoard.isItem(newItemX, -1).setY(newItemY);
        }
        return false;
    }
}
