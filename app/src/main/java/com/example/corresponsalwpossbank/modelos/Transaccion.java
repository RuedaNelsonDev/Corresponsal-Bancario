package com.example.corresponsalwpossbank.modelos;

public class Transaccion {
    String id;
    String tipo;
    String comision;
    String valorTotal;
    String valor;
    String cuentaCorresponsal;
    String cedulaRemitente;
    String cedulaReceptor;
    String tarjeta;
    String cuotas;
    String fecha;
    String hora;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCuentaCorresponsal() {
        return cuentaCorresponsal;
    }

    public void setCuentaCorresponsal(String idCorresponsal) {
        this.cuentaCorresponsal = idCorresponsal;
    }

    public String getCedulaRemitente() {
        return cedulaRemitente;
    }

    public void setCedulaRemitente(String cedulaRemitente) {
        this.cedulaRemitente = cedulaRemitente;
    }

    public String getCedulaReceptor() {
        return cedulaReceptor;
    }

    public void setCedulaReceptor(String cedulaReceptor) {
        this.cedulaReceptor = cedulaReceptor;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getCuotas() {
        return cuotas;
    }

    public void setCuotas(String cuotas) {
        this.cuotas = cuotas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
