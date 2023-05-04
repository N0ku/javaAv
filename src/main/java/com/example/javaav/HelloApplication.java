package com.example.javaav;

import com.example.javaav.Model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.text.DateFormat;
import java.util.stream.IntStream;


public class HelloApplication extends Application {
    public static Restaurant restaurant;


    @Override
    public void start(Stage stage) throws IOException, ParseException {

        InitRestaurant();
        InitChrono();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Restaurant");
        stage.setScene(scene);
        stage.show();
    }


    static void InitRestaurant() throws ParseException {
        Employees employe1 = new Employees(0, "Emma", "emma@mail.com", "06 72 34 56 78", 20, "Rue des Lilas", "Cuisinier", 20, 1245.67f);
        Employees employe2 = new Employees(1, "Oscar", "oscar@mail.com", "06 72 34 56 78", 23, "Rue des Lilas", "Chef", 30, 1405.11f);
        Employees employe3 = new Employees(2, "Sophie", "sophie@mail.com", "06 72 34 56 78", 40, "Rue des Lilas", "Serveur", 22, 1100.02f);
        Employees employe4 = new Employees(3, "Leo", "leo@mail.com", "06 72 34 56 78", 19, "Rue des Lilas", "Serveur", 23, 1381.93f);
        Employees employe5 = new Employees(4, "Hugo", "hugo@mail.com", "06 72 34 56 78", 30, "Rue des Lilas", "Commis", 10, 400.23f);
        String dataJson = "[]";
        try {
            dataJson= new String(Files.readAllBytes(Paths.get("src/main/resources/com/example/javaav/json/data.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonData = new JSONObject(dataJson);
       ArrayList<Employees> employees = handleEmployeeFromJson(jsonData.getJSONArray("employees"));

        Customers customers1 = new Customers(0, "Alice", "alice@example.com", "06 12 34 56 78", 28, "1 rue de la Liberté", null,  0);
        Customers customers2 = new Customers(1, "Bob", "bob@example.com", "06 98 76 54 32", 35, "10 avenue des Fleurs", null,  2);
        Customers customers3 = new Customers(2, "Charlie", "charlie@example.com", "06 44 88 12 16", 42, "22 rue du Château", null,  2);
        Customers customers4 = new Customers(3, "David", "david@example.com", "06 55 33 77 99", 20, "5 boulevard du Parc", null,  0);
        Customers customers5 = new Customers(4, "Emma", "emma@example.com", "06 66 99 11 22", 50, "3 rue des Écoles", null,  1);
        Customers customers6 = new Customers(5, "Frank", "frank@example.com", "06 23 45 67 89", 31, "14 avenue de la Gare", null,  3);
        Customers customers7 = new Customers(6, "Grace", "grace@example.com", "06 77 88 99 00", 27, "8 rue de la Paix", null,  1);
        Customers customers8 = new Customers(7, "Henry", "henry@example.com", "06 12 34 56 78", 45, "19 avenue du Soleil", null,  2);
        Customers customers9 = new Customers(8, "Isabella", "isabella@example.com", "06 66 66 66 66", 23, "11 rue de la Victoire", null,  0);
        Customers customers10 = new Customers(9, "Jack", "jack@example.com", "06 44 55 66 77", 39, "6 rue de la Fontaine", null,  3);

        ArrayList<Customers> customersFree = new ArrayList<>(Arrays.asList(customers1, customers2, customers3, customers4, customers5, customers6, customers7, customers8, customers9, customers10));

        ArrayList<Customers> customers = new ArrayList<>();
        ArrayList<Customers> customersTest = new ArrayList<>();
        customersTest.add(customers1);
        customersTest.add(customers2);


        Tables table1 = new Tables(0, 5, "Terrasse", false, customersTest);
        Tables table2 = new Tables(1, 8, "Terrasse", true, customers);
        Tables table3 = new Tables(2, 2, "Terrasse", true, customers);
        Tables table4 = new Tables(3, 1, "Terrasse", true, customers);
        Tables table5 = new Tables(4, 6, "Terrasse", true, customers);

        ArrayList<Tables> tables = new ArrayList<>(Arrays.asList(table1, table2, table3, table4, table5));

        DateFormat format = new SimpleDateFormat("HH:mm");
        Date serviceStart = format.parse("12:00");
        Date serviceEnd = format.parse("12:25");

        Service service = new Service(serviceStart, serviceEnd, true, "00:00");

        ArrayList<String> allergies = new ArrayList<>();

// Object 1
        ArrayList<Ingredients> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredients(0, 10, "Tomates", allergies));
        ingredients1.add(new Ingredients(1, 20, "Fromage", allergies));
        Meals meal1 = new Meals("Manakish Zaatar", "https://frenchbakery.cafe/wp-content/uploads/2021/08/RARE0108.jpg", 5.5f, 50, "Pain plat au thym et au sumac", 2.5f, ingredients1);

// Object 2
        ArrayList<Ingredients> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredients(2, 20, "Poulet", allergies));
        ingredients2.add(new Ingredients(3, 5, "Poivrons", allergies));
        Meals meal2 = new Meals("Chich Taouk", "https://assets.afcdn.com/recipe/20160405/32958_w1024h576c1cx1500cy1002.jpg", 12.5f, 35, "Brochettes de poulet mariné aux épices libanaises, servies avec du riz", 3.0f, ingredients2);

// Object 3
        ArrayList<Ingredients> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredients(4, 15, "Aubergines", allergies));
        ingredients3.add(new Ingredients(5, 10, "Tomates", allergies));
        Meals meal3 = new Meals("Moussaka libanaise", "https://res.cloudinary.com/hv9ssmzrz/image/fetch/c_fill,f_auto,h_488,q_auto,w_650/https://s3-eu-west-1.amazonaws.com/images-ca-1-0-1-eu/recipe_photos/original/133618/21-final-e1476354254531.jpg", 14.5f, 20, "Gratin d'aubergines, de tomates et de pois chiches, gratiné au fromage", 3.5f, ingredients3);

// Object 4
        ArrayList<Ingredients> ingredients4 = new ArrayList<>();
        ingredients4.add(new Ingredients(6, 10, "Pois chiches", allergies));
        ingredients4.add(new Ingredients(7, 15, "Sésame", allergies));
        Meals meal4 = new Meals("Houmous", "https://img.cuisineaz.com/660x660/2017/06/20/i128397-.jpeg", 7.5f, 25, "Purée de pois chiches, de tahini, d'ail et de jus de citron, servie avec du pain pita", 3.0f, ingredients4);

// Object 5
        ArrayList<Ingredients> ingredients5 = new ArrayList<>();
        ingredients5.add(new Ingredients(8, 20, "Agneau", allergies));
        ingredients5.add(new Ingredients(9, 30, "Ail", allergies));
        Meals meal5 = new Meals("Shawarma", "https://content-images.weber.com/emea-recipes/FR/Shawarma-de-poulet.jpg?auto=compress,format&w=750", 10.5f, 40, "Viande d'agneau marinée, rôtie sur une broche et servie avec des légumes grillés et de l'ail", 4.0f, ingredients5);
        // Object 6
        ArrayList<Ingredients> ingredients6 = new ArrayList<>();
        ingredients6.add(new Ingredients(10, 15, "Poivrons", allergies));
        ingredients6.add(new Ingredients(11, 5, "Pignons de pin", allergies));
        Meals meal6 = new Meals("Makdous", "https://www.196flavors.com/wp-content/uploads/2021/01/makdous-4-FP.jpg", 12.5f, 35, "Aubergines farcies à l'ail, aux noix et aux poivrons", 3.0f, ingredients6);

// Object 7
        ArrayList<Ingredients> ingredients7 = new ArrayList<>();
        ingredients7.add(new Ingredients(12, 20, "Viande hachée", allergies));
        ingredients7.add(new Ingredients(13, 10, "Tomates", allergies));
        Meals meal7 = new Meals("Kebbeh", "https://www.auxdelicesdupalais.net/wp-content/uploads/2022/06/kebbe-libanaise-4.jpg", 14.5f, 20, "Croquettes de viande hachée et de blé concassé farcies de viande hachée et de tomate", 3.5f, ingredients7);

// Object 8
        ArrayList<Ingredients> ingredients8 = new ArrayList<>();
        ingredients8.add(new Ingredients(16, 10, "Feuilles de vigne", allergies));
        ingredients8.add(new Ingredients(17, 2, "Tomates", allergies));
        ingredients8.add(new Ingredients(18, 1, "Oignon", allergies));
        ingredients8.add(new Ingredients(19, 1, "Citron", allergies));
        ingredients8.add(new Ingredients(20, 100, "Riz", allergies));
        ingredients8.add(new Ingredients(21, 50, "Persil frais", allergies));
        ingredients8.add(new Ingredients(22, 50, "Menthe fraîche", allergies));
        ingredients8.add(new Ingredients(23, 50, "Coriandre fraîche", allergies));
        ingredients8.add(new Ingredients(24, 5, "Huile d'olive", allergies));
        Meals meal8 = new Meals("Dolma", "https://fac.img.pmdstatic.net/fit/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Ffac.2F2021.2F09.2F02.2F82c21deb-049c-4cee-b7f0-e1a1ed7fb308.2Ejpeg/750x562/quality/80/crop-from/center/cr/wqkgSVNUT0NLL0dFVFRZIElNQUdFUyAvIEZlbW1lIEFjdHVlbGxl/dolma.jpeg", 15.0f, 40, "Feuilles de vigne farcies au riz, aux herbes et aux épices, arrosées de jus de citron et d'huile d'olive", 4.5f, ingredients8);

// Object 9
        ArrayList<Ingredients> ingredients9 = new ArrayList<>();
        ingredients9.add(new Ingredients(16, 20, "Ail", allergies));
        ingredients9.add(new Ingredients(17, 30, "Aubergines", allergies));
        Meals meal9 = new Meals("Baba Ghanouj", "https://www.simplyleb.com/wp-content/uploads/Baba-Ghanouj-15.jpg", 16.5f, 40, "Purée d'aubergines grillées, d'ail, de tahini, de jus de citron et d'huile d'olive", 4.0f, ingredients9);


        ArrayList<Meals> meals = new ArrayList<>(Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6, meal7, meal8, meal9));

        restaurant = new Restaurant("Le Beyrouth", 0, "12 rue des Pigeons, 75002 Paris", employees, customersFree, meals, tables, 1000, service);
    }



