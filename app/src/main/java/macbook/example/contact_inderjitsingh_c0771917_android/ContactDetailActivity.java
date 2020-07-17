package macbook.example.contact_inderjitsingh_c0771917_android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class ContactDetailActivity extends AppCompatActivity
{
    androidx.appcompat.widget.Toolbar toolbar;
    EditText firstNameLayout,lastNameLayout,contactLayout,emailLayout,addressLayout;
    String id,strFirstName,strLastName,strContact,strEmail,strAddress,strItem;
    Button edit,save;
    SqliteHelper sqliteHelperObject;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstNameLayout = (EditText) findViewById(R.id.firstName2);
        lastNameLayout = (EditText) findViewById(R.id.lastName2);
        contactLayout = (EditText) findViewById(R.id.contactNumber2);
        emailLayout = (EditText) findViewById(R.id.email2);
        addressLayout = (EditText) findViewById(R.id.address2);

        strFirstName = firstNameLayout.getText().toString().trim();
        strLastName = lastNameLayout.getText().toString().trim();
        strContact = contactLayout.getText().toString().trim();
        strEmail = emailLayout.getText().toString().trim();
        strAddress = addressLayout.getText().toString().trim();

         edit = (Button) findViewById(R.id.clearDetail);
         save = (Button) findViewById(R.id.saveDetail);

        disableFields();

        strItem = getIntent().getStringExtra("MyClass");

            edit.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);

        sqliteHelperObject = new SqliteHelper(ContactDetailActivity.this);
        database = sqliteHelperObject.getWritableDatabase();

        Cursor cursor = sqliteHelperObject.getContactData(strItem);
        if (cursor != null && cursor.moveToFirst())
        {
              do
             {
                 id = cursor.getString(0);
                firstNameLayout.setText(cursor.getString(1));
                lastNameLayout.setText(cursor.getString(2));
                contactLayout.setText(cursor.getString(3));
                emailLayout.setText(cursor.getString(4));
                addressLayout.setText(cursor.getString(5));
               }
            while (cursor.moveToNext());
        }

        save.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                disableFields();
                save.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                strFirstName = firstNameLayout.getText().toString();
                strLastName = lastNameLayout.getText().toString();
                strContact = contactLayout.getText().toString();
                strEmail = emailLayout.getText().toString();
                strAddress = addressLayout.getText().toString();

                sqliteHelperObject.updateContact(strItem,strFirstName,strLastName,strContact,strEmail,strAddress,database);

                Toast.makeText(ContactDetailActivity.this, " Successfully Modified.", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                DisplayUtils.enableFields(firstNameLayout);
                DisplayUtils.enableFields(lastNameLayout);
                DisplayUtils.enableFields(contactLayout);
                DisplayUtils.enableFields(emailLayout);
                DisplayUtils.enableFields(addressLayout);
            }
        });

    }

    private void disableFields()
    {
        DisplayUtils.disableFields(firstNameLayout);
        DisplayUtils.disableFields(lastNameLayout);
        DisplayUtils.disableFields(contactLayout);
        DisplayUtils.disableFields(emailLayout);
        DisplayUtils.disableFields(addressLayout);
    }
}
