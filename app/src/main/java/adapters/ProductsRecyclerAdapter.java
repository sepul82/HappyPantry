package adapters;

import SQL.DataBaseHelper;
import activities.ListaCompra;
import activities.ProductsListActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.example.loginscreen.R;
import model.Products;
import java.util.List;
import android.util.Log;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ProductsViewHolder> {
    private List<Products> listProducts;




    public ProductsRecyclerAdapter(List<Products> listProducts) {
        Log.d(TAG,"hola1");
        this.listProducts = listProducts;
    }
    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_recycler, parent, false);
        Log.d(TAG,"hola");
        return new ProductsViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        holder.textViewProductName.setText(listProducts.get(position).getNombre_producto());
        holder.textViewProductUbicacion.setText(listProducts.get(position).getUbicacion_producto());
        holder.textViewProductCantidad.setText(listProducts.get(position).getCantidad_producto());
        holder.textViewProductCaducidad.setText(listProducts.get(position).getFecha_caducidad_producto());
        Log.d(TAG, "POSITION");
        System.out.println(position);
    }
    @Override
    public int getItemCount() {
        Log.v(ProductsRecyclerAdapter.class.getSimpleName(),""+listProducts.size());
        Log.d(TAG, "ITEMCOUNT");
        System.out.println(listProducts.indexOf(listProducts));
        return listProducts.size();
    }
    /**
     * ViewHolder class
     */
    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView textViewProductName;
        public AppCompatTextView textViewProductCantidad;
        public AppCompatTextView textViewProductUbicacion;
        public AppCompatTextView textViewProductCaducidad;
        public ImageView imageViewBorrarProducto;
        Context context;


        public ProductsViewHolder(View view) {
            super(view);
            context = view.getContext();

            textViewProductName = (AppCompatTextView) view.findViewById(R.id.textViewProductName);
            textViewProductCantidad = (AppCompatTextView) view.findViewById(R.id.textViewProductCantidad);
            textViewProductUbicacion = (AppCompatTextView) view.findViewById(R.id.textViewProductUbicacion);
            textViewProductCaducidad = (AppCompatTextView) view.findViewById(R.id.textViewProductCaducidad);
            imageViewBorrarProducto= (ImageView) view.findViewById(R.id.borraritem);
            Log.d(TAG, "VIEWHOLDER");
            imageViewBorrarProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionAdapter = getAdapterPosition();
                    System.out.println(positionAdapter);
                    Log.d(TAG,"ADAPTERPOSITION");
                    deleteProduct(positionAdapter);
                }
            });

        }
        private void deleteProduct(int position){
            Log.d(TAG,"AQUI VIENE");
            System.out.println(context);
           DataBaseHelper databaseHelper = new DataBaseHelper(context);
            String id_producto_String= Integer.toString(listProducts.get(position).getId_producto());
            System.out.println(id_producto_String);
            //System.out.println(listProducts.get(position).getId_producto());
            databaseHelper.deleteProduct(id_producto_String);
            listProducts.remove(position);
            notifyDataSetChanged();
            Log.d(TAG,"FINAL");



        }

    }


}
