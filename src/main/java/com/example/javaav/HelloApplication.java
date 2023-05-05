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
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class HelloApplication extends Application {
    public static Restaurant restaurant;
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static  DateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");

    @Override
    public void start(Stage stage) throws IOException, ParseException {

        InitRestaurant();
        initChrono();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Le Beyrouth");
        stage.setScene(scene);
        stage.show();
    }


    static void InitRestaurant() throws ParseException {
        String dataJson = "[]";
        try {
            dataJson= new String(Files.readAllBytes(Paths.get("src/main/resources/com/example/javaav/json/data.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonData = new JSONObject(dataJson);
        ArrayList<Employees> employees = handleEmployeeFromJson(jsonData.getJSONArray("employees"));
        ArrayList<Customers> customersFree = handleCustomersFromJson(jsonData.getJSONArray("customersList"));
        ArrayList<Tables> tables = handleTablesFromJson(jsonData.getJSONArray("tablesList"));
        Service service = handleServiceFromJson(jsonData.getJSONObject("service"));
        ArrayList<Meals> meals=handleMealsFromJson(jsonData.getJSONArray("mealsList"));
        System.out.println(customersFree);

        Customers customers2 = new Customers( "Bob", "bob@example.com", "06 98 76 54 32", 35, "10 avenue des Fleurs",   2);
        Customers customers3 = new Customers( "Charlie", "charlie@example.com", "06 44 88 12 16", 42, "22 rue du Château",  2);
        Customers customers4 = new Customers( "David", "david@example.com", "06 55 33 77 99", 20, "5 boulevard du Parc",   0);
        Customers customers5 = new Customers( "Emma", "emma@example.com", "06 66 99 11 22", 50, "3 rue des Écoles",  1);
        Customers customers6 = new Customers( "Frank", "frank@example.com", "06 23 45 67 89", 31, "14 avenue de la Gare",  3);
        Customers customers7 = new Customers( "Grace", "grace@example.com", "06 77 88 99 00", 27, "8 rue de la Paix",   1);
        Customers customers8 = new Customers( "Henry", "henry@example.com", "06 12 34 56 78", 45, "19 avenue du Soleil",   2);
        Customers customers9 = new Customers( "Isabella", "isabella@example.com", "06 66 66 66 66", 23, "11 rue de la Victoire",   0);
        Customers customers10 = new Customers( "Jack", "jack@example.com", "06 44 55 66 77", 39, "6 rue de la Fontaine",   3);

// Object 2
        ArrayList<Ingredients> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredients(2, 20, "Poulet"));
        ingredients2.add(new Ingredients(3, 5, "Poivrons"));
        Meals meal2 = new Meals("Chich Taouk", "https://assets.afcdn.com/recipe/20160405/32958_w1024h576c1cx1500cy1002.jpg", 12.5f, 35, "Brochettes de poulet mariné aux épices libanaises, servies avec du riz", 3.0f, ingredients2);

// Object 3
        ArrayList<Ingredients> ingredients3 = new ArrayList<>();
        ingredients3.add(new Ingredients(4, 15, "Aubergines"));
        ingredients3.add(new Ingredients(5, 10, "Tomates"));
        Meals meal3 = new Meals("Moussaka libanaise", "https://res.cloudinary.com/hv9ssmzrz/image/fetch/c_fill,f_auto,h_488,q_auto,w_650/https://s3-eu-west-1.amazonaws.com/images-ca-1-0-1-eu/recipe_photos/original/133618/21-final-e1476354254531.jpg", 14.5f, 20, "Gratin d'aubergines, de tomates et de pois chiches, gratiné au fromage", 3.5f, ingredients3);

// Object 4
        ArrayList<Ingredients> ingredients4 = new ArrayList<>();
        ingredients4.add(new Ingredients(6, 10, "Pois chiches"));
        ingredients4.add(new Ingredients(7, 15, "Sésame"));
        Meals meal4 = new Meals("Houmous", "https://img.cuisineaz.com/660x660/2017/06/20/i128397-.jpeg", 7.5f, 25, "Purée de pois chiches, de tahini, d'ail et de jus de citron, servie avec du pain pita", 3.0f, ingredients4);

// Object 5
        ArrayList<Ingredients> ingredients5 = new ArrayList<>();
        ingredients5.add(new Ingredients(8, 20, "Agneau"));
        ingredients5.add(new Ingredients(9, 30, "Ail"));
        Meals meal5 = new Meals("Shawarma", "https://content-images.weber.com/emea-recipes/FR/Shawarma-de-poulet.jpg?auto=compress,format&w=750", 10.5f, 40, "Viande d'agneau marinée, rôtie sur une broche et servie avec des légumes grillés et de l'ail", 4.0f, ingredients5);
        // Object 6
        ArrayList<Ingredients> ingredients6 = new ArrayList<>();
        ingredients6.add(new Ingredients(10, 15, "Poivrons"));
        ingredients6.add(new Ingredients(11, 5, "Pignons de pin"));
        Meals meal6 = new Meals("Makdous", "https://www.196flavors.com/wp-content/uploads/2021/01/makdous-4-FP.jpg", 12.5f, 35, "Aubergines farcies à l'ail, aux noix et aux poivrons", 3.0f, ingredients6);

// Object 7
        ArrayList<Ingredients> ingredients7 = new ArrayList<>();
        ingredients7.add(new Ingredients(12, 20, "Viande hachée"));
        ingredients7.add(new Ingredients(13, 10, "Tomates"));
        Meals meal7 = new Meals("Kebbeh", "https://www.auxdelicesdupalais.net/wp-content/uploads/2022/06/kebbe-libanaise-4.jpg", 14.5f, 20, "Croquettes de viande hachée et de blé concassé farcies de viande hachée et de tomate", 3.5f, ingredients7);

// Object 8
        ArrayList<Ingredients> ingredients8 = new ArrayList<>();
        ingredients8.add(new Ingredients(16, 10, "Feuilles de vigne"));
        ingredients8.add(new Ingredients(17, 2, "Tomates"));
        ingredients8.add(new Ingredients(18, 1, "Oignon"));
        ingredients8.add(new Ingredients(19, 1, "Citron"));
        ingredients8.add(new Ingredients(20, 100, "Riz"));
        ingredients8.add(new Ingredients(21, 50, "Persil frais"));
        ingredients8.add(new Ingredients(22, 50, "Menthe fraîche"));
        ingredients8.add(new Ingredients(23, 50, "Coriandre fraîche"));
        ingredients8.add(new Ingredients(24, 5, "Huile d'olive"));
        Meals meal8 = new Meals("Dolma", "https://fac.img.pmdstatic.net/fit/https.3A.2F.2Fi.2Epmdstatic.2Enet.2Ffac.2F2021.2F09.2F02.2F82c21deb-049c-4cee-b7f0-e1a1ed7fb308.2Ejpeg/750x562/quality/80/crop-from/center/cr/wqkgSVNUT0NLL0dFVFRZIElNQUdFUyAvIEZlbW1lIEFjdHVlbGxl/dolma.jpeg", 15.0f, 40, "Feuilles de vigne farcies au riz, aux herbes et aux épices, arrosées de jus de citron et d'huile d'olive", 4.5f, ingredients8);

// Object 9
        ArrayList<Ingredients> ingredients9 = new ArrayList<>();
        ingredients9.add(new Ingredients(16, 20, "Ail"));
        ingredients9.add(new Ingredients(17, 30, "Aubergines"));
        Meals meal9 = new Meals("Baba Ghanouj", "https://www.simplyleb.com/wp-content/uploads/Baba-Ghanouj-15.jpg", 16.5f, 40, "Purée d'aubergines grillées, d'ail, de tahini, de jus de citron et d'huile d'olive", 4.0f, ingredients9);



        restaurant = new Restaurant(jsonData.getString("name"), jsonData.getInt("recipe"), jsonData.getString("adress"), employees, customersFree, meals, tables, jsonData.getInt("capital"), service);
    }



    private static ArrayList<Employees> handleEmployeeFromJson(JSONArray json){
        ArrayList<Employees> employees = new ArrayList<>();
        IntStream
                .range(0,json.length()).mapToObj(json::getJSONObject).forEach(e ->{
                    employees.add(new Employees(e.getString("name"),e.getString("mail"),
                            e.getString("tel"),e.getInt("age"), e.getString("adress"),e.getString("jobName"),
                            e.getInt("workHours"),e.getFloat("salary")));
                });
        return employees;

    }
    private static ArrayList<Customers> handleCustomersFromJson(JSONArray json) {
        ArrayList<Customers> customers = new ArrayList<>();
        IntStream.range(0, json.length())
                .mapToObj(json::getJSONObject)
                .forEach(c -> {
                    JSONArray ordersJson = c.getJSONArray("orders");
                    ArrayList<Orders> orders = new ArrayList<>();
                    if (ordersJson.length() > 0) {
                        orders = IntStream.range(0, ordersJson.length())
                                .mapToObj(i -> ordersJson.getJSONObject(i))
                                .map(o -> {
                                    JSONArray mealsJson = o.getJSONArray("mealList");
                                    ArrayList<Meals> meals = IntStream.range(0, mealsJson.length())
                                            .mapToObj(i -> mealsJson.getJSONObject(i))
                                            .map(m -> new Meals(m.getString("name"), m.getString("imgUrl"),
                                                    m.getFloat("price"), m.getInt("nbOrder"), m.getString("desc"),
                                                    m.getFloat("marge"), handleIngredientsFromJson(m.getJSONArray("ingredients"))))
                                            .collect(Collectors.toCollection(ArrayList::new));
                                    try {
                                        return new Orders(meals, o.getDouble("totalPrice"), DATE_FORMAT.parse(o.getString("hour")));
                                    } catch (ParseException e) {
                                        // Handle the exception appropriately
                                        return null;
                                    }
                                })
                                .filter(Objects::nonNull)
                                .collect(Collectors.toCollection(ArrayList::new));
                    }
                    int groupId = c.getInt("groupId");

                    Customers customer = new Customers(c.getString("name"), c.getString("mail"), c.getString("tel"),
                            c.getInt("age"), c.getString("adress"),  groupId);

                    customer.getOrders().addAll(orders);
                    customers.add(customer);
                });
        return customers;
    }
    private static ArrayList<Ingredients> handleIngredientsFromJson(JSONArray json) {
        return IntStream.range(0, json.length())
                .mapToObj(i -> json.getJSONObject(i))
                .map(j -> {
                    return new Ingredients(j.getInt("id"), j.getInt("price"), j.getString("name"));
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Tables> handleTablesFromJson(JSONArray json) {
        ArrayList<Tables> tables = new ArrayList<>();
        IntStream.range(0, json.length())
                .mapToObj(json::getJSONObject)
                .forEach(t -> {
                    int tableNumber = t.getInt("tableNumber");
                    int size = t.getInt("size");
                    String place = t.getString("place");
                    boolean isFree = t.getBoolean("isFree");
                    JSONArray customersJson = t.getJSONArray("customers");
                    ArrayList<Customers> customers = new ArrayList<>();
                    if (customersJson.length() > 0) {
                       customers= handleCustomersFromJson(customersJson);
                    }
                    tables.add(new Tables(tableNumber, size, place, isFree, customers));
                });
        return tables;
    }
    
    private static ArrayList<Meals> handleMealsFromJson(JSONArray mealsJson) {
        ArrayList<Meals> meals = IntStream.range(0, mealsJson.length())
                .mapToObj(i -> mealsJson.getJSONObject(i))
                .map(m -> new Meals(m.getString("name"), m.getString("imgUrl"),
                        m.getFloat("price"), m.getInt("nbOrder"), m.getString("desc"),
                        m.getFloat("marge"), handleIngredientsFromJson(m.getJSONArray("ingredients"))))
                .collect(Collectors.toCollection(ArrayList::new));
        return meals;
    }
    private static Service handleServiceFromJson(JSONObject json) throws ParseException {
        Date serviceStart = HOUR_FORMAT.parse(json.getString("serviceStart"));
        Date serviceEnd = HOUR_FORMAT.parse(json.getString("serviceEnd"));

        boolean isRunning = true;
        String seconds = json.getString("seconds");
        return new Service(serviceStart, serviceEnd, isRunning, seconds);
    }
    static void initChrono() {

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