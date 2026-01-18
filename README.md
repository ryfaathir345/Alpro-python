# Huffman & Dijkstra Visualization

Sebuah aplikasi Java berbasis **Swing GUI** untuk memvisualisasikan algoritma **Huffman** dan **Dijkstra**.
Aplikasi ini memungkinkan pengguna untuk:

* Menghitung kode Huffman dan hasil encoding dari teks.
* Menemukan jarak terpendek pada sebuah graph menggunakan algoritma Dijkstra.
* Melihat visualisasi graph secara interaktif.

---

## Fitur

### Huffman

* Input teks, tampilkan frekuensi karakter.
* Hasilkan kode Huffman untuk setiap karakter.
* Tampilkan hasil encoding dari teks.

### Dijkstra

* Input graph dalam format:

  ```
  A B 5
  B C 3
  C D 2
  ```
* Hitung jarak terpendek dari node awal.
* Visualisasi node dan edge dengan warna menandakan jarak.

---

## Instalasi

1. Pastikan sudah terpasang **Java JDK 8+**.
2. Clone repository ini:

   ```bash
   git clone https://github.com/ryfaathir345/Alpro-python.git
   ```
3. Masuk ke folder project:

   ```bash
   cd huffman-dijkstra-visualization
   ```
4. Compile project:

   ```bash
   javac -d bin src/org/example/AlgoritmaApp.java
   ```
5. Jalankan aplikasi:

   ```bash
   java -cp bin org.example.AlgoritmaApp
   ```

---

## Cara Penggunaan

1. Jalankan aplikasi, akan muncul jendela GUI.
2. Pilih algoritma dari dropdown (`Huffman` atau `Dijkstra`).
3. Masukkan input di panel kiri:

   * **Huffman:** masukkan teks biasa.
   * **Dijkstra:** masukkan graph dalam format `node1 node2 weight`.
4. Tekan tombol **Proses**.
5. Hasil akan muncul di panel tengah, sedangkan visualisasi graph muncul di panel kanan.

---

## Contoh Input

### Huffman

```
hello world
```

### Dijkstra

```
A B 4
A C 2
B C 5
B D 10
C D 3
```

---

## Teknologi

* Java 8+
* Swing GUI

---

## Lisensi

MIT License © 2026
