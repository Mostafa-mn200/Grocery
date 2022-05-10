package com.example.grocery.activits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.grocery.R;
import com.example.grocery.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class detailedActivity extends AppCompatActivity implements View.OnClickListener {

    TextView quantity;
    int totalQuantity=1;
    int totalPrice=0;
    ImageView detailedImg,addItem,removeItem;
    TextView price,ratting,description;
    Button addToCart;
    Toolbar toolbar;
    ViewAllModel viewAllModel=null;

    FirebaseFirestore fb;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        fb=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        dis();
    }

    private void dis() {
        toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object obj=getIntent().getSerializableExtra("detail");
        if (obj instanceof ViewAllModel){
            viewAllModel=(ViewAllModel)obj;
        }

        quantity=findViewById(R.id.quantity);
        detailedImg=findViewById(R.id.detailed_img);
        addItem=findViewById(R.id.add_item);
        removeItem=findViewById(R.id.remove_item);
        price=findViewById(R.id.detailed_price);
        ratting=findViewById(R.id.detailed_rating);
        description=findViewById(R.id.detailed_desc);
        if (viewAllModel!=null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            ratting.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("Price :$"+viewAllModel.getPrice()+"");

            totalPrice=viewAllModel.getPrice()*totalQuantity;

        }
        addToCart=findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(this);
        addItem.setOnClickListener(this);
        removeItem.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_item:
                if (totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=viewAllModel.getPrice()*totalQuantity;
                }
                break;
            case R.id.remove_item:
                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=viewAllModel.getPrice()*totalQuantity;
                }
                break;
            case R.id.add_to_cart:
                addedToCart();
                break;
        }
    }

    private void addedToCart() {
        String saveCurrentDate,SaveCurrentTime;
        Calendar calForDate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime=currentTime.format(calForDate.getTime());

        final HashMap<String,Object>cartMap=new HashMap<>();
        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("CurrentDate",saveCurrentDate);
        cartMap.put("CurrentTime",SaveCurrentTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        fb.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                Toast.makeText(detailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}