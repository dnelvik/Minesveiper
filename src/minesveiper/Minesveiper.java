package minesveiper;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;


// 9 = bombe
// 10 = ingen
// 11 = flagg
// 12 = tatt

public class Minesveiper {
    
    private static final int STR = 216;
    private static final int ANTALLBOMBER = 15;
    private static int flagg = ANTALLBOMBER;
    private static int[][] brett;
    private static int[][] opprinneligBrett;
    private static final int KVADRAT = 20;
    private static Canvas canvas = new Canvas(STR, STR);
    private static final int BREDDE = 10;
    private static final int HØYDE = 10;
    
    public static void lagBrett(){
        brett = new int[HØYDE][BREDDE];
        opprinneligBrett = new int[HØYDE][BREDDE];
        for (int i=0; i<ANTALLBOMBER; i++){
            int kol = (int) (Math.random()*BREDDE);
            int rad = (int) (Math.random()*HØYDE);
            if (brett[rad][kol] == 9)
                i--;
            else {
                brett[rad][kol] = 9;
                opprinneligBrett[rad][kol] = 9;
            }
        }
        lagTall();
    }
    public static void lagTall(){
        for (int i=0; i<HØYDE; i++){
            for (int x=0; x<BREDDE; x++){
                int ant = 0;
                if (brett[i][x] != 9){
                    for (int a = -1; a<2; a++){
                        if (i==9 && a==1) break;
                        else if (i == 0 && a == -1) a = 0;

                        for (int b = -1; b<2; b++){
                            if (x == 9 && b == 1) break;
                            else if (x == 0 && b == -1) b = 0;
                            if (brett[i+a][x+b] == 9)
                                ant++;
                        }
                    }
                    brett[i][x] = ant;
                    opprinneligBrett[i][x] = ant;
                }
            }
        }
    }
    
    public static Canvas tegnMS(){
        clear();
        canvas.getGraphicsContext2D().fillRect(0, 0, STR, STR);
        canvas.getGraphicsContext2D().setFill(Color.GRAY);
        lagBrett();
        for (int rad=0; rad<HØYDE; rad++){
            for (int kol=0; kol<BREDDE; kol++){
                double xKor = 3.5 +(KVADRAT*kol) + kol;
                double yKor = 1 + (KVADRAT*rad) + rad;
                canvas.getGraphicsContext2D().fillRect(xKor, yKor, KVADRAT, KVADRAT);
            }
        }
        return canvas;
    }
    
    public static int [] museKlikk(double x, double y){
        int[] kor = new int[2];
        double xK = x;
        double yK = y;
        
        int kol = 0;
        int rad = 0;
        
        //Finner kolonnen som ble trykket på
        while (xK>0){
            xK -= 21;
            kol += 1;
        }
        //Finner raden som ble trykket på
        while (yK > 0){
            yK -= 21;
            rad += 1;
        }
        kor[0] = rad; kor[1] = kol;
        return kor;
    }
    
    public static void venstreTrykk(double x, double y){
        int[] kor = museKlikk(x, y);
        int rad = kor[0] - 1;
        int kol = kor[1] - 1;
        if (brett[rad][kol] == 11 || brett[rad][kol] == 12) return;
        if (brett[rad][kol] == 9) bombeKlikk(rad, kol);
        else if (brett[rad][kol] == 0) nullKlikk(rad, kol);
        else if (brett[rad][kol] != 10) tallKlikk(rad, kol);
    }
    
    public static void høyreTrykk(double x, double y){
        int[] kor = museKlikk(x, y);
        int rad = kor[0] - 1;
        int kol = kor[1] - 1;
        double xKor = 3.5 +(KVADRAT*kol) + kol;
        double yKor = 1 + (KVADRAT*rad) + rad;
        if (brett[rad][kol] == 12) return;
        if (brett[rad][kol] != 11 && brett[rad][kol] != 10 && flagg > 0){
            brett[rad][kol] = 11;
            canvas.getGraphicsContext2D().setFill(Color.BEIGE);
            canvas.getGraphicsContext2D().fillRect(xKor, yKor, KVADRAT, KVADRAT);
            flagg--;
        } else if (brett[rad][kol] == 11){
            brett[rad][kol] = opprinneligBrett[rad][kol];
            canvas.getGraphicsContext2D().setFill(Color.GREY);
            canvas.getGraphicsContext2D().fillRect(xKor, yKor, KVADRAT, KVADRAT);
            flagg++;
        }
        if (sjekkVinn()) new Alert(Alert.AlertType.INFORMATION, "Du vant! Woop woop").showAndWait();;
    }
    
