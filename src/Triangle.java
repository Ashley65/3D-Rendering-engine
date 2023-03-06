import java.awt.*;

class Triangle {
    Vectex[] vectex = new Vectex[3];
    Color color;

    Triangle(Vectex v1, Vectex v2, Vectex v3, Color color) {
        vectex[0] = v1;
        vectex[1] = v2;
        vectex[2] = v3;
        this.color = color;
    }
}
