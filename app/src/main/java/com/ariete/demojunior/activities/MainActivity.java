package com.ariete.demojunior.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ariete.demojunior.R;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument; // IMPORTANT!!!
import com.itextpdf.kernel.pdf.PdfWriter; // IMPORTANT!!!
import com.itextpdf.layout.Document; // IMPORTANT!!!
import com.itextpdf.layout.element.Paragraph; // IMPORTANT!!!

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button generatePDF;
    private EditText fileName;
    private EditText someTextFirst;
    private EditText someTextSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generatePDF = findViewById(R.id.generate_pdf);

        fileName = findViewById(R.id.file_name);
        someTextFirst = findViewById(R.id.some_text_first);
        someTextSecond = findViewById(R.id.some_sext_second);

        generatePDF.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.generate_pdf) {
            if (!fileName.getText().toString().isEmpty()
                    && !someTextFirst.getText().toString().isEmpty()
                    && !someTextSecond.getText().toString().isEmpty()) {
                generatePDF(fileName.getText().toString(), someTextFirst.getText().toString(), someTextSecond.getText().toString());
            }
        }
    }

    public void generatePDF(String fileName, String title, String desc) {
        File file = new File(getExternalFilesDir(null), fileName + ".pdf");

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

}