package com.example.dictionaryapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.R;


public class MeaningViewHolder extends RecyclerView.ViewHolder {

    public TextView textview_partsOfSpeech;
    public RecyclerView recycler_definition;

    public MeaningViewHolder(@NonNull View itemView) {
        super(itemView);

        textview_partsOfSpeech = itemView.findViewById(R.id.textview_partsOfSpeech);
        recycler_definition = itemView.findViewById(R.id.recycler_definition);
    }
}
