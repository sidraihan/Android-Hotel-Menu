package com.example.zeeshan.hotelmenuhome;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.example.zeeshan.hotelmenuhome.R;


public class MainActivity extends AppCompatActivity {
    CheckBox pm,bc,ct,pp;
    Button buttonOrder;

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.settings)
        {
            return true;
        }
        else if(item.getItemId()== R.id.help)
            return true;

        else
            return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void to_display_order(View view)
    {
        //Getting instance of CheckBoxes and Button from the activty_main.xml file
        pm=(CheckBox)findViewById(R.id.pm);
        bc=(CheckBox)findViewById(R.id.bc);
        ct=(CheckBox)findViewById(R.id.ct);
        pp=(CheckBox)findViewById(R.id.pp);
        buttonOrder=(Button)findViewById(R.id.button);

        //Applying the Listener on the Button click

        int totalamount=0;
        StringBuilder result=new StringBuilder();
        result.append("\nYou have ordered:");
        if(pm.isChecked()){
            result.append("\n\nPaneer Masala - Rs.100");
            totalamount+=100;
        }
        if(bc.isChecked()){
            result.append("\n\nButter Chicken - Rs.150");
            totalamount+=150;
        }
        if(pp.isChecked()){
            result.append("\n\nPalak Paneer - Rs.120");
            totalamount+=120;
        }

        if(ct.isChecked()){
            result.append("\n\nChicken Tandoori - Rs.320");
            totalamount+=320;
        }
        result.append("\n\nTotal:Rs."+totalamount);
        //Displaying the message on the toast

        Intent intent = new Intent(getApplicationContext(),DisplayOrder.class);
        intent.putExtra("order",result.toString());
        startActivity(intent);
        //finish();




    }



}