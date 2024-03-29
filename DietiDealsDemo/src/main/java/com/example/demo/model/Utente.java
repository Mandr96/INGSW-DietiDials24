package com.example.demo.model;

import com.example.demo.authService.token.Token;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.Collection;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")
@JsonIgnoreProperties({"authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked", "username"})
@Entity
//TODO MANCA IL LINK
public class Utente implements UserDetails {
    @Getter
    @Setter
    @Id
    private String email;
    @Getter
    @Setter
    private String nome;
    @Getter
    @Setter
    private String cognome;
    @Setter
    private String password;
    @Getter
    @Setter
    private String shortbio;
    @Getter
    @Setter
    private String city;
    @Getter
    @Setter
    private File profilePic;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private List<Notifica> notifiche;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creatore")
    @JsonIgnore
    private List<Asta> aste;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @JsonIgnore
    private List<Offerta> offerte;
    @JsonIgnore
    @OneToMany(mappedBy = "utente")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
