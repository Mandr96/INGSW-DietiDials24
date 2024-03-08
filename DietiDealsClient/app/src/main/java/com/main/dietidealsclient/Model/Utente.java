package com.main.dietidealsclient.Model;
import com.fasterxml.jackson.annotation.*;
import lombok.*;


import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked", "username"})
//TODO MANCA IL LINK
public class Utente implements Serializable {

    private String email;
    private String nome;
    private String cognome;
    private String password;
    private String shortbio;
    private String city;
    private File profilePic;
    @JsonIgnore
    private List<Notifica> notifiche;
    @JsonIgnore
    private List<Asta> aste;
    @JsonIgnore
    private List<Offerta> offerte;
    @JsonIgnore
    private List<Token> tokens;

    @JsonCreator
    public Utente(@JsonProperty("email") String email,
                  @JsonProperty("nome") String nome,
                  @JsonProperty("cognome") String cognome,
                  @JsonProperty("password") String password,
                  @JsonProperty("shortbio") String shortbio,
                  @JsonProperty("city") String city,
                  @JsonProperty("profilepic") File profilePic,
                  @JsonProperty("notifiche") List<Notifica> notifiche,
                  @JsonProperty("aste") List<Asta> aste,
                  @JsonProperty("offerte") List<Offerta> offerte) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.shortbio = shortbio;
        this.city = city;
        this.profilePic = profilePic;
        this.notifiche = notifiche;
        this.aste = aste;
        this.offerte = offerte;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShortbio() {
        return shortbio;
    }

    public void setShortbio(String shortbio) {
        this.shortbio = shortbio;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public File getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(File profilePic) {
        this.profilePic = profilePic;
    }

    public List<Notifica> getNotifiche() {
        return notifiche;
    }

    public void setNotifiche(List<Notifica> notifiche) {
        this.notifiche = notifiche;
    }

    public List<Asta> getAste() {
        return aste;
    }

    public void setAste(List<Asta> aste) {
        this.aste = aste;
    }

    public List<Offerta> getOfferte() {
        return offerte;
    }

    public void setOfferte(List<Offerta> offerte) {
        this.offerte = offerte;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", password='" + password + '\'' +
                ", shortbio='" + shortbio + '\'' +
                ", city='" + city + '\'' +
                ", profilePic=" + profilePic +
                ", notifiche=" + notifiche +
                ", aste=" + aste +
                ", offerte=" + offerte +
                '}';
    }
}
