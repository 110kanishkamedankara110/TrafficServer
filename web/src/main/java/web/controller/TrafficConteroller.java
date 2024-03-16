package web.controller;

import dto.Gps;
import dto.TrafficDataDto;
import ejb.remote.TrafficData;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import web.util.ServerClients;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


@Path("/api/traffic/")
public class TrafficConteroller {
    TrafficData td;

    public TrafficConteroller() {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
            td = (TrafficData) ic.lookup("java:global/trafficEar/com.phoenix-ejb-traffic-1.0/TrafficDataBean");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }
    @GET
    @Path("locations")
    public List<Gps> getLocations() {
        return td.getLocations();
    }

    @GET
    @Path("getData/{latitude}/{longitude}")
    public List<TrafficDataDto> getData(@PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
        return td.getData(latitude, longitude);
    }

    @GET
    @Path("getData")
    public List<TrafficDataDto> getData() {
        return td.getData();
    }

}
