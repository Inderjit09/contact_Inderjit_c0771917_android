package macbook.example.contact_inderjitsingh_c0771917_android;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity
{
    androidx.appcompat.widget.Toolbar toolbar;
    EditText firstNameET,lastNameET,contactET,emailET,addressET;
    String strFirstName,strLastName,strContact,strEmail,strAddress;
    Button clear,save;
    SqliteHelper sqliteHelperObject;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstNameET = (EditText) findViewById(R.id.firstName);
        lastNameET = (EditText) findViewById(R.id.lastName);
        contactET = (EditText) findViewById(R.id.contactNumber);
        emailET = (EditText) findViewById(R.id.email);
        addressET = (EditText) findViewById(R.id.address);


        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);

        sqliteHelperObject = new SqliteHelper(AddContactActivity.this);
        database = sqliteHelperObject.getWritableDatabase();


        save.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v)
        {

            Validations.isGenericallyValid(firstNameET); Validations.isGenericallyValid(contactET);

                 strFirstName = firstNameET.getText().toString();
                 strLastName = lastNameET.getText().toString();
                 strContact = contactET.getText().toString();
                 strEmail = emailET.getText().toString();
                 strAddress = addressET.getText().toString();

                 sqliteHelperObject.insertContact(strFirstName,strLastName,strContact,strEmail,strAddress,database);
                 Toast.makeText(AddContactActivity.this, " Added Successfully. ", Toast.LENGTH_SHORT).show();
                 clearfields();

        }
    });

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                clearfields();
            }
        });

    }

   public void clearfields()
    {
        firstNameET.setText("");
        lastNameET.setText("");
        contactET.setText("");
        emailET.setText("");
        addressET.setText("");
    }
}
