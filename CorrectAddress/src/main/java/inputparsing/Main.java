package inputparsing;

import edu.pa.correct.Address;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Address address = new Address();
        System.out.println("Enter country: ");
        address.setCountry(scanner.nextLine());
        System.out.println("Enter county: ");
        address.setCounty(scanner.nextLine());
        System.out.println("Enter city: ");
        address.setCity(scanner.nextLine());
        System.out.println("Enter street address: ");
        address.setStreetAddress(scanner.nextLine());
        System.out.println("Enter postal code: ");
        address.setPostalCode(scanner.nextLine());
        InputParse inputParse = new InputParse(address);

    }
}