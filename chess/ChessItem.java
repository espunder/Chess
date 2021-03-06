package chess;

/**
 * Фигуры
 */


interface Move {
    boolean hasMove(int x, int y);

    void toMove(int x, int y);
}

public abstract class ChessItem implements Move {

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public String toString(){
        return getClass().getSimpleName();
    }

    void setColor(String color) {
        this.color = color;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    boolean getHadSteps() {
        return hadSteps != 0;
    }

    void eatIt(int x, int y) {
        ChessBoard.items.remove(ChessBoard.isItem(x, y));
    }

    int hadSteps; // подсчет количества ходов фигуры

    private String color; // цвет

    private int x, y; // координаты


   /*private int markerPawn;

    public void setMarkerPawn(int markerPawn){
        this.markerPawn = markerPawn;
    }
    public int getMarkerPawn(){
        return this.markerPawn;
    }*/

}

class King extends ChessItem { // Король

    King(String color, int x, int y) {
        setColor(color);
        setX(x);
        setY(y);
        hadSteps = 0;
    } //конструктор

    public boolean hasMove(int x, int y) {
        /*король не может встать под шах
        ПАТ. МАТ.
        Рокировка — двойной ход, который выполняют король и ладья, ни разу не ходившие.
       Рокировка невозможна:
        +если король по ходу партии уже делал ходы
        +(включая ход-рокировку)
        +с той ладьёй, которая уже ходила
        +«вертикальная рокировка» с ладьёй, превращённой из пешки. >>>>>hadsteps решает
       Рокировка временно невозможна:
        -пока поле, на котором находится король (король находится под шахом), или поле, которое он должен пересечь или занять, атаковано одной или несколькими фигурами противника;
        +пока между королём и ладьей, предназначенными для рокировки, находится какая-либо фигура[2].*/

        if (((x - getX()) == 0 || Math.abs(x - getX()) == 1) && ((y - getY()) == 0 || Math.abs(y - getY()) == 1)) {
            if (ChessBoard.hasItem(x, y) && ChessBoard.isItem(x, y).getColor().equals(getColor()))
            {
                return false; // на новом месте фигура Вашего цвета
            }
             else return !kingUnderAttack(x, y);
        } else
            return (!getHadSteps() && y == getY() && !kingUnderAttack(getX(), getY()) && !kingUnderAttack(x, y) // рокировка
                    &&
                    (
                            (y == 1 && x == 3 && !ChessBoard.wRook1.getHadSteps() && !ChessBoard.hasItem(2, 1) && !ChessBoard.hasItem(3, 1) && !ChessBoard.hasItem(4, 1) &&
                                    !kingUnderAttack(2, 1) && !kingUnderAttack(3, 1) && !kingUnderAttack(4, 1))
                                    || (y == 1 && x == 7 && !ChessBoard.wRook2.getHadSteps() && !ChessBoard.hasItem(6, 1) && !ChessBoard.hasItem(7, 1) &&
                                    !kingUnderAttack(6, 1) && !kingUnderAttack(7, 1))
                                    || (y == 8 && x == 3 && !ChessBoard.bRook1.getHadSteps() && !ChessBoard.hasItem(2, 8) && !ChessBoard.hasItem(3, 8) && !ChessBoard.hasItem(4, 8) &&
                                    !kingUnderAttack(2, 8) && !kingUnderAttack(3, 8) && !kingUnderAttack(4, 8))
                                    || (y == 8 && x == 7 && !ChessBoard.bRook2.getHadSteps() && !ChessBoard.hasItem(6, 8) && !ChessBoard.hasItem(7, 8) &&
                                    !kingUnderAttack(6, 8) && !kingUnderAttack(7, 8))
                    )
            );
    }

