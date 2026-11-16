/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemmanajemenevent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**
 *
 * @author USER
 */
public class SistemEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private String namaEO;
    private HashMap<String, Peserta> daftarPeserta;
    private HashMap<String, TiketAcara> daftarTiket;
    private ArrayList<TransaksiTiket> daftarTransaksi;

    public SistemEvent(String namaEO) {
        this.namaEO = namaEO;
        this.daftarPeserta = new HashMap<>();
        this.daftarTiket = new HashMap<>();
        this.daftarTransaksi = new ArrayList<>();
    }

    public void tambahPeserta(String id, String nama) {
        daftarPeserta.put(id, new Peserta(id, nama));
    }

    public void tambahTiketAcara(String id, String nama, int kuota) {
        daftarTiket.put(id, new TiketAcara(id, nama, kuota));
    }

    public void jalankanTransaksiDariFile(String namaFile) {
        File file = new File(namaFile);
        if (!file.exists()) {
            System.out.println("[ERROR] File " + namaFile + " tidak ditemukan.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String baris = scanner.nextLine();
                String[] blokTransaksi = baris.split(",(?=\\s*ID=)");

                for (String blok : blokTransaksi) {
                    blok = blok.trim();
                    if (blok.isEmpty()) continue;

                    try {
                        prosesSatuTransaksi(blok);
                    } catch (TransaksiInvalidException e) {
                        System.out.println("[TRANSAKSI DITOLAK] " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("[FORMAT ERROR] Gagal memparsing data: " + blok);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] File input tidak dapat dibuka: " + e.getMessage());
        }
    }

    private void prosesSatuTransaksi(String blok) throws TransaksiInvalidException {
        String idPeserta = "";
        String kodeTiket = "";
        int jml = 0;

        try {
            String[] parts = blok.split(",");
            for (String p : parts) {
                p = p.trim();
                if (p.startsWith("ID=")) {
                    idPeserta = p.substring(3).trim();
                } else if (p.startsWith("KD=")) {
                    kodeTiket = p.substring(3).trim();
                } else if (p.startsWith("JML=")) {
                    String jmlStr = p.substring(4).trim().replaceAll("[^0-9]", "");
                    jml = Integer.parseInt(jmlStr);
                }
            }
        } catch (Exception e) {
            throw new TransaksiInvalidException("Format string tidak valid: " + blok);
        }

        if (idPeserta.isEmpty() || kodeTiket.isEmpty()) {
            throw new TransaksiInvalidException("Format string tidak valid: " + blok);
        }

        Peserta peserta = daftarPeserta.get(idPeserta);
        if (peserta == null) {
            throw new TransaksiInvalidException("ID Peserta '" + idPeserta + "' tidak terdaftar.");
        }

        TiketAcara tiket = daftarTiket.get(kodeTiket);
        if (tiket == null) {
            throw new TransaksiInvalidException("Kode Tiket '" + kodeTiket + "' tidak ditemukan.");
        }

        if (tiket.getKuota() < jml) {
            throw new TransaksiInvalidException("Kuota tiket '" + tiket.getNama() + "' tidak mencukupi.");
        }

        tiket.kurangiKuota(jml);
        TransaksiTiket transaksi = new TransaksiTiket(peserta, tiket, jml);
        daftarTransaksi.add(transaksi);
        System.out.println("[BERHASIL] " + peserta.getNama() + " memesan " + jml + " tiket " + tiket.getNama());
    }

    public void simpanLaporanAkhir() {
        System.out.println("\n--- MENYIMPAN LAPORAN AKHIR & SERIALIZATION ---");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("storage.db"))) {
            oos.writeObject(this);
            System.out.println("[SUKSES] Objek sistem berhasil diserialisasi ke 'storage.db'.");
        } catch (IOException e) {
            System.out.println("[ERROR SERIALIZATION] " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter("laporan.txt")) {
            writer.write("=== LAPORAN TRANSAKSI BESAR (QTY >= 5) ===\n");
            boolean adaData = false;
            for (TransaksiTiket trx : daftarTransaksi) {
                if (trx.getQty() >= 5) {
                    writer.write(trx.toString() + "\n");
                    adaData = true;
                }
            }
            if (!adaData) {
                writer.write("Tidak ada transaksi dengan QTY >= 5.\n");
            }
            System.out.println("[SUKSES] Laporan teks berhasil ditulis ke 'laporan.txt'.");
        } catch (IOException e) {
            System.out.println("[ERROR FILE I/O] " + e.getMessage());
        }
    }
}