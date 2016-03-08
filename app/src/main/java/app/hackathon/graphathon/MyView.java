package app.hackathon.graphathon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

/**
 * Created by theem on 3/6/2016.
 */
public final class MyView{
    private static int width = 10;
    private static String function;
    private static int[] xpoints = new int[width];
    private static int[] ypoints = new int[width];

    public MyView(){
    }

    public static void setFunct(String s){
        function = s;
    }
    public static String getFunct(){
        return function;
    }
    public static void populatePoints(String f) {
        System.out.println(function);
        String func = f;
        func = func.substring(func.indexOf("=") + 1);
        System.out.println(func);
        int base = 0;//-width / 2;
        int basey = 0;//-width / 2;
        for (int i = 0; i < width; i++) {
            xpoints[i] = base;
            ypoints[i] = (int) Math.round(generatePoint(func, basey));
            base++;
            basey++;
        }
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] = xpoints[i];// + width / 2;
            ypoints[i] = ypoints[i];// + width / 2;
        }
    }


    public static int getXpoints(int i){
        return xpoints[i];
    }
    public static int getYpoints(int i){
        return ypoints[i];
    }

    public static double generatePoint(String func, double i)
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
                if((func.charAt(j)+"").equalsIgnoreCase("x") && j+1 >= func.length())
                    return generatePoint(func.substring(j),-i*slope);
                else if (func.charAt(j) == '/')
                    return -(((double)slope)/(generatePoint(func.substring(j+1),i)));
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
            if((func.charAt(j)+"").equalsIgnoreCase("x") && j+1 >= func.length())
                return generatePoint(func.substring(j),i*slope);
            else if (func.charAt(j) == '/')
                return (((double)slope)/(generatePoint(func.substring(j+1),i)));

            else return slope*generatePoint(func.substring(j),i);
        }
        //base case
        if(func.equalsIgnoreCase("x"))
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
                return generatePoint(func.substring(func.indexOf("(")),Math.sin(generatePoint(func.substring(4, func.lastIndexOf(")")),i)));
            }
            if(func.indexOf("cos") == 0)
            {
                return generatePoint(func.substring(func.indexOf("(")),Math.cos(generatePoint(func.substring(4, func.lastIndexOf(")")),i)));
            }
            if(func.indexOf("tan") == 0)
            {
                return generatePoint(func.substring(func.indexOf("(")),Math.tan(generatePoint(func.substring(4, func.lastIndexOf(")")),i)));
            }
        }
        //x^exponent
        else if((func.charAt(0)+"").equalsIgnoreCase("x") && func.charAt(1) == '*' && func.charAt(2) == '*')
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
                return -(Math.pow(i, power));
            }
            return (Math.pow(i, power));
        }
        //basic operands
        if((func.charAt(0)+"").equalsIgnoreCase("x") && func.length() != 1)
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
                return generatePoint("x" + func.substring(func.indexOf(numStringForm)+numStringForm.length()),((i + numAfterSign)));
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
                return generatePoint("x" + func.substring(func.indexOf(numStringForm)+numStringForm.length()),((i - numAfterSign)));
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
                return generatePoint("x" + func.substring(func.indexOf(numStringForm)+numStringForm.length()),((i) / numAfterSign));
            }
        }
        //log functions

        return i;
    }

}
