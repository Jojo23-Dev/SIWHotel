insert into credenziali (username, password, ruolo) values('paolo', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ADMIN_ROLE');
insert into credenziali (username, password, ruolo) values('Michele', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'DEFAULT_ROLE');
insert into utente (credenziali_id,data_di_nascita, email, telefono, cognome, nome, nazione) values (1,'2000-01-01', 'admin1@siwhotel.it', '+39 333 1234567', '1', 'Admin', 'Italia');
insert into utente (credenziali_id,data_di_nascita, email, telefono, cognome, nome, nazione) values (2,'1963-02-17', 'michaeljordan@bulls.com', '+1 333 1234567', 'Giordano', 'Michele', 'Stati Uniti');

-- FIX: gli stati prenotazione non erano mai stati inseriti nel DB.
-- PrenotazioneController/StatoPrenotazioneNonPagata/StatoPrenotazionePagata cercano gli stati con id 0, 1, 2:
-- senza queste righe ogni tentativo di prenotazione, pagamento o chiusura soggiorno andava in errore (NoSuchElementException).
insert into stato_prenotazione (id, tipo_stato) values (0, 'NON_PAGATA');
insert into stato_prenotazione (id, tipo_stato) values (1, 'PAGATA');
insert into stato_prenotazione (id, tipo_stato) values (2, 'TERMINATA');

-- insert into camera(prezzo, dimensione, id,	letti,	persone, descrizione, tipo) values(50.00, 25,	2,	4,	4,	'spaziosa camera quadrupla con backroom nell armadio',	'quadrupla')
-- insert into camera(prezzo, dimensione, id,	letti,	persone, descrizione, tipo) values(30.00,	15,	1,	2,	2,	'splendida camera matrimoniale con vista di merda',	'matrimoniale')