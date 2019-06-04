package com.ssdi.pricesplash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText searchTerm;
    public static Long barcode_value = null;
    public final static String api_key = "jdwvf7zdht0ruhiqfx75xezjutfe88";
    public static String search_input = null;
    ImageButton  search_btn, history_btn;
    Button scan_btn;
    ApiInterface apiService;
    private SharedPreferences sharedPreferences;
    public Set<String> set_products;
    ListView listView;
    ArrayAdapter<String> arrayadapter;
    String[] array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        set_products = sharedPreferences.getStringSet("products",new HashSet<String>());

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        scan_btn = findViewById(R.id.scan_btn);
        history_btn = findViewById(R.id.search_history);
        listView = findViewById(R.id.listview);
        MainActivity.barcode_value = 786162003522L;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScanActivity.class));

            }
        });

        // allocate memory for string array
        array = new String[set_products.size()];

        // copy elements from set to string array
        int i = 0;
        for (String s: set_products)
            array[i++] = s;

        arrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,array);
        listView.setAdapter(arrayadapter);
        searchTerm = findViewById(R.id.product_name_ed);
        search_btn = findViewById(R.id.search_image);
        MainActivity.search_input = searchTerm.getText().toString();
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make a copy, update it and save it
                if (searchTerm.getText().toString().isEmpty()) {
                    searchTerm.setError("Enter search term");

                } else {
                    Set<String> newStrSet = new HashSet<String>();
                    newStrSet.add(searchTerm.getText().toString());
                    newStrSet.addAll(set_products);
                    //set_products.add(searchTerm.getText().toString());
                    System.out.println(set_products);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("products", newStrSet);
                    editor.commit();

                    startActivity(new Intent(MainActivity.this, Comparison_Screen.class)
                            .putExtra("product_name", searchTerm.getText().toString()));
                }
            }
        });
        final HistoryAdapter[] adapter = new HistoryAdapter[1];
        DatabaseHelper helper = new DatabaseHelper(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MainActivity.this,Comparison_Screen.class)
                        .putExtra("product_name",array[i]));
            }
        });
        history_btn.setOnClickListener(v->{
            ArrayList<History> historyList = (ArrayList) helper.getHistory();
            adapter[0] = new HistoryAdapter(getApplicationContext(),historyList);
            final LayoutInflater factory = getLayoutInflater();

            final View alertdialogview = factory.inflate(R.layout.historylayout, null);

            RecyclerView recyclerView = alertdialogview.findViewById(R.id.alertrecyclerview);

            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            recyclerView.setLayoutManager(linearLayoutManager1);
            recyclerView.setAdapter(adapter[0]);
            new MaterialDialog.Builder(this)
                    .title("Saved Searches")
                    .customView(alertdialogview,true)
                    .cancelable(true)
                    .positiveText("Close")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        });



    }

    @Override
    protected void onResume() {
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        set_products = sharedPreferences.getStringSet("products",new HashSet<String>());

        // allocate memory for string array
        array = new String[set_products.size()];

        // copy elements from set to string array
        int i = 0;
        for (String s: set_products)
            array[i++] = s;
        arrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,array);
        listView.setAdapter(arrayadapter);
        super.onResume();
    }
}