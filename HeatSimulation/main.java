import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;


public class main 
{
    
    public static double HeatColorFunction01(int x1, float time01)
    {
        //uses Thermal diffusivity of quarts which is 1.4 mm^(2)/s    The length of the x-axis is specifically 1000 pixels long, so it's 100 mm long

        int n = 1;
        float z = 0;
        while(n < 200)
        {
            z = (z + 2550*((((float) Math.sin(1000-((double) n * 3.14)) / ( 1000 - ((float) n * (float) 3.14) ) ) - ((float) Math.sin(1000 + ((double) n * 3.14)) / ( 1000 + ((float) n * (float) 3.14) ) ))) * (float) Math.sin((double)n * 3.14 * (double) x1/1000) * (float) Math.exp(-((double) n*n*3.14*3.14 * 1.4 * (double) time01)/(1000*1000))); 
            n = n + 1;
        }

        return z;
    }

    public static float SigmaFunction(float z)
    {
        float C01;
        C01 = (float) (1/ (1 + Math.exp(-z)));

        return C01;
    }
   
    public static void main(String[] args) throws InterruptedException
    {
        JFrame frame = new JFrame();

    
        frame.setTitle("Heat Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.setVisible(true);

        BufferedImage img01 = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);


        float h = 0;

        for(int t = 0; t < 10000000; t = t + 1)
        {
            for(int x = 1; x < 1000; x++)
            {

                h = (float) HeatColorFunction01(x/10, t);

                Color color01 = new Color((float) 0.5 * main.SigmaFunction(h), (float) 0.1, (float) 0.8 * main.SigmaFunction(h));

                for(int y = 1; y < 1000; y++)
                {

                    img01.setRGB(x, y, color01.getRGB());
                
                }
            }

            frame.getContentPane().setLayout(new FlowLayout());
            frame.getContentPane().add(new JLabel(new ImageIcon(img01)));
            frame.pack();
            frame.setVisible(true);
            
            TimeUnit.SECONDS.sleep(1);

            frame.getContentPane().removeAll();

        }


    }

    
}