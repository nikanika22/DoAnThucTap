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
import com.example.ticketbookingappandroidstudioproject.model.Account;
import com.example.ticketbookingappandroidstudioproject.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountAdapterAdmin extends ArrayAdapter<Account> {
    Activity context;
    int resource;
    List<Account> DSAccount;

    public MyAccountAdapterAdmin(Activity context, int resource, List<Account> DSAccount) {
        super(context, resource, DSAccount);
        this.context = context;
        this.resource = resource;
        this.DSAccount = DSAccount;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();

        View item = inflater.inflate(this.resource, null);

        TextView txtName = item.findViewById(R.id.txtName);
        TextView txtEmail = item.findViewById(R.id.txtEmail);
        TextView txtPhone = item.findViewById(R.id.txtPhone);
        TextView txtIsActive = item.findViewById(R.id.txtStatus);

        ImageView btnDelete = item.findViewById(R.id.btnDeleteAccount);
        ImageView btnUpdate = item.findViewById(R.id.btnEditAccount);

        Account Account = this.DSAccount.get(position);

        txtName.setText(Account.getFullName());
        txtEmail.setText(Account.getEmail());
        txtPhone.setText(Account.getPhone());
        if (Account.isActive()) {
            txtIsActive.setText("Đang hoạt động");
            txtIsActive.setTextColor(Color.GREEN);
        } else {
            txtIsActive.setText("Ngưng hoạt động");
            txtIsActive.setTextColor(Color.RED);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(Account);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAccountActivity.class);
                intent.putExtra("Account", Account);
                intent.putExtra("id", Account.getId());
                context.startActivity(intent);
            }
        });

        return item;
    }

    private void deleteAccount(Account Account) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("YourAppPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken != null) {
            String bearerToken = "Bearer " + authToken;

            ApiService.apiService.deleteAccount(bearerToken, Account.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
                        DSAccount.remove(Account);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Failed to delete account: " + response.message(), Toast.LENGTH_SHORT).show();
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
