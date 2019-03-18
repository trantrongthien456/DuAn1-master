package com.example.johnluu.duan1.model;

public class Chuong {
    public int _idchuong;
    public int _idsach;
    public String tenchuong;
    public String duongdan;

    public String audio;

    public Chuong(String tenchuong) {
        this.tenchuong=tenchuong;
    }

    public Chuong(int _idsach,  String tenchuong,String duongdan, String audio) {
        this._idsach = _idsach;
        this.tenchuong=tenchuong;
        this.duongdan = duongdan;
        this.audio = audio;
    }

    public Chuong(int _idchuong, int _idsach,String tenchuong, String duongdan, String audio) {
        this._idchuong = _idchuong;
        this._idsach = _idsach;
        this.tenchuong=tenchuong;
        this.duongdan = duongdan;
        this.audio = audio;
    }
}
