package com.example.zeeshan.hotelmenuhome;

import android.database.Cursor;
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
            help.setMessage(R.string.helpText);
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
            int nickIndex = c.getColumnIndex("nick");

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

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void to_display_order(View view) {
        try {


            SQLiteDatabase hotelDatabase = this.openOrCreateDatabase("Hotel", MODE_PRIVATE, null);
            //hotelDatabase.execSQL("DROP TABLE orderitems");
            //hotelDatabase.execSQL("CREATE TABLE IF NOT EXISTS orderitems(id INTEGER PRIMARY KEY,bill INT(5))");
            //hotelDatabase.execSQL("INSERT INTO menuitems (name,nick, price, description) VALUES ('Chicken Tandoori','chicken_tandoori', 320, 'Prepared on tandoor. This is a traditional Indian dish.')");
            //hotelDatabase.execSQL("DROP TABLE menu_order");
            //hotelDatabase.execSQL("CREATE TABLE IF NOT EXISTS menu_order(id INTEGER PRIMARY KEY,item_id INTEGER,order_id INTEGER,FOREIGN KEY(item_id) REFERENCES menuitems(id),FOREIGN KEY(order_id) REFERENCES orderitems(id))");

            Cursor c = hotelDatabase.rawQuery("SELECT * FROM menuitems", null);
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int priceIndex = c.getColumnIndex("price");
            int nickIndex = c.getColumnIndex("nick");
            int numRows = hotelDatabase.rawQuery("SELECT id FROM menuitems", null).getCount();
            c.moveToFirst();
            ArrayList<String> al = new ArrayList<>();
            String[] items={};
            int bill =0;
            int idorderIndex;
            int billIndex;
            int orderid=0;
            int ids[]=new int[100];
            int no_of_items=0;



            for (int i=0,m=0;i<numRows;i++)
            {
                view2 = (View) view.getParent().getParent();
                ch = (CheckBox) view2.findViewWithTag(c.getString(nickIndex));

                if (ch.isChecked()) {
                    al.add(c.getString(nameIndex));
                    bill +=c.getInt(priceIndex);
                    ids[m]=c.getInt(idIndex);
                    m++;
                    no_of_items++;

                }



                c.moveToNext();
            }

            hotelDatabase.execSQL("INSERT INTO orderitems VALUES(null,"+bill+")");
            orderid = hotelDatabase.rawQuery("SELECT id FROM orderitems", null).getCount();


            for (int k=0;k<ids.length;k++)
            {
                hotelDatabase.execSQL("INSERT INTO menu_order VALUES(null,"+ids[k]+","+orderid+")");
            }

            Cursor c2 = hotelDatabase.rawQuery("SELECT * FROM menu_order WHERE order_id="+orderid,null);

            int menu_index = c2.getColumnIndex("item_id");
            int order_index = c2.getColumnIndex("order_id");

            c2.moveToFirst();


            for (int l = 0;l<no_of_items;l++)
            {
                Log.i("MenuitemID:",Integer.toString(c2.getInt(menu_index)));
                Log.i("Order_id:",Integer.toString(c2.getInt(order_index)));

                c2.moveToNext();

            }




            Intent intent = new Intent(getApplicationContext(), DisplayOrder.class);
            intent.putExtra("order_items",no_of_items);
            intent.putExtra("bill",Integer.toString(bill));
            intent.putExtra("order_id",orderid);
            startActivity(intent);





        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}