package app.hackathon.graphathon;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.*;

public class GraphButtonActivity extends Activity {
    MyView pls = new MyView();
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pls.populatePoints(MyView.getFunct());
        LinearLayout buttonGraph = new LinearLayout(this);
        buttonGraph.setOrientation(LinearLayout.VERTICAL);
        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        Button Start = new Button(this);
        Start.setText("Test Button 1");
        Button Stop = new Button(this);
        Stop.setText("Test Button 2");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); // Verbose!
        
        buttons.addView(Start, lp);
        buttons.addView(Stop, lp);
        buttonGraph.addView(buttons, lp);

        //float[] xvalues = new float[] { -1.0f, 1.0f, 2.0f, 3.0f , 4.0f, 5.0f, 6.0f };
        //float[] yvalues = new float[] { 15.0f, 2.0f, 0.0f, 2.0f, -2.5f, -1.0f , -3.0f };

        System.out.println(pls.getFunct());

        float[] xvalues = new float[10];
        float[] yvalues = new float[10];
        for (int i=0;i<10;i++){
        	double temp = (-5+i*.01);
        	xvalues[i] = (float)MyView.getXpoints(i);
        	yvalues[i] = (float)MyView.getYpoints(i);
        }
        System.out.println(Arrays.toString(xvalues));
        System.out.println(Arrays.toString(yvalues));

        plot2d graph = new plot2d(this, xvalues, yvalues, 1);

        buttonGraph.addView(graph, lp);

        setContentView(buttonGraph);
        
    }

}