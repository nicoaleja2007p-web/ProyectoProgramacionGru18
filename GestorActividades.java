package negocio;

import modelo.Actividad;

import java.util.ArrayList;

public class GestorActividades {

    private ArrayList<Actividad> actividades;

    public GestorActividades() {
        this.actividades = new ArrayList<Actividad>();
    }

    public boolean registrarActividad(String nombre, double costo, int duracion, String categoria) {
        if (nombre == null || nombre.equals("")) {
            System.out.println("Error: El nombre de la actividad es obligatorio.");
            return false;
        }

        if (costo < 0) {
            System.out.println("Error: El costo no puede ser negativo.");
            return false;
        }

        if (duracion <= 0) {
            System.out.println("Error: La duracion debe ser mayor a cero.");
            return false;
        }

        if (categoria == null || categoria.equals("")) {
            System.out.println("Error: La categoria es obligatoria.");
            return false;
        }

        int nuevoId = actividades.size() + 1;
        Actividad nueva = new Actividad(nuevoId, nombre, costo, duracion, categoria);
        actividades.add(nueva);
        System.out.println("Actividad registrada: " + nombre);
        return true;
    }

    public ArrayList<Actividad> filtrarPorPresupuesto(double presupuesto) {
        ArrayList<Actividad> resultado = new ArrayList<Actividad>();
        for (int i = 0; i < actividades.size(); i++) {
            if (actividades.get(i).esViable(presupuesto)) {
                resultado.add(actividades.get(i));
            }
        }
        return resultado;
    }

    public ArrayList<Actividad> filtrarPorCategoria(String categoria) {
        ArrayList<Actividad> resultado = new ArrayList<Actividad>();
        for (int i = 0; i < actividades.size(); i++) {
            if (actividades.get(i).getCategoria().equals(categoria)) {
                resultado.add(actividades.get(i));
            }
        }
        return resultado;
    }

    public ArrayList<Actividad> filtrarInteligente(double presupuesto, String categoria) {
        ArrayList<Actividad> resultado = new ArrayList<Actividad>();
        for (int i = 0; i < actividades.size(); i++) {
            Actividad actividad = actividades.get(i);
            if (actividad.esViable(presupuesto) && actividad.getCategoria().equals(categoria)) {
                resultado.add(actividad);
            }
        }
        return resultado;
    }

    public boolean eliminarActividad(int id) {
        for (int i = 0; i < actividades.size(); i++) {
            if (actividades.get(i).getId() == id) {
                actividades.remove(i);
                System.out.println("Actividad eliminada.");
                return true;
            }
        }
        System.out.println("Error: Actividad no encontrada.");
        return false;
    }

    public Actividad buscarPorId(int id) {
        for (int i = 0; i < actividades.size(); i++) {
            if (actividades.get(i).getId() == id) {
                return actividades.get(i);
            }
        }
        return null;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }
}