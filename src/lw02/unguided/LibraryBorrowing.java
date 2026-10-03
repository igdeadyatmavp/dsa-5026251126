package lw02.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class LibraryBorrowing {
    public static void main(String[] args) {
        // Inisialisasi LinkedList untuk Buku
        LinkedList<String[]> bookList = new LinkedList<>();
        bookList.add(new String[]{"Kalkulus", "2"});
        bookList.add(new String[]{"Fisika", "1"});
        bookList.add(new String[]{"Statistika", "2"});

        // Inisialisasi LinkedList untuk Member
        LinkedList<String[]> memberList = new LinkedList<>();
        int MAX_BORROW = 2;

        // Inisialisasi LinkedList untuk menyimpan semua request pembacaan
        LinkedList<String[]> requestList = new LinkedList<>();

        // Membaca file borrowing.txt
        try {
            // Pastikan path ini menunjuk tepat ke lokasi borrowing.txt Anda
            File file = new File("borrowing.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                requestList.add(parts);

                // Tambahkan member baru dengan jumlah pinjaman awal 0 jika belum terdaftar
                boolean isMemberExist = false;
                for (String[] member : memberList) {
                    if (member[0].equals(parts[0])) {
                        isMemberExist = true;
                        break;
                    }
                }
                if (!isMemberExist) {
                    memberList.add(new String[]{parts[0], "0"});
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File borrowing.txt tidak ditemukan.");
            return;
        }

        // Memindahkan data request ke dalam Queue secara FIFO
        Queue<String[]> requestQueue = new LinkedList<>(requestList);

        // Stack untuk menyimpan request yang gagal secara LIFO
        Stack<String[]> failedStack = new Stack<>();

        // List terpisah untuk menyimpan request yang berhasil
        LinkedList<String[]> successfulRequests = new LinkedList<>();

        // Memproses setiap request dari Queue
        while (!requestQueue.isEmpty()) {
            String[] currentRequest = requestQueue.poll();
            String memberName = currentRequest[0];
            String bookTitle = currentRequest[1];

            String[] targetBook = null;
            String[] targetMember = null;

            // Pencarian referensi buku
            for (String[] book : bookList) {
                if (book[0].equals(bookTitle)) {
                    targetBook = book;
                    break;
                }
            }

            // Pencarian referensi member
            for (String[] member : memberList) {
                if (member[0].equals(memberName)) {
                    targetMember = member;
                    break;
                }
            }

            // Evaluasi kondisi peminjaman
            if (targetBook != null && targetMember != null) {
                int currentStock = Integer.parseInt(targetBook[1]);
                int currentBorrowed = Integer.parseInt(targetMember[1]);

                if (currentStock > 0 && currentBorrowed < MAX_BORROW) {
                    // Update stok dan jumlah pinjaman jika berhasil
                    targetBook[1] = String.valueOf(currentStock - 1);
                    targetMember[1] = String.valueOf(currentBorrowed + 1);
                    successfulRequests.add(currentRequest);
                } else {
                    // Dorong ke Stack jika gagal
                    failedStack.push(currentRequest);
                }
            }
        }

        // Cetak daftar request yang berhasil
        System.out.println("=== Successfully Processed Requests ===");
        for (String[] req : successfulRequests) {
            System.out.println(req[0] + " " + req[1]);
        }

        // Cetak sisa stok buku
        System.out.println("=== Remaining Book Stock ===");
        for (String[] book : bookList) {
            System.out.println(book[0] + ": " + book[1]);
        }

        // Cetak request yang gagal dari Stack (menghasilkan urutan LIFO)
        System.out.println("=== Failed Requests ===");
        while (!failedStack.isEmpty()) {
            String[] failedReq = failedStack.pop();
            System.out.println(failedReq[0] + " " + failedReq[1]);
        }
    }
}