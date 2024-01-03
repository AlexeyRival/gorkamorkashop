package com.rivalsoftware.gorkamorkashop;

import static com.rivalsoftware.gorkamorkashop.Utility.StringToBitMap;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rivalsoftware.gorkamorkashop.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Random;

public class AdminActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ArrayList<Product> products;
    private Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_screen);

        mDatabase = FirebaseDatabase.getInstance("https://dakka-e148c-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        Bundle arguments = getIntent().getExtras();
        random = new Random();

        products = (ArrayList<Product>)arguments.getSerializable(Product.class.getSimpleName());
        Redraw();
    }
    private void Redraw()
    {
        LinearLayout myRoot = findViewById(R.id.admin_scroll);
        myRoot.removeAllViews();
        for(int i=0;i<products.size();++i)
        {
            int j=i;
            View v = LayoutInflater.from(getBaseContext()).inflate(R.layout.small_product, null,false);
            ((EditText)v.findViewById(R.id.productName)).setText(products.get(i).name);
            ((EditText)v.findViewById(R.id.productName)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    products.get(j).name=editable.toString();
                }
            });
            ((ImageView)v.findViewById(R.id.productPic)).setImageBitmap(StringToBitMap(products.get(i).picture));
            ((Button)v.findViewById(R.id.productDelete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    products.remove(j);
                    Redraw();
                }
            });
            myRoot.addView(v);
        }
    }
    public void AddNewProduct(View view)
    {
        Log.i("aaa","AAAAAAAA");
        products.add(ProductFactory.GenerateProduct(products.size(),random));
        Redraw();
    }
    public void SetProducts(View view)
    {
        mDatabase.child("Shop").setValue(null);
        for(int i=0;i<products.size();++i)
        {
            products.get(i).uid=i;
            mDatabase.child("Shop").child(products.get(i).uid+"").setValue(products.get(i));
        }
    }
}
