package adapters;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.loginscreen.R;
import activities.ListaCompra;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListViewAdapterCompra extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    // The ListViewAdapter Constructor
    // @param context: the Context from the ListaCompra
    // @param items: The list of items in the Grocery List
    public ListViewAdapterCompra(Context context, ArrayList<String> items) {
        super(context, R.layout.list_row, items);
        this.context = context;
        list = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row, null);
            TextView name = convertView.findViewById(R.id.name);
            ImageView remove = convertView.findViewById(R.id.remove);
            ImageView copy = convertView.findViewById(R.id.copy);
            TextView number = convertView.findViewById(R.id.number);

            number.setText(position + 1 + ".");
            name.setText(list.get(position));

            // Listeners for duplicating and removing an item.
            // They use the static removeItem and addItem methods created in ListaCompra.
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListaCompra.removeItem(position);
                }
            });
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListaCompra.addItem(list.get(position));
                }
            });
        }
        return convertView;
    }

}
