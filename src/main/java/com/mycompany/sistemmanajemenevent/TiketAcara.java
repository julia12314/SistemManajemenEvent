/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemmanajemenevent;

/**
 *
 * @author USER
 */
public class TiketAcara extends EntitasEvent {
    private static final long serialVersionUID = 1L;
    private int kuota;

    public TiketAcara(String id, String nama, int kuota) {
        super(id, nama);
        this.kuota = kuota;
    }

    public int getKuota() { return kuota; }
    public void kurangiKuota(int jumlah) { this.kuota -= jumlah; }

    @Override
    public String getDeskripsi() {
        return "Tiket [" + id + "] " + nama + " (Sisa Kuota: " + kuota + ")";
    }
}    

