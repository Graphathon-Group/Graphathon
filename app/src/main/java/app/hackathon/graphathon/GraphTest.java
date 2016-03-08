/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hackathon.graphathon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.media.Image;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.*;
/**
 *
 * @author Dylan
 */
public class GraphTest extends View {
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    public int[] xpoints = new int[width];
    public int[] ypoints = new int[width];

   // public static void main(String[] args) {
        // TODO code application logic here
        //new GraphTest("test", 800, 800);

   // }
    
    public GraphTest(Context c, AttributeSet attrs, int inWidth, int inHeight) {
        super(c, attrs);
        context = c;
        width = inWidth;
        height = inHeight;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);


        // Frame can be read as 'window' here.
    /*Frame frame = new Frame(name);
    frame.add(this);
    frame.setSize(width,height);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.addWindowListener(new WindowAdapter() { 
      public void windowClosing(WindowEvent e) {System.exit(0);} 
    });*/
    
    //buffer = createImage(width, height);
  }
    public int[] getXVals()
    {
        return xpoints;
    }
    public int[] getYVals()
    {
        return ypoints;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint brush = new Paint();

        brush.setColor(Color.BLUE);
        brush.setStrokeWidth(2);
        brush.setStyle(Paint.Style.STROKE);
        Path path = new Path();

        path.moveTo(xpoints[0], ypoints[0]);
        for(int j = 0; j < xpoints.length; j++)
        {
            path.lineTo(xpoints[j],ypoints[j]);
        }
        canvas.drawPath(path, brush);
    }

    /*@Override
    public void paint(Graphics brush)
    {
        brush.setColor(Color.white);
        brush.fillRect(0,0,width,height);
        
        brush.setColor(Color.black);
        brush.drawLine(width/2,0,width/2,height);
        brush.drawLine(0,height/2,width,height/2);
        for(int i = 0; i <= width; i ++)
        {
            if(i % 20 == 0)
                brush.drawLine(i, height/2-2, i, height/2+2);
        }
        for(int i = 0; i <= height; i ++)
        {
            if(i % 20 == 0)
                brush.drawLine(width/2-2, i, width/2+2, i);
        }
        INSERT FUNCTION BELOW*/
        public void popululatePoints(String function) {
            String func = function;
            func = func.substring(func.indexOf("=") + 1);
            int base = -width / 2;
            int basey = -width / 2;
            for (int i = 0; i < width; i++) {
                xpoints[i] = base;
                ypoints[i] = (int) Math.round(generatePoint(func, basey));
                base++;
                basey++;
            }
            for (int i = 0; i < xpoints.length; i++) {
                xpoints[i] = xpoints[i] + width / 2;
                ypoints[i] = -ypoints[i] + width / 2;
            }
        }
        //System.out.println(Arrays.toString(xpoints));
        //System.out.println(Arrays.toString(ypoints));
        
        //brush.setColor(Color.BLUE);
        //brush.drawPolyline(xpoints, ypoints, xpoints.length);
        

