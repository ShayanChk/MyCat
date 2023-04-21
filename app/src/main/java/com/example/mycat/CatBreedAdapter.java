package com.example.mycat;
import android.widget.Filterable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CatBreedAdapter extends RecyclerView.Adapter<CatBreedAdapter.CatBreedViewHolder> implements Filterable {
    private List<CatBreed> catBreeds;
    private List<CatBreed> catBreedsFiltered;
    private Context context;

    public CatBreedAdapter(List<CatBreed> catBreeds, Context context) {
        this.catBreeds = catBreeds;
        this.catBreedsFiltered = new ArrayList<>(catBreeds);
        this.context = context;
    }

    public void setCatBreeds(List<CatBreed> catBreeds) {
        this.catBreeds = catBreeds;
        this.catBreedsFiltered = new ArrayList<>(catBreeds);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CatBreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cat_breed, parent, false);
        return new CatBreedViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull CatBreedViewHolder holder, int position) {
        CatBreed catBreed = catBreedsFiltered.get(position);
        holder.breedName.setText(catBreed.getBreed());
        holder.country.setText(catBreed.getCountry());
        holder.origin.setText(catBreed.getOrigin());
        holder.coat.setText(catBreed.getCoat());
        holder.pattern.setText(catBreed.getPattern());
    }

    @Override
    public int getItemCount() {
        return catBreedsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                List<CatBreed> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(catBreeds);
                } else {
                    for (CatBreed breed : catBreeds) {
                        if (breed.getBreed().toLowerCase().contains(query)) {
                            filteredList.add(breed);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                catBreedsFiltered.clear();
                catBreedsFiltered.addAll((List<CatBreed>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class CatBreedViewHolder extends RecyclerView.ViewHolder {
        TextView breedName;
        TextView country;
        TextView origin;
        TextView coat;
        TextView pattern;

        public CatBreedViewHolder(@NonNull View itemView) {
            super(itemView);
            breedName = itemView.findViewById(R.id.tv_breed);
            country = itemView.findViewById(R.id.tv_country_origin);
            origin = itemView.findViewById(R.id.tv_country_origin);
            coat = itemView.findViewById(R.id.tv_coat_pattern);
            pattern = itemView.findViewById(R.id.tv_coat_pattern);
        }
    }
}
