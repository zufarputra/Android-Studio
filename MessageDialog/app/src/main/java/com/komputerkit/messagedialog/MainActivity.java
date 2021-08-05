package com.komputerkit.messagedialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast (String pesan){
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();
    }

    public void showAlert(String pesan){
        AlertDialog.Builder buatAlert = new AlertDialog.Builder(this);
        buatAlert.setTitle("Perhatian");
        buatAlert.setMessage(pesan);

        buatAlert.show();
    }

    public void showAlertBotton (String pesan){
        AlertDialog.Builder showAlert = new AlertDialog.Builder(this);
        showAlert.setTitle("Peringatan");
        showAlert.setMessage(pesan);

        showAlert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("Data sudah dihapus");
            }
        });
        showAlert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("Data TIdak di hapus");
            }
        });

        showAlert.show();
    }

    public void btnToast(View view) {
        showToast("Selamat belajar");
    }

    public void btnAlert(View view) {
        showAlert("Selamat mencoba");
    }

    public void btnAlertDialogBottom(View view) {
        showAlertBotton("Yakin akan menghapus?");
    }
}