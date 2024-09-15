package pe.edu.upeu.gatofx.servicio;

import pe.edu.upeu.gatofx.modelo.GatoTO;
import java.util.List;

public interface GatoServiceI {
    void guardarEstado(GatoTO gameState);
    List<GatoTO> obtenerEstados();
    void actualizarEstado(GatoTO gameState, int index);
    void eliminarEstado(int index);
    void reiniciarJuego();
}
