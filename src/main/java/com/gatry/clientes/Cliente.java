
package com.gatry.clientes;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private String tipoCliente;

    public Cliente(int idCliente, String nombre, String telefono,
                   String correo, String direccion, String tipoCliente) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.tipoCliente = tipoCliente;
    }

    public Cliente(String nombre, String telefono, String correo,
                   String direccion, String tipoCliente) {
        this(0, nombre, telefono, correo, direccion, tipoCliente);
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }
}
