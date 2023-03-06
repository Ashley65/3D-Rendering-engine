import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

class Panel{
    public static void main(String[] args){
        JFrame frame = new JFrame("Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        // Create a slider to control the heading
        JSlider headingSlider = new JSlider(0, 360, 0);
        pane.add(headingSlider, BorderLayout.SOUTH);

        // Create a slider to control the pitch
        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        pane.add(pitchSlider, BorderLayout.EAST);



        // Create an panel to display the render result
        JPanel renderPanel = new JPanel(){
            public void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0,getWidth(), getHeight());

                List<Triangle> triangles = new ArrayList<>();
                triangles.add(new Triangle(new Vectex(100, 100, 100), new Vectex(-100, -100, 100), new Vectex(-100, 100, -100), Color.BLUE));
                triangles.add(new Triangle(new Vectex(100, 100, 100), new Vectex(-100, -100, 100), new Vectex(100, -100, -100), Color.RED));
                triangles.add(new Triangle(new Vectex(100, 100, 100), new Vectex(100, -100, -100), new Vectex(-100, 100, -100), Color.GREEN));
                triangles.add(new Triangle(new Vectex(-100, -100, 100), new Vectex(100, -100, -100), new Vectex(-100, 100, -100), Color.YELLOW));
                

                double heading = Math.toRadians(headingSlider.getValue());
                Matrix headingMatrix = new Matrix(new double[]{
                        Math.cos(heading), Math.sin(heading), 0,
                        -Math.sin(heading), Math.cos(heading), 0,
                        0, 0, 1
                });
                double pitch = Math.toRadians(pitchSlider.getValue());
                Matrix pitchMatrix = new Matrix(new double[]{
                        1, 0, 0,
                        0, Math.cos(pitch), Math.sin(pitch),
                        0, -Math.sin(pitch), Math.cos(pitch)
                });

                Matrix transformMatrix = headingMatrix.multiply(pitchMatrix);





                g2.translate(getWidth()/2, getHeight()/2);
                g2.setColor(Color.WHITE);
                for (Triangle triangle : triangles){
                    Vectex v1 = transformMatrix.transform(triangle.vectex[0]);
                    Vectex v2 = transformMatrix.transform(triangle.vectex[1]);
                    Vectex v3 = transformMatrix.transform(triangle.vectex[2]);
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


        renderPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                double yi = 180.0 / renderPanel.getHeight();
                double xi = 180.0 / renderPanel.getWidth();
                headingSlider.setValue((int) (headingSlider.getValue() + e.getX() * xi));
                pitchSlider.setValue((int) (pitchSlider.getValue() + e.getY() * yi));
                renderPanel.repaint();

            }
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                double yi = 180.0 / renderPanel.getHeight();
                double xi = 360.0 / renderPanel.getWidth();
                headingSlider.setValue((int) (headingSlider.getValue() + e.getX() * xi));
                pitchSlider.setValue((int) (pitchSlider.getValue() + e.getY() * yi));
                renderPanel.repaint();
            }
        });

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

class Matrix{
    double[] Values;
    Matrix(double[] Values){
        this.Values = Values;
    }
    Matrix multiply(Matrix diff){
        double[] result = new double[9];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                for (int k = 0; k < 3; k++){
                    result[i * 3 + j] += this.Values[i * 3 + k] * diff.Values[k * 3 + j];
                    
        
                }
            }
        }
        return new Matrix(result);
    }
    Vectex transform(@NotNull Vectex trans){
        return new Vectex(trans.x * Values[0] + trans.y * Values[3] + trans.z * Values[6],
                trans.x * Values[1] + trans.y * Values[4] + trans.z * Values[7],
                trans.x * Values[2] + trans.y * Values[5] + trans.z * Values[8]);
    }
}

