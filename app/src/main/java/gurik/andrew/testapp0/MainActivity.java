package gurik.andrew.testapp0;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    EditText et_W;
    EditText et_w;
    EditText et_R;
    EditText et_A_tsa;
    EditText et_S;
    EditText et_k;
    EditText et_u;
    EditText et_o;
    EditText et_y1;
    EditText et_y2;
    EditText et_y3;
    EditText et_F_extraction;
    Button b_calc;

    double d_W;
    double d_w;
    double d_R;
    double d_A_tsa;
    double d_S;
    double d_k;
    double d_u;
    double d_o;
    double d_y1;
    double d_y2;
    double d_y3;

    boolean bad_input = false; // true if an input is missing

    protected double input_to_double(EditText et_input){
        String in_str = et_input.getText().toString();
        double out_dbl = 0;
        try
        {
            out_dbl = Double.parseDouble(in_str);
        }
        catch(NumberFormatException e)
        {
            //not a double
            bad_input = true;
        }

        return out_dbl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set widgets
        et_W = (EditText)findViewById(R.id.txt_W);
        et_w = (EditText)findViewById(R.id.txt_w);
        et_R = (EditText)findViewById(R.id.txt_R);
        et_A_tsa = (EditText)findViewById(R.id.txt_A_tsa);
        et_S = (EditText)findViewById(R.id.txt_S);
        et_k = (EditText)findViewById(R.id.txt_k);
        et_u = (EditText)findViewById(R.id.txt_u);
        et_o = (EditText)findViewById(R.id.txt_o);
        et_y1 = (EditText)findViewById(R.id.txt_y1);
        et_y2 = (EditText)findViewById(R.id.txt_y2);
        et_y3 = (EditText)findViewById(R.id.txt_y3);
        et_F_extraction = (EditText)findViewById(R.id.txt_F_extraction);
        b_calc = (Button)findViewById(R.id.button_calc);

        et_W.setHint("Victim Weight");
        et_w.setHint("Material Weight");
        et_R.setHint("Cylinder Radius");
        et_A_tsa.setHint("Victim Top Surface Area");
        et_S.setHint("Victim Surface Area");
        et_k.setHint("Pressure Ratio");
        et_u.setHint("μ grain on grain");
        et_o.setHint("μ grain on a victim");
        et_y1.setHint("head to feet");
        et_y2.setHint("head to grain surface ");
        et_y3.setHint("feet to grain surface");
        et_F_extraction.setHint("Extraction Force");

        // zero input structure
        gscClearInputValuesJNI();

        // Call C++ functions when 'Calculate' is clicked
        b_calc.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String output_string = "-nan";

                        bad_input = false;

                        // convert input strings to doubles
                        d_W = input_to_double(et_W);
                        d_w = input_to_double(et_w);
                        d_R =  input_to_double(et_R);
                        d_A_tsa = input_to_double(et_A_tsa);
                        d_S = input_to_double(et_S);
                        d_k = input_to_double(et_k);
                        d_u = input_to_double(et_u);
                        d_o = input_to_double(et_o);
                        d_y1 = input_to_double(et_y1);
                        d_y2 = input_to_double(et_y2);
                        d_y3 = input_to_double(et_y3);

                        if(false == bad_input)
                        {
                            // call the C++ set input values function
                            gscSetInputValuesJNI(d_W, d_w, d_R, d_A_tsa, d_S, d_k, d_u, d_o, d_y1, d_y2, d_y3);

                            output_string = gscCalcJNI();
                        }
                        else
                        {
                            output_string = "...";
                        }

                        // call the C++ calculate function
                        et_F_extraction.setText(output_string);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String gscCalcJNI();
    public native void gscClearInputValuesJNI();
    public native void gscSetInputValuesJNI(double i_W, double i_w, double i_R, double i_A_tsa, double i_S, double i_k, double i_u, double i_o, double i_y1, double i_y2, double i_y3);

}
