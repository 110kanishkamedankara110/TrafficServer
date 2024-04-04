package web.controller;

import dto.Gps;
import dto.TrafficDataDto;
import ejb.remote.TrafficData;


import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.List;


@Path("/api/traffic/")
public class TrafficConteroller {
    TrafficData td;
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

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
        response.setHeader("Access-Control-Allow-Origin", "*");
        return td.getLocations();
    }

    @GET
    @Path("getData/{latitude}/{longitude}")
    public List<TrafficDataDto> getData(@PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return td.getData(latitude, longitude);
    }

    @GET
    @Path("getData")
    public List<TrafficDataDto> getData() {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return td.getData();
    }

}
