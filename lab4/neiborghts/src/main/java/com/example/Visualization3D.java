package com.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.util.List;

public class Visualization3D extends Application {

    private static List<Patient> patients;

    public static void showVisualization(List<Patient> patientList) {
        patients = patientList;
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("3D Feature Visualization (Glucose, BMI, Blood Pressure)");

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.LIGHTGRAY);

        for (Patient patient : patients) {
            double glucose = patient.getGlucose();
            double bmi = patient.getBmi();
            double bloodPressure = patient.getBloodPressure();

            Sphere sphere = new Sphere(5);
            sphere.setTranslateX(glucose);
            sphere.setTranslateY(bmi);     
            sphere.setTranslateZ(bloodPressure); 
            sphere.setMaterial(new javafx.scene.paint.PhongMaterial(Color.AQUA)); 

            root.getChildren().add(sphere);
        }

        javafx.scene.PerspectiveCamera camera = new javafx.scene.PerspectiveCamera(true);
        camera.setTranslateZ(-100); 
        camera.setNearClip(1);
        camera.setFarClip(1000);
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
