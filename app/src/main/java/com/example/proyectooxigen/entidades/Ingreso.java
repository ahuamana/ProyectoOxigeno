package com.example.proyectooxigen.entidades;

public class Ingreso {


    private String DepartamentoEmpresa;
    private String DireccionEmpresa;
    private String DisponibilidadEmpresa;
    private String LatitudEmpresa;
    private String LogoEmpresaURL;
    private String LongitudEmpresa;

    private String NombreEmpresa;
    private String PrecioUnitarioProducto;
    private String ProvinciaEmpresa;

    private String ServicioEmpresa;
    private String TelefonoEmpresa;

    public Ingreso() {
    }

    public Ingreso(String departamentoEmpresa, String direccionEmpresa, String disponibilidadEmpresa, String latitudEmpresa, String logoEmpresaURL, String longitudEmpresa, String nombreEmpresa, String precioUnitarioProducto, String provinciaEmpresa, String servicioEmpresa, String telefonoEmpresa) {
        DepartamentoEmpresa = departamentoEmpresa;
        DireccionEmpresa = direccionEmpresa;
        DisponibilidadEmpresa = disponibilidadEmpresa;
        LatitudEmpresa = latitudEmpresa;
        LogoEmpresaURL = logoEmpresaURL;
        LongitudEmpresa = longitudEmpresa;
        NombreEmpresa = nombreEmpresa;
        PrecioUnitarioProducto = precioUnitarioProducto;
        ProvinciaEmpresa = provinciaEmpresa;
        ServicioEmpresa = servicioEmpresa;
        TelefonoEmpresa = telefonoEmpresa;
    }

    public String getDepartamentoEmpresa() {
        return DepartamentoEmpresa;
    }

    public void setDepartamentoEmpresa(String departamentoEmpresa) {
        DepartamentoEmpresa = departamentoEmpresa;
    }

    public String getDireccionEmpresa() {
        return DireccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        DireccionEmpresa = direccionEmpresa;
    }

    public String getDisponibilidadEmpresa() {
        return DisponibilidadEmpresa;
    }

    public void setDisponibilidadEmpresa(String disponibilidadEmpresa) {
        DisponibilidadEmpresa = disponibilidadEmpresa;
    }

    public String getLatitudEmpresa() {
        return LatitudEmpresa;
    }

    public void setLatitudEmpresa(String latitudEmpresa) {
        LatitudEmpresa = latitudEmpresa;
    }

    public String getLogoEmpresaURL() {
        return LogoEmpresaURL;
    }

    public void setLogoEmpresaURL(String logoEmpresaURL) {
        LogoEmpresaURL = logoEmpresaURL;
    }

    public String getLongitudEmpresa() {
        return LongitudEmpresa;
    }

    public void setLongitudEmpresa(String longitudEmpresa) {
        LongitudEmpresa = longitudEmpresa;
    }

    public String getNombreEmpresa() {
        return NombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        NombreEmpresa = nombreEmpresa;
    }

    public String getPrecioUnitarioProducto() {
        return PrecioUnitarioProducto;
    }

    public void setPrecioUnitarioProducto(String precioUnitarioProducto) {
        PrecioUnitarioProducto = precioUnitarioProducto;
    }

    public String getProvinciaEmpresa() {
        return ProvinciaEmpresa;
    }

    public void setProvinciaEmpresa(String provinciaEmpresa) {
        ProvinciaEmpresa = provinciaEmpresa;
    }

    public String getServicioEmpresa() {
        return ServicioEmpresa;
    }

    public void setServicioEmpresa(String servicioEmpresa) {
        ServicioEmpresa = servicioEmpresa;
    }

    public String getTelefonoEmpresa() {
        return TelefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        TelefonoEmpresa = telefonoEmpresa;
    }
}
