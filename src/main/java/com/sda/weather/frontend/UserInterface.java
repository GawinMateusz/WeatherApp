package com.sda.weather.frontend;

import com.sda.weather.location.LocationController;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@RequiredArgsConstructor
public class UserInterface {

    private final LocationController locationController;
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("Aplikacja jest uruchomiona\n");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Witaj w aplikacji pogody, co chcesz zrobić?");
            System.out.println("1. Dodać nową lokalizację");
            System.out.println("2. Wyświetlić lokalizacje");
            System.out.println("3. Pobrać informacje pogodowe");
            System.out.println("0. Wyjść z aplikacji");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addLocalization();
                    break;
                case 2:
                    getAllLocalizations();
                    break;
                case 3:
                    getWeatherInfo();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void getWeatherInfo() {
        System.out.println("Wpisz numer lokalizacji dla której chcesz wybrać pogodę: ");
        for (int i = 0; i < locationController.getAllLocations().size(); i++) {
            System.out.println(i + 1 + " : " + locationController.getAllLocations().get(i).getCityName()
                    + ", Współrzędne:\nSzerokość: " + locationController.getAllLocations().get(i).getLatitude()
                    + "\nDługość: " + locationController.getAllLocations().get(i).getLongitude());
        }
        System.out.println("Jeśli Twojej lokalizacji brak na liście wpisz 0, aby cofnąć i dodaj lokalizację!");
        int locationNumber = scanner.nextInt();
        if (locationNumber == 0) {
            this.run();
            return;
        }
        System.out.println("Wybierz dzień dla którego należy wyświetlić pogodę: ");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.now();
        for (int i = 0; i < 5; i++) {
            System.out.println(i + 1 + " : " + dateTime.format(dtf));
            dateTime = dateTime.plusDays(1);
        }
        int dayToCheckWeather = scanner.nextInt();
        LocalDate finaldate = LocalDate.now().plusDays(dayToCheckWeather - 1);
        String response = locationController.getWeather(locationNumber, finaldate);
        System.out.println(response);
    }

    private void getAllLocalizations() {
        String locations = locationController.getAllLocationsToString();
        System.out.println("Odpowiedź serwera: " + locations);
    }

    private void addLocalization() {
        System.out.println("Podaj nazwę nowej miejscowości: ");
        String cityName = scanner.nextLine();
        System.out.println("Podaj szerokość geograficzną [W przypadku minut kątowych użyj przecinka jako separatora: -90,0 -> E, 90,0 -> W]: ");
        Double latitude = scanner.nextDouble();
        System.out.println("Podaj dlugość geograficzną [W przypadku minut kątowych użyj przecinka jako separatora: -180,0 -> S, 180,0 -> N]: ");
        Double longitude = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj nazwę kraju: ");
        String countryName = scanner.nextLine();
        System.out.println("Jeśli chcesz wpisz region, jeśli nie wciśnij enter: ");
        String region = scanner.nextLine();
        String requestBody = String.format("{\"cityName\":\"%s\",\"latitude\":\"%s\",\"longitude\":\"%s\",\"countryName\":\"%s\",\"region\":\"%s\"}", cityName, latitude, longitude, countryName, region);
        String responseBody = locationController.createLocation(requestBody);
        System.out.println("Odpowiedź serwera: " + responseBody);
    }
}
