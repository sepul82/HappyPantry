package SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import model.User;
import model.Products;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class DataBaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";
    // User table name
    private static final String TABLE_USER = "user";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Products table name
    private static final String TABLE_PRODUCTOS = "productos";

    // Tabla Productos; Columnas

    private static final String COLUMN_PRODUCTOS_ID= "id_producto";
    private static final String COLUMN_PRODUCTOS_NOMBREPRODUCTO= "nombre_producto";
    //private static final String COLUMN_PRODUCTOS_CATEGORIA = "categoria_producto";
    private static final String COLUMN_PRODUCTOS_UBICACION = "ubicacion_producto";
    private static final String COLUMN_PRODUCTOS_CADUCIDAD = "fecha_caducidad_producto";
    private static final String COLUMN_PRODUCTOS_CANTIDAD = "cantidad_producto";
    private static final String COLUMN_PRODUCTOS_USUARIO = "usuario_id_producto";



    // create table sql query USER
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
    // drop table sql query USER
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // create table sql query PRODUCTOS
    private String CREATE_PRODUCTOS_TABLE = "CREATE TABLE "
            + TABLE_PRODUCTOS + "("
            + COLUMN_PRODUCTOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PRODUCTOS_NOMBREPRODUCTO + " TEXT, "
           // + COLUMN_PRODUCTOS_CATEGORIA + " TEXT, "
            + COLUMN_PRODUCTOS_UBICACION + " TEXT, "
            + COLUMN_PRODUCTOS_CADUCIDAD + " TEXT, "
            + COLUMN_PRODUCTOS_CANTIDAD + " INTEGER, "
            + COLUMN_PRODUCTOS_USUARIO + " INTEGER,"
            + " FOREIGN KEY ("+COLUMN_PRODUCTOS_USUARIO+") REFERENCES "+TABLE_USER+"("+COLUMN_USER_ID+"));";


    // drop table sql query PRODUCTOS
    private String DROP_PRODUCTOS_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCTOS;



    // Variable devolver el user_product_id
    private String return_email_user_id;
    /**
     * Constructor
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCTOS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUCTOS_TABLE);

        // Create tables again
        onCreate(db);
    }
    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    /**
     * This method is to create user record
     *
     * @param product
     */
    public void addProduct(Products product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTOS_NOMBREPRODUCTO, product.getNombre_producto());
        //values.put(COLUMN_PRODUCTOS_CATEGORIA, product.getCategoria_producto());
        values.put(COLUMN_PRODUCTOS_UBICACION, product.getUbicacion_producto());
        values.put(COLUMN_PRODUCTOS_CADUCIDAD, product.getFecha_caducidad_producto());
        values.put(COLUMN_PRODUCTOS_CANTIDAD, product.getCantidad_producto());
        values.put(COLUMN_PRODUCTOS_USUARIO, product.getUsuario_Id_producto());
        // Inserting Row
        db.insert(TABLE_PRODUCTOS, null, values);
        db.close();
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }

    /**
     * Método para devolver todos los productos de la BBDD
     * @return
     */

     public List<Products> getAllProducts() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_PRODUCTOS_ID,
                COLUMN_PRODUCTOS_NOMBREPRODUCTO,
                //COLUMN_PRODUCTOS_CATEGORIA,
                COLUMN_PRODUCTOS_UBICACION,
                COLUMN_PRODUCTOS_CADUCIDAD,
                COLUMN_PRODUCTOS_CANTIDAD,
                COLUMN_PRODUCTOS_USUARIO
        };
        // sorting orders
        String sortOrder =
                COLUMN_PRODUCTOS_CADUCIDAD + " ASC";
        List<Products> productsList = new ArrayList<Products>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the PRODUCTS table
        /**
         * Here query function is used to fetch records from user table this function works like we
         * use sql query.
         * SQL query equivalent to this query function is
         * SELECT id_producto, usuario_id_producto, nombre_producto, categoria_producto,
         * ubicacion_producto, cantidad_producto, fecha_caducidad_producto FROM products
         * ORDER BY fecha_caducidad_producto;
         */
        Cursor cursor2 = db.query(TABLE_PRODUCTOS, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor2.moveToFirst()) {
            do {
                Products product = new Products();
                product.setId_producto(Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_ID))));
                product.setNombre_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_NOMBREPRODUCTO)));
                //product.setCategoria_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CATEGORIA)));
                product.setUbicacion_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_UBICACION)));
                //product.setCantidad_producto(Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CANTIDAD))));
                product.setCantidad_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CANTIDAD)));
                //product.setFecha_caducidad_producto(StringToDateConvert(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CADUCIDAD))));
                product.setFecha_caducidad_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CADUCIDAD)));
                product.setUsuario_id_producto(Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_USUARIO))));
                // Adding user record to list
                productsList.add(product);
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        db.close();
        // return user list
        return productsList;
    }

    /**
     * Método para devolver todos los productos de un usuario
     * @param email_user_id
     * @return
     */

    public List<Products> getAllProducts(String email_user_id) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_PRODUCTOS_ID,
                COLUMN_PRODUCTOS_NOMBREPRODUCTO,
                //COLUMN_PRODUCTOS_CATEGORIA,
                COLUMN_PRODUCTOS_UBICACION,
                COLUMN_PRODUCTOS_CADUCIDAD,
                COLUMN_PRODUCTOS_CANTIDAD,
                COLUMN_PRODUCTOS_USUARIO
        };


        // selection criteria
        String selection = COLUMN_PRODUCTOS_USUARIO + " = ?";
        // selection argument
        String[] selectionArgs = {email_user_id};
        // sorting orders
        String sortOrder =
                COLUMN_PRODUCTOS_CADUCIDAD + " ASC";
        List<Products> productsList = new ArrayList<Products>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the PRODUCTS table
        /**
         * Here query function is used to fetch records from user table this function works like we
         * use sql query.
         * SQL query equivalent to this query function is
         * SELECT id_producto, usuario_id_producto, nombre_producto, categoria_producto,
         * ubicacion_producto, cantidad_producto, fecha_caducidad_producto FROM products
         * ORDER BY fecha_caducidad_producto;
         */
        Cursor cursor2 = db.query(TABLE_PRODUCTOS, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor2.moveToFirst()) {
            do {
                Products product = new Products();
                product.setId_producto(Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_ID))));
                product.setNombre_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_NOMBREPRODUCTO)));
                //product.setCategoria_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CATEGORIA)));
                product.setUbicacion_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_UBICACION)));
                //product.setCantidad_producto(Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CANTIDAD))));
                product.setCantidad_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CANTIDAD)));
                //product.setFecha_caducidad_producto(StringToDateConvert(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CADUCIDAD))));
                product.setFecha_caducidad_producto(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_CADUCIDAD)));
                product.setUsuario_id_producto(Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(COLUMN_PRODUCTOS_USUARIO))));
                // Adding user record to list
                productsList.add(product);
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        db.close();
        // return user list
        return productsList;
    }




    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to update Product record
     *
     * @param product
     */
    public void updateProduct(Products product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTOS_NOMBREPRODUCTO, product.getNombre_producto());
        //values.put(COLUMN_PRODUCTOS_CATEGORIA, product.getCategoria_producto());
        values.put(COLUMN_PRODUCTOS_UBICACION, product.getUbicacion_producto());
        values.put(COLUMN_PRODUCTOS_CADUCIDAD, product.getFecha_caducidad_producto());
        values.put(COLUMN_PRODUCTOS_CANTIDAD, product.getCantidad_producto());
        values.put(COLUMN_PRODUCTOS_USUARIO, product.getUsuario_Id_producto());
        // updating row
        db.update(TABLE_PRODUCTOS, values, COLUMN_PRODUCTOS_ID + " = ?",
                new String[]{String.valueOf(product.getId_producto())});
        db.close();
    }
    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    /**
     * This method is to delete PRODUCT record
     *
     * @param product
     */
    public void deleteProduct(Products product) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete Product record by id
        db.delete(TABLE_PRODUCTOS, COLUMN_PRODUCTOS_ID + " = ?",
                new String[]{String.valueOf(product.getId_producto())});
        db.close();
    }
    public void deleteProduct(String id_producto) {
        System.out.println("DATABASE DELETE PRODUCT: "+id_producto);
        String[] selectionArgs = {id_producto};
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Product record by id
        db.delete(TABLE_PRODUCTOS, COLUMN_PRODUCTOS_ID + " = ?",
                selectionArgs);
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    /**
     * This method to check user exist or not
     *
     * @param nombre_producto
     * @return true/false
     */
    public boolean checkProduct(String nombre_producto) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_PRODUCTOS_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_PRODUCTOS_NOMBREPRODUCTO + " = ?";
        // selection argument
        String[] selectionArgs = {nombre_producto};
        // query user table with condition
        /**
         * Here query function is used to fetch records from PRODUCTS table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT id_producto FROM product WHERE nombre_producto = 'Mandarinas';
         */
        Cursor cursor = db.query(TABLE_PRODUCTOS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkProduct(String nombre_producto, String email_user_id_producto) {
        // array of columns to fetch
        System.out.println(nombre_producto);
        System.out.println(email_user_id_producto);
        String[] columns = {
                COLUMN_PRODUCTOS_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_PRODUCTOS_NOMBREPRODUCTO + " = ?" + " AND " + COLUMN_PRODUCTOS_USUARIO + " = ?";
        System.out.println(selection);
        // selection arguments
        String[] selectionArgs = {nombre_producto, email_user_id_producto};


        // query user table with condition
        /**
         * Here query function is used to fetch records from PRODUCTS table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT id_producto FROM product WHERE nombre_producto = 'Mandarinas';
         */
        Cursor cursor = db.query(TABLE_PRODUCTOS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            System.out.println("DENTRO CURSOR");
            return true;
        }
        System.out.println("FUERA CURSOR");
        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /// Se Convierte un String a Date
    public Date StringToDateConvert(String s){

        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            result  = dateFormat.parse(s);
        }

        catch(ParseException e){
            e.printStackTrace();

        }
        return result ;
    }

    /**
     * Se devuelve el ID del usuario activo
     * @param email_user_id_array
     * @return
     */
    public String getUserID(String[] email_user_id_array) {

        //Columnas a buscar
        String[] columns = {
         COLUMN_USER_ID
         };
        //Campo a buscar
        String selection = COLUMN_USER_EMAIL + " = ?";


        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                email_user_id_array,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        // Traversing through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {

                return_email_user_id=cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));

            } while (cursor.moveToNext());
        }
        System.out.println(return_email_user_id);
        cursor.close();
        db.close();
        return return_email_user_id;
    }
    public String getUserID(String email_user_id_string) {

        //Columnas a buscar
        String[] columns = {
                COLUMN_USER_ID
        };
        //Campo a buscar
        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArg= {email_user_id_string};


        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArg,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        // Traversing through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {

                return_email_user_id=cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));

            } while (cursor.moveToNext());
        }
        System.out.println(return_email_user_id);
        cursor.close();
        db.close();
        return return_email_user_id;
    }

}
