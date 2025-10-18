package org.example.medico_norelacional.Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.medico_norelacional.DAO.CitaDAO;
import org.example.medico_norelacional.Model.Cita;
import org.example.medico_norelacional.Model.Paciente;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class CitasController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Cita> tableview;

    @FXML
    private TableColumn<Cita, String> tcEspecialidad;

    @FXML
    private TableColumn<Cita, Integer> tcNCita;

    @FXML
    private TableColumn<Cita, String> tctFecha;

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtDireccion;

    @FXML
    private ComboBox<?> txtEspecialidad;

    @FXML
    private TextField txtNCita;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    private Paciente pacienteActual;
    private final CitaDAO citaDAO = new CitaDAO();

    public void setPaciente(Paciente paciente){
        this.pacienteActual=paciente;
        cargarDatosPaciente();
    }

    private void cargarDatosPaciente() {
        if (pacienteActual == null) {
            txtDNI.setText(pacienteActual.getDni());
            txtNombre.setText(pacienteActual.getNombre());
            txtDireccion.setText(pacienteActual.getDireccion());
            txtTelefono.setText(pacienteActual.getTelefono());

            //Datos de paciente solo de lectura
            txtDNI.setEditable(false);
            txtNombre.setEditable(false);
            txtTelefono.setEditable(false);
            txtDireccion.setEditable(false);
        }
    }

    @FXML
    void onClickVerCitas(ActionEvent event) {
        if (pacienteActual != null) {
            //Obtener id del paciente
            org.bson.types.ObjectId pacienteID = pacienteActual.getId();
            //Buscar citas en la bd
            List<Cita> citas = citaDAO.findByPacienteId(pacienteID);

            //Convertir la lista a observableLista para el tanleView
            tableview.setItems(FXCollections.observableArrayList(citas));

            if (citas.isEmpty()){
                System.out.println("El paciente no tiene citas");
            }
        } else {
            System.out.println("Paciente no encontrado");
        }
    }

    @FXML
    void onClickBorrar(ActionEvent event) {

    }

    @FXML
    void onClickModificar(ActionEvent event) {

    }

    @FXML
    void onClickNueva(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
// 1. Configurar la TableView para usar la clase Cita y las propiedades de Cita

        // La columna "Nº Cita" se mapea a la propiedad 'numeroCita' de la clase Cita
        tcNCita.setCellValueFactory(new PropertyValueFactory<>("numeroCita"));

        // La columna "Especialidad" se mapea a la propiedad 'especialidad' de la clase Cita
        tcEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));

        // La columna "Fecha" requiere una celda personalizada (CellFactory) para formatear LocalDateTime a String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        tctFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCita"));
        tctFecha.setCellFactory(column -> new TableCell<Cita, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // El PropertyValueFactory devuelve el objeto LocalDateTime, no un String,
                    // por lo que debemos castearlo o usar un callback, pero la forma más limpia
                    // es sobreescribir el 'updateItem' y obtener el valor crudo.
                    Cita cita = getTableView().getItems().get(getIndex());
                    if (cita != null && cita.getFechaCita() != null) {
                        setText(cita.getFechaCita().format(formatter));
                    } else {
                        setText("");
                    }
                }
            }
        });

        // Establecer un mensaje por defecto para la tabla vacía
        tableview.setPlaceholder(new Label("Tabla sin contenido. Pulse 'Ver citas Paciente' para cargar."));
    }
}
