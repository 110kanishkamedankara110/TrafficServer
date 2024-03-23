package web.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.glassfish.jersey.server.mvc.Viewable;
import web.util.MessageReciver;

import javax.swing.text.View;

@Path("/page/")
public class PageController {
    @GET
    @Path("index")
    public Viewable getHome(){
       return new Viewable("/views/index");
    }
}
