package com.example.lenovo.pfa_project.Models;

public class Reservation {
    private String idRestaurant;
    private String idClient;
    private String NbPersonnes;
    private String DateReserv;
    private String HeureReserv;
    private String NumPhone;
    private String idReservation;
    private String EtatReservation;




    public Reservation(String idRestaurant, String idClient,
                       String nbPersonnes, String dateReserv,
                       String heureReserv,String NumPhone) {
        this.idRestaurant = idRestaurant;
        this.idClient = idClient;
        NbPersonnes = nbPersonnes;
        DateReserv = dateReserv;
        HeureReserv = heureReserv;
        this.NumPhone=NumPhone;
        EtatReservation="En cours";

    }



    public Reservation(String id,String idRestaurant, String idClient,
                       String nbPersonnes, String dateReserv,
                       String heureReserv,String NumPhone,String etat) {
        this.idRestaurant = idRestaurant;
        this.idClient = idClient;
        NbPersonnes = nbPersonnes;
        DateReserv = dateReserv;
        HeureReserv = heureReserv;
        this.NumPhone=NumPhone;
        idReservation=id;
        EtatReservation=etat;
    }

    public Reservation(String nbPersonnes, String dateReserv, String heureReserv, String nomRest,String etat) {
        NbPersonnes = nbPersonnes;
        DateReserv = dateReserv;
        HeureReserv = heureReserv;
        idRestaurant = nomRest;
        EtatReservation=etat;

    }


    public String getEtatReservation() {
        return EtatReservation;
    }

    public void setEtatReservation(String etatReservation) {
        EtatReservation = etatReservation;
    }

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public String getNumPhone() {
        return NumPhone;
    }

    public void setNumPhone(String numPhone) {
        NumPhone = numPhone;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNbPersonnes() {
        return NbPersonnes;
    }

    public void setNbPersonnes(String nbPersonnes) {
        NbPersonnes = nbPersonnes;
    }

    public String getDateReserv() {
        return DateReserv;
    }

    public void setDateReserv(String dateReserv) {
        DateReserv = dateReserv;
    }

    public String getHeureReserv() {
        return HeureReserv;
    }

    public void setHeureReserv(String heureReserv) {
        HeureReserv = heureReserv;
    }
}
