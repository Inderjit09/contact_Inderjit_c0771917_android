package macbook.example.contact_inderjitsingh_c0771917_android;

import android.provider.BaseColumns;

public class Contact
{
    int id;
    String firstName,lastName,contactNumber,email,address;


    public Contact(String firstName, String lastName, String contactNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
    }

    public Contact(int id, String firstName, String lastName, String contactNumber, String email, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static final class SubContactClass implements BaseColumns
    {
        public static final String TABLE_NAME = "Contact";
        public static final String COLUMN_FNAME = "FIRST NAME";
        public static final String COLUMN_LNAME = "LAST NAME";
        public static final String COLUMN_CONTACTNUMBER = "CONTACT NUMBER";
        public static final String COLUMN_EMAIL = "EMAIL";
        public static final String COLUMN_ADDRESS = "ADDRESS";

    }

}
