package com.example.rajaongkir;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rajaongkir.model.Kota;
import com.example.rajaongkir.model.Provinsi;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Provinsi provinsi;
    Kota kota;
    Button btncekongkir;
    TextView tvongkir, tvlamakirim;
    Spinner spinprovinsi, spinKota;
    ArrayList<String> province_name = new ArrayList<>();
    ArrayList<String> city_name = new ArrayList<>();
    ArrayList<Integer> province_id = new ArrayList<>();
    ArrayList<Integer> city_id = new ArrayList<>();
    int id_kota_tujuan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvongkir = (TextView) findViewById(R.id.tvOngkir);
        tvlamakirim = (TextView) findViewById(R.id.tvLamaKirim);
        spinprovinsi = (Spinner) findViewById(R.id.spinProvinsi);
        spinKota = (Spinner) findViewById(R.id.spinKota);
        btncekongkir = (Button) findViewById(R.id.btnCekOngkir);
        btncekongkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekOngkir("399", ""+id_kota_tujuan, 1, "jne");
            }
        });

        load_provinsi();

        spinprovinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int kode = 0;
                Log.i("info spin touch", ""+province_name.get(position)+" " +
                        "Kode ; "+province_id.get(position));
                load_kota(province_id.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                id_kota_tujuan = city_id.get(position);
                Log.i("info city touch", ""+id_kota_tujuan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void load_provinsi(){
        
    }
}