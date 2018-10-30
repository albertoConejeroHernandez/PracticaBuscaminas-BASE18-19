import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author jesusredondogarcia
 **
 */
public class ActionBoton implements ActionListener {

	VentanaPrincipal ventana;
	int i;
	int j;

	public ActionBoton(VentanaPrincipal ventana, int i, int j) {
		this.ventana = ventana;
		this.i = i;
		this.j = j;
	}

	/**
	 * Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (ventana.juego.abrirCasilla(i, j)) {
			ventana.mostrarNumMinasAlrededor(i, j);
			ventana.refrescarPantalla();
			ventana.actualizarPuntuacion();
			explosionDeCasilla(i, j);
		} else {
			if (ventana.juego.esFinJuego() == false) {
				ventana.mostrarFinJuego(true);
			} else {
				ventana.mostrarFinJuego(false);
			}
		}

	}

	public void explosionDeCasilla(int i, int j) {
		if (ventana.juego.getMinasAlrededor(i, j) == 0) {
			for (int k = -1; k < 2; k++) {
				for (int k2 = -1; k2 < 2; k2++) {
					if (k + i >= 0 && k + i < ventana.juego.LADO_TABLERO && k2 + j >= 0
							&& k2 + j < ventana.juego.LADO_TABLERO) {
						if (ventana.juego.getMinasAlrededor(k + i, k2 + j) == 0) {
							ventana.juego.abrirCasilla(k + i, k2 + j);
							ventana.mostrarNumMinasAlrededor(k + i, k2 + j);
							ventana.refrescarPantalla();
							ventana.actualizarPuntuacion();
						}
						if (ventana.juego.abrirCasilla(i + 1, j + 1)) {
							explosionDeCasilla(i + 1, j + 1);
						} else {
							explosionDeCasilla(i - 1, j - 1);
						}
					}

				}
			}

			
		}
	}

}
