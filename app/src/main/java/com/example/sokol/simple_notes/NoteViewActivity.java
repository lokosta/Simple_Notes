package com.example.sokol.simple_notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by MD.ISRAFIL MAHMUD on 7/15/2017.
 */

public class NoteViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        TextView titleview=(TextView)findViewById(R.id.tv1);
        TextView textview=(TextView)findViewById(R.id.tv2);

        String title=getIntent().getStringExtra("title");
        String text=getIntent().getStringExtra("text");
        titleview.setText(title);
        textview.setText(text);
    }
}
