# INGSW-DietiDials24
## Progetto universitario di Ingegneria del Software
#### DietiDeals24 è una piattaforma per la gestione di aste online. Il sistema consiste in un’applicazione mobile, desktop o web-based, performante e affidabile, attraverso cui gli utenti possono fruire delle funzionalità del sistema in modo intuitivo, rapido e piacevole.
****
### Funzionalità assegnate
1. Un utente può registrare un nuovo account ed utilizzarlo per accedere al sistema. Un account può essere di due tipi: venditore o acquirente. La stessa e-mail può essere utilizzata per al più un account venditore e un account compratore. È apprezzata la possibilità di effettuare la registrazione utilizzando credenziali di terze parti (e.g.: Google, Facebook, GitHub, etc…). Gli utenti possono personalizzare il proprio profilo con una short bio, link al proprio sito web / ai propri social, area geografica, etc. <br>
2. Il sistema permette ai venditori/compratori di creare aste di diverso tipo per la vendita/acquisto di beni/servizi e presentare offerte per le aste correntemente attive. Ciascuna asta è caratterizzata da una descrizione del bene/servizio in vendita e, opzionalmente, da una fotografia dello stesso. Ciascuna asta inoltre è caratterizzata da una categoria (e.g.: elettronica, informatica, giocattoli, alimentari, servizi, etc…), introdotta per facilitare la navigazione tra le tante aste presenti nel sistema.<br>
3. Venditori e compratori possono effettuare ricerche tra le aste correntemente attive, filtrando per categoria e/o per parole chiave, e visualizzare i dettagli di ciascuna asta. Inoltre, è possibile visualizzare anche il profilo utente del venditore che ha creato l’asta.<br>
4. Un venditore può creare una nuova Asta a tempo fisso. Un’asta a tempo fisso è caratterizzata da una data di scadenza, scelta dal venditore. Inoltre, il venditore può specificare una soglia minima (segreta) di prezzo al quale vendere il prodotto. Gli acquirenti possono visualizzare i dettagli dell’asta, inclusa l’offerta più alta ricevuta finora, ma non la soglia minima di prezzo fissata dal venditore. Gli acquirenti possono, fino alla data di scadenza dell’asta, presentare delle offerte migliorative rispetto alla migliore offerta corrente, specificando l’importo desiderato (in €). Al momento della scadenza, il compratore con l’offerta più alta si aggiudica il bene/servizio. Se non si raggiunge la soglia minima segreta impostata dal venditore, l’asta viene considerata fallita. In entrambi i casi, il venditore e tutti gli acquirenti che hanno partecipato all’asta visualizzano una notifica.<br>
7. (7) Un venditore può creare una nuova Asta silenziosa. In questo tipo di asta, il venditore specifica una data di scadenza e i compratori possono inviare offerte segrete al venditore. Il venditore può scegliere se accettare o rifiutare le offerte ricevute. Una sola offerta può essere accettata per ogni asta.<br>
8. (8) Un compratore può creare una nuova Asta Inversa. In questo tipo di asta, il compratore specifica il prodotto/servizio richiesto, eventualmente inserendo un’immagine dello stesso, un prezzo iniziale che è disposto a pagare, e una data di scadenza. I venditori in grado di fornire quel particolare prodotto/servizio possono quindi partecipare all’asta competendo abbassando il prezzo. In particolare, fino al momento della scadenza dell’asta, i venditori possono presentare offerte al ribasso. Al momento della scadenza dell’asta, il venditore con l’offerta più bassa si aggiudica la fornitura del prodotto/servizio.<br>


  Tutte le funzionalità assegnate devono essere realizzate implementando un sistema distribuito in cui è possibile individuare due macro-componenti:
* una parte front-end, comprensiva delle interfacce utente;
- una parte back-end, che espone delle interfacce di programmazione (e.g.: __REST API__) che vengono utilizzate dal front-end.