    public void toMove(int x, int y) {
        if (ChessBoard.hasItem(x, y)) {
            String text = "Король ест " + Chess.whoEated(x, y);
            System.out.println(text);
            Chess.writeLogs(text);
            eatIt(x, y);
            ChessBoard.setItem(this, x, y);
        } else if (Math.abs(x - getX()) == 2) {
            if (x == 3 && y == 1) {
                ChessBoard.wRook1.setX(4);
                ChessBoard.wRook1.setY(1);
                ChessBoard.wRook1.hadSteps++;
                System.out.println("Длинная рокировка.");
                Chess.writeLogs("Длинная рокировка.");
            } else if (x == 7 && y == 1) {
                ChessBoard.wRook2.setX(6);
                ChessBoard.wRook2.setY(1);
                ChessBoard.wRook2.hadSteps++;
                System.out.println("Короткая рокировка.");
                Chess.writeLogs("Короткая рокировка.");
            } else if (x == 3 && y == 8) {
                ChessBoard.bRook1.setX(4);
                ChessBoard.bRook1.setY(8);
                ChessBoard.bRook1.hadSteps++;
                System.out.println("Длинная рокировка.");
                Chess.writeLogs("Длинная рокировка.");
            } else if (x == 7 && y == 8) {
                ChessBoard.bRook2.setX(6);
                ChessBoard.bRook2.setY(8);
                ChessBoard.bRook2.hadSteps++;
                System.out.println("Короткая рокировка.");
                Chess.writeLogs("Короткая рокировка.");
            }
            ChessBoard.setItem(this, x, y);
        } else {
            String text = "Король ходит на " + (char) (96 + x) + y;
            System.out.println(text);
            Chess.writeLogs(text);
            ChessBoard.setItem(this, x, y);
        }
    }

    boolean kingUnderAttack(int x, int y) {
        // сохраняем начальное положение короля в координаты default
        int defaultX = getX();
        int defaultY = getY();
        // ставим короля на новое место, и смотрим, будет ли он под ударом.
        if (!ChessBoard.hasItem(x, y) || (x==defaultX && y==defaultY))
        {
            this.setX(x);
            this.setY(y);
            for (ChessItem item : ChessBoard.items)
            {
                if (!item.getColor().equals(getColor()) && !(item instanceof King))
                {
                    if(item.hasMove(x,y))
                    {
                        Chess.iAmAttackYou = item;
                        this.setX(defaultX);
                        this.setY(defaultY);
                        return true;
                    }
                    else continue;
                }
                else if(!item.getColor().equals(getColor()) && (item instanceof King))
                {
                    if (((x - item.getX()) == 0 || Math.abs(x - item.getX()) == 1) && ((y - item.getY()) == 0 || Math.abs(y - item.getY()) == 1))
                    {
                        Chess.iAmAttackYou = item;
                        this.setX(defaultX);
                        this.setY(defaultY);
                        return true;
                    }
                    else continue;
                }
                else continue;
            }
            this.setX(defaultX);
            this.setY(defaultY);
            return false;
        }
        else if (!ChessBoard.isItem(x, y).getColor().equals(getColor()))
        {
            ChessBoard.isItem(x, y).setX(18);
            ChessBoard.isItem(18, y).setY(0);
            this.setX(x);
            this.setY(y);
            for (ChessItem item : ChessBoard.items)
            {
                if (!item.getColor().equals(getColor()) && !(item instanceof King))
                {
                    if(item.hasMove(x,y))
                    {
                        Chess.iAmAttackYou = item;
                        ChessBoard.isItem(18, 0).setX(x);
                        ChessBoard.isItem(x, 0).setY(y);
                        this.setX(defaultX);
                        this.setY(defaultY);
                        return true;
                    }
                    else continue;
                }
                else if(!item.getColor().equals(getColor()) && (item instanceof King))
                {
                    if (((x - item.getX()) == 0 || Math.abs(x - item.getX()) == 1) && ((y - item.getY()) == 0 || Math.abs(y - item.getY()) == 1))
                    {
                        Chess.iAmAttackYou = item;
                        ChessBoard.isItem(18, 0).setX(x);
                        ChessBoard.isItem(x, 0).setY(y);
                        this.setX(defaultX);
                        this.setY(defaultY);
                        return true;
                    }
                    else continue;
                }
                else continue;
            }
            ChessBoard.isItem(18, 0).setX(x);
            ChessBoard.isItem(x, 0).setY(y);
            this.setX(defaultX);
            this.setY(defaultY);
            return false;
        }
        else return false;
    }
}

class Queen extends ChessItem { // Ферзь(Королева)

