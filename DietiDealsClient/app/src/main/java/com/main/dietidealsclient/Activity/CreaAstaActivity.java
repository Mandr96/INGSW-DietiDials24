package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.R;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class CreaAstaActivity extends ComponentActivity {
    String type = "AstaClassica";
    String name = null;
    String description = null;
    String cat = null;
    Float minPrice = null;
    Date scadenza = null;
    File imageFile = null;
    //TODO set default image
    ImageView imagePreview;
    ActivityResultLauncher<Intent> resultLauncher;
    AsteController asteController;

    public CreaAstaActivity(){
        asteController = new AsteController();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_asta);

        String userType = getIntent().getExtras().getString("TIPO");
        imagePreview = findViewById(R.id.new_articolo_image);
        registerResult();

        if(userType.equals("VENDITORE")) {
            findViewById(R.id.search_type).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView_type).setVisibility(View.INVISIBLE);
            type = "AstaInversa";
        }

        findViewById(R.id.crea_btn).setOnClickListener(view -> {
            try {
                InserisciAsta();
            } catch (InterruptedException e) {
                Toast.makeText(this, "Impossibile inserire asta",Toast.LENGTH_LONG).show();
            }
        });
        imagePreview.setOnClickListener(view -> {
                imageChooser();
        });
    }

    private void InserisciAsta() throws InterruptedException {
        name = ((EditText)findViewById(R.id.new_articolo_name)).getText().toString();
        description = ((EditText)findViewById(R.id.new_articolo_description)).getText().toString();
        cat = ((Spinner)findViewById(R.id.search_categoria)).getSelectedItem().toString().replace(" ", "_");
        type = ((Spinner)findViewById(R.id.search_type)).getSelectedItem().toString();
        //Prezzo minimo
        String tmpStr = ((EditText)findViewById(R.id.new_articolo_minPrice)).getText().toString();
        minPrice = Float.parseFloat(tmpStr);
        //Calcolo scadenza
        int days = Integer.parseInt(((EditText)findViewById(R.id.new_articolo_days)).getText().toString());
        int hours = Integer.parseInt(((EditText)findViewById(R.id.new_articolo_hours)).getText().toString());
        scadenza = Timestamp.from(Instant.now().plusSeconds(days*24*60*60).plusSeconds(hours*60*60));
        //Image setting
        if(imagePreview.getTag() != null && !imagePreview.getTag().toString().isBlank()) {
            imageFile = new File(imagePreview.getTag().toString());
        }
        //Controlli
        if(name.isBlank()) {
            Toast.makeText(this, "Inserisci l'articolo", Toast.LENGTH_LONG).show();
            return;
        }
        else if(description.isBlank()) {
            Toast.makeText(this, "Inserisci una descrizione", Toast.LENGTH_LONG).show();
            return;
        }
        else if(minPrice == null || !(minPrice > 0)) {
            Toast.makeText(this, "Valore offerta minima non valido", Toast.LENGTH_LONG).show();
            return;
        }
        else if(cat == null) {
            Toast.makeText(this, "Inserisci una categoria", Toast.LENGTH_LONG).show();
            return;
        }
        else if(scadenza.before(Timestamp.from(Instant.now()))) {
            Toast.makeText(this, "Scadenza non valida", Toast.LENGTH_LONG).show();
            return;
        }

        asteController.createNewAsta(new Timestamp(scadenza.getTime()),name,description,cat,imageFile,type,minPrice);
        gotoHomeCompratore();
        Toast.makeText(this, "Asta creata", Toast.LENGTH_SHORT).show();
    }

    void imageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            imagePreview.setImageURI(imageUri);
                            imagePreview.setTag(imageUri.toString());
                        }
                        catch (Exception e) {
                            Toast.makeText(CreaAstaActivity.this, "Nessun immagine selezionata", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void gotoHomeCompratore() {
        Intent myIntent = new Intent(CreaAstaActivity.this, HomeCompratoreActivity.class);
        CreaAstaActivity.this.startActivity(myIntent);
        finish();
    }
}
