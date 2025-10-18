package org.example.medico_norelacional.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.medico_norelacional.DAO.PacienteDAO;
import org.example.medico_norelacional.Model.Paciente;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtEmailId;

    @FXML
    private TextField txtPass;

    @FXML
    private Label lblMensaje;

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    @FXML
    void onClickLogin(ActionEvent event) {
        String email = txtEmailId.getText().trim();
        String pass = txtPass.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            lblMensaje.setText("Rellena todos los campos");
            return;
        }

        Paciente p = pacienteDAO.findByEmail(email);
        if (p == null) {
            lblMensaje.setText("El usuario no existe");
            return;
        }

        if (!pass.equals(p.getPassword())) {
            lblMensaje.setText("Contraseña incorrecta");
            return;
        }

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("citas.fxml"));
            Parent root = loader.load();

            CitasController citasController = loader.getController();

            citasController.setPaciente(p);

            Stage stage = (Stage) txtEmailId.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Citas");
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
