package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionaryapp.Adapter.MeaningAdapter;
import com.example.dictionaryapp.Adapter.PhoneticAdapter;
import com.example.dictionaryapp.Model.APIResponse;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    TextView textview_word;
    RecyclerView recycler_phonetics, recycler_meaning;

    PhoneticAdapter phoneticAdapter;
    MeaningAdapter meaningAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        textview_word = findViewById(R.id.textview_word);
        recycler_phonetics = findViewById(R.id.recycler_phonetics);
        recycler_meaning = findViewById(R.id.recycler_meaning);

        progressDialog = new ProgressDialog(this);


        getSupportActionBar().setTitle("Dictionary");

        progressDialog.setTitle("Loading...");
        progressDialog.show();

        RequestManager manager = new RequestManager(MainActivity.this);
        manager.getWordMeanings(listener,"hello");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                progressDialog.setTitle("Finding meanings for  " + query);
                progressDialog.show();

                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getWordMeanings(listener,query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            progressDialog.dismiss();
            if (apiResponse == null){
                Toast.makeText(MainActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void onError(String message) {

            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(APIResponse apiResponse) {

        textview_word.setText("word: " + apiResponse.getWord());
        recycler_phonetics.setHasFixedSize(true);
        recycler_phonetics.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
        phoneticAdapter = new PhoneticAdapter(MainActivity.this,apiResponse.getPhonetics());
        recycler_phonetics.setAdapter(phoneticAdapter);

        recycler_meaning.setHasFixedSize(true);
        recycler_meaning.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
        meaningAdapter = new MeaningAdapter(MainActivity.this,apiResponse.getMeanings());
        recycler_meaning.setAdapter(meaningAdapter);

    }
}