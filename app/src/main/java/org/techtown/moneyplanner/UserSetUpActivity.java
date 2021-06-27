package org.techtown.moneyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;

import org.techtown.moneyplanner.databinding.UserSetUpActivityBinding;

public class UserSetUpActivity extends AppCompatActivity {
    private UserSetUpActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_set_up);

        Intent intent = new Intent();

        binding.btnUserSetupOk.setOnClickListener((View v)-> {
            intent.putExtra("email", String.valueOf(binding.etUserEmail.getText()));
            intent.putExtra("name", String.valueOf(binding.etUserName.getText()));
            intent.putExtra("balance", Long.valueOf(String.valueOf(binding.etUserMoney.getText())));
            setResult(RESULT_OK, intent);
            finish();

        });

        binding.btnUserSetupCancel.setOnClickListener((View v)->{
            setResult(RESULT_CANCELED);
            finish();
        });
    }


}