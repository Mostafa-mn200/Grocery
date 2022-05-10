package com.example.grocery.activits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.grocery.R;
import com.example.grocery.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class PlacedOrdersActivity1 extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_orders1);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        List<MyCartModel> list= (List<MyCartModel>) getIntent().getSerializableExtra("itemList");
        if (list!=null&&list.size()>0){
            for (MyCartModel model:list){
                final HashMap<String,Object> cartMap=new HashMap<>();
                cartMap.put("productName",model.getProductName());
                cartMap.put("productPrice",model.getProductPrice());
                cartMap.put("CurrentDate",model.getCurrentDate());
                cartMap.put("CurrentTime",model.getCurrentTime());
                cartMap.put("totalQuantity",model.getTotalQuantity());
                cartMap.put("totalPrice",model.getTotalPrice());

                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        Toast.makeText(PlacedOrdersActivity1.this, "Your order has been placed", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }
}