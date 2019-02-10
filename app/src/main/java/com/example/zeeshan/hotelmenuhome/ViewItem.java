package com.example.zeeshan.hotelmenuhome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        String description="";
        Intent intent = getIntent();
        String itemname = intent.getStringExtra("itemname");
        ImageView imageView = (ImageView)findViewById(R.id.itemimage);
        int imageresource = getResources().getIdentifier("@drawable/"+itemname,null,this.getPackageName());
        imageView.setImageResource(imageresource);

        if (itemname.equals("paneer_masala")){
           description = "Paneer Masala is a traditional Indian dish. Also known as Paneer Tikka Masala. It is spicy in taste.Price Rs.100";
        }

        else if (itemname.equals("chicken_tandoori"))
        {
            description= "Chicken Tandoori is a non-vegetarian dish. Famous for its spicy nature. Price Rs.320";
        }

        else if (itemname.equals("butter_chicken"))
        {
            description="Butter chicken or murgh makhani is a dish, originating from the Indian subcontinent, of chicken in a mildly spiced tomato sauce.";
        }

        else if (itemname.equals("palak_paneer"))
        {
            description="Palak paneer is a vegetarian dish originating from the Indian subcontinent, consisting of paneer in a thick paste made from puréed spinach and seasoned with ginger, garlic, garam masala, and other spices. Palak paneer may be called saag paneer in some restaurants in the United States and Canada.";
        }

        TextView textView = (TextView)findViewById(R.id.itemdescription);
        textView.setText(description);
    }
}
