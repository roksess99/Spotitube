package nl.han.oose.dea.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.services.TracksService;

@ApplicationScoped
@Path("/tracks")
public class TracksResource {
    private TracksService tracksService;

    @Inject
    public void setTracksService(TracksService tracksService) {
        this.tracksService = tracksService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTracks(@QueryParam("forPlaylist") int id) {
        try {
            return Response.status(Response.Status.OK).entity(tracksService.getAllAvailableTracks(id)).build();

        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
