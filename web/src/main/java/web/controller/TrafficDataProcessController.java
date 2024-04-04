package web.controller;

import dto.AverageData;
import dto.Gps;
import ejb.remote.TrafficDataProcess;
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

@Path("/api/processed/")
public class TrafficDataProcessController {

    TrafficDataProcess td;
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    public TrafficDataProcessController() {

        InitialContext ic = null;
        try {
            ic = new InitialContext();
            td = (TrafficDataProcess) ic.lookup("java:global/trafficEar/com.phoenix-ejb-traffic-1.0/TrafficDataProcessBean");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

    @GET
    @Path("getAverage")
    public List<AverageData> getAverage() {
        return td.getAverage();
    }



    @GET

    @Path("getAllAverage")
    public int getAllAverage() {
        return td.getAllAverage();
    }

    @GET
    @Path("getMaxSpeed")
    public List<AverageData> getMaxSpeeds() {
        return td.getMaxSpeeds();
    }



    @GET
    @Path("getAllMaxSpeed")
    public AverageData getMaxAllSpeeds() {
        return td.getMaxAllSpeeds();
    }

    @GET
    @Path("getMinSpeed")
    public List<AverageData> getMinSpeeds() {
        return td.getMinSpeeds();
    }



    @GET
    @Path("getAllMinSpeed")
    public AverageData getMinAllSpeeds() {
        return td.getMinAllSpeeds();
    }
}
