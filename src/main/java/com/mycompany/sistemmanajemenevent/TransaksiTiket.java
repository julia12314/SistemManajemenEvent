/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemmanajemenevent;
import java.io.Serializable;
/**
 *
 * @author USER
 */
public class TransaksiTiket implements Serializable {
    private static final long serialVersionUID = 1L;
    private Peserta peserta;
    private TiketAcara tiket;
    private int qty;

    public TransaksiTiket(Peserta peserta, TiketAcara tiket, int qty) {
        this.peserta = peserta;
        this.tiket = tiket;
        this.qty = qty;
    }

    public Peserta getPeserta() { return peserta; }
    public TiketAcara getTiket() { return tiket; }
    public int getQty() { return qty; }
    
    @Override
    public String toString() {
        return "ID=" + peserta.getId() + ",KD=" + tiket.getId() + ",JML=" + qty;
    }
}
