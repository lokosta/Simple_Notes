package com.example.sokol.simple_notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ViewStub stubList;
    ViewStub stubGrid;
    ListView listView;
    GridView gridView;

    GriedAdapter griedAdapter;
    ListAdapter listAdapter;
    int currentViewMode=0;
    static final int VIEW_MODE_LIST_VIEW=0;
    static final int VIEW_MODE_GRIED_VIEW=1;

    MyDataBaseHelper db;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_Title = "title";
    public static final String COLUMN_Text = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stubList=(ViewStub) findViewById(R.id.stab_list);
        stubGrid=(ViewStub) findViewById(R.id.stab_gried);

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.mylistview);
        gridView=(GridView) findViewById(R.id.mygriedview);

        db = new MyDataBaseHelper(getApplicationContext());

        loadData();


        SharedPreferences sharedPreferences=getSharedPreferences("ViewMode",MODE_PRIVATE);
        currentViewMode=sharedPreferences.getInt("currentViewMode",VIEW_MODE_LIST_VIEW);

        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        gridView.setOnItemLongClickListener(onItemLongClickListener);

        switchView();




    }

public void switchView(){
        if(currentViewMode==VIEW_MODE_LIST_VIEW){
            stubList.setVisibility(View.VISIBLE);
            stubGrid.setVisibility(View.GONE);
        }
        else{stubList.setVisibility(View.GONE);
            stubGrid.setVisibility(View.VISIBLE);}
    setAdapters();
}

public void setAdapters(){
        if(currentViewMode==VIEW_MODE_LIST_VIEW){
            listAdapter=new ListAdapter(MainActivity.this,dataList);
            listView.setAdapter(listAdapter);
        }else{ griedAdapter=new GriedAdapter(MainActivity.this,dataList);
            gridView.setAdapter(griedAdapter);

        }
}

    @Override
    public void onResume(){
        super.onResume();
        loadData();

    }

   // click the button to add a new note
    public void addNew(View view) {
        Intent intent = new Intent(this, Add_Note.class);
        startActivity(intent);
    }


    public void loadData() {
        dataList.clear();
        Cursor cursor = db.getAllNotes();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {

                HashMap<String, String> map = new HashMap<>();
                map.put(COLUMN_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                map.put(COLUMN_Title, cursor.getString(cursor.getColumnIndex(COLUMN_Title)));
                map.put(COLUMN_Text, cursor.getString(cursor.getColumnIndex(COLUMN_Text)));


                dataList.add(map);

                switchView();

                cursor.moveToNext();
            }
        }

    }


       //  list item press to look note

        AdapterView.OnItemClickListener onItemClick=new AdapterView.OnItemClickListener()
         { @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(MainActivity.this, NoteViewActivity.class);
                i.putExtra("id", dataList.get(+position).get(COLUMN_ID));
                i.putExtra("title", dataList.get(+position).get(COLUMN_Title));
                i.putExtra("text", dataList.get(+position).get(COLUMN_Text));
                startActivity(i);

            }
        };


        //  list item long press to delete note

        AdapterView.OnItemLongClickListener onItemLongClickListener=new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                return onLongListItemClick(view,pos,id);
            }
            protected boolean onLongListItemClick(View view,final int pos,long id){
                AlertDialog alertDialog=new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Delete...");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setCancelable(true);
                alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      db.deleteNote(dataList.get(pos).get(COLUMN_ID));
                      loadData();
                      switchView();
                    }
                });
                alertDialog.show();
                return true;
            }
        };



            //create menu to select the layout
            @Override
            public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu,menu);
            return super.onCreateOptionsMenu(menu);
            }

      @Override
      public boolean onOptionsItemSelected(MenuItem Item){
      switch(Item.getItemId()){
              case R.id.item1_menu:
               if(VIEW_MODE_LIST_VIEW==currentViewMode)  {
             currentViewMode=VIEW_MODE_GRIED_VIEW;
               }
               else{currentViewMode=VIEW_MODE_LIST_VIEW;}

               switchView();
               SharedPreferences sharedPreferences=getSharedPreferences("ViewMode",MODE_PRIVATE);
               SharedPreferences.Editor editor=sharedPreferences.edit();
               editor.putInt("currentViewMode",currentViewMode);
               editor.apply();
               break;
          }
          return true;
      }

}