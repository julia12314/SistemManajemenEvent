/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemmanajemenevent;

/**
 *
 * @author USER
 */
public class Peserta extends EntitasEvent {
    private static final long serialVersionUID = 1L;

    public Peserta(String id, String nama) {
        super(id, nama);
    }

    @Override
    public String getDeskripsi() {
        return "Peserta [" + id + "] " + nama;
    }    
}
