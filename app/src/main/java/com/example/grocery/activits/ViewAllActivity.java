package com.example.grocery.activits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.grocery.R;
import com.example.grocery.adapters.NavCategoryAdapter;
import com.example.grocery.adapters.ViewAllAdapter;
import com.example.grocery.models.NavCategoryModel;
import com.example.grocery.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    RecyclerView ViewAllRec;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;
    FirebaseFirestore db;
    Toolbar toolbar;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        String type=getIntent().getStringExtra("type");

        db=FirebaseFirestore.getInstance();

        toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);


        ViewAllRec=findViewById(R.id.view_all_rec);
        ViewAllRec.setVisibility(View.GONE);

        ViewAllRec.setLayoutManager(new LinearLayoutManager(this));
        viewAllModelList =new ArrayList<>();
        viewAllAdapter=new ViewAllAdapter(this,viewAllModelList);
        ViewAllRec.setAdapter(viewAllAdapter);

        ///////////getting fruit
        if (type!=null&&type.equalsIgnoreCase("fruit")) {
            db.collection("AllProducts").whereEqualTo("type", "fruit")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    ViewAllRec.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(ViewAllActivity.this, "error fruit" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

            ///////////getting vegetable
            if (type!=null&&type.equalsIgnoreCase("vegetable")){
                db.collection("AllProducts").whereEqualTo("type","vegetable")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                        ViewAllModel viewAllModel=documentSnapshot.toObject(ViewAllModel.class);
                                        viewAllModelList.add(viewAllModel);
                                        viewAllAdapter.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                        ViewAllRec.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    Toast.makeText(ViewAllActivity.this, "error vegetable"+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        }
        ///////////getting products
        if (type!=null&&type.equalsIgnoreCase("Products")){
            db.collection("AllProducts").whereEqualTo("type","Products")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel=documentSnapshot.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    ViewAllRec.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(ViewAllActivity.this, "error Products"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        ///////////getting fish
        if (type!=null&&type.equalsIgnoreCase("fish")){
            db.collection("AllProducts").whereEqualTo("type","fish")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel=documentSnapshot.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    ViewAllRec.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(ViewAllActivity.this, "error Products"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        ///////////getting milk
        if (type!=null&&type.equalsIgnoreCase("milk")){
            db.collection("AllProducts").whereEqualTo("type","milk")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel=documentSnapshot.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    ViewAllRec.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(ViewAllActivity.this, "error Products"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        ///////////getting egg
        if (type!=null&&type.equalsIgnoreCase("egg")){
            db.collection("AllProducts").whereEqualTo("type","egg")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel=documentSnapshot.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    ViewAllRec.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(ViewAllActivity.this, "error Products"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}