    Queen(String color, int x, int y) {
        setColor(color);
        setX(x);
        setY(y);
        hadSteps = 0;
    } // конструктор

    @Override
    public boolean hasMove(int x, int y) {
        if (x == getX()) { //ход по вертикали
            for (int i = 0; i < Math.abs(y - getY()) - 1; i++) {
                if (ChessBoard.hasItem(x, Math.abs(getY() * ((y - getY()) / Math.abs(y - getY())) + i + 1))) // если y>getY - проверка вверх, если y<getY - вниз.
                    return false;
                else continue;
            }
            if (ChessBoard.hasItem(x, y))
                return (!ChessBoard.isItem(x, y).getColor().equals(getColor()));
            else return true;
        } else if (y == getY()) { // ход по горизонтали
            for (int i = 0; i < Math.abs(x - getX()) - 1; i++) {
                if (ChessBoard.hasItem(Math.abs(getX() * ((x - getX()) / Math.abs(x - getX())) + i + 1), y)) // если x>getX - проверка вправо, если x<getX - влево.
                    return false;
                else continue;
            }
            if (ChessBoard.hasItem(x, y))
                return (!ChessBoard.isItem(x, y).getColor().equals(getColor()));
            else return true;
        } else if (Math.abs(x - getX()) == Math.abs(y - getY())) { // ход по диагонали
            for (int i = 0; i < Math.abs(x - getX()) - 1; i++) {
                if (ChessBoard.hasItem(Math.abs(getX() * ((x - getX()) / Math.abs(x - getX())) + i + 1), Math.abs(getY() * ((y - getY()) / Math.abs(y - getY())) + i + 1))) // аналогично.
                    return false;
                else continue;
            }
            if (ChessBoard.hasItem(x, y))
                return (!ChessBoard.isItem(x, y).getColor().equals(getColor()));
            else return true;
        } else return false;
    }


    @Override
    public void toMove(int x, int y) {
        if (ChessBoard.hasItem(x, y)) {
            String text = "Ферзь ест " + Chess.whoEated(x, y);
            System.out.println(text);
            Chess.writeLogs(text);
            eatIt(x, y);
            ChessBoard.setItem(this, x, y);
        } else {
            String text = "Ферзь ходит на " + (char) (96 + x) + y;
            System.out.println(text);
            Chess.writeLogs(text);
            ChessBoard.setItem(this, x, y);
        }
    }
}

class Rook extends ChessItem { // Ладья(Тура)

    Rook(String color, int x, int y) {
        setColor(color);
        setX(x);
        setY(y);
        hadSteps = 0;
    }

    @Override
    public boolean hasMove(int x, int y) {
        if (x == getX()) { //ход по вертикали
            for (int i = 0; i < Math.abs(y - getY()) - 1; i++) {
                if (ChessBoard.hasItem(x, Math.abs(getY() * ((y - getY()) / Math.abs(y - getY())) + i + 1))) // если y>getY - проверка вверх, если y<getY - вниз.
                    return false;
                else continue;
            }
            if (ChessBoard.hasItem(x, y))
                return (!ChessBoard.isItem(x, y).getColor().equals(getColor()));
            else return true;
        } else if (y == getY()) { // ход по горизонтали
            for (int i = 0; i < Math.abs(x - getX()) - 1; i++) {
                if (ChessBoard.hasItem(Math.abs(getX() * ((x - getX()) / Math.abs(x - getX())) + i + 1), y)) // если x>getX - проверка вправо, если x<getX - влево.
                    return false;
                else continue;
            }
            if (ChessBoard.hasItem(x, y))
                return (!ChessBoard.isItem(x, y).getColor().equals(getColor()));
            else return true;
        } else return false;
    }

