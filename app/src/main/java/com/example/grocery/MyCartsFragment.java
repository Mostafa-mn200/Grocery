package com.example.grocery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.grocery.activits.PlacedOrdersActivity1;
import com.example.grocery.adapters.MyCartAdapter;
import com.example.grocery.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartsFragment extends Fragment {

    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel>cartModelList;
    TextView overTotalAmount;
    Button buyNow;

    FirebaseFirestore db;
    FirebaseAuth auth;

    ProgressBar progressBar;
    public MyCartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_my_carts, container, false);

        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        overTotalAmount=root.findViewById(R.id.textView6);
        //LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));


        progressBar=root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);


        recyclerView=root.findViewById(R.id.rec_cart);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList=new ArrayList<>();
        myCartAdapter=new MyCartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(myCartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser()
                .getUid()).collection("AddToCart")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                            String doucomentId=documentSnapshot.getId();

                            MyCartModel myCartModel=documentSnapshot.toObject(MyCartModel.class);

                            myCartModel.setDocumentId(doucomentId);
                            cartModelList.add(myCartModel);
                            myCartAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        calculateTotalAmount(cartModelList);

                    }
                });



        buyNow=root.findViewById(R.id.buy_now_btn);
        buyNow.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), PlacedOrdersActivity1.class);
            intent.putExtra("itemList", (Serializable) cartModelList);
            startActivity(intent);
        });
        return root;
    }

    private void calculateTotalAmount(List<MyCartModel> cartModelList) {
        double totalAmount=0.0;
        for (MyCartModel myCartModel:cartModelList){
            totalAmount+=myCartModel.getTotalPrice();
        }

        overTotalAmount.setText("Total Amount :"+totalAmount);
    }


   /* public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int intentBill=intent.getIntExtra("totalAmount",0);
            overTotalAmount.setText("Total Bill :"+intentBill+"$");
        }
    };*/
}