package activities;




import adapters.ListViewAdapterCompra;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.gesture.Gesture;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loginscreen.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import model.Products;

public class ListaCompra extends AppCompatActivity {

    static ListView listView;
    EditText input;
    ImageView enter;
    static ListViewAdapterCompra adapter;
    static ArrayList<String> items;
    static Context context;
    private Products producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);
        getSupportActionBar().hide();
        listView = findViewById(R.id.list);
        input = findViewById(R.id.input);
        enter = findViewById(R.id.add);
        context = getApplicationContext();

        // add hardcoded items to grocery list
        items = new ArrayList<>();


        listView.setLongClickable(true);
        adapter = new ListViewAdapterCompra(this, items);
        listView.setAdapter(adapter);

        // Display the item name when the item's row is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem = (String) listView.getItemAtPosition(position);
                Toast.makeText(ListaCompra.this, clickedItem, Toast.LENGTH_SHORT).show();
            }
        });
        // Remove an item when its row is long pressed
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeItem(i);
                return false;
            }
        });

        // add item when the user presses the enter button
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input.getText().toString();
                if (text == null || text.length() == 0) {
                    makeToast("Introduce un producto.");
                } else {
                    addItem(text);
                    input.setText("");
                    makeToast("Añadido " + text);
                }
            }
        });
        loadContent();
    }

    // function to read grocery list from file and load it into ListView
    public void loadContent() {
        String nombrefichero;
        producto = new Products();
        nombrefichero=producto.getEmail_producto();
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, nombrefichero+"lista.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String s = new String(content);
            // [Apple, Banana, Kiwi, Strawberry]
            s = s.substring(1, s.length() - 1);
            String split[] = s.split(", ");

            // There may be no items in the grocery list.
            if (split.length == 1 && split[0].isEmpty())
                items = new ArrayList<>();
            else items = new ArrayList<>(Arrays.asList(split));

            adapter = new ListViewAdapterCompra(this, items);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Override onDestroy() to save the contents of the grocery list right before the app is terminated
    @Override
    protected void onDestroy() {
        String nombrefichero;
        producto = new Products();
        nombrefichero=producto.getEmail_producto();
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, nombrefichero+"lista.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int i) {
        makeToast("Borrado: " + items.get(i));
        items.remove(i);
        listView.setAdapter(adapter);
    }

    // function to add an item given its name.
    public static void addItem(String item) {
        items.add(item);
        listView.setAdapter(adapter);
    }

    // function to make a Toast given a string
    static Toast t;

    private static void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }
}