    public static void bombeKlikk(int rad, int kol){
        double xKor = 3.5 +(KVADRAT*kol) + kol;
        double yKor = 1 + (KVADRAT*rad) + rad;
        canvas.getGraphicsContext2D().setFill(Color.RED);
        canvas.getGraphicsContext2D().fillRect(xKor, yKor, KVADRAT, KVADRAT);
        new Alert(Alert.AlertType.WARNING, "Du traff en mine").showAndWait();
        tegnMS();
        
    }
    
    public static void tallKlikk(int rad, int kol){
        if (brett[rad][kol] == 12 || brett[rad][kol] == 10) return;
            double xKor = (3.5 +(KVADRAT*kol) + kol);
            double yKor = (1 + (KVADRAT*rad) + rad);
            canvas.getGraphicsContext2D().setFill(Color.LIGHTGREY);
            canvas.getGraphicsContext2D().fillRect(xKor, yKor, KVADRAT, KVADRAT);
            xKor = (3.5 +(KVADRAT*kol) + kol) + KVADRAT/2 - 3;
            yKor = (1 + (KVADRAT*rad) + rad) + KVADRAT/2 + 4.3;
            canvas.getGraphicsContext2D().setFill(Color.BLACK);
            canvas.getGraphicsContext2D().fillText("" + brett[rad][kol], xKor, yKor);
            brett[rad][kol] = 12;
    }
    
    public static void nullKlikk(int rad, int kol){
        if (brett[rad][kol] == 10) return;
        double xKor = 3.5 +(KVADRAT*kol) + kol;
        double yKor = 1 + (KVADRAT*rad) + rad;
        canvas.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
        canvas.getGraphicsContext2D().fillRect(xKor, yKor, KVADRAT, KVADRAT);
        brett[rad][kol] = 10;
        sjekkUnder(rad, kol);
        sjekkHøyre(rad, kol);
        sjekkVenstre(rad, kol);
        sjekkOver(rad, kol);
        if (sjekkUnder(rad, kol) == 12 && sjekkHøyre(rad, kol) == 12){
            if (brett[rad+1][kol+1] == 0) nullKlikk(rad+1, kol+1);
            else tallKlikk(rad+1, kol+1);
        }
        if (sjekkUnder(rad, kol) == 12 && sjekkVenstre(rad, kol) == 12){
            if (brett[rad+1][kol-1] == 0) nullKlikk(rad+1, kol-1);
            else tallKlikk(rad+1, kol-1);
        }
        if (sjekkOver(rad, kol) == 12 && sjekkHøyre(rad, kol) == 12){
            if (brett[rad-1][kol+1] == 0) nullKlikk(rad-1, kol+1);
            else tallKlikk(rad-1, kol+1);
        }
        if (sjekkOver(rad, kol) == 12 && sjekkVenstre(rad, kol) == 12){
            if (brett[rad-1][kol-1] == 0) nullKlikk(rad-1, kol-1);
            else tallKlikk(rad-1, kol-1);        
        }
    }
    
    public static boolean sjekkVinn(){
        int antallKlart = 0;
        for (int rad=0; rad<10; rad++){
            for (int kol=0; kol<10; kol++){
                if (brett[rad][kol] == 11){
                    if (opprinneligBrett[rad][kol] == 9) antallKlart++;
                    else return false;
                }
            }
        }
        return antallKlart == ANTALLBOMBER;
    }
    
    public static int antFlagg(){
        return flagg;
    }
    
    public static void clear(){
        brett = null;
        opprinneligBrett = null;
        flagg = ANTALLBOMBER;
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
    }
    public static int sjekkOver(int rad, int kol){
        if (rad > 0){
            if (brett[rad-1][kol] == 0) nullKlikk(rad-1, kol);
            else if (brett[rad-1][kol] < 9) tallKlikk(rad-1, kol);
            else return brett[rad-1][kol];
        }
        return 0;
    }
    
    public static int sjekkUnder(int rad, int kol){
        if (rad<BREDDE-1){
            if (brett[rad+1][kol] == 0) nullKlikk(rad+1, kol);
            else if (brett[rad+1][kol] < 9) tallKlikk(rad+1, kol);
            else return brett[rad+1][kol];
        }
        return 0;
    }
    public static int sjekkHøyre(int rad, int kol){
        if (kol<BREDDE-1){
            if (brett[rad][kol+1] == 0) nullKlikk(rad, kol+1);
            else if (brett[rad][kol+1] < 9) tallKlikk(rad, kol+1);
            else return (brett[rad][kol+1]);
        }
        return 0;
    }
    public static int sjekkVenstre(int rad, int kol){
        if (kol > 0){
            if (brett[rad][kol-1] == 0) nullKlikk(rad, kol-1);
            else if (brett[rad][kol-1] < 9) tallKlikk(rad, kol-1);
            else return (brett[rad][kol-1]);
        }
        return 0;
    }
}
