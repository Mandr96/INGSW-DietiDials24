package com.main.dietidealsclient.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.main.dietidealsclient.Controller.AsteController;
import com.main.dietidealsclient.Controller.UserProfileController;
import com.main.dietidealsclient.Model.Asta;
import com.main.dietidealsclient.Model.AstaSilenziosa;
import com.main.dietidealsclient.Model.Utente;
import com.main.dietidealsclient.R;
import com.main.dietidealsclient.Utility.LoggedUser;
import com.main.dietidealsclient.Utility.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
    Dialog dialogSendOffer;

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

        dialogSendOffer = new Dialog(AstaDetailsActivity.this);
        dialogSendOffer.setContentView(R.layout.dialog_invia_offerta);
        //dialogSendOffer.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSendOffer.setCancelable(false);
        setData();


        btnSendOffer.setOnClickListener(view -> {
            Logger.log("AstaDetailsPage","Apertura schermata di dialogo 'Invia Offerta'");
            dialogSendOffer.show();
        });
        setDialogEvent();
        viewNomeVenditore.setOnClickListener(view -> {
            Logger.log("AstaDetailsPage","Cambio Activity -> UserDetailsPage");
            goToUserDetails();
        });
        btnBack.setOnClickListener(view -> {
            Logger.log("AstaDetailsPage","'Indietro' premuto");
            goBack();
        });
    }

    private void setData() {
        selectedAsta = (Asta)getIntent().getSerializableExtra("ASTA");
        owner = new UserProfileController().getAstaOwner(selectedAsta.getId());
        Float actualPrice = getIntent().getFloatExtra("PRICE", 0f);
        Float yourOffer = getIntent().getFloatExtra("OFFER", 0f);

        viewNomeArticolo.setText(selectedAsta.getNomeProdotto());
        //TODO set immagine
        //viewArticoloImg = findViewById(R.id.image_articolo);
        viewDescription.setText(selectedAsta.getDescrizione());
        viewNomeVenditore.setText(owner.getNome()+" "+owner.getCognome());
        viewBestOffer.setText(floatAsPriceString(actualPrice));
        viewYourOffer.setText(floatAsPriceString(yourOffer));
        viewScadenza.setText("Scade tra: "+selectedAsta.getDurata());
        if(yourOffer <= 0) {
            viewYourOffer.setVisibility(View.INVISIBLE);
            findViewById(R.id.offertText).setVisibility(View.INVISIBLE);
        }
        if(actualPrice <= 0) {
            viewBestOffer.setText(floatAsPriceString(selectedAsta.getActualPrice()));
        }
        if(selectedAsta instanceof AstaSilenziosa) {
            viewBestOffer.setVisibility(View.INVISIBLE);
            findViewById(R.id.priceText).setVisibility(View.INVISIBLE);
        }
        if(owner.getEmail().equals(LoggedUser.getInstance().getLoggedUser().getEmail())) {
            btnSendOffer.setVisibility(View.INVISIBLE);
        }
    }

    private void setDialogEvent() {
        Button inviaOfferta = dialogSendOffer.findViewById(R.id.btn_invia_offerta);
        ImageView imgClose = dialogSendOffer.findViewById(R.id.image_close);
        EditText editValore = dialogSendOffer.findViewById(R.id.valore_offerta_edit_text);
        editValore.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused)
                Logger.log("AstaDetailsPage","Inizio inserimento valore offerta");
            else
                Logger.log("AstaDetailsPage","Fine inserimento valore offerta");
        });
        inviaOfferta.setOnClickListener(view -> {
            Float valore = Float.parseFloat(editValore.getText().toString());
            if(valore != Float.NaN) {
                new AsteController().createNewOffer(selectedAsta, valore);
                Toast.makeText(AstaDetailsActivity.this, "Offerta inviata",Toast.LENGTH_LONG).show();
                Logger.log("AstaDetailsPage","'Indietro premuto' ->");
                goBack();
            }
            else {
                Toast.makeText(AstaDetailsActivity.this, "Valore offerta non valido",Toast.LENGTH_LONG).show();
            }
        });
        imgClose.setOnClickListener(view -> {
            Logger.log("AstaDetailsPage","Chiusura schermata di dialogo 'Invia Offerta'");
            dialogSendOffer.dismiss();
        });
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
        finish();
    }

    private String floatAsPriceString(Float num) {
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(num)+" €";
    }
}
