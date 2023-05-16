package com.sahil.mynote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.sahil.mynote.databinding.ActivityData2Binding;
import com.sahil.mynote.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataActivity2.class);

                intent.putExtra("type","addMode");
                startActivityForResult(intent, 1);


            }
        });
        binding.recycle.setLayoutManager(new LinearLayoutManager(this));
        binding.recycle.setHasFixedSize(true);
        RVadapter adapter = new RVadapter();
        binding.recycle.setAdapter(adapter);

        noteViewModel.getALLNodes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction==ItemTouchHelper.RIGHT){
                    noteViewModel.delete(adapter.getNode(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "note deleted", Toast.LENGTH_SHORT).show();

                   // Toast.makeText(MainActivity.this, "Updating", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this,DataActivity2.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title",adapter.getNode(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("disp",adapter.getNode(viewHolder.getAdapterPosition()).getDisp());
                    intent.putExtra("id",adapter.getNode(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent,2);


                }




            }
        }).attachToRecyclerView(binding.recycle);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            String title = data.getStringExtra("title");
            String desp = data.getStringExtra("disp");

            Note note = new Note(title,desp);
            noteViewModel.insert(note);

            Toast.makeText(this, "note added", Toast.LENGTH_SHORT).show();

        }
        else if(requestCode==2){
            String title = data.getStringExtra("title");
            String desp = data.getStringExtra("disp");
            Note note = new Note(title,desp);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);


            Toast.makeText(this, "not updated", Toast.LENGTH_SHORT).show();



        }
    }
}