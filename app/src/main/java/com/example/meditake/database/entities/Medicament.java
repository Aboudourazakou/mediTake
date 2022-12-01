package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity=CategorieMedicament.class, parentColumns="id", childColumns="categorieId"))
public class Medicament {
    @PrimaryKey
    private Long id;

    private String nom;

    private String img;

    private int qte;

    private int categorieId;

    public Medicament() {
    }

    public Medicament( String nom, String img, int qte, int categorieId) {

        this.nom = nom;
        this.img = img;
        this.qte = qte;
        this.categorieId = categorieId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", img='" + img + '\'' +
                ", qte=" + qte +
                ", categorieId=" + categorieId +
                '}';
    }
}
