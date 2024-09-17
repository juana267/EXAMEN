package pe.edu.upeu.gatofx.modelo;

public class GameResult {
    private String partida;
    private String jugador1;
    private String jugador2;
    private String ganador;
    private String estado;

    // Constructor
    public GameResult(String partida, String jugador1, String jugador2, String ganador, String estado) {
        this.partida = partida;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.ganador = ganador;
        this.estado = estado;
    }

    // Getters and Setters
    public String getPartida() { return partida; }
    public void setPartida(String partida) { this.partida = partida; }

    public String getJugador1() { return jugador1; }
    public void setJugador1(String jugador1) { this.jugador1 = jugador1; }

    public String getJugador2() { return jugador2; }
    public void setJugador2(String jugador2) { this.jugador2 = jugador2; }

    public String getGanador() { return ganador; }
    public void setGanador(String ganador) { this.ganador = ganador; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
