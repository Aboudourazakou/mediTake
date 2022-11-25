package com.example.meditake.database.dto;

public class UtilisateurLogin {


    private String login;
    private String motDePasse;

    public UtilisateurLogin(String login, String motDePasse) {
        this.login = login;
        this.motDePasse = motDePasse;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /*
    public static UtilisateurLogin map(Utilisateur utilisateur){
        UtilisateurLogin u = new UtilisateurLogin();
        u.login = utilisateur.getLogin();
        u.motDePasse = utilisateur.getMotDePasse();
        return u;
    }*/
}