    @Override
    public void toMove(int x, int y) {
        if (ChessBoard.hasItem(x, y)) {
            String text = "Ладья ест " + Chess.whoEated(x, y);
            System.out.println(text);
            Chess.writeLogs(text);
            eatIt(x, y);
            ChessBoard.setItem(this, x, y);
        } else {
            String text = "Ладья ходит на " + (char) (96 + x) + y;
            System.out.println(text);
            Chess.writeLogs(text);
            ChessBoard.setItem(this, x, y);
        }
    }
}

class Knight extends ChessItem { // Конь
     Knight(String color, int x, int y) {
         setColor(color);
         setX(x);
         setY(y);
        hadSteps = 0;
    }

    @Override
    public boolean hasMove(int x, int y) {
        if ((Math.abs(x - getX()) == 1 && Math.abs(y - getY()) == 2) || (Math.abs(x - getX()) == 2 && Math.abs(y - getY()) == 1)) {
            if (ChessBoard.hasItem(x, y))
                return (!ChessBoard.isItem(x, y).getColor().equals(getColor()));
            else return true;
        } else return false;
    }

    @Override
    public void toMove(int x, int y) {
        if (ChessBoard.hasItem(x, y)) {
            String text = "Конь ест " + Chess.whoEated(x, y);
            System.out.println(text);
            Chess.writeLogs(text);
            eatIt(x, y);
            ChessBoard.setItem(this, x, y);
        } else {
            String text = "Конь ходит на " + (char) (96 + x) + y;
            System.out.println(text);
            Chess.writeLogs(text);
            ChessBoard.setItem(this, x, y);
        }
    }
}

class Bishop extends ChessItem { // Слон(Офицер)
     Bishop(String color, int x, int y) {
        setColor(color);
        setX(x);
        setY(y);
        hadSteps = 0;
    }

    @Override
    public boolean hasMove(int x, int y) {
        if (Math.abs(x - getX()) == Math.abs(y - getY())) { // ход по диагонали
            for (int i = 0; i < Math.abs(x - getX()) - 1; i++) {
                if (ChessBoard.hasItem(Math.abs(getX() * ((x - getX()) / Math.abs(x - getX())) + i + 1), Math.abs(getY() * ((y - getY()) / Math.abs(y - getY())) + i + 1))) // аналогично.
                    return false;
                else continue;
            }
            if (ChessBoard.hasItem(x, y))
                return (!ChessBoard.isItem(x, y).getColor().equals(getColor()));
            else return true;
        } else return false;
    }

    @Override
    public void toMove(int x, int y) {
        if (ChessBoard.hasItem(x, y)) {
            String text = "Слон ест " + Chess.whoEated(x, y);
            System.out.println(text);
            Chess.writeLogs(text);
            eatIt(x, y);
            ChessBoard.setItem(this, x, y);
        } else {
            String text = "Слон ходит на " + (char) (96 + x) + y;
            System.out.println(text);
            Chess.writeLogs(text);
            ChessBoard.setItem(this, x, y);
        }
    }
}

class Pawn extends ChessItem { // Пешка

    int markerPawn = 0;

     Pawn(String color, int x, int y) {
         setColor(color);
         setX(x);
         setY(y);
        hadSteps = 0;
    }

