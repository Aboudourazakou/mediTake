package com.example.meditake.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity=CategorieMedicament.class, parentColumns="id", childColumns="categorieId"))
public class Medicament {
    @PrimaryKey
    private Long id;

    private String nom;
    private int qte;

    private long categorieId;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public Medicament() {
    }

    public Medicament( String nom, byte[] img, int qte, int categorieId) {

        this.nom = nom;
        this.image = img;
        this.qte = qte;
        this.categorieId = categorieId;
    }

    public Medicament(Long id, String nom, byte[] img, int qte, int categorieId) {
        this.id = id;
        this.nom = nom;
        this.image = img;
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

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(long categorieId) {
        this.categorieId = categorieId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", qte=" + qte +
                ", categorieId=" + categorieId +
                '}';
    }
}