È altresì auspicabile che il back-end sia distribuito come __container Docker__ e che, al momento della discussione del progetto con demo del prodotto, sia messo in opera utilizzando tecnologie allo stato dell’arte quali ad esempio __servizi di public Cloud Computing come Azure o AWS__, e sia quindi accessibile attraverso la rete Internet.<br>
Per lo svolgimento delle attività di progettazione, __è obbligatorio l’utilizzo di tool di CASE__. Inoltre, si richiede tassativamente di astrarre il design per favorire il riutilizzo del codice e la futura implementazione di altre funzionalità, __esplicitando nella documentazione le scelte intraprese per favorire tale astrazione__.<br>
Per quanto riguarda le tecnologie da utilizzare, è data piena libertà di scelta al Gruppo di Lavoro contraente, con l’unico vincolo dell’__utilizzo obbligatorio di linguaggi di programmazione Object-Oriented__. Per esempio, la parte front-end può essere realizzata come applicazione desktop (e.g.: con Java + Swing/JavaFx), come applicazione web, oppure come applicazione mobile. __Il Gruppo di Lavoro dovrà motivare le proprie scelte tecnologiche in fase di discussione del prodotto__. <br>
Si sottolinea, inoltre, che le fasi di analisi e progettazione object-oriented del sistema sono fondamentali per una positiva valutazione del Progetto Modulo A. *L'utilizzo di servizi di MBaaS (e.g., Firebase) non sostituisce dette fasi di analisi e progettazione, e pertanto non saranno accettati i progetti in cui logica applicativa e persistenza dei dati sono gestiti esclusivamente tramite servizi esterni, rendendo il sistema troppo dipendente da uno specifico provider.*
> [!IMPORTANT]
> I Gruppi che sceglieranno di realizzare il front-end con desktop quali Java Swing/JavaFX dovranno implementare anche le funzionalità 9 e 10, in aggiunta a quelle assegnate.
> ###### *9. I compratori che si aggiudicano una fornitura possono lasciare un feedback al venditore. Il feedback consiste in un valore numerico da 1 a 5 e in una descrizione testuale di al più 140 caratteri. Il punteggio medio di un venditore viene visualizzato nelle aste da lui create.<br><br> 10. Un amministratore può accedere al sistema, visualizzare statistiche sul numero di aste attive, numero di aste complessive, numero di offerte medie per asta, etc.*
****
### Output richiesti
1. <ins>Documento dei Requisiti Software</ins>
  - a. Analisi dei Requisiti
    - [ ] i. Modellazione di tutti i casi d’uso richiesti.
    - [ ] ii. Individuazione del target degli utenti
    - [ ] iii. Descrizioni Testuali Strutturate per almeno quattro **(due, se si consegna prima del 15/04/2024)** casi d’uso significativi (autenticazione esclusa) a scelta dei contraenti tra quelli richiesti.
    - [ ] iv. Prototipazione visuale via Mock-up dell’interfaccia utente per tutti i casi d’uso assegnati attraverso strumenti di rapid prototyping, come “Figma”.
    - [ ] v. Valutazione dell’usabilità a priori.
    - [ ] vi. Glossario.
  - b. Specifica dei Requisiti.
    - [ ] i. Classi, oggetti e relazioni di analisi.
    - [ ] ii. Diagrammi di sequenza di analisi per due casi d’uso significativi a scelta dei contraenti tra quelli assegnati.
    - [ ] iii. Prototipazione funzionale e progettazione degli event-based statechart dell’interfaccia grafica, relativamente ai casi d’uso individuati al punto 1.a.iii;
2. <ins>Documento di Design del sistema.</ins>
  - [ ] a. Descrizione dell’architettura proposta, con esplicita definizione dei criteri di design adottati e delle motivazioni dietro tale scelta.
  - [ ] b. Descrizione/motivazione delle scelte tecnologiche adottate.
  - [ ] c. Diagramma delle classi di design.
  - [ ] d. Diagrammi di sequenza di design per i casi d’uso identificati al punto 1.a.iii.
3. <ins>Codice Sorgente sviluppato, comprensivo di eventuale Dockerfile.</ins>
  - [ ] a. File di build automatica
  - [ ] b. Evidenza dell’uso di strumenti di versioning
  - [ ] c. Report di qualità del codice, generati da SonarQube o similari (nel caso solo per il back-end)
4. <ins>Testing e valutazione sul campo dell’usabilità.</ins>
  - [ ] a. Codice xUnit per unit testing di quattro **(due, se si consegna prima del 31/03/2024)** metodi non banali, che abbiano almeno due parametri. In aggiunta al codice, una apposita sezione della documentazione deve descrivere le strategie adottate per la progettazione dei test dei due metodi (e.g.: classi di equivalenza individuate e coperte, criteri di copertura strutturale, etc.).
  - [ ] b. Valutazione dell’usabilità sul campo, realizzata sul prodotto finito sia con tecniche simili a quelle messe in atto al punto 1.a.v, sia mediante analisi di file di log. È necessario allegare anche i file di log utilizzati nell’analisi.
> [!NOTE]
> Si noti che i Punti 1, 2 e 4 vanno realizzati come un unico documento, di seguito indicato come “documentazione”.

