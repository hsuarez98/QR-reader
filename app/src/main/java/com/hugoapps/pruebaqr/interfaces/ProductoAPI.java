package com.hugoapps.pruebaqr.interfaces;

import com.hugoapps.pruebaqr.models.Producto;
import com.hugoapps.pruebaqr.network.ServiceListApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductoAPI {
    String key = "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZFVzdWFyaW8iOiI2Yzc4YWYxYS0yZTdlLTQ5ZDMtYjAxNS1lZDI3YTlmNDgzNWQiLCJub21icmVVc3VhcmlvIjoidGVzdHdlYiIsImlhdCI6MTcwNTA1NzAyMSwiaXNzIjoiaHR0cHM6Ly93d3cuYWR6Z2kuY29tIiwianRpIjoiIn0.J9SasbEaxwU2hlG5YRpDEeEJc8vZgb6cVYzj3cRNo84";

    //Obtener lista productos
    @GET("productos?offset=0&limit=20")
    @Headers({"token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZFVzdWFyaW8iOiI2Yzc4YWYxYS0yZTdlLTQ5ZDMtYjAxNS1lZDI3YTlmNDgzNWQiLCJub21icmVVc3VhcmlvIjoidGVzdHdlYiIsImlhdCI6MTcwNTA1NzAyMSwiaXNzIjoiaHR0cHM6Ly93d3cuYWR6Z2kuY29tIiwianRpIjoiIn0.J9SasbEaxwU2hlG5YRpDEeEJc8vZgb6cVYzj3cRNo84"})
    Call <ServiceListApiResponse> getProductos();


    //Buscar productos
    @GET("productos")
    @Headers({"token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZFVzdWFyaW8iOiI2Yzc4YWYxYS0yZTdlLTQ5ZDMtYjAxNS1lZDI3YTlmNDgzNWQiLCJub21icmVVc3VhcmlvIjoidGVzdHdlYiIsImlhdCI6MTcwNTA1NzAyMSwiaXNzIjoiaHR0cHM6Ly93d3cuYWR6Z2kuY29tIiwianRpIjoiIn0.J9SasbEaxwU2hlG5YRpDEeEJc8vZgb6cVYzj3cRNo84"})
    Call<ServiceListApiResponse> find(@Query("buscar") String titulo);

    //ordenar lista
    @GET("https://datos.adzgi.com:6587/api/productos")
    @Headers({"token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZFVzdWFyaW8iOiI2Yzc4YWYxYS0yZTdlLTQ5ZDMtYjAxNS1lZDI3YTlmNDgzNWQiLCJub21icmVVc3VhcmlvIjoidGVzdHdlYiIsImlhdCI6MTcwNTA1NzAyMSwiaXNzIjoiaHR0cHM6Ly93d3cuYWR6Z2kuY29tIiwianRpIjoiIn0.J9SasbEaxwU2hlG5YRpDEeEJc8vZgb6cVYzj3cRNo84"})
    Call<ServiceListApiResponse> getProductosOrden(@Query("orden")String ordenar);

    //Obtener producto
    @GET("productos")
    @Headers({"token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZFVzdWFyaW8iOiI2Yzc4YWYxYS0yZTdlLTQ5ZDMtYjAxNS1lZDI3YTlmNDgzNWQiLCJub21icmVVc3VhcmlvIjoidGVzdHdlYiIsImlhdCI6MTcwNTA1NzAyMSwiaXNzIjoiaHR0cHM6Ly93d3cuYWR6Z2kuY29tIiwianRpIjoiIn0.J9SasbEaxwU2hlG5YRpDEeEJc8vZgb6cVYzj3cRNo84"})
    Call<Producto> escanerQr(@Query("codigobarras") String codigo);
}
