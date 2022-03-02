package model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class Products {

        private int id_producto;
        private int usuario_id_producto;
        private String nombre_producto;
        //private String categoria_producto;
        private String ubicacion_producto;
        private String cantidad_producto;
        private String fecha_caducidad_producto;
        public static String usuario_email;

        // Métodos SET y GET

        public int getId_producto() {
            return id_producto;
        }
        public void setId_producto(int id) {
            this.id_producto = id;
        }
        public int getUsuario_Id_producto() {
        return usuario_id_producto;
    }
    public String getUsuario_Id_producto_String() {
        String usuario_id_producto_String= Integer.toString(usuario_id_producto);
        return usuario_id_producto_String;
    }
        public void setUsuario_id_producto(int id) {
        this.usuario_id_producto = id;
            Log.d(TAG, "SET ID USARIO");
    }
        public void setUsuario_id_producto(String id) {
           int in = Integer.parseInt(id);
        this.usuario_id_producto = in;
            Log.d(TAG, "SET ID:STRING USUARIO");
            System.out.println(usuario_id_producto);
    }
        public String getCantidad_producto() {
        return cantidad_producto;
    }
        public void setCantidad_producto(String cantidad_producto) {
            this.cantidad_producto = cantidad_producto;
        }
        public String getNombre_producto() {
            return nombre_producto;
        }
        public void setNombre_producto(String name) {
            this.nombre_producto = name;
        }
        /**public String getCategoria_producto() {
            return categoria_producto;
        }
        public void setCategoria_producto(String categoria_producto) {
            this.categoria_producto = categoria_producto;
        }*/
        public String getUbicacion_producto() {
            return ubicacion_producto;
        }
        public void setUbicacion_producto(String ubicacion_producto) {


            this.ubicacion_producto = ubicacion_producto;
        }

        // Se devuelve un String con la Fecha de caducidad en formato yyyy/MM/dd
        public String getFecha_caducidad_producto() {


        /**
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String fechaComoCadena;
        fechaComoCadena = sdf.format(fecha_caducidad_producto);

        return fechaComoCadena;
        */
         return fecha_caducidad_producto;
        }
    public void setFecha_caducidad_producto(String fecha_caducidad_producto) {

       /** final String OLD_FORMAT = "dd / MM / yyyy";
        final String NEW_FORMAT = "yyyy / MM / dd";

        System.out.println(fecha_caducidad_producto);
        String newDateString;
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(fecha_caducidad_producto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);*/




        this.fecha_caducidad_producto = fecha_caducidad_producto;
        System.out.println(fecha_caducidad_producto);
    }
    public String getEmail_producto() {
        return usuario_email;
    }
    public void setEmail_producto(String usuario_email) {
        this.usuario_email = usuario_email;
        System.out.println(usuario_email);

    }


}
