import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import reservation.Seats;
import reservation.Food;

public class MovieReservationApp extends Application {

    //Global data
    public class GlobalData {
        public static String selectedMovie;
        public static String selectedShowtime;
        public static List<Seats> selectedSeats = new ArrayList<>();
        public static List<Seats> reservedSeats = new ArrayList<>();
        public static int adultTickets = 0;
        public static int childTickets = 0;
        public static int seniorTickets = 0;
        public static List<Food> selectedFood = new ArrayList<>();
    }

    //welcome screen
    public void start(Stage primaryStage) {
        VBox welcomeScreen = new VBox(20);
        welcomeScreen.setAlignment(Pos.CENTER);
        welcomeScreen.setPadding(new Insets(20));
        welcomeScreen.setStyle("-fx-background-color: black;");

        Label welcomeLabel = new Label("Welcome to The Cinema!");
        welcomeLabel.setStyle("-fx-text-fill: white;-fx-font-size: 32px; -fx-font-weight: bold;");

        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold;");
        continueButton.setCursor(Cursor.HAND);
        continueButton.setOnAction(_ -> {

            selectMovieScreen(primaryStage);
        });

        welcomeScreen.getChildren().addAll(welcomeLabel, continueButton);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();

        Scene scene = new Scene(welcomeScreen, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Movie Reservation App");

        primaryStage.centerOnScreen();

        primaryStage.show();

    }

    //select movie & showtime screen
    public void selectMovieScreen(Stage primaryStage) {
        VBox selectMovieScreen = new VBox(20);
        selectMovieScreen.setAlignment(Pos.CENTER);
        selectMovieScreen.setPadding(new Insets(20));
        selectMovieScreen.setStyle("--fx-background-color:rgb(0, 0, 0);");

        //select movie label
        Label selectMovieLabel = new Label("Select a Movie");
        selectMovieLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        //select showtime label
        Label selectShowtimeLabel = new Label("Select a Showtime");
        selectShowtimeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        //movie list
        List<String> movieList = List.of("Get Out", "The Princess & the Frog", "The Lion King", "Black Panther");

        //showtimes map
        Map<String, List<String>> showtimesMap = Map.of(
        "Get Out", List.of("12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM"),
        "The Princess & the Frog", List.of("11:00 AM", "2:00 PM", "5:00 PM", "8:00 PM"),
        "The Lion King", List.of("10:00 AM", "1:00 PM", "4:00 PM", "7:00 PM"),
        "Black Panther", List.of("9:00 AM", "12:00 PM", "3:00 PM", "6:00 PM")
        );

        // Movie combo box
        ComboBox<String> movieChoiceBox = new ComboBox<>();
        movieChoiceBox.getItems().addAll(movieList);
        movieChoiceBox.setPromptText("Select Movie");
        movieChoiceBox.setStyle("-fx-background-color: white; -fx-font-size: 16px;");
        movieChoiceBox.setCursor(Cursor.HAND);

        
        //showtimes combo box
        ComboBox<String> showtimesChoiceBox = new ComboBox<>();
        showtimesChoiceBox.setPromptText("Select Showtime");
        showtimesChoiceBox.setStyle("-fx-background-color: white; -fx-font-size: 16px;");
        showtimesChoiceBox.setCursor(Cursor.HAND);

        movieChoiceBox.setOnAction(_ -> {
            showtimesChoiceBox.getItems().clear();
            if (showtimesMap.containsKey(movieChoiceBox.getValue())) {
                showtimesChoiceBox.getItems().addAll(showtimesMap.get(movieChoiceBox.getValue()));
            }
        });

        //continue button
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        continueButton.setCursor(Cursor.HAND);
        continueButton.setOnAction(_ -> {
            if(movieChoiceBox.getValue() == null || showtimesChoiceBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a movie and showtime", ButtonType.OK);
                alert.showAndWait();
            } else {
                GlobalData.selectedMovie = movieChoiceBox.getValue();
                GlobalData.selectedShowtime = showtimesChoiceBox.getValue();
                
                selectSeatsScreen(primaryStage);
            }
        });

        selectMovieScreen.getChildren().addAll(selectMovieLabel, movieChoiceBox, selectShowtimeLabel, showtimesChoiceBox, continueButton);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();

        Scene scene = new Scene(selectMovieScreen, screenWidth, screenHeight);
        selectMovieScreen.setStyle("-fx-background-color:rgb(0, 0, 0);");
        primaryStage.setTitle("Movie Reservation App");
        primaryStage.setScene(scene);

        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    //select seats screen
    public void selectSeatsScreen(Stage primaryStage) {

        BorderPane rootLayout = new BorderPane();

        Random random = new Random();
        int totalReservedSeats = 8;

        for (int i = 0; i < totalReservedSeats; i++) {
            int randomSeatNumber = random.nextInt(30) + 1;
            String seatNumber = String.valueOf(randomSeatNumber);
            Seats reservedSeats = new Seats(seatNumber, true);

            if(!GlobalData.reservedSeats.contains(reservedSeats)) {
                GlobalData.reservedSeats.add(reservedSeats);
            }

        }

        StackPane screenPane = new StackPane();
        VBox.setMargin(screenPane, new Insets(20, 0, 0, 0));
        
        Rectangle screenRectangle = new Rectangle(700, 70);
        screenRectangle.setFill(Color.GRAY);
        screenRectangle.setArcWidth(15); //Rounded corners
        screenRectangle.setArcHeight(15);

        Label screenLabel = new Label("SCREEN");
        screenLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white");

        screenPane.getChildren().addAll(screenRectangle, screenLabel);
        screenPane.setAlignment(Pos.CENTER);

        rootLayout.setTop(screenPane);

        VBox selectSeatsScreen = new VBox(20);
        selectSeatsScreen.setAlignment(Pos.CENTER);
        selectSeatsScreen.setPadding(new Insets(20));

        Label selectSeatsLabel = new Label("Select Seats");
        selectSeatsLabel.setTextFill(Color.WHITE);
        selectSeatsLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitle = new Label("(The seats in gray are reserved.)");
        subtitle.setTextFill(Color.WHITE);
        
        GridPane seatGrid = new GridPane();
        seatGrid.setAlignment(Pos.CENTER);
        seatGrid.setHgap(40);
        seatGrid.setVgap(40);

        final int rows = 5;
        final int columns = 6;
        double buttonWidth = 70;
        double buttonHeight = 70;

        for (int row = 0; row < rows; row++) {
            char columnLetter = (char) ('A' + row);
            for (int column = 0; column < columns; column++) {
                String seatNumber = String.valueOf((row * columns) + column + 1);
                String seatLabel = columnLetter + "-" + (column + 1);
                Seats seat = new Seats(seatNumber, false);

                //button for each seat
                Button seatButton = new Button(seatLabel);

                if (GlobalData.reservedSeats.contains(seat)) {
                    seatButton.setStyle(
                        "-fx-background-color: #d3d3d3; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold; " +
                        "-fx-padding: 10px; -fx-background-radius: 15px;"
                    );
                    seatButton.setDisable(true);


                } else {
                    seatButton.setStyle(
                        "-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold; " +
                        "-fx-padding: 10px; -fx-background-radius: 15px;"
                    );

                }
                
                seatButton.setCursor(Cursor.HAND);
                seatButton.setPrefSize(buttonWidth, buttonHeight);

                //handle selection and deselection
                seatButton.setOnAction(_ -> {
                    if (GlobalData.selectedSeats.contains(seat)) {
                        GlobalData.selectedSeats.remove(seat);
                        seatButton.setStyle(
                            "-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold; " +
                            "-fx-padding: 10px; -fx-background-radius: 15px;"
                        );
                    } else {
                        GlobalData.selectedSeats.add(seat);
                        seatButton.setStyle(
                            "-fx-background-color: #FF4500; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold; " +
                            "-fx-padding: 10px; -fx-background-radius: 15px;"
                        );
                    }
                });
                
                seatGrid.add(seatButton, column, row);
            }
        }

        ScrollPane scrollPane = new ScrollPane(seatGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        AnchorPane anchorPane = new AnchorPane();

        //continue button
        Button continueButton = new Button("Continue");
        AnchorPane.setBottomAnchor(continueButton, 20.0);
        AnchorPane.setRightAnchor(continueButton, 20.0);
        continueButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        continueButton.setCursor(Cursor.HAND);
        continueButton.setOnAction(_ -> {
            if (GlobalData.selectedSeats.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select at least one seat", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                // Print selected seats
                System.out.println("Selected Seats: ");
                GlobalData.selectedSeats.forEach(seat -> seat.setReserved(true));
            }

            selectTicketScreen(primaryStage);
        });

        //back button
        Button backButton = new Button("Back");
        AnchorPane.setBottomAnchor(backButton, 20.0);
        AnchorPane.setLeftAnchor(backButton, 20.0);
        backButton.setStyle("-fx-background-color: black; -fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(_ -> {
            GlobalData.selectedSeats.clear();
            selectMovieScreen(primaryStage);
        });

        anchorPane.getChildren().addAll(continueButton, backButton);

        selectSeatsScreen.getChildren().addAll(screenPane, selectSeatsLabel, seatGrid, subtitle, anchorPane);

        rootLayout.setCenter(selectSeatsScreen);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();

        Scene scene = new Scene(rootLayout, screenWidth, screenHeight);
        rootLayout.setStyle("-fx-background-color: black");
        primaryStage.setTitle("Movie Reservation App");
        primaryStage.setScene(scene);


        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    //select ticket type screen
    public void selectTicketScreen(Stage primaryStage) {
  
        VBox ticketSelectionScreen = new VBox(20);
        ticketSelectionScreen.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Select the number of tickets you want");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        Label adultTicketLabel = new Label("Adult Tickets ($10.00):");
        adultTicketLabel.setStyle("-fx-text-fill: white; -fx-text-weight: bold; -fx-font-size: 16px");
        Spinner<Integer> adultTicketSpinner = new Spinner<>(0, 10, GlobalData.adultTickets);
        adultTicketSpinner.setCursor(Cursor.HAND);

        Label childTicketLabel = new Label("Child Tickets ($5.00)");
        childTicketLabel.setStyle("-fx-text-fill: white; -fx-text-weight: bold; -fx-font-size: 16px;");
        Spinner<Integer> childTicketSpinner = new Spinner<>(0, 10, GlobalData.childTickets);
        childTicketSpinner.setCursor(Cursor.HAND);

        Label seniorTicketLabel = new Label("Senior Tickets ($7.50)");
        seniorTicketLabel.setStyle("-fx-text-fill: white; -fx-text-weight: bold; -fx-font-size: 16px;");
        Spinner<Integer> seniorTicketSpinner = new Spinner<>(0, 10, GlobalData.seniorTickets);
        seniorTicketSpinner.setCursor(Cursor.HAND);

        //Continue button
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        continueButton.setCursor(Cursor.HAND);
        continueButton.setOnAction(_ -> {
            if (adultTicketSpinner.getValue() == 0 && childTicketSpinner.getValue() == 0 && seniorTicketSpinner.getValue() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select at least one ticket", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                GlobalData.adultTickets = adultTicketSpinner.getValue();
                GlobalData.childTickets = childTicketSpinner.getValue();
                GlobalData.seniorTickets = seniorTicketSpinner.getValue();
                GlobalData.selectedSeats.forEach(seat -> seat.setReserved(true));
            }
           
            //Calculate the total cost
            double totalCost = GlobalData.adultTickets * 10.00 + GlobalData.childTickets * 5.00 + GlobalData.seniorTickets * 7.50;

            System.out.println("Total Tickets Selected:");
            System.out.println("Adult: " + GlobalData.adultTickets);
            System.out.println("Child: " + GlobalData.childTickets);
            System.out.println("Senior: " + GlobalData.seniorTickets);
            System.out.println("Total Cost: $" + totalCost);

            selectFoodScreen(primaryStage);
        });

        

        // Skip button
        Button skipButton = new Button("Skip");
        skipButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        skipButton.setCursor(Cursor.HAND);
        skipButton.setOnAction(_ -> {
            System.out.println("User skipped ticket selection");

            selectFoodScreen(primaryStage);
        });

        //back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(_ -> {
            GlobalData.adultTickets = 0;
            GlobalData.childTickets = 0;
            GlobalData.seniorTickets = 0;
            selectSeatsScreen(primaryStage);
        });

        ticketSelectionScreen.getChildren().addAll(
                titleLabel,
                adultTicketLabel, adultTicketSpinner,
                childTicketLabel, childTicketSpinner,
                seniorTicketLabel, seniorTicketSpinner,
                continueButton, skipButton, backButton
        );

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();

        Scene scene = new Scene(ticketSelectionScreen, screenWidth, screenHeight);
        ticketSelectionScreen.setStyle("-fx-background-color: black;");
        primaryStage.setTitle("Ticket Selection");
        primaryStage.setScene(scene);

        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    //select food screen
    public void selectFoodScreen(Stage primaryStage) {

        VBox selectFoodScreen = new VBox(20);
        selectFoodScreen.setAlignment(Pos.CENTER);
        selectFoodScreen.setPadding(new Insets(20));

        Label selectFoodLabel = new Label("Select Food");
        selectFoodLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Popcorn", 5.00, 0));
        foodList.add(new Food("Candy", 2.50, 0));
        foodList.add(new Food("Hot Dog", 4.00, 0));
        foodList.add(new Food("Nachos", 6.00, 0));
        foodList.add(new Food("Pretzel", 3.50, 0));
        foodList.add(new Food("Fountain Drink/Slushie", 5.00, 0));

        VBox foodItemBox = new VBox(10);
        foodItemBox.setAlignment(Pos.CENTER);
        foodItemBox.setSpacing(20);


        for (Food food : foodList) {
            CheckBox checkBox = new CheckBox(food.getFoodName() + " - $" + String.format("%.2f",food.getFoodPrice()));
            checkBox.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-fonth-weight: bold");
            checkBox.setCursor(Cursor.HAND);

            Spinner<Integer> foodQuantitySpinner = new Spinner<>(0, 10, 0);
            foodQuantitySpinner.setStyle("-fx-background-color: white; -fx-font-size: 12px;");
            foodQuantitySpinner.setPrefWidth(90);
            foodQuantitySpinner.setDisable(true);

            checkBox.setOnAction(_ -> {
                if (checkBox.isSelected()) {
                    foodQuantitySpinner.setDisable(false);
                    food.setFoodQuantity(foodQuantitySpinner.getValue());
                    GlobalData.selectedFood.add(food);
                    System.out.println("Selected: " + food.getFoodName());
                } else {
                    foodQuantitySpinner.getValueFactory().setValue(0);
                    foodQuantitySpinner.setDisable(true);
                    GlobalData.selectedFood.remove(food);
                    System.out.println("Deselected: " + food.getFoodName());
                }
            });

            foodQuantitySpinner.valueProperty().addListener((_, _, newValue) -> {
                if (checkBox.isSelected()) {
                    food.setFoodQuantity(newValue);
                    System.out.println("Updated quantity for " + food.getFoodName() + ": " + newValue);
                }
            });
        
            foodItemBox.getChildren().addAll(checkBox, foodQuantitySpinner);
        }

        AnchorPane anchorPane = new AnchorPane();

        //continue button
        Button continueButton = new Button("Continue");
        AnchorPane.setBottomAnchor(continueButton, 20.0);
        AnchorPane.setRightAnchor(continueButton, 20.0);
        continueButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;");
        continueButton.setCursor(Cursor.HAND);
        continueButton.setOnAction(_ -> {
            if (GlobalData.selectedFood.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select at least one food item", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                System.out.println("Selected Food Items: ");
                GlobalData.selectedFood.forEach(food -> System.out.println(food.getFoodName()));
            }
            
            totalScreen(primaryStage);
        });

        //skip button
        Button skipButton = new Button("Skip");
        skipButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        skipButton.setCursor(Cursor.HAND);
        skipButton.setOnAction(_ -> {
            System.out.println("User skipped food selection");
            GlobalData.selectedFood.clear();
            totalScreen(primaryStage);
        });

        //back button
        Button backButton = new Button("Back");
        AnchorPane.setBottomAnchor(backButton, 20.0);
        AnchorPane.setLeftAnchor(backButton, 20.0);
        backButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(_ -> {
            GlobalData.selectedFood.clear();
            selectTicketScreen(primaryStage);
        });

        anchorPane.getChildren().addAll(continueButton, backButton);

        selectFoodScreen.getChildren().addAll(selectFoodLabel, foodItemBox, skipButton, anchorPane);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();

        Scene scene = new Scene(selectFoodScreen, screenWidth, screenHeight);
        selectFoodScreen.setStyle("-fx-background-color: black");
        primaryStage.setTitle("Movie Reservation App");
        primaryStage.setScene(scene);

        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    //total screen
    public void totalScreen(Stage primaryStage) {

        VBox totalScreen = new VBox(20);
        totalScreen.setAlignment(Pos.CENTER);
        totalScreen.setPadding(new Insets(20));
        totalScreen.setStyle("-fx-background-color: black;");


        Label titleLabel = new Label("Reservation Summary");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        //Movie and Showtime
        Label movieLabel = new Label("Movie: " + GlobalData.selectedMovie);
        movieLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        Label showtimeLabel = new Label("Showtime: " + GlobalData.selectedShowtime);
        showtimeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        //random theater number
        Random randomTheaterNumber = new Random();
        int theaterNumber = randomTheaterNumber.nextInt(20) + 1;

        Label theaterLabel = new Label("Theater Number: " + theaterNumber);
        theaterLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        //seats
        Label seatsLabel = new Label("Selected Seats: " +
            GlobalData.selectedSeats.stream()
                .map(seat -> {
                int seatNumber = Integer.parseInt(seat.getSeatNumber());
                int row = (seatNumber - 1) / 6;
                int column = (seatNumber - 1) % 6;
                char columnLetter = (char) ('A' + row);
                 System.out.println("SeatNumber: " + seatNumber + ", Row: " + row + ", Column: " + column + ", ColumnLetter: " + columnLetter);
                return columnLetter + "-" + (column + 1);
            })
            .reduce((a, b) -> a + ", " + b)
            .orElse("None")
        );
        seatsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        //Tickets
        Label ticketsLabel = new Label("Tickets: Adult (" + GlobalData.adultTickets + "), Child (" + GlobalData.childTickets + "), Senior (" + GlobalData.seniorTickets + ")");
        ticketsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        //Food
        Label foodLabel = new Label("Selected Food: " + 
            GlobalData.selectedFood.stream()
            .map(food -> food.getFoodName() + " (" + food.getFoodQuantity() + ")")
            .reduce((a, b) -> a + ", " + b)
            .orElse("None")
        );
        foodLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        //total cost
        double ticketCost = GlobalData.adultTickets * 10.00 + GlobalData.childTickets * 5.00 + GlobalData.seniorTickets * 7.50;
        double foodCost = GlobalData.selectedFood.stream()
            .mapToDouble(food -> food.getFoodPrice() * food.getFoodQuantity())
            .sum();
        double totalCost = ticketCost + foodCost;

        Label totalCostLabel = new Label("Total Cost: $" + String.format("%.2f", totalCost));
        totalCostLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(_ -> {
            GlobalData.selectedFood.clear();
            selectFoodScreen(primaryStage);
        });

        //Checkout button
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;");
        checkoutButton.setCursor(Cursor.HAND);
        checkoutButton.setOnAction(_ -> {
            // Print confirmation message
            System.out.println("The user is going to checkout.");
            contactInformationScreen(primaryStage); // Call the confirmation screen
        });

        totalScreen.getChildren().addAll(titleLabel, movieLabel, showtimeLabel, theaterLabel, seatsLabel, ticketsLabel, foodLabel, totalCostLabel, checkoutButton, backButton);
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();
    
    
        Scene scene = new Scene(totalScreen, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reservation Summary");

        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    // Contact Information Screen
    public void contactInformationScreen(Stage primaryStage) {

        VBox contactScreen = new VBox(20);
        contactScreen.setAlignment(Pos.CENTER);
        contactScreen.setPadding(new Insets(20));
        contactScreen.setStyle("-fx-background-color: black;");

        Label titleLabel = new Label("Enter Your Contact Information");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        // Subtitle
        Label subtitleLabel = new Label("We'll send your reservation details directly to you.");
        subtitleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        // Radio Buttons
        ToggleGroup contactGroup = new ToggleGroup();
        RadioButton phoneOption = new RadioButton("Phone Number");
        phoneOption.setToggleGroup(contactGroup);
        phoneOption.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        RadioButton emailOption = new RadioButton("Email Address");
        emailOption.setToggleGroup(contactGroup);
        emailOption.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        // Dynamic Input Field
        TextField contactField = new TextField();
        VBox.setVgrow(contactField, Priority.NEVER);
        contactField.setPromptText("Select an option above");
        contactField.setStyle("-fx-background-color: white; -fx-prompt-text-fill: black; -fx-font-size: 14px;");
        contactField.setMaxSize(800, 25);
        contactField.setPrefSize(800, 25);

        contactGroup.selectedToggleProperty().addListener((_, _, _) -> {
            if (phoneOption.isSelected()) {
                contactField.setPromptText("Enter your phone number");
                contactField.setText("");
            } else if (emailOption.isSelected()) {
                contactField.setPromptText("Enter your email address");
                contactField.setText("");
            }
        });

        // Total and Payment Information
        Label totalLabel = new Label("Total: $" + String.format("%.2f", GlobalData.adultTickets * 10.00 + GlobalData.childTickets * 5.00 + GlobalData.seniorTickets * 7.50 + GlobalData.selectedFood.stream().mapToDouble(food -> food.getFoodPrice() * food.getFoodQuantity()).sum()));
        totalLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        VBox paymentSection = new VBox(10);
        VBox.setVgrow(paymentSection, Priority.NEVER);
        paymentSection.setAlignment(Pos.CENTER);
        paymentSection.setMaxSize(800, 25);
        paymentSection.setPrefSize(800, 25);

        if (GlobalData.adultTickets * 10.00 + GlobalData.childTickets * 5.00 + GlobalData.seniorTickets * 7.50 + GlobalData.selectedFood.stream().mapToDouble(food -> food.getFoodPrice() * food.getFoodQuantity()).sum() > 0) {
            // Card Number Field
            TextField cardNumberField = new TextField();
            cardNumberField.setPromptText("Card Number");
            cardNumberField.setStyle("-fx-background-color: white; -fx-prompt-text-fill: black; -fx-font-size: 14px; -fx-pref-width: 200px; -fx-pref-height: 30px;");

            // Expiration Date Field
            TextField expirationDateField = new TextField();
            expirationDateField.setPromptText("MM/YY");
            expirationDateField.setStyle("-fx-background-color: white; -fx-prompt-text-fill: black; -fx-font-size: 14px; -fx-pref-width: 200px; -fx-pref-height: 30px;");

            // CVV Field
            TextField cvvField = new TextField();
            cvvField.setPromptText("CVV");
            cvvField.setStyle("-fx-background-color: white; -fx-prompt-text-fill: black; -fx-font-size: 14px; -fx-pref-width: 200px; -fx-pref-height: 30px;");

            paymentSection.getChildren().addAll(
            new Label("Payment Information:") {{
            setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            }},
            cardNumberField,
            expirationDateField,
            cvvField
            );
        }

        Button sendButton = new Button("Send Reservation");
        sendButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;");
        sendButton.setCursor(Cursor.HAND);
        sendButton.setOnAction(_ -> {
            String input = contactField.getText();
            if (phoneOption.isSelected() && input.matches("\\d+")) {
                System.out.println("Reservation sent to phone number: " + input);
            } else if (emailOption.isSelected() && input.matches("^.+@.+\\..+$")) {
                System.out.println("Reservation sent to email address: " + input);
            } else {
                System.out.println("Invalid input! Please check and try again.");
            }

            confirmationScreen(primaryStage);
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(_ -> {
            GlobalData.selectedFood.forEach(food -> System.out.println(food.getFoodName()));
            totalScreen(primaryStage);
        });

        // Add components to layout
        contactScreen.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            phoneOption,
            emailOption,
            contactField,
            totalLabel,
            paymentSection,
            sendButton,
            backButton
        );

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();

        Scene scene = new Scene(contactScreen, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Contact Information");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    //confirmation screen
    public void confirmationScreen(Stage primaryStage) {

        VBox confirmationScreen = new VBox(20);
        confirmationScreen.setAlignment(Pos.CENTER);
        confirmationScreen.setPadding(new Insets(20));
        confirmationScreen.setStyle("-fx-background-color: black;");

        //confirmation label
        Label confirmationLabel = new Label("Your order has been confirmed!");
        confirmationLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        //continue button
        Button exitButton = new Button("Close");
        exitButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;");
        exitButton.setCursor(Cursor.HAND);
        exitButton.setOnAction(_ -> {
            //exit the application
            System.exit(0);
        });
        confirmationScreen.getChildren().addAll(confirmationLabel, exitButton);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();

        Scene scene = new Scene(confirmationScreen, screenWidth, screenHeight);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Reservation Confirmation");

        primaryStage.centerOnScreen();
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}