    private static ArrayList<Employees> handleEmployeeFromJson(JSONArray json){
        ArrayList<Employees> employees = new ArrayList<>();
        IntStream
                .range(0,json.length()).mapToObj(json::getJSONObject).forEach(e ->{
                    employees.add(new Employees(e.getInt("id"),e.getString("name"),e.getString("mail"),
                            e.getString("tel"),e.getInt("age"), e.getString("adress"),e.getString("jobName"),
                            e.getInt("workHours"),e.getFloat("salary")));
                });
        return employees;

    }


    static void InitChrono() {

        Date serviceEnd = restaurant.getService().getServiceEnd();
        Date serviceStart = restaurant.getService().getServiceStart();
        long diffMillis = Math.abs(serviceEnd.getTime() - serviceStart.getTime());
        Duration duration = Duration.ofMillis(diffMillis);
        final int[] seconds = new int[]{(int) duration.toSeconds()};
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (seconds[0] > 0) {
                    String value = computeChronoValue();
                    Platform.runLater(() -> restaurant.getService().setSeconds(value));
                    Thread.sleep(1000);
                }
                return null;
            }

            private String computeChronoValue() {
                int remainingSeconds = seconds[0];
                seconds[0] = remainingSeconds - 1;

                // 15mins into seconds
                if (seconds[0] < 900) {
                    restaurant.getService().setRunning(false);
                }
                int minutes = remainingSeconds / 60;
                int secondsA = remainingSeconds % 60;
                String value = String.format("%02d:%02d", minutes, secondsA);
                if (remainingSeconds <= 0) {
                    value = "00:00";
                    cancel();
                }
                return value;
            }
        };


        new Thread(task).start();
    }

    public static void main(String[] args) {
        launch();
    }


}