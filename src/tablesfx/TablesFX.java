 ///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
// */
package tablesfx;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.PreparedStatement;

import java.io.File;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Nour Ahmed
 */
public class TablesFX extends Application {

    private ObservableList<Students> data;

    TableView table;

    Connection conn = null;

    PreparedStatement pst = null;

    ResultSet rest = null;

    @Override
    public void start(Stage primaryStage) {
// CGPA application
        Image image = new Image(getClass().getResource("logo.png").toExternalForm());

        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(200);
        imageView.setFitWidth(250);

        // Grid  Delete 
        GridPane gridDelete = new GridPane();

        gridDelete.setVgap(10);
        gridDelete.setHgap(10);
        gridDelete.setPadding(new Insets(20));

        Label titleDelete = new Label("Delete by ID :");

        Label idDelete = new Label("id");
        TextField idFieldDelete = new TextField();

        Button Delete = new Button("Delete");
        //Delete.getStyleClass().add("submitbutton");

        gridDelete.add(titleDelete, 0, 0, 2, 1);

        gridDelete.add(idDelete, 0, 1);
        gridDelete.add(idFieldDelete, 1, 1);

        gridDelete.add(Delete, 0, 2);

        Delete.setOnAction((event) -> {

            int idv = Integer.parseInt(idFieldDelete.getText());

            conn = DBConn.DBConnection();

            String delete = "Delete from Students where id = ? ";

            try {
                pst = conn.prepareStatement(delete);

                pst.setInt(1, idv);
                pst.executeUpdate();
                pst.close();
                conn.close();

                show();

            } catch (SQLException ex) {
                System.getLogger(TablesFX.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        });

        // Grid   Update 
        GridPane gridUpdate = new GridPane();

        gridUpdate.setVgap(10);
        gridUpdate.setHgap(10);
        gridUpdate.setPadding(new Insets(20));

        Label titleUpdate = new Label("  Update CGPA by Id  :");

        Label idUpdate = new Label("id");
        TextField idFieldUpdate = new TextField();

        Label cgpaUpdate = new Label("cgpa");
        TextField cgpaFieldUpdate = new TextField();

        Button Update = new Button("Update");
        Update.getStyleClass().add("submitbutton");

        gridUpdate.add(titleUpdate, 0, 0, 2, 1);

        gridUpdate.add(idUpdate, 0, 1);
        gridUpdate.add(idFieldUpdate, 1, 1);

        gridUpdate.add(cgpaUpdate, 0, 2);
        gridUpdate.add(cgpaFieldUpdate, 1, 2);

        gridUpdate.add(Update, 0, 3);

        Update.setOnAction((e) -> {

            int idValue = Integer.parseInt(idFieldUpdate.getText());
            double cgpaValue = Double.parseDouble(cgpaFieldUpdate.getText());

            conn = DBConn.DBConnection();

            String upate = "Update Students set cgpa = ? where id =?";

            try {
                pst = conn.prepareStatement(upate);

                pst.setInt(2, idValue);
                pst.setDouble(1, cgpaValue);

                pst.executeUpdate();

                pst.close();
                conn.close();

                show();

            } catch (SQLException ex) {
                System.getLogger(TablesFX.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        });

        // Grid 
        GridPane grid = new GridPane();

        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        Label title = new Label("Student Information :");

        Label name = new Label("name");
        TextField nameField = new TextField();

        Label id = new Label("id");
        TextField idField = new TextField();

        Label cgpa = new Label("cgpa");
        TextField cgpaField = new TextField();

        Button submit = new Button("submit");
        submit.getStyleClass().add("submitbutton");

        Button clear = new Button("clear");

        grid.add(title, 0, 0, 2, 1);

        grid.add(name, 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(id, 0, 2);
        grid.add(idField, 1, 2);

        grid.add(cgpa, 0, 3);
        grid.add(cgpaField, 1, 3);

        grid.add(submit, 0, 4);
        grid.add(clear, 1, 4);

        // Table 
        table = new TableView();

        TableColumn<Students, String> nameC = new TableColumn<>("Name");

        nameC.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn<Students, Integer> idC = new TableColumn<>("ID");
        idC.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<Students, Double> cgpaC = new TableColumn<>("CGPA");
        cgpaC.setCellValueFactory(new PropertyValueFactory("cgpa"));

        table.getColumns().addAll(nameC, idC, cgpaC);
        show();

        submit.setOnAction((event) -> {

            String nameValue = nameField.getText();
            int idValue = Integer.parseInt(idField.getText());
            double cgpaValue = Double.parseDouble(cgpaField.getText());
            table.getItems().addAll(new Students(nameValue, idValue, cgpaValue));

            // sql 
            conn = DBConn.DBConnection();

            String insert = "Insert  into Students  (id , name ,  cgpa ) values (?,?,?)";

            try {
                pst = conn.prepareStatement(insert);

                pst.setInt(1, idValue);

                pst.setString(2, nameValue);
                pst.setDouble(3, cgpaValue);

                if (pst.executeUpdate() == 1) {

                    System.out.print("Done ");
                }

                pst.close();
                conn.close();
                show();

            } catch (SQLException ex) {
                System.getLogger(TablesFX.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        });

        clear.setOnAction((event) -> {

            nameField.clear();
            idField.clear();
            cgpaField.clear();

        });

        VBox UD = new VBox(gridDelete, gridUpdate);
        VBox f = new VBox(imageView, grid);
        VBox vbox = new VBox(table);

        FlowPane root = new FlowPane(f, vbox, UD);

        root.setAlignment(Pos.CENTER);

        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 900, 600);

        scene.getStylesheets().add(new File("src/tablesfx/style.css").toURI().toString());

        primaryStage.setTitle("CGPA App");
        
        
        
        primaryStage.setOnCloseRequest((event ) -> {
        
        
        Alert alert = new Alert  ( Alert.AlertType.CONFIRMATION);
        
        alert.setContentText("Are your Sure ? ");
        alert.setHeaderText("CONFIRMATION");
        
        alert.showAndWait();
        
             if (alert.getResult().getText().equals("Cancel")   ){
             
             event.consume();
            
             
             
             
             }
      
        
        
        
        
        
        });
        
        
        
        
        
        
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void show() {

        data = FXCollections.observableArrayList();

        conn = DBConn.DBConnection();

        String select = "Select *   from Students ";

        try {
            pst = conn.prepareStatement(select);

            rest = pst.executeQuery();

            while (rest.next()) {
                data.add(new Students(rest.getString(2), rest.getInt(1), rest.getDouble(3)));

                ///  System.out.println(rest.getInt(1) + "   " + rest.getString(2) + "  " + rest.getDouble(3));
            }

            pst.close();
            conn.close();

            table.setItems(data);

        } catch (SQLException ex) {
            System.getLogger(TablesFX.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
//
//}
