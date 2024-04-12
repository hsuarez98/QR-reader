package com.hugoapps.pruebaqr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hugoapps.pruebaqr.adapter.ProductoAdapter;
import com.hugoapps.pruebaqr.interfaces.ProductoAPI;
import com.hugoapps.pruebaqr.interfaces.RecyclerViewInterface;
import com.hugoapps.pruebaqr.models.Producto;
import com.hugoapps.pruebaqr.network.ApiClient;
import com.hugoapps.pruebaqr.network.ServiceListApiResponse;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    TextView txtId, txtPrecio, txtProducto, txtLogo;
    ImageView imgProducto, imgOrdenar,btnQr;
    SearchView svBuscar;
    LinearLayout layout;
    Producto producto;
    private ArrayList<Producto> listaProductos;
    private ProductoAdapter productoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaProductos = new ArrayList<>();
        RecyclerView rvProductos = findViewById(R.id.rvProductos);
        rvProductos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        productoAdapter = new ProductoAdapter(MainActivity.this, listaProductos,this);
        rvProductos.setAdapter(productoAdapter);

        svBuscar = findViewById(R.id.svBuscar);
        imgOrdenar = findViewById(R.id.btnOrdenar);
        btnQr = findViewById(R.id.btnQr);
        txtLogo = findViewById(R.id.txtLogo);
        layout = findViewById(R.id.main);

        verProductos();

        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanerQr();
            }
        });

        svBuscar.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgOrdenar.setVisibility(View.GONE);
                btnQr.setVisibility(View.GONE);
                txtLogo.setVisibility(View.GONE);
            }
        });

        svBuscar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                imgOrdenar.setVisibility(View.VISIBLE);
                btnQr.setVisibility(View.VISIBLE);
                txtLogo.setVisibility(View.VISIBLE);
                return false;
            }
        });

        svBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String texto) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String texto) {
                buscar(texto);
                return true;
            }
        });
    }

    private void escanerQr() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Subir volumen enciende flash");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String id = result.getContents();
            for (Producto producto1 : listaProductos){
                if (producto1.getCodigoProducto().equals(id)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Producto: ");
                    builder.setMessage(
                            Html.fromHtml("<big>" + producto1.getTitulo() +"</big>"+ " <br><br>"
                                    + producto1.getCodigoProducto() +
                                    " <br><br>" + producto1.getPrecio().toString()));

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }else{
                    Snackbar.make(layout, "No se ha encontrado el producto", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    });

    public void verProductos() {
        ApiClient.getClient().create(ProductoAPI.class).getProductos().enqueue(new Callback<ServiceListApiResponse>() {
            @Override
            public void onResponse(Call<ServiceListApiResponse> call, Response<ServiceListApiResponse> response) {
                if (response.code() == 200) {
                    if (response.body().isSuccess()) {
                        listaProductos.addAll(response.body().getData());
                        productoAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceListApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void buscar(String titulo) {
        ApiClient.getClient().create(ProductoAPI.class).find(titulo).enqueue(new Callback<ServiceListApiResponse>() {
            @Override
            public void onResponse(Call<ServiceListApiResponse> call, Response<ServiceListApiResponse> response) {
                try {
                    if (response.code() == 200) {
                        if (response.body().isSuccess()) {
                            ArrayList<Producto> listaFiltrada = new ArrayList<>();
                            for (Producto producto : listaProductos) {
                                if (producto.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                                    listaFiltrada.add(producto);
                                }
                            }

                            if (listaFiltrada.isEmpty()) {
                                Toast.makeText(MainActivity.this, "Lista vacía", Toast.LENGTH_SHORT).show();
                            } else {
                                productoAdapter.setListaFiltrada(listaFiltrada);
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception exception) {
                    Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceListApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostrarPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, imgOrdenar);
        popupMenu.getMenuInflater().inflate(R.menu.ordenmenu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                ApiClient.getClient().create(ProductoAPI.class).getProductosOrden(item.getTitle().toString()).enqueue(new Callback<ServiceListApiResponse>() {
                    @Override
                    public void onResponse(Call<ServiceListApiResponse> call, Response<ServiceListApiResponse> response) {
                        try {
                            if (response.code() == 200) {
                                if (response.body().isSuccess()) {
                                    listaProductos.addAll(response.body().getData());
                                    productoAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServiceListApiResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                return true;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onItemClick(int position) {
        producto = new Producto();
        producto = listaProductos.get(position);
        Double d=Double.parseDouble(producto.getPrecio().toString());
        Intent intent = new Intent(MainActivity.this,ProductActivity.class);
        intent.putExtra("CodigoProducto",producto.getCodigoProducto());
        intent.putExtra("Precio",d);
        intent.putExtra("Titulo",producto.getTitulo());
        startActivity(intent);
    }
}