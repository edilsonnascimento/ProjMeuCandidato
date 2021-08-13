package br.utfpr.projmeucandidato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AutoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria);
    }

    public static void sobre (AppCompatActivity activity){

        Intent intent = new Intent(activity, AutoriaActivity.class);

        activity.startActivity(intent);
    }
}