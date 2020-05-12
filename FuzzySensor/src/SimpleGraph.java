import java.awt.*;
import javax.swing.*;
public class SimpleGraph extends JFrame {
    private final int WIDTH = 330;
    private final int HEIGHT = 500;
    private Container drawable;
    private JPanel canvas;
    public SimpleGraph(double[] someData) {
        super("SimpleGraph");
        drawable = getContentPane();
        canvas = new GraphCanvas(someData);
        drawable.add(canvas);
        setSize(WIDTH, HEIGHT);
    }
    public class GraphCanvas extends JPanel {
        private double[] data;
        private int points;
        private double[] XData;
        private double[] YData;
        public GraphCanvas(double[] someData) {
            super();
            data = someData;
            points = data.length / 2;
            XData = new double[points];
            YData = new double[points];
            for(int i = 0; i < points; i++) {
                XData[i] = data[i * 2];
                YData[i] = data[i * 2 + 1];
            }
        }
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            for(int i = 0; i < points - 1; i++) {
                int x0 = (int) (XData[i] + 0.5);
                int x1 = (int) (XData[i + 1] + 0.5);
                int y0 = (int) (YData[i] +0.5);
                int y1 = (int) (YData[i + 1] + 0.5);
                g2.drawLine(x0, y0, x1, y1);
                if (i == 0)
                  g2.drawString(("" + x0 + ", " + y0), x0 - 20, y0 + 10);
                if (i == points - 2)
                  g2.drawString(("" + x1 + ", " + y1), x1, y1);
            }
        }
    }
    public static void main(String[] args) {
        double[] d = { 12726, 13.300258359140091, 
        		12734, 13.300258359140091, 
        		12742, 13.300258359140091, 
        		12754, 13.300258359140091, 
        		12762, 13.300258359140091, 
        		12772, 13.300258359140091, 
        		12783, 13.300258359140091, 
        		12791, 13.300258359140091, 
        		12799, 13.300258359140091, 
        		12813, 13.300258359140091, 
        		12820, 13.300258359140091, 
        		12841, 13.300258359140091, 
        		12845, 13.300258359140091, 
        		12848, 13.300258359140091, 
        		12858, 13.300258359140091, 
        		12870, 28.542907229125316, 
        		12891, 46.647512469188975, 
        		12895, 84.8917871729082, 
        		12903, 85.46809983908726, 
        		12910, 85.58005603115892, 
        		12919, 85.7140086562922, 
        		12930, 85.73350522798067, 
        		12940, 85.72702432265909, 
        		12961, 85.73350522798067, 
        		12963, 52.260393848540865, 
        		12967, 13.308279579283521, 
        		12977, 13.300772813176566, 
        		12985, 13.300469842074145, 
        		12993, 13.300469842074145, 
        		13004, 13.300258359140091, 
        		13012, 13.300258359140091, 
        		13022, 13.300258359140091, 
        		13033, 13.300258359140091, 
        		13041, 13.300258359140091, 
        		13053, 13.300258359140091, 
        		13063, 13.300258359140091, 
        		13071, 13.300469842074145, 
                       };
        Frame f = new SimpleGraph(d);
        f.show();
    }
}