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
public abstract class EntitasEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String nama;

    public EntitasEvent(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }

    public abstract String getDeskripsi();
}
