package com.example.kanum.testingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public List<Json> jsonList;
    private Gson gson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.json_view_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.json-generator.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);


        Call<List<Json>> call = service.getJson();

        call.enqueue(new Callback<List<Json>>() {
            @Override
            public void onResponse(Call<List<Json>> call, Response<List<Json>> response) {
                if(response.isSuccessful()){

                    jsonList = response.body();
                    mAdapter = new JsonAdapter(jsonList);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Json>> call, Throwable t) {

            }
        });

        return view;
    }


    public class JsonAdapter extends RecyclerView.Adapter<JsonAdapter.ViewHolder> {

        public List<Json> mSetdata;
        public int itemSelected;

        public JsonAdapter(List<Json> jsonList) {
            mSetdata = jsonList;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextViewHolder;
            public ImageView mItemViewHolder;


            public ViewHolder(View itemView) {
                super(itemView);
                mTextViewHolder = (TextView) itemView.findViewById(R.id.textViewItem);
                mItemViewHolder = (ImageView) itemView.findViewById(R.id.imageViewItem);

            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

            ViewHolder mViewHolder = new ViewHolder(view);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.mTextViewHolder.setText(mSetdata.get(position).getText());
            Glide.with(holder.mItemViewHolder.getContext())
                    .load(mSetdata.get(position).getImage())
                    .into(holder.mItemViewHolder);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition = holder.getAdapterPosition();
                    itemSelected = holder.getAdapterPosition();
                    onClickSelected(mSetdata.get(adapterPosition).getText(), mSetdata.get(adapterPosition).getImage());
                }
            });
//            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.blue));
//                    holder.mTextViewHolder.setTextColor(getResources().getColor(R.color.white));
//                    return false;
//                }
//            });
            if(itemSelected == position){
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.green));
                holder.mTextViewHolder.setTextColor(getResources().getColor(R.color.white));
            } else {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.white));
                holder.mTextViewHolder.setTextColor(getResources().getColor(R.color.black));
            }

        }

        @Override
        public int getItemCount() {
            return mSetdata.size();
        }

        public void onClickSelected(String text, String url){

            if(getActivity().getSupportFragmentManager().getFragments().size() > 1) getActivity().getSupportFragmentManager().popBackStack();

            JsonSecondFragment jsonSecFrag = new JsonSecondFragment();
            Bundle args = new Bundle();
            args.putString("text", text);
            args.putString("image", url);
            jsonSecFrag.setArguments(args);

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteiner0, jsonSecFrag, "jsonSecFragment")
                        .addToBackStack("jsonSecFragment")
                        .commit();

            } else {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteiner2, jsonSecFrag, "jsonSecFragment")
                        .addToBackStack("jsonSecFragment")
                        .commit();
                mAdapter.notifyDataSetChanged();
            }
            mAdapter.notifyDataSetChanged();
        }

    }


}