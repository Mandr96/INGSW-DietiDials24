package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.AstaSilenziosa;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Utility.LoggedUser;

public class AstaDetailsActivity extends ComponentActivity {

    Utente owner;
    Asta selectedAsta;
    ImageView btnBack;
    TextView viewNomeArticolo;
    ImageView viewArticoloImg;
    TextView viewDescription;
    TextView viewNomeVenditore;
    TextView viewBestOffer;
    TextView viewYourOffer;
    TextView viewScadenza;
    Button btnSendOffer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asta_details);

        viewNomeArticolo = findViewById(R.id.name_articolo);
        viewArticoloImg = findViewById(R.id.image_articolo);
        btnBack = findViewById(R.id.search_btton_back);
        viewDescription  = findViewById(R.id.description_articolo);
        viewNomeVenditore = findViewById(R.id.venditore_name);
        viewBestOffer = findViewById(R.id.priceNumber);
        viewYourOffer = findViewById(R.id.offerNumber);
        viewScadenza = findViewById(R.id.scadenza);
        btnSendOffer = findViewById(R.id.confirm_btn);

        setData();

        btnSendOffer.setOnClickListener(view -> {

        });
        viewNomeVenditore.setOnClickListener(view -> {
            goToUserDetails();
        });
        btnBack.setOnClickListener(view -> {
            goBack();
        });
    }

    private void setData() {
        selectedAsta = (Asta)getIntent().getSerializableExtra("ASTA");
        owner = new UserProfileController().getAstaOwner(selectedAsta.getId());
        viewNomeArticolo.setText(selectedAsta.getNomeProdotto());
        //TODO set immagine
        //viewArticoloImg = findViewById(R.id.image_articolo);
        viewDescription.setText(selectedAsta.getDescrizione());
        viewNomeVenditore.setText(owner.getNome()+" "+owner.getCognome());
        viewBestOffer.setText(Float.toString(getIntent().getFloatExtra("PRICE", 0f)));
        viewYourOffer.setText(Float.toString(getIntent().getFloatExtra("OFFER", 0f)));
        //TODO get durata
        viewScadenza.setText(selectedAsta.getScadenza().toString());
        if(Float.parseFloat(viewYourOffer.getText().toString()) <= 0) {
            viewYourOffer.setVisibility(View.INVISIBLE);
            findViewById(R.id.offertText).setVisibility(View.INVISIBLE);
        }
        if(selectedAsta instanceof AstaSilenziosa) {
            viewBestOffer.setVisibility(View.INVISIBLE);
            findViewById(R.id.priceText).setVisibility(View.INVISIBLE);
        }
        if(owner.getEmail().equals(LoggedUser.getInstance().getLoggedUser().getEmail())) {
            btnSendOffer.setVisibility(View.INVISIBLE);
        }
    }

    private void goToUserDetails() {
        Intent myIntent = new Intent(AstaDetailsActivity.this, UserDetailsActivity.class);
        //TODO IMG
        myIntent.putExtra("NAME", viewNomeVenditore.getText().toString());
        myIntent.putExtra("EMAIL", owner.getEmail());
        myIntent.putExtra("BIO", owner.getShortbio());
        myIntent.putExtra("IG", "@its.enricoo");
        myIntent.putExtra("FB", "Enrico Fausti Fanpage");
        AstaDetailsActivity.this.startActivity(myIntent);
    }

    private void goBack() {
        Intent myIntent = new Intent(AstaDetailsActivity.this, HomeActivity.class);
        AstaDetailsActivity.this.startActivity(myIntent);
        finish();
    }
}
