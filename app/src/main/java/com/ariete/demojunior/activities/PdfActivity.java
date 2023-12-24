package com.ariete.demojunior.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.ariete.demojunior.databinding.ActivityPdfBinding;

public class PdfActivity extends AppCompatActivity {

    private ActivityPdfBinding binding;

    private Bundle bundle;

    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bundle = getIntent().getExtras();

        fileUri = (Uri) bundle.get("frog");

        Log.i("extra2", fileUri.toString());
        if (fileUri != null) {
            binding.pdfView.fromUri(fileUri).load();
        }
    }
}