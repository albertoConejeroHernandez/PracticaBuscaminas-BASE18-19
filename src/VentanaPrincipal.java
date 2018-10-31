import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.security.acl.Group;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

/**
 * En esta clase tratamos los puntos iniciales de la ventana de juego, en la que
 * controlamos los botones y funcionalidad de la ventana, editamos los campos
 * que queremos para adpatarla y ver el juego como queremos.
 * 
 * @author AlbertoConejeroHernandez
 *         <p>
 * 		Link:{@link inicializar}
 *         </p>
 * 
 *         <pre>
 * {@code
 * 		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
 * }
 *         </pre>
 * 
 * @since 1.0
 * @version 1.0
 * @see ControlJuego
 *
 */
public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;
	JPanel temas;
	JButton botonCambiaTemas;
	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	// Temas
	ButtonGroup grupoTemas;
	JRadioButton dark;
	JRadioButton classic;
	JRadioButton porDefecto;
	String tema = "porDefecto";

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		temas = new JPanel(new GridBagLayout());
		botonCambiaTemas = new JButton("Temas");
		panelImagen = new JPanel(new GridLayout(1, 1));
		panelImagen.add(botonCambiaTemas);
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10, 10));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);
		// Temas
		grupoTemas = new ButtonGroup();
		dark = new JRadioButton("Dark");
		classic = new JRadioButton("Classic");
		porDefecto = new JRadioButton("Tema por defecto");
		grupoTemas.add(dark);
		grupoTemas.add(classic);
		grupoTemas.add(porDefecto);

		temas.add(dark);
		temas.add(classic);
		temas.add(porDefecto);

		// Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}
		// Botones
		botonesJuego = new JButton[10][10];
		/**
		 * En este apartado del codigo obligamos a que mantenga los colores que le hemos
		 * puesto en el tema ya que al cambiar en el boton GO!(botonEmpezar) nos pone
		 * los colores de inicio si no hacemos este switc/case.
		 * 
		 */

		switch (tema) {
		case "porDefecto":

			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego[i].length; j++) {
					botonesJuego[i][j] = new JButton("-");
					panelesJuego[i][j].add(botonesJuego[i][j]);
				}
			}
			break;
		case "classic":
			panelJuego.setBackground(Color.BLUE);
			panelPuntuacion.setBackground(Color.GREEN);
			pantallaPuntuacion.setForeground(Color.BLUE);
			pantallaPuntuacion.setBackground(Color.GREEN);
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego[i].length; j++) {
					botonesJuego[i][j] = new JButton();
					botonesJuego[i][j].setText("X");
					botonesJuego[i][j].setForeground(Color.BLUE);
					botonesJuego[i][j].setBackground(Color.GREEN);
					panelesJuego[i][j].add(botonesJuego[i][j]);
				}
			}
			panelEmpezar.setBackground(Color.BLUE);
			panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
			break;
		case "dark":
			panelJuego.setBackground(Color.BLACK);
			panelPuntuacion.setBackground(Color.BLACK);
			pantallaPuntuacion.setForeground(Color.WHITE);
			pantallaPuntuacion.setBackground(Color.BLACK);
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego[i].length; j++) {
					botonesJuego[i][j] = new JButton();
					botonesJuego[i][j].setText("?");
					botonesJuego[i][j].setForeground(Color.WHITE);
					botonesJuego[i][j].setBackground(Color.BLACK);
					panelesJuego[i][j].add(botonesJuego[i][j]);
				}
			}
			panelEmpezar.setBackground(Color.BLACK);
			panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
			break;

		}

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Metodo para elegir el tema con el que deseas jugar la partida, es un radio
	 * button que seleccionamos
	 */
	public void cambiaTemas() {
		JOptionPane.showMessageDialog(null, temas);
		if (dark.isSelected()) {
			tema = "dark";
			panelJuego.setBackground(Color.BLACK);
			panelPuntuacion.setBackground(Color.BLACK);
			pantallaPuntuacion.setForeground(Color.WHITE);
			pantallaPuntuacion.setBackground(Color.BLACK);
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego[i].length; j++) {
					botonesJuego[i][j].setText("?");
					botonesJuego[i][j].setForeground(Color.white);
					botonesJuego[i][j].setBackground(Color.BLACK);

				}
			}
			panelEmpezar.setBackground(Color.BLACK);
			panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		}
		if (classic.isSelected()) {
			tema = "classic";
			panelJuego.setBackground(Color.BLUE);
			panelPuntuacion.setBackground(Color.GREEN);
			pantallaPuntuacion.setForeground(Color.BLUE);
			pantallaPuntuacion.setBackground(Color.GREEN);
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego[i].length; j++) {
					botonesJuego[i][j].setText("X");
					botonesJuego[i][j].setForeground(Color.BLUE);
					botonesJuego[i][j].setBackground(Color.GREEN);

				}
			}
			panelEmpezar.setBackground(Color.BLUE);
			panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		}
		if (porDefecto.isSelected()) {
			tema = "porDefecto";
			panelJuego.setBackground(null);
			panelPuntuacion.setBackground(null);
			pantallaPuntuacion.setForeground(null);
			pantallaPuntuacion.setBackground(null);
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego[i].length; j++) {
					botonesJuego[i][j].setText("-");
					botonesJuego[i][j].setBackground(null);
					botonesJuego[i][j].setForeground(null);
				}
			}
			panelEmpezar.setBackground(null);
			panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		}
	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j].addActionListener(new ActionBoton(this, i, j));

			}
		}
		botonEmpezar.addActionListener((e) -> {
			ventana.remove(panelEmpezar);
			ventana.remove(panelImagen);
			ventana.remove(panelJuego);
			ventana.remove(panelPuntuacion);
			juego = new ControlJuego();
			inicializar();
			refrescarPantalla();
		});

		botonCambiaTemas.addActionListener((e) -> {
			cambiaTemas();
			refrescarPantalla();
		});
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca
	 * el botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto
	 * según la siguiente correspondecia (consultar la variable
	 * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó
	 * más : rojo
	 * 
	 * @param i:
	 *            posición vertical de la celda.
	 * @param j:
	 *            posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		int numMinasAlrededor = juego.getMinasAlrededor(i, j);
		JLabel numero = new JLabel();
		panelesJuego[i][j].removeAll();
		numero.setText(Integer.toString(numMinasAlrededor));
		if (Integer.parseInt(numero.getText()) != juego.MINA) {
			numero.setForeground(correspondenciaColores[numMinasAlrededor]);
			numero.setHorizontalTextPosition(SwingConstants.CENTER);
			numero.setHorizontalAlignment(SwingConstants.CENTER);
		}
		panelesJuego[i][j].add(numero);
		refrescarPantalla();

	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion
	 *            : Un booleano que indica si es final del juego porque ha explotado
	 *            una mina (true) o bien porque hemos desactivado todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		if (porExplosion) {
			JOptionPane.showMessageDialog(ventana, "Has perdido: " + juego.getPuntuacion());
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego.length; j++) {
					botonesJuego[i][j].setEnabled(false);
				}
			}
		} else {
			JOptionPane.showMessageDialog(ventana, "Has ganado: " + juego.getPuntuacion());
			for (int i = 0; i < botonesJuego.length; i++) {
				for (int j = 0; j < botonesJuego.length; j++) {
					botonesJuego[i][j].setEnabled(false);
				}
			}
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(Integer.toString(juego.getPuntuacion()));
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

}
