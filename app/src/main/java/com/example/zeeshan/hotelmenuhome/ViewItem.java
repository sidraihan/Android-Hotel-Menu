package com.example.zeeshan.hotelmenuhome;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        try {

            Intent intent = getIntent();
            int itemid = intent.getIntExtra("itemid", 0);
           // String itemid = intent.getStringExtra("itemid");

            SQLiteDatabase hotelDatabase = this.openOrCreateDatabase("Hotel", MODE_PRIVATE, null);

            //Cursor c = hotelDatabase.rawQuery("SELECT * FROM menuitems", null);
            Cursor c = hotelDatabase.rawQuery("SELECT * FROM menuitems WHERE TRIM(id)="+itemid, null);
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int priceIndex = c.getColumnIndex("price");
            int descriptionIndex = c.getColumnIndex("description");
            int nickIndex = c.getColumnIndex("nick");

            c.moveToFirst();

            String price="";
            String description = "";
            String name = "";

            ImageView imageView = (ImageView) findViewById(R.id.itemimage);
            int imageresource = getResources().getIdentifier("@drawable/" + "a" + itemid, null, this.getPackageName());
            imageView.setImageResource(imageresource);

           /* while(true)
            {

                if((c.getString(nickIndex)).equals(itemname))
                {
                    description=c.getString(descriptionIndex);
                    name = c.getString(nameIndex);
                    price=Integer.toString(c.getInt(priceIndex));
                    break;
                }
                c.moveToNext();
            }*/

            description=c.getString(descriptionIndex);
            name = c.getString(nameIndex);
            price=Integer.toString(c.getInt(priceIndex));

            TextView textView = (TextView) findViewById(R.id.itemdescription);
            TextView nametext = (TextView)findViewById(R.id.item);
            TextView pricetext = (TextView)findViewById(R.id.itemprice);
            nametext.setText(name);
            textView.setText(description);
            pricetext.setText("Price:Rs."+price);

        }
        catch(Exception e)
            {
                e.printStackTrace();
            }
        }

}
