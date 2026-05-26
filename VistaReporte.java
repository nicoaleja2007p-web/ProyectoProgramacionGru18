package interfaz;

import modelo.Viaje;
import negocio.GestorReporte;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaReporte extends JPanel {

    private VentanaPrincipal ventana;

    private JLabel labelTitulo;
    private JTextArea areaReporte;
    private JScrollPane scroll;
    private JButton botonVolver;
    private JButton botonNuevoViaje;

    public VistaReporte(VentanaPrincipal ventana) {
        this.ventana = ventana;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setBackground(new Color(245, 245, 250));

        labelTitulo = new JLabel("Reporte de Costos");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(new Color(41, 98, 160));

        areaReporte = new JTextArea();
        areaReporte.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaReporte.setEditable(false);
        areaReporte.setBackground(new Color(255, 255, 255));
        areaReporte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scroll = new JScrollPane(areaReporte);
        scroll.setPreferredSize(new java.awt.Dimension(600, 350));

        botonVolver = new JButton("Ver Itinerario");
        botonVolver.setFont(new Font("Arial", Font.PLAIN, 13));
        botonVolver.setBackground(new Color(200, 200, 200));
        botonVolver.setFocusPainted(false);

        botonNuevoViaje = new JButton("Planificar otro viaje");
        botonNuevoViaje.setFont(new Font("Arial", Font.BOLD, 13));
        botonNuevoViaje.setBackground(new Color(41, 98, 160));
        botonNuevoViaje.setForeground(Color.WHITE);
        botonNuevoViaje.setFocusPainted(false);
    }

    private void configurarLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelTitulo, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scroll, gbc);

        gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(botonVolver, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(botonNuevoViaje, gbc);
    }

    private void configurarEventos() {
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("itinerario");
            }
        });

        botonNuevoViaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("planificador");
            }
        });
    }

    public void mostrarReporte(Viaje viaje) {
        GestorReporte gestorReporte = new GestorReporte(viaje);
        double gastoDiarioMax = ventana.getGestorPerfil().getPerfil().getGastoDiarioMax();

        String texto = gestorReporte.resumenGeneral();
        texto = texto + "\n";
        texto = texto + gestorReporte.generarDesglose();
        texto = texto + "\n\n";

        ArrayList<String> excesos = gestorReporte.detectarExcesos(gastoDiarioMax);
        if (excesos.size() == 0) {
            texto = texto + "No se detectaron excesos de presupuesto por dia.\n";
        } else {
            texto = texto + "=== ALERTAS DE EXCESO ===\n";
            for (int i = 0; i < excesos.size(); i++) {
                texto = texto + excesos.get(i) + "\n";
            }
        }

        ArrayList<String> alternativas = gestorReporte.sugerirAlternativas(
                gastoDiarioMax,
                ventana.getGestorActividades().getActividades()
        );

        if (alternativas.size() > 0) {
            texto = texto + "\n=== ACTIVIDADES ALTERNATIVAS ECONOMICAS ===\n";
            for (int i = 0; i < alternativas.size(); i++) {
                texto = texto + alternativas.get(i) + "\n";
            }
        }

        areaReporte.setText(texto);
        areaReporte.setCaretPosition(0);
    }
}
