package com.example.zeeshan.hotelmenuhome;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    CheckBox pm,bc,ct,pp;
    Button buttonOrder,ctview,ppview,pmview,bcview;

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

        try
        {
            SQLiteDatabase hotelDatabase = this.openOrCreateDatabase("Hotel",MODE_PRIVATE,null);
           // hotelDatabase.execSQL("DROP TABLE menuitems");
            /*hotelDatabase.execSQL("CREATE TABLE IF NOT EXISTS menuitems(name VARCHAR, nick VARCHAR, price INT(4), description VARCHAR)");
            hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Chicken Tandoori','chicken_tandoori', 320, 'Prepared on tandoor. This is a traditional Indian dish.')");
            hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Paneer Masala','paneer_masala', 100, 'Vegetarian dish having a typical taste of Indian spices.')");
            hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Palak Paneer','palak_paneer', 120, 'Simple dish containing spinach and green chutney masala.')");
            hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Butter Chicken','butter_chicken', 150, 'Indian non vegetarian dish little spicy and buttery in nature')");
*/


            Cursor c = hotelDatabase.rawQuery("SELECT * FROM menuitems",null);
            int nameIndex = c.getColumnIndex("name");
            int priceIndex = c.getColumnIndex("price");
            int nickIndex = c.getColumnIndex("nick");
            int descriptionIndex = c.getColumnIndex("description");

            c.moveToFirst();

            while(c != null){

                Log.i("name",c.getString(nameIndex));
                Log.i("nick",c.getString(nickIndex));
                Log.i("price",Integer.toString(c.getInt(priceIndex)));
                Log.i("description",c.getString(descriptionIndex));
                c.moveToNext();


            }



        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
/*
        try
        {

            SQLiteDatabase hotelDatabase = this.openOrCreateDatabase("Hotel",MODE_PRIVATE,null);
            hotelDatabase.execSQL("CREATE TABLE IF NOT EXISTS menuitems (name VARCHAR, price INT(4),description VARCHAR) ");
            hotelDatabase.execSQL("INSERT INTO menuitems (name, price, description) VALUES ('Butter Chicken', 150, 'Indian dish made a little spicy and buttery.')");

            Cursor c = hotelDatabase.rawQuery("SELECT * FROM menuitems",null);
            int nameIndex = c.getColumnIndex("name");
            int priceIndex = c.getColumnIndex("price");
            int descriptionIndex = c.getColumnIndex("description");

            c.moveToFirst();

            while(c != null){

                Log.i("name",c.getString(nameIndex));
                Log.i("price",Integer.toString(c.getInt(priceIndex)));
                Log.i("description",c.getString(descriptionIndex));
                c.moveToNext();


            }
  }

        catch (Exception e)
        {
            e.printStackTrace();
        }
*/
        ctview = (Button)findViewById(R.id.ctview);
        pmview = (Button)findViewById(R.id.pmview);
        ppview = (Button)findViewById(R.id.ppview);
        bcview = (Button)findViewById(R.id.bcview);

        pmview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ViewItem.class);
                i.putExtra("itemname","paneer_masala");
                startActivity(i);
            }
        });

        ctview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ViewItem.class);
                i.putExtra("itemname","chicken_tandoori");
                startActivity(i);
                finish();
            }
        });

        bcview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ViewItem.class);
                i.putExtra("itemname","butter_chicken");
                startActivity(i);
                finish();
            }
        });

        ppview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ViewItem.class);
                i.putExtra("itemname","palak_paneer");
                startActivity(i);
                finish();
            }
        });

        ctview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Price: Rs.320",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        pmview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Price: Rs.100",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        ppview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Price: Rs.120",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        bcview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Price: Rs.150",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void to_display_order(View view)
    {
        pm=(CheckBox)findViewById(R.id.pm);
        bc=(CheckBox)findViewById(R.id.bc);
        ct=(CheckBox)findViewById(R.id.ct);
        pp=(CheckBox)findViewById(R.id.pp);
        buttonOrder=(Button)findViewById(R.id.button);


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

        Intent intent = new Intent(getApplicationContext(),DisplayOrder.class);
        intent.putExtra("order",result.toString());
        startActivity(intent);
        //finish();




    }



}