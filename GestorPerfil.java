package negocio;

import modelo.Actividad;
import modelo.PerfilPreferencias;

import java.util.ArrayList;

public class GestorPerfil {

    private PerfilPreferencias perfil;

    public GestorPerfil(PerfilPreferencias perfil) {
        this.perfil = perfil;
    }

    public void configurarPerfil(String estilo, double gastoDiario, ArrayList<String> categorias, ArrayList<Double> puntajes) {
        if (estilo == null || estilo.equals("")) {
            System.out.println("Error: El estilo de viaje es obligatorio.");
            return;
        }

        if (gastoDiario <= 0) {
            System.out.println("Error: El gasto diario debe ser mayor a cero.");
            return;
        }

        perfil.actualizarPerfil(estilo, gastoDiario);

        for (int i = 0; i < categorias.size(); i++) {
            perfil.agregarCategoria(categorias.get(i), puntajes.get(i));
        }

        System.out.println("Perfil configurado correctamente.");
    }

    public ArrayList<Actividad> filtrarPorAfinidad(ArrayList<Actividad> actividades) {
        ArrayList<Actividad> filtradas = new ArrayList<Actividad>();

        for (int i = 0; i < actividades.size(); i++) {
            Actividad actividad = actividades.get(i);
            double afinidad = perfil.calcularAfinidad(actividad.getCategoria());
            if (afinidad >= 50.0) {
                filtradas.add(actividad);
            }
        }

        return filtradas;
    }

    public PerfilPreferencias getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilPreferencias perfil) {
        this.perfil = perfil;
    }
}
