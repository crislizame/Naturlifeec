package net.jhonlizame.naturlife.core;

/**
 * Created by CRISLIZAME on 22/2/2018.
 */

public class core {

    //entradas
    public static final String TABLA_ENTRADAS="entradas";
    public static final String CAMPO_NOMBRES="nombres";
    public static final String CAMPO_APELLIDOS="apellidos";
    public static final String CAMPO_DIR="direccion";
    public static final String CAMPO_COOR="coordenadas";
    public static final String CAMPO_IDENTRADAS="id_entradas";
    public static final String CAMPO_IDENTRADASN="id_entradasn";
    public static final String CAMPO_NUEVOTRAFK="idnuevotra_fk";
    public static final String CAMPO_NPAGO="npago";
    public static final String CAMPO_VALOR="valor";
    public static final String CAMPO_FECHAPAGO="fechapago";
    public static final String CAMPO_PAGADO="pagado";
    public static final String CAMPO_ESTADO="estado";
    //Cobro
    public static final String TABLA_COBRO="cobro";
    public static final String CAMPO_VALORCOBRO="valor";
    public static final String CAMPO_FECHACOBRO="fechacobro";
    public static final String CAMPO_IDENTRADASFK="identradas_fk";
    public static final String CAMPO_HORA="hora";
    public static final String CAMPO_COORDENADASCOBRO="coordenadas";
    public static final String CAMPO_NOVEDADES="novedades";
    public static final String CAMPO_OBSERVA="obser";
    public static final String CAMPO_IDCOBRO="idcobro";


    public static final String CREAR_TABLA_ENTRADAS="CREATE TABLE IF NOT EXISTS "+TABLA_ENTRADAS+" " +
            "("+CAMPO_IDENTRADAS+" INTEGER,"+CAMPO_IDENTRADASN+" INTEGER,"+CAMPO_NOMBRES+" TEXT, "+CAMPO_APELLIDOS+" TEXT," +
            " "+CAMPO_DIR+" TEXT, "+CAMPO_COOR+" TEXT, "+CAMPO_NUEVOTRAFK+" TEXT,"+CAMPO_NPAGO+" TEXT, " +
            ""+CAMPO_VALOR+" TEXT,"+CAMPO_FECHAPAGO+" TEXT,"+CAMPO_PAGADO+" TEXT,"+CAMPO_ESTADO+" TEXT)";

    public static final String CREAR_TABLA_COBRO="CREATE TABLE IF NOT EXISTS "+TABLA_COBRO+" " +
            "("+CAMPO_IDCOBRO+" INTEGER,"+CAMPO_VALORCOBRO+" TEXT, "+CAMPO_FECHACOBRO+" TEXT," +
            " "+CAMPO_IDENTRADASFK+" TEXT, "+CAMPO_HORA+" TEXT, "+CAMPO_COORDENADASCOBRO+" TEXT, "+CAMPO_NOVEDADES+" TEXT, " +
            ""+CAMPO_OBSERVA+" TEXT)";


    public static final String BASE_URL = "http://naturlifeec.com/vmapp/ws/";

    public static final String CREAR_TABLA_USUARIOS="CREATE TABLE IF NOT EXISTS usuarios " +
            "(idusuariosn INTEGER,idusuarios INTEGER, nombres TEXT, apellidos TEXT, cedula TEXT, celular TEXT,correo TEXT, fechaing TEXT,ulting TEXT)";
    public static final String googlee = "https://google.com.ec";
    public static final String STRING_ENTRADAS=BASE_URL+"?view=entradas";
    public static final String clicodigoxpedido="";




}
