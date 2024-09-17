package pe.edu.upeu.gatofx.servicio;

import org.springframework.stereotype.Service;
import pe.edu.upeu.gatofx.modelo.GatoTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class GatoServiceImp implements GatoServiceI {
    private List<GatoTO> gameStates = new ArrayList<>();

    @Override
    public void guardarEstado(GatoTO gameState) {
        gameStates.add(gameState);
    }

    @Override
    public List<GatoTO> obtenerEstados() {
        return new ArrayList<>(gameStates);
    }

    @Override
    public void actualizarEstado(GatoTO gameState, int index) {
        if (index >= 0 && index < gameStates.size()) {
            gameStates.set(index, gameState);
        }
    }

    @Override
    public void eliminarEstado(int index) {
        if (index >= 0 && index < gameStates.size()) {
            gameStates.remove(index);
        }
    }

    @Override
    public void reiniciarJuego() {
        gameStates.clear();
    }
}
