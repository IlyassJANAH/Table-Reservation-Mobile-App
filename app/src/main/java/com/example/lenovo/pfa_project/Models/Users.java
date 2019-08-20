package com.example.lenovo.pfa_project.Models;

public class Users {


    private String Nom;
    private String Prenom;
    private String Type;

    public Users( String nom, String prenom) {

        Nom = nom;
        Prenom = prenom;
        Type="client";
    }
    public Users( String nom, String prenom,String type) {

        Nom = nom;
        Prenom = prenom;
        Type=type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }
}