//                   _                         __                  
//                  | |                       / _|                 
//       _   _ _ __ | |_ _____      ___ __   | |_ _   _ _ __   ___ 
//      | | | | '_ \| __/ _ \ \ /\ / / '_ \  |  _| | | | '_ \ / __|
//      | |_| | |_) | || (_) \ V  V /| | | |_| | | |_| | | | | (__ 
//       \__,_| .__/ \__\___/ \_/\_/ |_| |_(_)_|  \__,_|_| |_|\___|
//            | |                                                  
//            |_|                                               
    public double generatePoint(String func, double i)
    {
        //System.out.println(func);
        //System.out.println(i);
        //negative slope
        if(func.charAt(0) == '-')
        {
            if(func.charAt(1) >= '0' && func.charAt(1) <= '9')
            {
                String number = "";
                int j = 1;
                while(func.charAt(j) >= '0' && func.charAt(j) <= '9')
                {
                    number += func.charAt(j);
                    j++;
                }
                int slope = Integer.parseInt(number);
                if(func.charAt(j) == 'x' && j+1 >= func.length())
                    return generatePoint(func.substring(j),-i*slope);
                else if (func.charAt(j) == '/')
                    return -(20.0*((double)slope)/(generatePoint(func.substring(j+1),i)/20.0));
            }
            else
            {
                return -generatePoint(func.substring(1),i);
            }
        }
        //positive slope
        if(func.charAt(0) >= '0' && func.charAt(0) <= '9')
        {
            String number = "";
            int j = 0;
            while(func.charAt(j) >= '0' && func.charAt(j) <= '9')
            {
                number += func.charAt(j);
                j++;
            }
            int slope = Integer.parseInt(number);
            if(func.charAt(j) == 'x' && j+1 >= func.length())
                return generatePoint(func.substring(j),i*slope);
            else if (func.charAt(j) == '/')
                return (20.0*((double)slope)/(generatePoint(func.substring(j+1),i)/20.0));
            
            else return slope*generatePoint(func.substring(j),i);
        }
        //base case
        if(func.equals("x"))
        {
            return i;
        }
        
        //peeling parentheses
        else if(func.charAt(0) == '(')
        {
            return generatePoint(func.substring(1,func.lastIndexOf(")"))+func.substring(func.lastIndexOf(")")+1), i);
        }
        //trig funtions
        else if(func.indexOf("sin") == 0 || func.indexOf("cos") == 0 || func.indexOf("tan") == 0)
        {
            if(func.indexOf("sin") == 0)
            {
                return generatePoint(func.substring(func.indexOf("(")),20.0*Math.sin(generatePoint(func.substring(4, func.lastIndexOf(")")),i/20.0)));
            }
            if(func.indexOf("cos") == 0)
            {
                return generatePoint(func.substring(func.indexOf("(")),20.0*Math.cos(generatePoint(func.substring(4, func.lastIndexOf(")")),i/20.0)));
            }
            if(func.indexOf("tan") == 0)
            {
                return generatePoint(func.substring(func.indexOf("(")),20.0*Math.tan(generatePoint(func.substring(4, func.lastIndexOf(")")),i/20.0)));
            }
        }
        //x^exponent
        else if(func.charAt(0) == 'x' && func.charAt(1) == '*' && func.charAt(2) == '*')
        {
            double power = 1;
            double denom = 0.0;
            if(func.charAt(3) == '(')
            {
                double numerator = Double.parseDouble(func.substring(4, func.indexOf("/")));
                double denominator = Double.parseDouble(func.substring(func.indexOf("/")+1, func.indexOf(")")));
                power = numerator/denominator;
                denom = denominator;
            }
            else
            {
                power = Double.parseDouble(func.substring(3,4));
            }
            if(i < 0 && denom != 0 && denom % 2 != 0) 
            {
                i = -i;
                return -20.0*(Math.pow(i/20.0, power));
            }
            return 20.0*(Math.pow(i/20.0, power));
        }
        //basic operands
        if(func.charAt(0) == 'x' && func.length() != 1)
        {
            int numAfterSign = 0;
            if(func.charAt(1) == '+')
            {
                boolean skipped = false;
                int j = 2;
                String numStringForm = "";
                while(j < func.length() && !skipped)
                {
                    if(func.charAt(j) >= '0' && func.charAt(j) <= '9')
                    {
                        numStringForm += func.charAt(j);
                        j++;
                    }
                    else if(j < func.length() && !numStringForm.equals(""))
                        skipped = true;
                    if(j == func.length())
                        skipped = true;
                }
                numAfterSign = Integer.parseInt(numStringForm);
                return 20.0*generatePoint("x" + func.substring(func.indexOf(numStringForm)+numStringForm.length()),((i/20.0 + numAfterSign)));
            }
            if(func.charAt(1) == '-')
            {
                boolean skipped = false;
                int j = 2;
                String numStringForm = "";
                while(j < func.length() && !skipped)
                {
                    if(func.charAt(j) >= '0' && func.charAt(j) <= '9')
                    {
                        numStringForm += func.charAt(j);
                        j++;
                    }
                    else if(j < func.length() && !numStringForm.equals(""))
                        skipped = true;
                    if(j == func.length())
                        skipped = true;
                }
                numAfterSign = Integer.parseInt(numStringForm);
                return 20.0*generatePoint("x" + func.substring(func.indexOf(numStringForm)+numStringForm.length()),((i/20.0 - numAfterSign)));
            }
            if(func.charAt(1) == '/')
            {
                boolean skipped = false;
                int j = 2;
                String numStringForm = "";
                while(j < func.length() && !skipped)
                {
                    if(func.charAt(j) >= '0' && func.charAt(j) <= '9')
                    {
                        numStringForm += func.charAt(j);
                        j++;
                    }
                    else if(j < func.length() && !numStringForm.equals(""))
                        skipped = true;
                    if(j == func.length())
                        skipped = true;
                }
                numAfterSign = Integer.parseInt(numStringForm);
                return 20.0*generatePoint("x" + func.substring(func.indexOf(numStringForm)+numStringForm.length()),((i/20.0) / numAfterSign));
            }
        }
        //log functions
        
        return i;
    }
    
}

