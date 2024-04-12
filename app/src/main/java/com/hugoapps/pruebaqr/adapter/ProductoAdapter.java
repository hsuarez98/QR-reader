package com.hugoapps.pruebaqr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hugoapps.pruebaqr.R;
import com.hugoapps.pruebaqr.interfaces.RecyclerViewInterface;
import com.hugoapps.pruebaqr.models.Producto;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Producto> productos;
    private final Context context;

    public ProductoAdapter(Context context, ArrayList<Producto> productos, RecyclerViewInterface recyclerViewInterface) {
        this.productos = productos;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.producto, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(productos.get(position), context);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgProducto;
        private final TextView txtProducto;
        private final TextView txtPrecio;
        private final TextView txtId;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imgProducto = itemView.findViewById(R.id.imgProducto);
            txtId = itemView.findViewById(R.id.txtId);
            txtPrecio = itemView.findViewById(R.id.txtCodigoProducto);
            txtProducto = itemView.findViewById(R.id.txtProducto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Producto producto, Context context) {
            txtId.setText(producto.getCodigoProducto());
            txtPrecio.setText(producto.getPrecio().toString());
            txtProducto.setText(producto.getTitulo());
            String url = "http://82.98.132.218:6587/images/" + producto.getCodigoProducto() + ".jpg";
            Glide.with(context).load(url).placeholder(R.drawable.cruz).into(imgProducto);

        }
    }

    public void setListaFiltrada(ArrayList<Producto> listaFiltrada) {
        this.productos = listaFiltrada;
        notifyDataSetChanged();
    }
}
