import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

class Panel{
    public static void main(String[] args){
        JFrame frame = new JFrame("Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        // Create an panel to display the render result
        JPanel renderPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0,getWidth(), getHeight());

                List<Triangle> triangles = new ArrayList<>();
                triangles.add(new Triangle(new Vectex(100, 100, 100), new Vectex(-100, -100, 100), new Vectex(-100, 100, -100), Color.BLUE));
                triangles.add(new Triangle(new Vectex(100, 100, 100), new Vectex(-100, -100, 100), new Vectex(100, -100, -100), Color.RED));
                triangles.add(new Triangle(new Vectex(100, 100, 100), new Vectex(100, -100, -100), new Vectex(-100, 100, -100), Color.GREEN));
                triangles.add(new Triangle(new Vectex(-100, -100, 100), new Vectex(100, -100, -100), new Vectex(-100, 100, -100), Color.YELLOW));


                g2.translate(getWidth()/2, getHeight()/2);
                g2.setColor(Color.WHITE);
                for (Triangle triangle : triangles){
                    Path2D path = new Path2D.Double();
                    path.moveTo(triangle.vectex[0].x, triangle.vectex[0].y);
                    path.lineTo(triangle.vectex[1].x, triangle.vectex[1].y);
                    path.lineTo(triangle.vectex[2].x, triangle.vectex[2].y);
                    path.closePath();
                    g2.draw(path);
                }
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setVisible(true);




    }
}

class Vectex{
    double x; // x coordinate
    double y; // y coordinate
    double z; // z coordinate

    Vectex(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

class Triangle{
    Vectex[] vectex = new Vectex[3];
    Color color;
    Triangle(Vectex v1, Vectex v2, Vectex v3, Color color){
        vectex[0] = v1;
        vectex[1] = v2;
        vectex[2] = v3;
        this.color = color;
    }
}

