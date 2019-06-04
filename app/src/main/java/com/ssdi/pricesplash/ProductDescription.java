package com.ssdi.pricesplash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ssdi.pricesplash.MainActivity.api_key;
import static com.ssdi.pricesplash.MainActivity.barcode_value;

public class ProductDescription extends AppCompatActivity {

    ApiInterface apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        if(barcode_value != null){

            Call<ResponseObject> call = apiService.getTopRatedMovies(api_key,"y",barcode_value+"");
            call.enqueue(new Callback<ResponseObject>() {
                @Override
                public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null){
                            System.out.println(response.body().toString());
                            setContentView(R.layout.productdescription);
                            ((TextView) findViewById(R.id.desc_tv)).setText(response.body().getProducts().get(0).getDescription());
                            if(response.body().getProducts().get(0).getImages().length!=0) {
                                Picasso.get().load(response.body().getProducts().get(0).getImages()[0]).into((ImageView) findViewById(R.id.product_image));
                            }((TextView) findViewById(R.id.title_tv)).setText("Product: " + response.body().getProducts().get(0).getProduct_name());
                            ((Button) findViewById(R.id.button2)).setOnClickListener(v->{
                                startActivity(new Intent(ProductDescription.this,Comparison_Screen.class)
                                .putExtra("product_name",response.body().getProducts().get(0).getProduct_name()));
                            });
                        }
                        else{
                            Toast.makeText(ProductDescription.this, "response body null", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(response.code() == 404){
                        setContentView(R.layout.productdescription);
                        ((TextView) findViewById(R.id.title_tv)).setText("Product Not Found.! Try searching by name instead" );
                        ((TextView) findViewById(R.id.desc_tv)).setText("");
                        ((Button) findViewById(R.id.button2)).setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<ResponseObject> call, Throwable t) {
                    setContentView(R.layout.productdescription);
                    System.out.println("Getting products failure" + t.getLocalizedMessage());
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
