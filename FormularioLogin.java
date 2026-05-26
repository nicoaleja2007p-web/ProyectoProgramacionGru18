package interfaz;

import negocio.GestorUsuario;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioLogin extends JPanel {

    private VentanaPrincipal ventana;
    private GestorUsuario gestorUsuario;

    private JTextField campoEmail;
    private JPasswordField campoContrasena;
    private JButton botonLogin;
    private JButton botonRegistro;
    private JLabel labelTitulo;
    private JLabel labelEmail;
    private JLabel labelContrasena;
    private JLabel labelMensaje;

    public FormularioLogin(VentanaPrincipal ventana, GestorUsuario gestorUsuario) {
        this.ventana = ventana;
        this.gestorUsuario = gestorUsuario;
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setBackground(new Color(245, 245, 250));

        labelTitulo = new JLabel("Planificador de Viajes");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setForeground(new Color(41, 98, 160));

        labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.PLAIN, 14));

        labelContrasena = new JLabel("Contrasena:");
        labelContrasena.setFont(new Font("Arial", Font.PLAIN, 14));

        campoEmail = new JTextField(20);
        campoEmail.setFont(new Font("Arial", Font.PLAIN, 13));

        campoContrasena = new JPasswordField(20);
        campoContrasena.setFont(new Font("Arial", Font.PLAIN, 13));

        botonLogin = new JButton("Iniciar Sesion");
        botonLogin.setFont(new Font("Arial", Font.BOLD, 13));
        botonLogin.setBackground(new Color(41, 98, 160));
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFocusPainted(false);

        botonRegistro = new JButton("Registrarse");
        botonRegistro.setFont(new Font("Arial", Font.PLAIN, 13));
        botonRegistro.setBackground(new Color(200, 200, 200));
        botonRegistro.setFocusPainted(false);

        labelMensaje = new JLabel(" ");
        labelMensaje.setFont(new Font("Arial", Font.ITALIC, 12));
        labelMensaje.setForeground(Color.RED);
    }

    private void configurarLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(labelEmail, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(campoEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(labelContrasena, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(campoContrasena, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelMensaje, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(botonLogin, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(botonRegistro, gbc);
    }

    private void configurarEventos() {
        botonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manejarLogin();
            }
        });

        botonRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manejarRegistro();
            }
        });
    }

    private void manejarLogin() {
        String email = campoEmail.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();

        if (email.equals("") || contrasena.equals("")) {
            labelMensaje.setText("Error: Todos los campos son obligatorios.");
            return;
        }

        boolean exito = gestorUsuario.login(email, contrasena);

        if (exito) {
            labelMensaje.setText(" ");
            campoEmail.setText("");
            campoContrasena.setText("");
            ventana.mostrarPanel("perfil");
        } else {
            if (gestorUsuario.getIntentosFallidos() >= 3) {
                labelMensaje.setText("Cuenta bloqueada por demasiados intentos.");
            } else {
                labelMensaje.setText("Email o contrasena incorrectos. Intento " + gestorUsuario.getIntentosFallidos() + "/3");
            }
        }
    }

    private void manejarRegistro() {
        String nombre = JOptionPane.showInputDialog(ventana, "Ingrese su nombre:");
        if (nombre == null || nombre.trim().equals("")) {
            JOptionPane.showMessageDialog(ventana, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = JOptionPane.showInputDialog(ventana, "Ingrese su email:");
        if (email == null || email.trim().equals("")) {
            JOptionPane.showMessageDialog(ventana, "El email es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String contrasena = JOptionPane.showInputDialog(ventana, "Ingrese su contrasena:");
        if (contrasena == null || contrasena.trim().equals("")) {
            JOptionPane.showMessageDialog(ventana, "La contrasena es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = gestorUsuario.registrarUsuario(nombre.trim(), email.trim(), contrasena.trim());

        if (exito) {
            JOptionPane.showMessageDialog(ventana, "Usuario registrado exitosamente. Ya puede iniciar sesion.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(ventana, "No se pudo registrar. El email ya esta en uso.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
