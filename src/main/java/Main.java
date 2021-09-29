import Model.City;
import Services.CityService;
import Services.Service;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Service<City> service = CityService.getInstance();
        service.saveFromFile();
        while (true) {
            System.out.println("1) Список городов");
            System.out.println("2) Список городов по имени в обратном порядке");
            System.out.println("3) Список городов по федеральному округу и имени в обратном опрядке");
            System.out.println("4) Город с наибольшим населением");
            System.out.println("5) Количество городов по регионам");
            System.out.println("10) Выход");
            Scanner console = new Scanner(System.in);
            int choice = console.nextInt();
            if (choice == 1) {
                service.printAll();
            } else if (choice == 2) {
                service.printAllOrdered((c1, c2) -> c2.getName().toUpperCase(Locale.ROOT).compareTo(c1.getName().toUpperCase(Locale.ROOT)));
            } else if (choice == 3) {
                service.printAllOrdered(((c1, c2) -> {
                    int comp = c2.getDistrict().toUpperCase(Locale.ROOT).compareTo(c1.getDistrict().toUpperCase(Locale.ROOT));
                    if (comp == 0) {
                        comp = c2.getName().toUpperCase(Locale.ROOT).compareTo(c1.getName().toUpperCase(Locale.ROOT));
                    }
                    return comp;
                }));
            } else if (choice == 4) {
                service.printIndexOfMaxValue();
            } else if (choice == 5) {
                service.printGroup();
            } else if (choice == 10) {
                System.exit(0);
            }
        }
    }
}
