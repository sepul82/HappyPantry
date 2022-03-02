package activities;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import adapters.ProductsRecyclerAdapter;
import adapters.ProductsRecyclerAdapterReducido;
import adapters.UsersRecyclerAdapter;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginscreen.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import helpers.InputValidation;
import model.Products;
import model.User;
import SQL.DataBaseHelper;
import android.util.Log;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;


public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = PrincipalActivity.this;
    private TextView SaludoTextView;
    private TextView EmailSaludoTextView;
    private Button ButtonAgregar;

    private Button ButtonTodo;
    private Button ButtonCompra;
    private Button ButtonSalir;
    private RecyclerView recyclerViewProductsListReducida;
    private List<Products> listProductsReducida;
    private ProductsRecyclerAdapterReducido productsRecyclerAdapterReducido;

    private String[] Email_Usuario_Activo_Producto;
    private String product_user_id;




    private InputValidation inputValidation;
    private DataBaseHelper databaseHelper;
    private User user;
    private Products producto;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        SaludoTextView = (TextView) findViewById(R.id.tv_pantalla_nombre);
        ButtonAgregar = (Button) findViewById(R.id.bt_pantalla_agregar);

        ButtonTodo = (Button) findViewById(R.id.bt_pantalla_vertodo);
        ButtonCompra = (Button) findViewById(R.id.bt_pantalla_lista);
        ButtonSalir = (Button) findViewById(R.id.bt_pantalla_cerrarsesion);
        EmailSaludoTextView=(TextView) findViewById(R.id.tv_pantalla_email);
        recyclerViewProductsListReducida = (RecyclerView) findViewById(R.id.recyclerViewPrincipalProducts);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        ButtonAgregar.setOnClickListener(this);

        ButtonTodo.setOnClickListener(this);
        ButtonCompra.setOnClickListener(this);
        ButtonSalir.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DataBaseHelper(activity);
        user = new User();
        producto = new Products();
        String emailFromIntent1 = getIntent().getStringExtra("EMAIL");
        EmailSaludoTextView.setText(emailFromIntent1);
        producto.setEmail_producto(emailFromIntent1);
        System.out.println(producto.getEmail_producto());

        listaReducida();



    }



    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pantalla_agregar:

                Intent accountsIntentAgregarProducto = new Intent(activity, AgregarProducto.class);

                startActivity(accountsIntentAgregarProducto);
                break;
                //Se vuelve a Pantalla de Login
            case R.id.bt_pantalla_cerrarsesion:
                Intent accountsIntent = new Intent(activity, LoginActivity.class);

                startActivity(accountsIntent);
                break;
            case R.id.bt_pantalla_vertodo:

                Intent accountsIntentVerTodo = new Intent(activity, ProductsListActivity.class);

                startActivity(accountsIntentVerTodo);
                break;
            case R.id.bt_pantalla_lista:

                Intent accountsIntentListaCompra = new Intent(activity, ListaCompra.class);

                startActivity(accountsIntentListaCompra);
                break;
        }
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite(String email_product_user_id) {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listProductsReducida.clear();
                listProductsReducida.addAll(databaseHelper.getAllProducts(email_product_user_id));
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                productsRecyclerAdapterReducido.notifyDataSetChanged();
            }
        }.execute();
    }

    /**
     * Método para mostrar la Lista Reducida de los productos
     */

    private void listaReducida(){
        listProductsReducida=new ArrayList<>();

        productsRecyclerAdapterReducido = new ProductsRecyclerAdapterReducido(listProductsReducida);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewProductsListReducida.setLayoutManager(mLayoutManager);
        recyclerViewProductsListReducida.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProductsListReducida.setHasFixedSize(true);
        recyclerViewProductsListReducida.setAdapter(productsRecyclerAdapterReducido);
        databaseHelper = new DataBaseHelper(activity);
        System.out.println(producto.getEmail_producto());

        Email_Usuario_Activo_Producto= new String[]{producto.getEmail_producto()};

        product_user_id=databaseHelper.getUserID(Email_Usuario_Activo_Producto);


        getDataFromSQLite(product_user_id);

    }

    @Override
    public void onResume() {

        super.onResume();
        this.listaReducida();
    }



}
