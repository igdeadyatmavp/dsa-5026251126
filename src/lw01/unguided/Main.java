package lw01.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Rental[] rentals = null;

        try (Scanner scanner = new Scanner(new File("rentals.txt"))) {
            if (scanner.hasNext()) {
                int totalRecords = scanner.nextInt();
                rentals = new Rental[totalRecords];

                for (int i = 0; i < totalRecords; i++) {
                    String type = scanner.next();
                    String id = scanner.next();
                    int days = scanner.nextInt();
                    int units = scanner.nextInt();
                    Rental rental = null;

                    if (type.equalsIgnoreCase("LAPTOP")) {
                        rental = new LaptopRental(id, days);
                    } else if (type.equalsIgnoreCase("PROJECTOR")) {
                        rental = new Projector(id, days);
                    }

                    if (rental != null) {
                        final Rental currentRental = rental;
                        final int totalCharge = currentRental.calculateCharge(units);

                        rentals[i] = new Rental(id, days) {
                            @Override
                            public int calculateCharge() {
                                return totalCharge;
                            }

                            @Override
                            public String label() {
                                return currentRental.label();
                            }
                        };
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File rentals.txt not found!");
            return;
        }
        if (rentals != null) {
            for (Rental rental : rentals) {
                if (rental != null) {
                    System.out.println(rental.summary());
                }
            }
        }
    }
}
