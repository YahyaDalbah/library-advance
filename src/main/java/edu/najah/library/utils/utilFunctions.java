package edu.najah.library.utils;

import edu.najah.library.models.services.UserDAOImp;
import edu.najah.library.models.user.User;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import java.io.IOException;

public class utilFunctions {
    public static void switchScene(Event event, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(utilFunctions.class.getResource("/edu/najah/library/" + fxml));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static String generateResetToken(String email) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        // Save token and expiration to the database
        UserDAOImp userDAO = new UserDAOImp();
        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Email not found");
        }

        user.setResetToken(token);
        user.setTokenExpiration(LocalDateTime.now().plusHours(1)); // token valid for 1 hour

        userDAO.updateUser(user);

        return token;
    }


}
