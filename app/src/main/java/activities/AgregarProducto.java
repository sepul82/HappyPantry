package activities;


import android.app.DatePickerDialog;
import android.os.Bundle;

import adapters.DatePickerFragment;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.example.loginscreen.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import helpers.InputValidation;
import model.Products;
import model.User;
import SQL.DataBaseHelper;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class AgregarProducto extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = AgregarProducto.this;
    private NestedScrollView nestedScrollViewProducto;
    private TextInputLayout textInputLayoutNombreProducto;
    private TextInputLayout textInputLayoutUbicacion;
    private TextInputLayout textInputLayoutCantidad;
    private TextInputLayout textInputLayoutCaducidad;
    private TextInputEditText textInputEditTextNombreProducto;
    private TextInputEditText textInputEditTextCantidad;
    private TextInputEditText textInputEditTextUbicacion;
    private TextInputEditText textInputEditTextCaducidad;
    private AppCompatButton appCompatButtonAgregar;
    private AppCompatButton appCompatButtonVolver;
    private InputValidation inputValidationProducto;
    private DataBaseHelper databaseHelper;
    private User user;
    private Products producto;
    private String[] Email_Usuario_Activo_Producto;
    private String fecha_reverse="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollViewProducto = (NestedScrollView) findViewById(R.id.nestedScrollViewProducto);
        textInputLayoutNombreProducto = (TextInputLayout) findViewById(R.id.textInputLayoutProductName);
        textInputLayoutUbicacion = (TextInputLayout) findViewById(R.id.textInputLayoutUbicacion);
        textInputLayoutCantidad = (TextInputLayout) findViewById(R.id.textInputLayoutCantidad);
        textInputLayoutCaducidad = (TextInputLayout) findViewById(R.id.textInputLayoutCaducidad);
        textInputEditTextNombreProducto = (TextInputEditText) findViewById(R.id.textInputEditTextProductName);
        textInputEditTextCaducidad = (TextInputEditText) findViewById(R.id.textInputEditTextCaducidad);
        textInputEditTextCantidad = (TextInputEditText) findViewById(R.id.textInputEditTextCantidad);
        textInputEditTextUbicacion = (TextInputEditText) findViewById(R.id.textInputEditTextUbicacion);
        appCompatButtonAgregar = (AppCompatButton) findViewById(R.id.appCompatButtonAgregarProducto);
        appCompatButtonVolver = (AppCompatButton) findViewById(R.id.appCompatButtonCancelarProducto);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonAgregar.setOnClickListener(this);
        appCompatButtonVolver.setOnClickListener(this);
        textInputEditTextCaducidad.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidationProducto = new InputValidation(activity);
        databaseHelper = new DataBaseHelper(activity);
        user = new User();
        producto = new Products();
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonAgregarProducto:
                postDataToSQLite();
                break;
            case R.id.appCompatButtonCancelarProducto:
                finish();
                break;
            case R.id.textInputEditTextCaducidad:
                showDatePickerDialog();
                break;
        }
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     * */

    private void postDataToSQLite() {
        if (!inputValidationProducto.isInputEditTextFilled(textInputEditTextNombreProducto, textInputLayoutNombreProducto, getString(R.string.error_message_nombreproducto))) {
            return;
        }
        if (!inputValidationProducto.isInputEditTextFilled(textInputEditTextUbicacion, textInputLayoutUbicacion, getString(R.string.error_message_ubicacion))) {
            return;
        }
        if (!inputValidationProducto.isInputEditTextFilled(textInputEditTextCantidad, textInputLayoutCantidad, getString(R.string.error_message_cantidad))) {
            return;
        }
        if (!inputValidationProducto.isInputEditTextFilled(textInputEditTextCaducidad, textInputLayoutCaducidad, getString(R.string.error_message_caducidad))) {
            return;
        }

        if (!databaseHelper.checkProduct(textInputEditTextNombreProducto.getText().toString().trim(), databaseHelper.getUserID(producto.getEmail_producto()))) {

            producto.setNombre_producto(textInputEditTextNombreProducto.getText().toString().trim());

            darVueltaFecha(textInputEditTextCaducidad.getText().toString().trim());

            producto.setFecha_caducidad_producto(fecha_reverse);

            producto.setUbicacion_producto(textInputEditTextUbicacion.getText().toString().trim());

            producto.setCantidad_producto(textInputEditTextCantidad.getText().toString().trim());


            Email_Usuario_Activo_Producto= new String[]{producto.getEmail_producto()};




            producto.setUsuario_id_producto(databaseHelper.getUserID(Email_Usuario_Activo_Producto));

            //System.out.println(databaseHelper.getUserID(Email_Usuario_Activo_Producto));
            Log.d(TAG, "DespuesUSUARIOID");


            databaseHelper.addProduct(producto);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollViewProducto, getString(R.string.success_message_producto), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollViewProducto, getString(R.string.error_producto_exists), Snackbar.LENGTH_LONG).show();
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextNombreProducto.setText(null);
        textInputEditTextUbicacion.setText(null);
        textInputEditTextCantidad.setText(null);
        textInputEditTextCaducidad.setText(null);
    }

    //Método para Mostrar el Cuadro de Fecha

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                textInputEditTextCaducidad.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Método para dar la vuelta a la fecha
     * @param fecha_Edit
     * @return
     */
    private String darVueltaFecha(String fecha_Edit){

        final String OLD_FORMAT = "dd / MM / yyyy";
        final String NEW_FORMAT = "yyyy / MM / dd";

        System.out.println(fecha_Edit);

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(fecha_Edit);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        fecha_reverse = sdf.format(d);



        System.out.println(fecha_reverse);
        return fecha_reverse;

    };
}