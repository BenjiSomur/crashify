package com.level6ninja.crashify.ws;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpUtils {
    /* TODO */
    private static final String URL_WS_CRASHIFY = "http://104.42.195.95:8080/CrashifyWS/ws/";
    private static final Integer CONNECT_TIMEOUT = 4000; //MILISEGUNDOS
    private static final Integer READ_TIMEOUT = 10000; //MILISEGUNDOS

    public static String login(String telefono, String password) {
        HttpURLConnection c = null;
        String res = null;
        try {
            URL u = new URL(URL_WS_CRASHIFY + "conductores/iniciarSesion");
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);
            c.setConnectTimeout(CONNECT_TIMEOUT);
            c.setReadTimeout(READ_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    c.getOutputStream());
            String urlParameters = String.format("telefono=%s&password=%s", telefono, password);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            Log.v("Usuario: ", telefono);
            Log.v("Contraseña", password);
            Integer status = c.getResponseCode();
            Log.v("Cosas: ", status.toString());
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }else{
                Log.v("Error: ", status.toString());
            }

        } catch (MalformedURLException ex) {
            Log.v("Malformed lo que sea", ex.getMessage());
            ex.printStackTrace();
            res = (ex.getMessage());
        } catch (IOException ex) {
            Log.v("IO lo que sea", ex.getMessage());
            ex.printStackTrace();
            res = (ex.getMessage());
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            if (c != null) {
                c.disconnect();
            }
        }
        return res;
    }

    public static String regitrarConductor(String nombre, String telefono,
                                           String password, String fechaNacimiento,
                                           String numeroLicencia) {
        HttpURLConnection c = null;
        String res = null;
        try {
            URL u = new URL(URL_WS_CRASHIFY + "/conductores/registrarConductor");
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);
            c.setConnectTimeout(CONNECT_TIMEOUT);
            c.setReadTimeout(READ_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    c.getOutputStream());
            String urlParameters = String.format("nombre=%s&telefono=%s&password=%s&" +
                            "fechaNacimiento=%s&numeroLicencia=%s"
                    , nombre, telefono, password, fechaNacimiento, numeroLicencia);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = c.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            res = (ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            res = (ex.getMessage());
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
        return res;
    }

    public static String autenticarConductor(String telefono, String token) {
        String res = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(URL_WS_CRASHIFY + "/conductores/autenticar");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream()
            );
            String urlParameters = String.format("telefono=%s&token=%s", telefono, token);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return res;
    }

    public static String regitrarVehiculo(String numPlacas, String marca,
                                          String modelo, String color,
                                          String year, String idConductor) {
        HttpURLConnection c = null;
        String res = null;
        try {
            URL u = new URL(URL_WS_CRASHIFY + "/vehiculos/registrarVehiculo");
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);
            c.setConnectTimeout(CONNECT_TIMEOUT);
            c.setReadTimeout(READ_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    c.getOutputStream());
            String urlParameters = String.format("numPlacas=%s&marca=%s&modelo=%s&" +
                            "color=%s&año=%s&idConductor=%s"
                    , numPlacas, marca, modelo, color, year, idConductor);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = c.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            res = (ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            res = (ex.getMessage());
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
        return res;
    }

    public static String editarVehiculo(String idVehiculo, String numPlacas, String marca,
                                        String modelo, String color,
                                        String year, String idConductor) {
        HttpURLConnection c = null;
        String res = null;
        try {
            URL u = new URL(URL_WS_CRASHIFY + "/vehiculos/editarVehiculo");
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);
            c.setConnectTimeout(CONNECT_TIMEOUT);
            c.setReadTimeout(READ_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    c.getOutputStream());
            String urlParameters = String.format("idVehiculo=%s&numPlacas=%s&marca=%s&modelo=%s&" +
                    "color=%s&año=%s&idConductor=%s" +
                    idVehiculo, numPlacas, marca, modelo, color, year, idConductor);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = c.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            res = (ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            res = (ex.getMessage());
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
        return res;
    }

    public static String getVehiculos(String idConductor) {
        HttpURLConnection c = null;
        String res = null;
        try {
            URL u = new URL(URL_WS_CRASHIFY + "/vehiculos/getVehiculos");
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);
            c.setConnectTimeout(CONNECT_TIMEOUT);
            c.setReadTimeout(READ_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    c.getOutputStream());
            String urlParameters = String.format("idConductor=%s", idConductor);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = c.getResponseCode();
            Log.v("Estatus vehiculos: ", status.toString());
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            res = (ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            res = (ex.getMessage());
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
        return res;
    }

    public static String getVehiculosReporte(String idReporte){
        HttpURLConnection conn = null;
        String res = null;
        try{
            URL u = new URL(URL_WS_CRASHIFY + "/conductores/iniciarSesion");
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream());
            String urlParameters = String.format("idReporte=%s",idReporte);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                res = sb.toString();
            }
        }catch(MalformedURLException ex) {
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            if(conn != null){
                conn.disconnect();
            }
        }
        return res;
    }

    public static String getMarcas(){
        HttpURLConnection conn = null;
        String res = null;
        try{
            URL url = new URL(URL_WS_CRASHIFY + "/marcas/getMarcas");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            Integer status = conn.getResponseCode();
            Log.v("Estatus marcas: ", status.toString());
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                res = sb.toString();
            }

        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }

        return res;
    }

    public static String getMarca(String idMarca){
        HttpURLConnection conn = null;
        String res = null;
        try{
            URL url = new URL(URL_WS_CRASHIFY+"/marcas/getMarca");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream()
            );
            String urlParameters = String.format("idMarca=%s",idMarca);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }

        return res;
    }
    public static String getAseguradoras(){
        HttpURLConnection conn = null;
        String res = null;
        try{
            URL url = new URL(URL_WS_CRASHIFY + "/aseguradoras/getAseguradoras");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }

        return res;
    }

    public static String getAseguradora(String idAseguradora){
        HttpURLConnection conn = null;
        String res = null;
        try{
            URL url = new URL(URL_WS_CRASHIFY+"/aseguradoras/getAseguradora");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream()
            );
            String urlParameters = String.format("idMarca=%s",idAseguradora);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }

        return res;
    }

    public static String getReportes(String idConductor){
        String res = null;
        HttpURLConnection conn = null;

        try{
            URL url = new URL(URL_WS_CRASHIFY + "/reportes/getReportes");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream()
            );
            String urlParameters = String.format("idCOnductor=%s",idConductor);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = conn.getResponseCode();
            if(status == 200 || status == 201){
                BufferedReader br = new BufferedReader(
                  new InputStreamReader(
                          conn.getInputStream()
                  )
                );
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine())!=null){
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            if(conn != null){
                conn.disconnect();
            }
        }

        return res;
    }

    public static String subirEvidencia(String idReporte, Bitmap bitmap){
        String res = null;
        HttpURLConnection conn = null;

        DataOutputStream outputStream = null;

        try{
            URL url = new URL(URL_WS_CRASHIFY + "/subirImagen/"+idReporte);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent","Android MultiPart HTTP Client 1.0");
            conn.setRequestProperty("Content-Type","application/octet-stream");

            outputStream = new DataOutputStream(conn.getOutputStream());

            ByteArrayOutputStream bitMapOutputStream =
                    new ByteArrayOutputStream();
            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    75,
                    bitMapOutputStream
            );
            byte original[]=bitMapOutputStream.toByteArray();

            int blockBytes, totalBytes, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            int lastByte = 0;
            totalBytes = original.length;
            bufferSize = Math.min(totalBytes, maxBufferSize);
            buffer = Arrays.copyOfRange(original, lastByte, bufferSize);
            blockBytes = buffer.length;
            while (totalBytes > 0) {
                outputStream.write(buffer, 0,bufferSize);
                totalBytes -= blockBytes;
                lastByte += blockBytes;
                bufferSize = Math.min(totalBytes,maxBufferSize);
                buffer = Arrays.copyOfRange(original,lastByte, lastByte+bufferSize);
                blockBytes = buffer.length;
            }
            bitMapOutputStream.close();
            outputStream.flush();

            Integer status = conn.getResponseCode();
            if(status == 200 || status == 201){
                if(conn.getInputStream() != null){
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()
                            )
                    );
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = br.readLine())!=null){
                        sb.append(line + "\n");
                    }
                    br.close();
                    res = sb.toString();
                }
            }
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            if (conn != null) {

                conn.disconnect();
            }
        }
        return res;
    }

    public static String getEvidencias(String idReporte){
        HttpURLConnection conn = null;
        String res = null;

        try{
            URL url = new URL(URL_WS_CRASHIFY + "/evidencias/getEvidencias");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECT_TIMEOUT);

            DataOutputStream wr = new DataOutputStream(
              conn.getOutputStream()
            );
            String urlParameters = String.format("idReporte=%s", idReporte);
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            Integer status = conn.getResponseCode();
            if (status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                res = sb.toString();
            }
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }

        return res;
    }
}
