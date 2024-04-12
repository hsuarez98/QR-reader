package com.hugoapps.pruebaqr.network;

import com.hugoapps.pruebaqr.models.Producto;

import java.util.ArrayList;

public class ServiceListApiResponse {
     private int status_code;
     private boolean success;
     private ArrayList<Producto> data;

    public int getStatus_code() {
        return status_code;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<Producto> getData() {
        return data;
    }
}
