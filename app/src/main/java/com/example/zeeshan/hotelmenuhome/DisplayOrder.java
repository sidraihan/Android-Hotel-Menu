package com.example.zeeshan.hotelmenuhome;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DisplayOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_order);
        Intent intent = getIntent();

        TextView order_item;
//        order_item.setText(intent.getStringExtra("order_id"));

        TextView bill = (TextView)findViewById(R.id.bill);
        bill.setText(intent.getStringExtra("bill"));

        int no_of_items = intent.getIntExtra("order_items",0);
        int order_id = intent.getIntExtra("order_id",0);

        try{
            SQLiteDatabase hotelDatabase = this.openOrCreateDatabase("Hotel",MODE_PRIVATE,null);

            Cursor c = hotelDatabase.rawQuery("SELECT menuitems.name,menuitems.price,orderitems.bill FROM menuitems,orderitems,menu_order WHERE menuitems.id=menu_order.item_id AND menu_order.order_id= "+order_id,null);

           // hotelDatabase.rawQuery("SELECT m.name, m.price, o.bill FROM menuitems m INNER JOIN orderitems o ON m.id = o.menuitem_id",null);
int itemName = c.getColumnIndex("name");
int itemPrice = c.getColumnIndex("price");
c.moveToFirst();

StringBuffer sb = new StringBuffer();

for (int i=0;i<no_of_items;i++)
{
    sb.append(c.getString(itemName)+" - "+Integer.toString(c.getInt(itemPrice))+"\n");
    c.moveToNext();
}


             order_item = (TextView)findViewById(R.id.order_items);
       order_item.setText(sb.toString());


        }



        catch (Exception e)
        {
            e.printStackTrace();
        }

        Button payButton = (Button)findViewById(R.id.pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),Payment.class);
                startActivity(intent1);
            }
        });



    }
}

