package com.rivalsoftware.gorkamorkashop;

import static androidx.core.math.MathUtils.clamp;

import static com.rivalsoftware.gorkamorkashop.Utility.BitMapToString;
import static com.rivalsoftware.gorkamorkashop.Utility.StringToBitMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.rivalsoftware.gorkamorkashop.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ActivityMainBinding binding;
    private ArrayList<Product> products;
    private boolean isStarted;
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        mDatabase = FirebaseDatabase.getInstance("https://dakka-e148c-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        rand = new Random();
//        for(int i=0;i<5;++i)WriteToFire(i);

        products = new ArrayList<>();
        isStarted=true;
        //DownloadProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DownloadProducts();
    }

    public void DownloadProducts()
    {
        mDatabase.child("Shop").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    products = task.getResult().getValue(new GenericTypeIndicator<ArrayList<Product>>(){});
                    ShowTop();
                }
            }
        });
    }
    public void ShowTop()
    {
        if(!isStarted||products==null||products.size()==0)return;
        for(int i=0;i<5&&i<products.size();++i)
        {
            LinearLayout myRoot=findViewById(i<2?R.id.mainColumn:R.id.secondColumn);
            View v = LayoutInflater.from(getBaseContext()).inflate(R.layout.vertical_shop_card, null,false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    i<2?3f:2f
            );
            v.setLayoutParams(params);
            ((TextView)v.findViewById(R.id.card_text)).setText(products.get(i).name);
            ((ImageView)v.findViewById(R.id.card_pic)).setImageBitmap(StringToBitMap(products.get(i).picture));
            myRoot.addView(v);
        }
    }
    public void ShowProducts()
    {
        GridLayout myRoot = findViewById(R.id.mainScroll);
        float factor = this.getResources().getDisplayMetrics().density;

        for(int i=0;i<products.size();++i)
        {
            View v = LayoutInflater.from(getBaseContext()).inflate(R.layout.vertical_shop_card, null,false);
            ((TextView)v.findViewById(R.id.card_text)).setText(products.get(i).name);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int)(180*factor),
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        );
            v.setLayoutParams(params);
            ((ImageView)v.findViewById(R.id.card_pic)).setImageBitmap(StringToBitMap(products.get(i).picture));
            myRoot.addView(v);
        }
    }
    private void WriteToFire(int uid)
    {
        Product product = ProductFactory.GenerateProduct(uid,rand);
        mDatabase.child("Shop").child(product.uid+"").setValue(product);
    }
    public void BecomeSeller(View view)
    {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra(Product.class.getSimpleName(),products);
        startActivity(intent);
    }
    public void Buy(Product product)
    {

    }
}
