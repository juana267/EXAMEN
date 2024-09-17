package pe.edu.upeu.gatofx.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.edu.upeu.gatofx.modelo.GatoTO;
import pe.edu.upeu.gatofx.modelo.GameResult;
import pe.edu.upeu.gatofx.servicio.GatoServiceImp;

@Component
public class GatoControl {

         @Autowired
        private GatoServiceImp calcServiceImp;

        @FXML
        private Button button00, button01, button02, button10, button11, button12, button20, button21, button22;
        @FXML
        private Button startButton, addButton;
        @FXML
        private TextField player1Name, player2Name;
        @FXML
        private Label turnLabel;
        @FXML
        private TableView<GameResult> scoreTable;
        @FXML
        private TableColumn<GameResult, String> colPartida, colJugador1, colJugador2, colGanador, colEstado;

        private boolean xTurn = true; // Alterna entre X y O
        private GatoTO gameModel = new GatoTO();
        private ActionEvent event;

        @FXML
        public void initialize() {
            // Asociar los botones del tablero al controlador de eventos
            button00.setOnAction(this::handleButtonClick);
            button01.setOnAction(this::handleButtonClick);
            button02.setOnAction(this::handleButtonClick);
            button10.setOnAction(this::handleButtonClick);
            button11.setOnAction(this::handleButtonClick);
            button12.setOnAction(this::handleButtonClick);
            button20.setOnAction(this::handleButtonClick);
            button21.setOnAction(this::handleButtonClick);
            button22.setOnAction(this::handleButtonClick);

            startButton.setOnAction(this::handleStartButton);
            addButton.setOnAction(this::handleAddButton);

            // Deshabilitar el tablero al inicio
            habilitarTablero(false);

            setupTable();
            updateTurnLabel();
        }

        @FXML
        private void handleStartButton(ActionEvent event) {
            this.event = event;
            // Verificar si los nombres de los jugadores están vacíos
            if (player1Name.getText().isEmpty() || player2Name.getText().isEmpty()) {
                // Mostrar alerta si uno de los nombres está vacío
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, ingrese los nombres de ambos jugadores antes de iniciar el juego.");
                alert.showAndWait();
            } else {
                // Los nombres no están vacíos, el juego puede comenzar
                updateTurnLabel();

                // Habilitar el tablero para que los jugadores puedan jugar
                habilitarTablero(true);
            }
        }

        private void handleButtonClick(ActionEvent event) {
            Button button = (Button) event.getSource();
            int row = GridPane.getRowIndex(button);
            int col = GridPane.getColumnIndex(button);

            if (button.getText().isEmpty()) {
                String player = xTurn ? "X" : "O";
                button.setText(player);
                gameModel.setCell(row, col, player);

                if (checkWin()) {
                    showAlert((xTurn ? player1Name.getText() : player2Name.getText()) + " es el Ganador!");
                    calcServiceImp.guardarEstado(gameModel); // Guardar estado del juego
                    updateScoreTable(xTurn ? player1Name.getText() : player2Name.getText());
                } else if (isBoardFull()) {
                    showAlert("Empate!");
                    calcServiceImp.guardarEstado(gameModel); // Guardar estado del juego
                    updateScoreTable("Empate");
                }

                xTurn = !xTurn;
                updateTurnLabel();
            }
        }

        private boolean checkWin() {
            return checkLine(0, 0, 0, 1, 0, 2) || checkLine(1, 0, 1, 1, 1, 2) || checkLine(2, 0, 2, 1, 2, 2) ||
                    checkLine(0, 0, 1, 0, 2, 0) || checkLine(0, 1, 1, 1, 2, 1) || checkLine(0, 2, 1, 2, 2, 2) ||
                    checkLine(0, 0, 1, 1, 2, 2) || checkLine(0, 2, 1, 1, 2, 0);
        }

        private boolean checkLine(int row1, int col1, int row2, int col2, int row3, int col3) {
            String v1 = gameModel.getCell(row1, col1);
            String v2 = gameModel.getCell(row2, col2);
            String v3 = gameModel.getCell(row3, col3);
            return !v1.isEmpty() && v1.equals(v2) && v2.equals(v3);
        }

        private boolean isBoardFull() {
            String[][] board = gameModel.getBoard();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].isEmpty()) {
                        return false;
                    }
                }
            }
            return true;
        }

        private void showAlert(String message) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Resultado del Juego");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
            resetBoard();
        }

        private void resetBoard() {
            button00.setText("");
            button01.setText("");
            button02.setText("");
            button10.setText("");
            button11.setText("");
            button12.setText("");
            button20.setText("");
            button21.setText("");
            button22.setText("");
            gameModel = new GatoTO(); // Reiniciar el modelo
            xTurn = true; // Reiniciar al jugador X
            updateTurnLabel();
        }

        private void updateTurnLabel() {
            turnLabel.setText("Turno: " + (xTurn ? player1Name.getText() : player2Name.getText()));
        }

        private void setupTable() {
            colPartida.setCellValueFactory(new PropertyValueFactory<>("partida"));
            colJugador1.setCellValueFactory(new PropertyValueFactory<>("jugador1"));
            colJugador2.setCellValueFactory(new PropertyValueFactory<>("jugador2"));
            colGanador.setCellValueFactory(new PropertyValueFactory<>("ganador"));
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        }

        private void updateScoreTable(String winner) {
            String partida = "Partida " + (scoreTable.getItems().size() + 1);
            String jugador1 = player1Name.getText();
            String jugador2 = player2Name.getText();
            String estado = "Finalizado";
            GameResult result = new GameResult(partida, jugador1, jugador2, winner, estado);
            scoreTable.getItems().add(result);
        }

        private void handleAddButton(ActionEvent event) {
            // Lógica para añadir el estado actual del juego a la tabla de resultados
            updateScoreTable(xTurn ? player1Name.getText() : player2Name.getText());
        }

        // Método para habilitar o deshabilitar el tablero
        private void habilitarTablero(boolean habilitado) {
            button00.setDisable(!habilitado);
            button01.setDisable(!habilitado);
            button02.setDisable(!habilitado);
            button10.setDisable(!habilitado);
            button11.setDisable(!habilitado);
            button12.setDisable(!habilitado);
            button20.setDisable(!habilitado);
            button21.setDisable(!habilitado);
            button22.setDisable(!habilitado);
        }

        public void restartGame() {
            // Limpiar los botones del tablero (restablecer a su estado inicial)
            resetBoard();

            // Resetear los nombres de los jugadores
            player1Name.clear();
            player2Name.clear();

            // Si tienes una tabla de puntajes, puedes limpiarla también
            scoreTable.getItems().clear();

            // Deshabilitar el tablero hasta que se inicie nuevamente
            habilitarTablero(false);
        }

    }
