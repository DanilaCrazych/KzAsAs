package com.example.kzasas;

public class BDStaff {
    int id;
    String fio;
    String otdel;
    String mail;
    String loginl;
    String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOtdel() {
        return otdel;
    }

    public void setOtdel(String otdel) {
        this.otdel = otdel;
    }
    public String getLoginl() {
        return loginl;
    }

    public void setLoginl(String loginl) {
        this.loginl = loginl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public BDStaff(int id, String fio, String otdel, String mail, String loginl, String password) {
        this.id = id;
        this.fio = fio;
        this.mail = mail;
        this.otdel = otdel;
        this.loginl = loginl;
        this.password = password;
    }
}
