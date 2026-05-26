package modelo;

import java.util.ArrayList;

public class PerfilPreferencias {

    private ArrayList<String> categorias;
    private String estiloViaje;
    private double gastoDiarioMax;
    private ArrayList<Double> puntajesAfinidad;

    public PerfilPreferencias(String estiloViaje, double gastoDiarioMax) {
        this.estiloViaje = estiloViaje;
        this.gastoDiarioMax = gastoDiarioMax;
        this.categorias = new ArrayList<String>();
        this.puntajesAfinidad = new ArrayList<Double>();
    }

    public void agregarCategoria(String categoria, double puntaje) {
        categorias.add(categoria);
        puntajesAfinidad.add(puntaje);
    }

    public double calcularAfinidad(String categoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).equals(categoria)) {
                return puntajesAfinidad.get(i);
            }
        }
        return 0.0;
    }

    public void actualizarPerfil(String nuevoEstilo, double nuevoGasto) {
        this.estiloViaje = nuevoEstilo;
        this.gastoDiarioMax = nuevoGasto;
    }

    public ArrayList<String> obtenerCategorias() {
        return categorias;
    }

    public String getEstiloViaje() {
        return estiloViaje;
    }

    public void setEstiloViaje(String estiloViaje) {
        this.estiloViaje = estiloViaje;
    }

    public double getGastoDiarioMax() {
        return gastoDiarioMax;
    }

    public void setGastoDiarioMax(double gastoDiarioMax) {
        this.gastoDiarioMax = gastoDiarioMax;
    }

    public ArrayList<Double> getPuntajesAfinidad() {
        return puntajesAfinidad;
    }

    public String toString() {
        return "PerfilPreferencias [estilo=" + estiloViaje + ", gastoDiarioMax=" + gastoDiarioMax + "]";
    }
}
