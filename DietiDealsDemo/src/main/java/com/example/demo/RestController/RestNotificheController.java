package com.example.demo.RestController;

import com.example.demo.data_access.NotificheRepository;
import com.example.demo.data_access.UtentiRepository;
import com.example.demo.model.Notifica;
import com.example.demo.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "notifica")
public class RestNotificheController {

    private final NotificheRepository notificheRep;

    @Autowired
    public RestNotificheController(NotificheRepository repository) {
        this.notificheRep = repository;
    }

    @GetMapping(value = "all/{email}")
    public List<Notifica> getByUser(@PathVariable("email") String email) {
        return notificheRep.findByUser(email);
    }

    @GetMapping(value = "unread/{email}")
    public List<Notifica> getUnread(@PathVariable("email") String email) {
        return notificheRep.findUnread(email);
    }

    @GetMapping(value = "setRead/{id}")
    public void setNotificaLetta(@PathVariable("id") Long id) {
        notificheRep.setRead(id);
    }

    public void addNotifica(Notifica not) { notificheRep.save(not); }
}
