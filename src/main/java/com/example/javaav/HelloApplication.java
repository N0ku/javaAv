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

        stage.sizeToScene();

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
//        Service service = handleServiceFromJson(jsonData.getJSONObject("service"));
        ArrayList<Meals> meals=handleMealsFromJson(jsonData.getJSONArray("mealsList"));
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date serviceStart = format.parse("12:00");
        Date serviceEnd = format.parse("12:25");

        Service service = new Service(serviceStart, serviceEnd, true,"00:00");
        restaurant = new Restaurant(jsonData.getString("name"), jsonData.getInt("recipe"), jsonData.getString("address"), employees, customersFree, meals, tables, jsonData.getInt("capital"), service);
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
                                        e.printStackTrace();
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
                    JSONArray customersJson = t.getJSONArray("customer");
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
        Date serviceStart = HOUR_FORMAT.parse(json.getString("startTime"));
        Date serviceEnd = HOUR_FORMAT.parse(json.getString("startEnd"));

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
    public static void quitter() {
        RestaurantToJsonConverter restaurantToJsonConverter = new RestaurantToJsonConverter();
        JSONObject jsonRestaurant = restaurantToJsonConverter.toJson();
        try {
            Files.writeString(Paths.get("src/main/resources/com/example/javaav/json/data.json"),
                    jsonRestaurant.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Merci d'avoir utilisé notre programme !");
        System.exit(0);
    }



}