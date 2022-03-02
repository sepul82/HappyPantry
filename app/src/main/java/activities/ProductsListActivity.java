package activities;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import adapters.ProductsRecyclerAdapter;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.loginscreen.R;
import model.Products;
import SQL.DataBaseHelper;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class ProductsListActivity extends AppCompatActivity {
    private AppCompatActivity activity = ProductsListActivity.this;

    private RecyclerView recyclerViewProductsList;
    private List<Products> listProducts;
    private ProductsRecyclerAdapter productsRecyclerAdapter;
    private DataBaseHelper databaseHelper;
    private Products products;
    private String[] Email_Usuario_Activo_Producto;
    private String product_user_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        getSupportActionBar().hide();
        initViews();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {

        recyclerViewProductsList = (RecyclerView) findViewById(R.id.recyclerViewProductsList);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listProducts = new ArrayList<>();
        products = new Products();
        productsRecyclerAdapter = new ProductsRecyclerAdapter(listProducts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewProductsList.setLayoutManager(mLayoutManager);
        recyclerViewProductsList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProductsList.setHasFixedSize(true);
        recyclerViewProductsList.setAdapter(productsRecyclerAdapter);
        databaseHelper = new DataBaseHelper(activity);
        System.out.println(products.getEmail_producto());
        System.out.println(products.getId_producto());

        Log.d(TAG, "PRODUCTLIST");
        Email_Usuario_Activo_Producto= new String[]{products.getEmail_producto()};




        product_user_id=databaseHelper.getUserID(Email_Usuario_Activo_Producto);


        getDataFromSQLite(product_user_id);
    }
    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite(String email_product_user_id) {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listProducts.clear();
                listProducts.addAll(databaseHelper.getAllProducts(email_product_user_id));
                Log.d(TAG, "GETDATA");
                System.out.println(listProducts.indexOf(products));
                System.out.println(listProducts.size());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                productsRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
