package com.fooyemeet2.Activities;

/**
 * Created by m.faid on 15/06/2016.
 */
public class Questions {

    private  String pseudo;
    private  String text;


    public Questions(String pseudo, String text) {
        this.pseudo = pseudo;
        this.text = text;
    }

    public  String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public  String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
