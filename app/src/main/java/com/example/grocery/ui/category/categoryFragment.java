package com.example.grocery.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.adapters.NavCategoryAdapter;
import com.example.grocery.adapters.PopularAdapter;
import com.example.grocery.databinding.FragmentCategoryBinding;
import com.example.grocery.models.NavCategoryModel;
import com.example.grocery.models.PopulerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class categoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    RecyclerView NavCategoryRec;
    NavCategoryAdapter navCategoryAdapter;
    List<NavCategoryModel> navCategoryModelList;
    FirebaseFirestore db;

    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db=FirebaseFirestore.getInstance();

        progressBar=root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        NavCategoryRec=root.findViewById(R.id.cat_rec);
        NavCategoryRec.setVisibility(View.GONE);

        NavCategoryRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        navCategoryModelList =new ArrayList<>();
        navCategoryAdapter=new NavCategoryAdapter(getActivity(),navCategoryModelList);
        NavCategoryRec.setAdapter(navCategoryAdapter);

        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavCategoryModel navCategoryModel=document.toObject(NavCategoryModel.class);
                                navCategoryModelList.add(navCategoryModel);
                                navCategoryAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                NavCategoryRec.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "erorr"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}