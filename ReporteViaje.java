package modelo;

import java.util.ArrayList;

public class ReporteViaje {

    private ArrayList<DiaDeViaje> itinerario;
    private double costoTotal;
    private ArrayList<String> alertas;

    public ReporteViaje(ArrayList<DiaDeViaje> itinerario) {
        this.itinerario = itinerario;
        this.alertas = new ArrayList<String>();
        this.costoTotal = 0.0;
    }

    public String generarDesglose() {
        String desglose = "=== DESGLOSE DEL VIAJE ===\n";
        for (int i = 0; i < itinerario.size(); i++) {
            DiaDeViaje dia = itinerario.get(i);
            desglose = desglose + "Dia " + (i + 1) + " (" + dia.getFecha() + "): $" + dia.getCostoDelDia() + "\n";
            ArrayList<Actividad> actividades = dia.getActividades();
            for (int j = 0; j < actividades.size(); j++) {
                desglose = desglose + "   - " + actividades.get(j).toString() + "\n";
            }
        }
        desglose = desglose + "TOTAL: $" + costoTotal;
        return desglose;
    }

    public ArrayList<String> detectarExcesos(double gastoDiarioMax) {
        alertas = new ArrayList<String>();
        for (int i = 0; i < itinerario.size(); i++) {
            double costoDia = itinerario.get(i).getCostoDelDia();
            if (costoDia > gastoDiarioMax) {
                alertas.add("Dia " + (i + 1) + " (" + itinerario.get(i).getFecha() + ") excede el limite: $" + costoDia + " > $" + gastoDiarioMax);
            }
        }
        return alertas;
    }

    public ArrayList<String> sugerirAlternativas(double gastoDiarioMax, ArrayList<Actividad> todasActividades) {
        ArrayList<String> sugerencias = new ArrayList<String>();
        for (int i = 0; i < todasActividades.size(); i++) {
            Actividad actividad = todasActividades.get(i);
            if (actividad.getCosto() <= gastoDiarioMax) {
                sugerencias.add("Alternativa economica: " + actividad.toString());
            }
        }
        return sugerencias;
    }

    public void exportarReporte() {
        System.out.println(generarDesglose());
        System.out.println("Alertas: " + alertas.size());
        for (int i = 0; i < alertas.size(); i++) {
            System.out.println(alertas.get(i));
        }
    }

    public double getCostoTotal() {
        costoTotal = 0.0;
        for (int i = 0; i < itinerario.size(); i++) {
            costoTotal = costoTotal + itinerario.get(i).getCostoDelDia();
        }
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public ArrayList<DiaDeViaje> getItinerario() {
        return itinerario;
    }

    public ArrayList<String> getAlertas() {
        return alertas;
    }
}