    @Override
    public boolean hasMove(int x, int y) {
        if (getColor().equals("white") && x == getX() && y == (getY() + 1) || getColor().equals("black") && x == getX() && y == (getY() - 1)) { // шаг вперед на 1 клетку
            if (ChessBoard.hasItem(x, y)) {
                return false;
            } else return true;

        } else if (getColor().equals("white") && !getHadSteps() && x == getX() && y == (getY() + 2) && !ChessBoard.hasItem(x, y) && !ChessBoard.hasItem(x, y - 1) // начальный ход пешки на 2 клетки вперед
                ||
                getColor().equals("black") && !getHadSteps() && x == getX() && y == (getY() - 2) && !ChessBoard.hasItem(x, y) && !ChessBoard.hasItem(x, y + 1)) {
            return true;
        } else
            return (
                    (getColor().equals("white") && Math.abs(x - getX()) == 1 && y == (getY() + 1) && ChessBoard.hasItem(x, y) && !ChessBoard.isItem(x, y).getColor().equals(getColor()))
                    ||
                    (getColor().equals("black") && Math.abs(x - getX()) == 1 && y == (getY() - 1) && ChessBoard.hasItem(x, y) && !ChessBoard.isItem(x, y).getColor().equals(getColor()))
                    ||
                    (getColor().equals("white") && Math.abs(x - getX()) == 1 && y == (getY() + 1) && !ChessBoard.hasItem(x, y) && ChessBoard.hasItem(x, y-1) &&
                            !ChessBoard.isItem(x, y-1).getColor().equals(getColor()) && (ChessBoard.isItem(x, y-1) instanceof Pawn) && ((Pawn)ChessBoard.isItem(x, y-1)).markerPawn==1)
                    ||
                    (getColor().equals("black") && Math.abs(x - getX()) == 1 && y == (getY() -1) && !ChessBoard.hasItem(x, y) && ChessBoard.hasItem(x, y+1) &&
                                    !ChessBoard.isItem(x, y+1).getColor().equals(getColor()) && (ChessBoard.isItem(x, y+1) instanceof Pawn) && ((Pawn)ChessBoard.isItem(x, y+1)).markerPawn==1)
            ) // попытка съесть по диагонали на 1 клетку
                    ;
    }

    @Override
    public void toMove(int x, int y) {
        if (ChessBoard.hasItem(x, y)) {
            String text = "Пешка ест " + Chess.whoEated(x, y);
            System.out.println(text);
            Chess.writeLogs(text);
            eatIt(x, y);
            ChessBoard.setItem(this, x, y);
        }
        else if (getColor().equals("white") && Math.abs(x - getX()) == 1 && y == (getY() + 1) && !ChessBoard.hasItem(x, y) && ChessBoard.hasItem(x, y-1) &&
                !ChessBoard.isItem(x, y-1).getColor().equals(getColor()) && (ChessBoard.isItem(x, y-1) instanceof Pawn) && ((Pawn)ChessBoard.isItem(x, y-1)).markerPawn==1)
        {
            String text = "Пешка ест Пешку \"на проходе\"";
            System.out.println(text);
            Chess.writeLogs(text);
            ((Pawn)ChessBoard.isItem(x, y-1)).markerPawn=0;
            eatIt(x, y-1);
            ChessBoard.setItem(this, x, y);
        }
        else if(getColor().equals("black") && Math.abs(x - getX()) == 1 && y == (getY() -1) && !ChessBoard.hasItem(x, y) && ChessBoard.hasItem(x, y+1) &&
                !ChessBoard.isItem(x, y+1).getColor().equals(getColor()) && (ChessBoard.isItem(x, y+1) instanceof Pawn) && ((Pawn)ChessBoard.isItem(x, y+1)).markerPawn==1)
        {
            String text = "Пешка ест Пешку \"на проходе\"";
            System.out.println(text);
            Chess.writeLogs(text);
            ((Pawn)ChessBoard.isItem(x, y+1)).markerPawn=0;
            eatIt(x, y+1);
            ChessBoard.setItem(this, x, y);
        }
        else {
            String text = "Пешка ходит на " + (char) (96 + x) + y;
            System.out.println(text);
            Chess.writeLogs(text);
            ChessBoard.setItem(this, x, y);
        }
    }
}
