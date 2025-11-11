package cs151.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.List;

public class CommentController {

    private StudentProfile current;
    @FXML private TextArea taComments;


    public void loadComments(StudentProfile p) {
        this.current = p;

        taComments.setText(nvl(p.getComments()));

        System.out.println("[Comments] Loaded: " + p.getName());
    }

    @FXML
    private void save() {
        if (current == null) {
            new Alert(Alert.AlertType.ERROR, "No Comments loaded.").showAndWait();
            return;
        }

        current.setComments(taComments.getText() == null ? "" : taComments.getText().trim());

        DataStore.replaceByName(current);

        new Alert(Alert.AlertType.INFORMATION, "Saved comments for: " + current.getName()).showAndWait();
    }

    @FXML
    protected void searchProf(ActionEvent event) {
        swapScene(event, "/cs151/application/search.fxml", 1000, 680, "Search Student Profiles");
    }

    private void swapScene(ActionEvent event, String fxml, int w, int h, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, w, h));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Navigation error").showAndWait();
        }
    }

    private static String nvl(String s) {
        return (s == null || s.isBlank()) ? "" : s;
    }
}
