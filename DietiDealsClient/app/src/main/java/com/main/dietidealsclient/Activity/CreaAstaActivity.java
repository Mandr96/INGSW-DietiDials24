package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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
import com.main.dietidealsclient.Controller.TipoAccount;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Utility.Logger;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class CreaAstaActivity extends ComponentActivity {

    Button btnCancel;
    String type = "AstaClassica";
    String name = null;
    String description = null;
    String cat = null;
    Float minPrice = null;
    Date scadenza = null;
    File imageFile = null;
    ImageView imagePreview;
    ActivityResultLauncher<Intent> resultLauncher;
    AsteController asteController;
    TipoAccount userType;

    public CreaAstaActivity(){
        asteController = new AsteController();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_asta);

        userType = (TipoAccount)getIntent().getSerializableExtra("TIPO");
        imagePreview = findViewById(R.id.new_articolo_image);
        btnCancel = findViewById(R.id.cancel_btn);
        registerResult();
        imagePreview.setImageResource(R.drawable.add_image);

        if(userType.equals(TipoAccount.COMPRATORE)) {
            findViewById(R.id.search_type).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView_type).setVisibility(View.INVISIBLE);
            type = "Inversa";
        }

        findViewById(R.id.crea_btn).setOnClickListener(view -> {
            try {
                InserisciAsta();
            } catch (InterruptedException e) {
                Logger.log("CreateAstaPage","Impossibile inserire asta");
                Toast.makeText(this, "Impossibile inserire asta",Toast.LENGTH_LONG).show();
            }
        });
        imagePreview.setOnClickListener(view -> {
            Logger.log("CreateAstaPage","Selezione dell'immagine");
            imageChooser();
        });
        btnCancel.setOnClickListener(view -> {
            Logger.log("CreaAstaPage","'Indietro' premuto");
            goBack();
        });
        setLogger();
    }

    private void InserisciAsta() throws InterruptedException {
        Logger.log("CreaAstaPage","Tentativo di creazione asta");
        name = ((EditText)findViewById(R.id.new_articolo_name)).getText().toString();
        description = ((EditText)findViewById(R.id.new_articolo_description)).getText().toString();
        cat = ((Spinner)findViewById(R.id.search_categoria)).getSelectedItem().toString().replace(" ", "_");
        //TODO da errore con l asta inversa così o non la crea inversa
        if (!type.equals("Inversa")){
            type = ((Spinner)findViewById(R.id.search_type)).getSelectedItem().toString();
        }
        //Prezzo minimo
        String tmpStr = ((EditText)findViewById(R.id.new_articolo_minPrice)).getText().toString();
        minPrice = Float.parseFloat(tmpStr.isEmpty() ? "0" : tmpStr);
        //Calcolo scadenza
        EditText dayEditText = findViewById(R.id.new_articolo_days);
        EditText hoursEditText = findViewById(R.id.new_articolo_hours);
        int days = Integer.parseInt(dayEditText.getText().toString().isEmpty() ? "7" : dayEditText.getText().toString());
        int hours = Integer.parseInt(hoursEditText.getText().toString().isEmpty() ? "0" : hoursEditText.getText().toString());
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
        else if(minPrice == null || !(minPrice >= 0)) {
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

        Logger.log("CreaAstaPage","Invio richiesta di creazione asta");
        asteController.createNewAsta(new Timestamp(scadenza.getTime()),name,description,cat,imageFile,type,minPrice);
        Logger.log("CreateAstaPage","Asta creata con successo");
        Toast.makeText(this, "Asta creata", Toast.LENGTH_SHORT).show();
        goBack();
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

    private void goBack() {
        finish();
    }

    private void setLogger() {
        EditText nameEdit = findViewById(R.id.new_articolo_name);
        EditText descriptionEdit = findViewById(R.id.new_articolo_description);
        EditText minPriceEdit = findViewById(R.id.new_articolo_minPrice);
        EditText scadEdit = findViewById(R.id.new_articolo_days);
        EditText scadHours = findViewById(R.id.new_articolo_hours);
        nameEdit.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("CreaAstaPage","Inizio inserimento nome articolo");
            else
                Logger.log("CreaAstaPage","Fine inserimento descrizione articolo");
        });
        descriptionEdit.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("CreaAstaPage","Inizio inserimento descrizione articolo");
            else
                Logger.log("CreaAstaPage","Fine inserimento descrizione articolo");
        });
        minPriceEdit.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("CreaAstaPage","Inizio inserimento prezzo minimo");
            else
                Logger.log("CreaAstaPage","Fine inserimento prezzo minimo");
        });
        scadEdit.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("CreaAstaPage","Inizio inserimento durata giorni");
            else
                Logger.log("CreaAstaPage","Fine inserimento durata giorni");
        });
        scadHours.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("CreaAstaPage","Inizio inserimento durata ore");
            else
                Logger.log("CreaAstaPage","Fine inserimento durata ore");
        });
        Spinner catSelector = findViewById(R.id.search_categoria);
        catSelector.setOnFocusChangeListener((view, isFocused) -> {
            if(!isFocused)
                Logger.log("CreaAstePage","Categoria selezionata");
        });
        Spinner typeSelector = findViewById(R.id.search_type);
        typeSelector.setOnFocusChangeListener((view, isFocused) -> {
            if(!isFocused)
                Logger.log("CreaAstePage","Tipo asta selezionato");
        });


    }
}
