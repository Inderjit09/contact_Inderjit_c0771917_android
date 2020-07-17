package macbook.example.contact_inderjitsingh_c0771917_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
{
    Context context;
    ArrayList<Contact> contactsArrayList;

    public ContactAdapter(Context context, ArrayList<Contact> contactsArrayList)
    {
        this.context = context;
        this.contactsArrayList = contactsArrayList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_cell, parent, false);
        context = parent.getContext();
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position)
    {
            final Contact contactObject = contactsArrayList.get(position);

            holder.name.setText(contactObject.getFirstName() + " " + contactObject.getLastName());
            holder.price.setText(contactObject.getContactNumber()+"");

            final long id = contactObject.getId();
            holder.itemView.setTag(id);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra("MyClass",contactObject.getFirstName());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return contactsArrayList.size();
    }

    public void filteredList(ArrayList<Contact> filteredContactArrayList)
    {
        if(filteredContactArrayList.size() == 0)
        {
            notifyDataSetChanged();
        }
        else
        {
            contactsArrayList = filteredContactArrayList;
            notifyDataSetChanged();
        }
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,price;
        ImageView image;

        ContactViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cell_name);
            price = itemView.findViewById(R.id.cell_number);
//            image = itemView.findViewById(R.id.cell_image);

        }

        }
    }

