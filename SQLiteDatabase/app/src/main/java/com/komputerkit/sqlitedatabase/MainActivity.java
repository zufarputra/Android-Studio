package com.komputerkit.sqlitedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Database db;
    EditText etBarang, etStok, etHarga;
    TextView tvPilihan;

    List<Barang> databarang = new ArrayList<Barang>();
    BarangAdapter adapter;
    RecyclerView rcvBarang;

    String idbarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load();
        selectData();
    }

    public void load () {
        db = new Database(this);
        db.buatTabel();


        etBarang = findViewById(R.id.etBarang);
        etStok = findViewById(R.id.etStok);
        etHarga = findViewById(R.id.etHarga);
        tvPilihan = findViewById(R.id.tvPilihan);
        rcvBarang = findViewById(R.id.rcvBarang);

        rcvBarang.setLayoutManager(new LinearLayoutManager(this));
        rcvBarang.setHasFixedSize(true);
    }

    public  void simpan (View v) {
        String barang = etBarang.getText().toString();
        String stok = etStok.getText().toString();
        String harga = etHarga.getText().toString();
        String pilihan = tvPilihan.getText().toString();

        if (barang.isEmpty() || stok.isEmpty() || harga.isEmpty() ){
            pesan("data kosong");
        } else {
            if(pilihan.equals("insert")){
                String sql = "INSERT INTO tblbarang (barang,stok,harga) VALUES ('"+barang+"',"+stok+","+harga+")";
                if (db.runSQL(sql)){
                    pesan("insert berhasil");
                    selectData();
                } else {
                    pesan("insert gagal");
                }




            } else {
                String sql = "UPDATE tblbarang\n" +
                        "SET barang = '"+barang+"', stok = "+stok+", harga = "+harga+"\n" +
                        "WHERE idbarang ="+idbarang+";";

                if (db.runSQL(sql)){
                    pesan("Data sudah di ubah");
                    selectData();
                } else {
                    pesan("Data tidak bisa di ubah");
                }
            }
        }
        etBarang.setText("");
        etStok.setText("");
        etHarga.setText("");
        tvPilihan.setText("insert");
    }

    public  void pesan (String isi){
        Toast.makeText(this, isi, Toast.LENGTH_SHORT).show();
    }

    public void selectData (){
        String sql = " SELECT * FROM tblbarang ORDER BY barang ASC ";
        Cursor cursor = db.select(sql);
        databarang.clear();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String idbarang = cursor.getString(cursor.getColumnIndex("idbarang"));
                String barang = cursor.getString(cursor.getColumnIndex("barang"));
                String stok = cursor.getString(cursor.getColumnIndex("stok"));
                String harga = cursor.getString(cursor.getColumnIndex("harga"));

                databarang.add(new Barang(idbarang,barang,stok,harga));
            }

            adapter = new BarangAdapter(this, databarang);
            rcvBarang.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }else {
            pesan("Data Kosong");
        }
    }

    public void deleteData(String id){
        idbarang = id;

        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("Peringatan!");
        al.setMessage("Yakin untuk menghapus?");
        al.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sql = "DELETE FROM tblbarang WHERE idbarang = "+idbarang+";";
                if (db.runSQL(sql)){
                    pesan("Data Sudah di Hapus");
                    selectData();
                }else {
                    pesan("Data Tidak bisa Di Hapus");
                }
            }
        });

                al.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        al.show();



    }

    public void selectUpdate (String id){
        idbarang = id;
        String sql = "SELECT * FROM tblbarang WHERE idbarang="+id+";";
        Cursor cursor = db.select(sql);
        cursor.moveToNext();

        etBarang.setText(cursor.getString(cursor.getColumnIndex("barang")));
        etStok.setText(cursor.getString(cursor.getColumnIndex("stok")));
        etHarga.setText(cursor.getString(cursor.getColumnIndex("harga")));

        tvPilihan.setText("update");

    }
}
