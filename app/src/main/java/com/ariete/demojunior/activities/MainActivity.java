package com.ariete.demojunior.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.ariete.demojunior.R;
import com.ariete.demojunior.databinding.ActivityMainBinding;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 99;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.generatePdf.setOnClickListener(this);
        binding.openPdf.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.generate_pdf) {
            if (!binding.fileName.getText().toString().isEmpty()
                    && !binding.someTextFirst.getText().toString().isEmpty()
                    && !binding.someTextSecond.getText().toString().isEmpty()) {
                generatePDF(binding.fileName.getText().toString(),
                        binding.someTextFirst.getText().toString(),
                        binding.someTextSecond.getText().toString());
            }
        }
        if (view.getId() == R.id.open_pdf) {
            if (!binding.fileName.getText().toString().isEmpty())
                viewPDF(binding.fileName.getText().toString());
        }
    }

    public void generatePDF(String fileName, String title, String desc) {
        File file = new File(getApplicationContext().getExternalFilesDir("pdf"), fileName + ".pdf");

        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(file));
            Document document = new Document(pdf);

            // 1. greeting
            Paragraph greeting = new Paragraph("Hello, friend. I am MR.ROBOT");
            greeting.setFontColor(new DeviceRgb(255, 37, 0));
            document.add(greeting);

            // 2.some text
            Paragraph someText1 = new Paragraph(title);
            someText1.setFontColor(new DeviceRgb(0, 73, 255));
            document.add(someText1);

            Paragraph someText2 = new Paragraph(desc);
            someText2.setFontColor(new DeviceRgb(252, 0, 255));
            document.add(someText2);

//            // 3. image
//            document.add(new Image());

            document.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Toast.makeText(this, "Your PDF file is saved!", Toast.LENGTH_SHORT).show();
    }

    private void viewPDF(String fileName) {
        if (allPermissionsGranted()) {
            /**
                * permissions предоставлены.
                * --------------------------
                * permisions granted.
            */
            goFile(fileName);
        } else {
            /**
                * ActivityCompat - помощник для доступа к функциям Activity.
                * ----------------------------------------------------------
                * ActivityCompat - a helper for accessing to Activity's functions.
            */
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    // список запрашиваемых permissions (the list of requested permissions)
                    REQUEST_CODE_STORAGE_PERMISSION
            );
        }
    }

    private boolean allPermissionsGranted() {
        return
            /**
                * ContextCompat - a helper for accessing to functions in context.
                * --------------------------------------------------------------
                * ContextCompat - помощник для доступа к функциям в контексте (context).
            */

            /**
                * checkSelfPermission() - the function(), which defines
                * were you granted specific permission.
                * -----------------------------------------------------
                * checkSelfPermission() - функция, которая определяет,
                * было ли вам предоставлено конкретное разрешение.
            */

            /**
                * PacketManager - a class for extraction different information
                * that apply to the packages of appps,
                * which at this moment setting on devce
                * ----------------------------------------------------------------
                * PacketManager - класс для извлечения различного рода информации,
                * относящейся к пакетам приложений,
                * которые в данный момент установлены на устройстве.
            */
            ContextCompat.checkSelfPermission(
                    getApplicationContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        == PackageManager.PERMISSION_GRANTED;
    }

    private void goFile(String fileName) {

        File file = new File(getApplicationContext().getExternalFilesDir("pdf"), fileName + ".pdf");
        Intent intent = new Intent(this, PdfActivity.class);

        intent.putExtra("frog", Uri.fromFile(file));
        intent.setType("application/data");

        startActivity(intent);
    }
}