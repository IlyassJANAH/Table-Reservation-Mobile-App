package com.example.lenovo.pfa_project.Models;

public class Restaurant {
    private String Id;
    private String Nom;
    private String Ville;
    private String TypeCuisine;
    private String Description;
    private String Image;
    private String Adresse;
    private Float evaluation;

    public Restaurant() {

    }


    public Restaurant(String id,String nom, String ville, String typeCuisine, String description,String image,String Adr) {
        Id=id;
        Nom = nom;
        Ville = ville;
        TypeCuisine = typeCuisine;
        Description = description;
        Image = image;
        Adresse=Adr;

    }

    public String getId() {
        return Id;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getNom() {
        return Nom;
    }

    public String getVille() {
        return Ville;
    }

    public String getTypeCuisine() {
        return TypeCuisine;
    }


    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return Image;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setVille(String ville) {
        Ville = ville;
    }

    public void setTypeCuisine(String typeCuisine) {
        TypeCuisine = typeCuisine;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage(String image) {
        Image = image;
    }
}
