package com.sahil.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;

import com.sahil.mynote.databinding.ActivityData2Binding;

public class DataActivity2 extends AppCompatActivity {
    ActivityData2Binding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityData2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String type = getIntent().getStringExtra("type");
        if(type.equals("update")){
            setTitle("UPDATE");
            binding.title.setText(getIntent().getStringExtra("title"));
            binding.description.setText(getIntent().getStringExtra("disp"));
            int id = getIntent().getIntExtra("id",0);
            binding.button.setText("update note");
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title",binding.title.getText().toString());
                    intent.putExtra("disp",binding.description.getText().toString());
                    intent.putExtra("id",id);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            });

        }else{
            setTitle("Add Mode");
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title",binding.title.getText().toString());
                    intent.putExtra("disp",binding.description.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DataActivity2.this,MainActivity.class));
    }
}