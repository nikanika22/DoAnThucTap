package com.example.ticketbookingappandroidstudioproject.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ticketbookingappandroidstudioproject.R;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;
import com.example.ticketbookingappandroidstudioproject.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProductAdapterAdmin extends ArrayAdapter<Product> {
    Activity context;
    int resource;
    List<Product> DSProduct;

    public MyProductAdapterAdmin(Activity context, int resource, List<Product> DSProduct) {
        super(context, resource, DSProduct);
        this.context = context;
        this.resource = resource;
        this.DSProduct = DSProduct;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();

        View item = inflater.inflate(this.resource, null);

        TextView name = item.findViewById(R.id.name);
        TextView price = item.findViewById(R.id.price);
        TextView active = item.findViewById(R.id.isactive);

        ImageView btnDelete = item.findViewById(R.id.btnDelete);
        ImageView btnUpdate = item.findViewById(R.id.btnEditProduct);

        Product product = this.DSProduct.get(position);

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        if (product.isActive()) {
            active.setText("Đang hoạt động");
            active.setTextColor(Color.GREEN);
        } else {
            active.setText("Ngưng hoạt động");
            active.setTextColor(Color.RED);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(product);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateProductActivity.class);
                intent.putExtra("product", product);
                intent.putExtra("id", product.getId());
                context.startActivity(intent);
            }
        });

        return item;
    }

    private void deleteProduct(Product Product) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.deleteProduct(bearerToken, Product.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Product deleted successfully.", Toast.LENGTH_SHORT).show();
                        DSProduct.remove(Product);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Failed to delete Product: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
}
