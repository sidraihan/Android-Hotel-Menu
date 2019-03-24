package com.example.zeeshan.hotelmenuhome;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//import static android.icu.text.MessagePattern.ArgType.SELECT;


public class MainActivity extends AppCompatActivity {
    CheckBox pm,bc,ct,pp,checkBox,ch;
    Button buttonOrder,ctview,ppview,pmview,bcview;
    AlertDialog.Builder help;
    LinearLayout linearLayout;
    View view2;

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
        else if(item.getItemId()== R.id.help) {
            help = new AlertDialog.Builder(this);
            help.setMessage("This application is a simple UI app used to order the food you want to eat!Just select what you want to eat and click order and we will provide you with delicious food!");
            AlertDialog alert = help.create();
            alert.setTitle("Help");
            alert.show();
            return true;
        }
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
            //hotelDatabase.execSQL("DELETE FROM menuitems WHERE name='Chicken Tandoori'");
            //hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Chicken Tandoori','chicken_tandoori', 320, 'Prepared on tandoor. This is a traditional Indian dish.')");

            //hotelDatabase.execSQL("DROP TABLE menuitems");
            //hotelDatabase.execSQL("CREATE TABLE IF NOT EXISTS orderitems(id INTEGER PRIMARY KEY AUTOINCREMENT,bill INT(5),menuitem_id INTEGER,FOREIGN KEY(menuitem_id) REFERENCES menuitems(id))");


           /* hotelDatabase.execSQL("DROP TABLE menuitems");
            hotelDatabase.execSQL("CREATE TABLE IF NOT EXISTS menuitems(id INTEGER PRIMARY KEY,name VARCHAR, nick VARCHAR, price INT(4), description VARCHAR)");
            hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Chicken Tandoori','chicken_tandoori', 320, 'Prepared on tandoor. This is a traditional Indian dish.')");
            hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Paneer Masala','paneer_masala', 100, 'Vegetarian dish having a typical taste of Indian spices.')");
            hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Palak Paneer','palak_paneer', 120, 'Simple dish containing spinach and green chutney masala.')");
            hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Butter Chicken','butter_chicken', 150, 'Indian non vegetarian dish little spicy and buttery in nature')");
*/
            linearLayout = (LinearLayout)findViewById(R.id.linear);

            ArrayList<String> al = new ArrayList<String>();

           int numRows = hotelDatabase.rawQuery("SELECT id FROM menuitems", null).getCount();
            Log.i("numRows",Integer.toString(numRows));





            final Cursor c = hotelDatabase.rawQuery("SELECT * FROM menuitems",null);
            final int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int priceIndex = c.getColumnIndex("price");
            int nickIndex = c.getColumnIndex("nick");
            int descriptionIndex = c.getColumnIndex("description");

            c.moveToFirst();

            for (int i =0;i<numRows;i++)
            {
                al.add(c.getString(nameIndex));
                c.moveToNext();
            }

            c.moveToFirst();

            for (int j=0;j<al.size();j++)
            {
                checkBox = new CheckBox(this);
                checkBox.setId(Integer.parseInt(c.getString(idIndex)));
                checkBox.setTag(c.getString(nickIndex));
                checkBox.setText(al.get(j));
                linearLayout.addView(checkBox);
                c.moveToNext();
            }


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

    }

    public void to_display_order(View view) {
        try {


            SQLiteDatabase hotelDatabase = this.openOrCreateDatabase("Hotel", MODE_PRIVATE, null);
           // hotelDatabase.execSQL("DROP TABLE orderitems");
           // hotelDatabase.execSQL("CREATE TABLE IF NOT EXISTS orderitems(id INTEGER PRIMARY KEY,bill INT(5),menuitem_id INTEGER,FOREIGN KEY(menuitem_id) REFERENCES menuitems(id))");
            //hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Chicken Tandoori','chicken_tandoori', 320, 'Prepared on tandoor. This is a traditional Indian dish.')");

            hotelDatabase.execSQL("CREATE TABLE IF NOT EXISTS menu_order(id INTEGER PRIMARY KEY,item_id INTEGER,order_id INTEGER,FOREIGN KEY(item_id) REFERENCES menuitems(id),FOREIGN KEY(order_id) REFERENCES orderitems(id))");

            Cursor c = hotelDatabase.rawQuery("SELECT * FROM menuitems", null);
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int priceIndex = c.getColumnIndex("price");
            int descriptionIndex = c.getColumnIndex("description");
            int nickIndex = c.getColumnIndex("nick");
            int numRows = hotelDatabase.rawQuery("SELECT id FROM menuitems", null).getCount();
            c.moveToFirst();
            ArrayList<String> al = new ArrayList<>();
            String[] items={};
            int bill1=0;
            int idorderIndex;
            int billIndex;
            int numRowsOrder;



            for (int i=0;i<numRows;i++)
            {
                view2 = (View) view.getParent().getParent();
                ch = (CheckBox) view2.findViewWithTag(c.getString(nickIndex));

                if (ch.isChecked()) {
                    al.add(c.getString(nameIndex));
                    bill1+=c.getInt(priceIndex);
                    numRowsOrder = hotelDatabase.rawQuery("SELECT id FROM orderitems", null).getCount();
                    numRowsOrder++;

                    hotelDatabase.execSQL("INSERT INTO menu_order VALUES(null,"+c.getString(idIndex)+","+numRowsOrder+")");
                    int numRowsJoin = hotelDatabase.rawQuery("SELECT id FROM orderitems", null).getCount();


                    Cursor c1 = hotelDatabase.rawQuery("SELECT * FROM menu_order", null);
                    c1.moveToFirst();
                    idorderIndex = c1.getColumnIndex("item_id");
                    billIndex = c1.getColumnIndex("order_id");
                    Log.i("item_id",Integer.toString(c1.getInt(idorderIndex)));
                    Log.i("order_id",Integer.toString(c1.getInt(billIndex)));
                    c1.moveToNext();

                }


                c.moveToNext();
            }

           // hotelDatabase.execSQL("INSERT INTO orderitems (items) VALUES ("+items+")");

            Intent intent = new Intent(getApplicationContext(), DisplayOrder.class);
            intent.putExtra("order",items);
            intent.putExtra("bill",Integer.toString(bill1));
            startActivity(intent);





        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}