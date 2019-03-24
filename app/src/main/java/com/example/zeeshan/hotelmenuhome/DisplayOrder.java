package com.example.zeeshan.hotelmenuhome;

import android.app.Activity;
import android.content.Intent;
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

        TextView order_item = (TextView)findViewById(R.id.order_items);
//        order_item.setText(intent.getStringExtra("order"));

        TextView bill = (TextView)findViewById(R.id.bill);
        bill.setText(intent.getStringExtra("bill"));

        try{
            SQLiteDatabase hotelDatabase = this.openOrCreateDatabase("Hotel",MODE_PRIVATE,null);

            hotelDatabase.rawQuery("SELECT m.name, m.price, o.bill FROM menuitems m INNER JOIN orderitems o ON m.id = o.menuitem_id",null);



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

