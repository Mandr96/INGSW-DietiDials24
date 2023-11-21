# INGSW-DietiDials24
### Progetto universitario di Ingegneria del Software
##### DietiDeals24 è una piattaforma per la gestione di aste online. Il sistema consiste in un’applicazione mobile, desktop o web-based, performante e affidabile, attraverso cui gli utenti possono fruire delle funzionalità del sistema in modo intuitivo, rapido e piacevole.
1. Un utente può registrare un nuovo account ed utilizzarlo per accedere al sistema. Un account può essere di due tipi: venditore o acquirente. La stessa e-mail può essere utilizzata per al più un account venditore e un account compratore. È apprezzata la possibilità di effettuare la registrazione utilizzando credenziali di terze parti (e.g.: Google, Facebook, GitHub, etc…). Gli utenti possono personalizzare il proprio profilo con una short bio, link al proprio sito web / ai propri social, area geografica, etc. <br><br>
2. Il sistema permette ai venditori/compratori di creare aste di diverso tipo per la vendita/acquisto di beni/servizi e presentare offerte per le aste correntemente attive. Ciascuna asta è caratterizzata da una descrizione del bene/servizio in vendita e, opzionalmente, da una fotografia dello stesso. Ciascuna asta inoltre è caratterizzata da una categoria (e.g.: elettronica, informatica, giocattoli, alimentari, servizi, etc…), introdotta per facilitare la navigazione tra le tante aste presenti nel sistema.<br><br>
3. Venditori e compratori possono effettuare ricerche tra le aste correntemente attive, filtrando per categoria e/o per parole chiave, e visualizzare i dettagli di ciascuna asta. Inoltre, è possibile visualizzare anche il profilo utente del venditore che ha creato l’asta.<br><br>
4. Un venditore può creare una nuova Asta a tempo fisso. Un’asta a tempo fisso è caratterizzata da una data di scadenza, scelta dal venditore. Inoltre, il venditore può specificare una soglia minima (segreta) di prezzo al quale vendere il prodotto. Gli acquirenti possono visualizzare i dettagli dell’asta, inclusa l’offerta più alta ricevuta finora, ma non la soglia minima di prezzo fissata dal venditore. Gli acquirenti possono, fino alla data di scadenza dell’asta, presentare delle offerte migliorative rispetto alla migliore offerta corrente, specificando l’importo desiderato (in €). Al momento della scadenza, il compratore con l’offerta più alta si aggiudica il bene/servizio. Se non si raggiunge la soglia minima segreta impostata dal venditore, l’asta viene considerata fallita. In entrambi i casi, il venditore e tutti gli acquirenti che hanno partecipato all’asta visualizzano una notifica.<br><br>
7. Un venditore può creare una nuova Asta silenziosa. In questo tipo di asta, il venditore specifica una data di scadenza e i compratori possono inviare offerte segrete al venditore. Il venditore può scegliere se accettare o rifiutare le offerte ricevute. Una sola offerta può essere accettata per ogni asta.<br><br>
8. Un compratore può creare una nuova Asta Inversa. In questo tipo di asta, il compratore specifica il prodotto/servizio richiesto, eventualmente inserendo un’immagine dello stesso, un prezzo iniziale che è disposto a pagare, e una data di scadenza. I venditori in grado di fornire quel particolare prodotto/servizio possono quindi partecipare all’asta competendo abbassando il prezzo. In particolare, fino al momento della scadenza dell’asta, i venditori possono presentare offerte al ribasso. Al momento della scadenza dell’asta, il venditore con l’offerta più bassa si aggiudica la fornitura del prodotto/servizio.<br><br>
Tutte le funzionalità assegnate devono essere realizzate implementando un sistema distribuito in cui è possibile individuare due macro-componenti:
* una parte front-end, comprensiva delle interfacce utente;
* una parte back-end, che espone delle interfacce di programmazione (e.g.: __REST API__) che vengono utilizzate dal front-end. <br>
È altresì auspicabile che il back-end sia distribuito come __container Docker__ e che, al momento della discussione del progetto con demo del prodotto, sia messo in opera utilizzando tecnologie allo stato dell’arte quali ad esempio __servizi di public Cloud Computing come Azure o AWS__, e sia quindi accessibile attraverso la rete Internet.
Per lo svolgimento delle attività di progettazione, __è obbligatorio l’utilizzo di tool di CASE__. Inoltre, si richiede tassativamente di astrarre il design per favorire il riutilizzo del codice e la futura implementazione di altre funzionalità, __esplicitando nella documentazione le scelte intraprese per favorire tale astrazione__.
Per quanto riguarda le tecnologie da utilizzare, è data piena libertà di scelta al Gruppo di Lavoro contraente, con l’unico vincolo dell’__utilizzo obbligatorio di linguaggi di programmazione Object-Oriented__. Per esempio, la parte front-end può essere realizzata come applicazione desktop (e.g.: con Java + Swing/JavaFx), come applicazione web, oppure come applicazione mobile. __Il Gruppo di Lavoro dovrà motivare le proprie scelte tecnologiche in fase di discussione del prodotto__.
Si sottolinea, inoltre, che le fasi di analisi e progettazione object-oriented del sistema sono fondamentali per una positiva valutazione del Progetto Modulo A. *L'utilizzo di servizi di MBaaS (e.g., Firebase) non sostituisce dette fasi di analisi e progettazione, e pertanto non saranno accettati i progetti in cui logica applicativa e persistenza dei dati sono gestiti esclusivamente tramite servizi esterni, rendendo il sistema troppo dipendente da uno specifico provider.*
