package com.example.grocery.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import com.example.grocery.adapters.HomeAdapter;
import com.example.grocery.adapters.PopularAdapter;
import com.example.grocery.adapters.RecommendedAdapter;
import com.example.grocery.adapters.ViewAllAdapter;
import com.example.grocery.databinding.FragmentHomeBinding;
import com.example.grocery.models.HomeCategory;
import com.example.grocery.models.PopulerModel;
import com.example.grocery.models.RecommendedModel;
import com.example.grocery.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentManager.TAG;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ScrollView mscrollView;
    ProgressBar mprogressBar;

    RecyclerView popularRec,homeCategoryRec,recommendedRec;
    FirebaseFirestore db;
    ///// popularItem
    PopularAdapter popularAdapter;
    List<PopulerModel> populerModelList;


    /////////////////////search view/////////////
    EditText searchBox;
    private List<ViewAllModel>viewAllModelList;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;


    //HomeCategory
    HomeAdapter homeAdapter;
    List<HomeCategory> homeCategoryList;

    //RecommendedItem
    RecommendedAdapter recommendedAdapter;
    List<RecommendedModel> recommendedModelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db=FirebaseFirestore.getInstance();

        popularRec=root.findViewById(R.id.poprec);
        homeCategoryRec=root.findViewById(R.id.explore_rec);
        recommendedRec=root.findViewById(R.id.recommended_rec);
        mscrollView=root.findViewById(R.id.scroll_view);
        mprogressBar=root.findViewById(R.id.progressbar);

        mprogressBar.setVisibility(View.VISIBLE);
        mscrollView.setVisibility(View.GONE);

        //poplarItem
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        populerModelList =new ArrayList<>();
        popularAdapter=new PopularAdapter(getActivity(),populerModelList);
        popularRec.setAdapter(popularAdapter);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopulerModel populerModel=document.toObject(PopulerModel.class);
                                populerModelList.add(populerModel);
                                popularAdapter.notifyDataSetChanged();

                                mprogressBar.setVisibility(View.GONE);
                                mscrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "erorr"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //HomeCategoryItem
        homeCategoryRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeCategoryList =new ArrayList<>();
        homeAdapter=new HomeAdapter(getActivity(),homeCategoryList);
        homeCategoryRec.setAdapter(homeAdapter);

        db.collection("HomeCatagory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory=document.toObject(HomeCategory.class);
                                homeCategoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "erorr"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //RecommendedItem
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedModelList =new ArrayList<>();
        recommendedAdapter=new RecommendedAdapter(getActivity(),recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);

        db.collection("Recommend")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel=document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "erorr"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        /////////////////////search view/////////////
        recyclerViewSearch=root.findViewById(R.id.search_rec);
        searchBox=root.findViewById(R.id.search_box);
        viewAllModelList=new ArrayList<>();
        viewAllAdapter=new ViewAllAdapter(getContext(),viewAllModelList);

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
          recyclerViewSearch.setHasFixedSize(true);
          searchBox.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              }

              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              }

              @Override
              public void afterTextChanged(Editable editable) {
                  if (editable.toString().isEmpty()){
                      viewAllModelList.clear();
                      viewAllAdapter.notifyDataSetChanged();
                  }else {
                      searchProduct(editable.toString());
                  }
              }
          });

        return root;
    }

    private void searchProduct(String type) {
        if (!type.isEmpty()){
            db.collection("AllProducts").whereEqualTo("type",type).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()&&task.getResult()!=null){
                            viewAllModelList.clear();
                            viewAllAdapter.notifyDataSetChanged();
                            for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                ViewAllModel viewAllModel=doc.toObject(ViewAllModel.class);
                                viewAllModelList.add(viewAllModel);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }

}