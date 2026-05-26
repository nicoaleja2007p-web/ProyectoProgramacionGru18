package interfaz;

import negocio.GestorUsuario;
import negocio.GestorActividades;
import negocio.GestorPerfil;
import negocio.GestorReporte;
import modelo.PerfilPreferencias;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelPrincipal;

    private GestorUsuario gestorUsuario;
    private GestorActividades gestorActividades;
    private GestorPerfil gestorPerfil;

    private FormularioLogin formularioLogin;
    private FormularioPerfil formularioPerfil;
    private FormularioPlanificador formularioPlanificador;
    private VistaItinerario vistaItinerario;
    private VistaReporte vistaReporte;

    public VentanaPrincipal() {
        gestorUsuario = new GestorUsuario();
        gestorActividades = new GestorActividades();
        PerfilPreferencias perfil = new PerfilPreferencias("cultural", 100.0);
        gestorPerfil = new GestorPerfil(perfil);

        cargarActividades();
        inicializarVentana();
        inicializarPaneles();
        mostrarPanel("login");
    }

    private void cargarActividades() {
        gestorActividades.registrarActividad("Tour ciudad historica", 30.0, 3, "cultural");
        gestorActividades.registrarActividad("Senderismo en montania", 20.0, 5, "aventura");
        gestorActividades.registrarActividad("Degustacion gastronomica", 50.0, 2, "gastronomia");
        gestorActividades.registrarActividad("Visita a museo", 15.0, 2, "cultural");
        gestorActividades.registrarActividad("Rafting en rio", 45.0, 4, "aventura");
        gestorActividades.registrarActividad("Clase de cocina local", 60.0, 3, "gastronomia");
        gestorActividades.registrarActividad("Paseo en bicicleta", 10.0, 2, "aventura");
        gestorActividades.registrarActividad("Visita a mercado artesanal", 5.0, 1, "cultural");
    }

    private void inicializarVentana() {
        setTitle("Planificador Inteligente de Viajes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarPaneles() {
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        formularioLogin = new FormularioLogin(this, gestorUsuario);
        formularioPerfil = new FormularioPerfil(this, gestorPerfil);
        formularioPlanificador = new FormularioPlanificador(this, gestorActividades, gestorPerfil);
        vistaItinerario = new VistaItinerario(this);
        vistaReporte = new VistaReporte(this);

        panelPrincipal.add(formularioLogin, "login");
        panelPrincipal.add(formularioPerfil, "perfil");
        panelPrincipal.add(formularioPlanificador, "planificador");
        panelPrincipal.add(vistaItinerario, "itinerario");
        panelPrincipal.add(vistaReporte, "reporte");

        add(panelPrincipal);
    }

    public void mostrarPanel(String nombre) {
        cardLayout.show(panelPrincipal, nombre);
    }

    public GestorUsuario getGestorUsuario() {
        return gestorUsuario;
    }

    public GestorActividades getGestorActividades() {
        return gestorActividades;
    }

    public GestorPerfil getGestorPerfil() {
        return gestorPerfil;
    }

    public VistaItinerario getVistaItinerario() {
        return vistaItinerario;
    }

    public VistaReporte getVistaReporte() {
        return vistaReporte;
    }

    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }
}