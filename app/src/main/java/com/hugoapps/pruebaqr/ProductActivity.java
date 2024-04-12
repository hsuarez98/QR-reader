package com.hugoapps.pruebaqr;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class ProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgProducto;
    TextView txtPrecio,txtCodigoProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        toolbar = findViewById(R.id.barrasup);
        imgProducto = findViewById(R.id.imgProducto);
        txtCodigoProducto = findViewById(R.id.txtCodigoProducto);
        txtPrecio = findViewById(R.id.txtPrecio);

        String codigoProducto = getIntent().getStringExtra("CodigoProducto");
        String url = "http://82.98.132.218:6587/images/" + codigoProducto + ".jpg";
        Double e1 = getIntent().getDoubleExtra("Precio", 0.00);
        txtPrecio.setText("Precio: " + e1.toString() + "€");
        txtCodigoProducto.setText("Codigo producto: " + codigoProducto);
        toolbar.setTitle(getIntent().getStringExtra("Titulo"));
        Glide.with(this).load(url).into(imgProducto);

        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}