package macbook.example.contact_inderjitsingh_c0771917_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ContactViewActivity extends AppCompatActivity {
    ArrayList<Contact> contactArrayList = new ArrayList<>();
    AutoCompleteTextView searchField;
    TextView countTV;
    SwipeRefreshLayout swipeRefresher;
    SqliteHelper sqliteHelper;
    SQLiteDatabase database;
    ContactAdapter adapter;
    Cursor cursor;

    String strId,firstName, lastName, contact, email, address;
    long id;
    int intId,totalNumberOfContacts;

    int removedPosition = 0;
    Contact removedItem;

    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public void onBackPressed()
    {
        finish();
        Intent login = new Intent(getApplicationContext(), ContactViewActivity.class);
        startActivity(login);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        sqliteHelper = new SqliteHelper(this);
        database = sqliteHelper.getWritableDatabase();
        countTV = (TextView) findViewById(R.id.count);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Contacts");
        toolbar.inflateMenu(R.menu.menu1);

        toolbar.setOnMenuItemClickListener(
                new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        getCount();
        searchField = (AutoCompleteTextView) findViewById(R.id.searchField);

        ArrayList<String> requiredArrayToAutoComplete = new ArrayList<>();
        cursor = database.rawQuery("SELECT DISTINCT FIRSTNAME,LASTNAME,EMAIL,CONTACT FROM CONTACT", new String[]{});

        if (cursor != null)
        {
            cursor.moveToFirst();
            do
            {
                String fName = cursor.getString(0);
                requiredArrayToAutoComplete.add(fName);
                String lName = cursor.getString(1);
                requiredArrayToAutoComplete.add(lName);
                String eemail = cursor.getString(2);
                requiredArrayToAutoComplete.add(eemail);
                String ccontact = cursor.getString(3);
                requiredArrayToAutoComplete.add(ccontact);
            }
            while (cursor.moveToNext());
        }
        String[] autoCompleteList = new String[requiredArrayToAutoComplete.size()];
        requiredArrayToAutoComplete.toArray(autoCompleteList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ContactViewActivity.this,android.R.layout.simple_list_item_1, autoCompleteList);
        searchField.setAdapter(arrayAdapter);

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s)
            {
                searchMethod(s.toString());
                closeKeyboard();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresher = findViewById(R.id.swipeRefresher);
        swipeRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                adapter.notifyDataSetChanged();
                Toast.makeText(ContactViewActivity.this, "List Refreshed.",Toast.LENGTH_SHORT).show();
                swipeRefresher.setRefreshing(false);
            }
        });

        cursor = database.rawQuery("SELECT * FROM CONTACT", new String[]{});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                strId = cursor.getString(0);
                id = Long.parseLong(strId);
                intId = Integer.parseInt(strId);

                firstName = cursor.getString(1);
                lastName = cursor.getString(2);
                contact = cursor.getString(3);
                email = cursor.getString(4);
                address = cursor.getString(5);

                Contact contactObject = new Contact(intId,firstName, lastName, contact, email, address);
                contactArrayList.add(contactObject);
            }
            while (cursor.moveToNext());
        }

        adapter = new ContactAdapter(this, contactArrayList);
        recyclerView.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

//                int position_dragged = dragged.getAdapterPosition();
//                int position_dragged = viewHolder.getAdapterPosition();
//                int position_target = target.getAdapterPosition();
//
//                Collections.swap(recyclerJavaClassArrayList,position_dragged,position_target);
//                adapter.notifyItemMoved(position_dragged,position_target);


                return false;
            }


            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                removedPosition = viewHolder.getAdapterPosition();
                removedItem = contactArrayList.get(viewHolder.getAdapterPosition());

                contactArrayList.remove(viewHolder.getAdapterPosition());
                removeItem((long) viewHolder.itemView.getTag());
                adapter.notifyDataSetChanged();
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                getCount();

                Snackbar snackbar = Snackbar.make(viewHolder.itemView,"1 item deleted! ", Snackbar.LENGTH_LONG);
                snackbar.setAction(" UNDO ", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        contactArrayList.add(removedPosition,removedItem);
                        adapter.notifyItemInserted(removedPosition);

                    }
                });
                snackbar.show();
                getCount();
            }

            @Override
            public int getMovementFlags (@NonNull RecyclerView
                                                 recyclerView, @NonNull RecyclerView.ViewHolder viewHolder)
            {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

                return makeMovementFlags(0, swipeFlags);

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
            {
                ColorDrawable swipeBackground = new ColorDrawable(Color.RED);
                Drawable deleteIcon = ContextCompat.getDrawable(ContactViewActivity.this,R.drawable.ic_delete);

                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight())/2;

                if(dX > 0)
                {
                    swipeBackground.setBounds(itemView.getLeft(),itemView.getTop(), Math.round(dX),itemView.getBottom());
                    deleteIcon.setBounds(itemView.getLeft() + iconMargin - Math.round(iconMargin/2),itemView.getTop() + iconMargin,itemView.getLeft() +
                            iconMargin + deleteIcon.getIntrinsicWidth(),itemView.getBottom() - iconMargin);

                }
                else
                {
                    swipeBackground.setBounds(itemView.getRight() + Math.round(dX),itemView.getTop(),itemView.getRight(),itemView.getBottom());
                    deleteIcon.setBounds(itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth(),itemView.getTop() + iconMargin,
                            itemView.getRight() - iconMargin + Math.round(iconMargin/2) ,itemView.getBottom() - iconMargin);
                }

                swipeBackground.draw(c);
                deleteIcon.draw(c);
                c.save();

                if(dX > 0)
                {
                    c.clipRect(itemView.getLeft(),itemView.getTop(), Math.round(dX),itemView.getBottom());
                }
                else
                {
                    c.clipRect(itemView.getRight() + Math.round(dX),itemView.getTop(),itemView.getRight(),itemView.getBottom());
                }
                c.restore();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        }).attachToRecyclerView(recyclerView);

    }

    private void removeItem(long id)
    {
        database = sqliteHelper.getWritableDatabase();
        database.delete("CONTACT","_id = ?",new  String[]{strId});
        getCount();
    }

    private void searchMethod(String text)
    {
        ArrayList<Contact> searchContactsArrayList = new ArrayList<>();

        for(Contact item:contactArrayList)
        {
            if(item.getFirstName().toLowerCase().contains(text.toLowerCase())
                    | item.getLastName().toLowerCase().contains(text.toLowerCase())
                    | item.getContactNumber().contains(text)
                    | item.getEmail().toLowerCase().contains(text.toLowerCase()))
            {
                searchContactsArrayList.add(item);
            }
        }
        adapter.filteredList(searchContactsArrayList);
    }

    public void getCount() {
        String countQuery = "SELECT  * FROM CONTACT";
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        countTV.setText(count+ " Contacts ");
    }


    public void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if(view!= null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1,menu);
        this.invalidateOptionsMenu();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.addContact:
            {
                Intent addContactIntent = new Intent(getApplicationContext(), AddContactActivity.class);
                startActivity(addContactIntent);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
