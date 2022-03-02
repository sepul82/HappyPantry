package adapters;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.loginscreen.R;
import model.Products;
import java.util.List;

public class ProductsRecyclerAdapterReducido extends RecyclerView.Adapter<ProductsRecyclerAdapterReducido.ProductsViewHolderReducido> {
    private List<Products> listProductsReducido;
    public ProductsRecyclerAdapterReducido(List<Products> listProductsReducido) {
        this.listProductsReducido = listProductsReducido;
    }
    @Override
    public ProductsViewHolderReducido onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemViewReducido = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productcaducidad_recycler, parent, false);
        return new ProductsViewHolderReducido(itemViewReducido);
    }
    @Override
    public void onBindViewHolder(ProductsViewHolderReducido holder, int position) {
        holder.textViewProductNameReducido.setText(listProductsReducido.get(position).getNombre_producto());
        holder.textViewProductCaducidadReducido.setText(listProductsReducido.get(position).getFecha_caducidad_producto());
    }
    @Override
    public int getItemCount() {
        Log.v(ProductsRecyclerAdapterReducido.class.getSimpleName(),""+listProductsReducido.size());
        return listProductsReducido.size();
    }
    /**
     * ViewHolder class
     */
    public class ProductsViewHolderReducido extends RecyclerView.ViewHolder {
        public AppCompatTextView textViewProductNameReducido;
        public AppCompatTextView textViewProductCaducidadReducido;
        public ProductsViewHolderReducido(View viewReducido) {
            super(viewReducido);
            textViewProductNameReducido = (AppCompatTextView) viewReducido.findViewById(R.id.textViewProductNameRed);
            textViewProductCaducidadReducido = (AppCompatTextView) viewReducido.findViewById(R.id.textViewProductCaducidadRed);
        }
    }
}
