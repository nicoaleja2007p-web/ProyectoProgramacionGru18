package negocio;

import modelo.Actividad;
import modelo.DiaDeViaje;
import modelo.PerfilPreferencias;
import modelo.Viaje;
import java.util.ArrayList;

public class Planificador {

    private Viaje viaje;
    private ArrayList<Actividad> actividades;
    private PerfilPreferencias perfil;

    public Planificador(Viaje viaje, ArrayList<Actividad> actividades, PerfilPreferencias perfil) {
        this.viaje = viaje;
        this.actividades = actividades;
        this.perfil = perfil;
    }

    public ArrayList<DiaDeViaje> generarItinerario(ArrayList<String> fechas) {
        ArrayList<DiaDeViaje> itinerario = new ArrayList<DiaDeViaje>();

        double gastoDiario = perfil.getGastoDiarioMax();
        ArrayList<Actividad> disponibles = filtrarActividades(gastoDiario);
        ArrayList<Boolean> usada = new ArrayList<Boolean>();

        for (int k = 0; k < disponibles.size(); k++) {
            usada.add(false);
        }

        for (int i = 0; i < fechas.size(); i++) {
            DiaDeViaje dia = new DiaDeViaje(fechas.get(i));
            double gastoAcumulado = 0.0;

            for (int j = 0; j < disponibles.size(); j++) {
                if (usada.get(j) == false) {
                    Actividad actividad = disponibles.get(j);
                    if (gastoAcumulado + actividad.getCosto() <= gastoDiario) {
                        dia.agregarActividad(actividad);
                        gastoAcumulado = gastoAcumulado + actividad.getCosto();
                        usada.set(j, true);
                    }
                }
            }

            itinerario.add(dia);
            viaje.agregarDia(dia);
        }

        return itinerario;
    }

    public void ajustarPresupuesto(double nuevoPresupuesto) {
        if (nuevoPresupuesto <= 0) {
            System.out.println("Error: El presupuesto debe ser mayor a cero.");
            return;
        }
        viaje.setPresupuesto(nuevoPresupuesto);
        perfil.setGastoDiarioMax(nuevoPresupuesto / viaje.calcularDuracion());
        System.out.println("Presupuesto ajustado a: $" + nuevoPresupuesto);
    }

    public ArrayList<Actividad> filtrarActividades(double presupuestoDiario) {
        ArrayList<Actividad> filtradas = new ArrayList<Actividad>();
        for (int i = 0; i < actividades.size(); i++) {
            if (actividades.get(i).esViable(presupuestoDiario)) {
                filtradas.add(actividades.get(i));
            }
        }
        return filtradas;
    }

    public boolean validarHorarios(DiaDeViaje dia) {
        int duracionTotal = dia.getDuracionTotal();
        if (duracionTotal <= 12) {
            return true;
        }
        System.out.println("Advertencia: El dia " + dia.getFecha() + " supera 12 horas de actividades.");
        return false;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public PerfilPreferencias getPerfil() {
        return perfil;
    }
}