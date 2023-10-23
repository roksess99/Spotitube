package nl.han.oose.dea.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.Entity.PlaylistEntity;
import nl.han.oose.dea.Entity.TrackEntity;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.services.PlaylistsService;

@ApplicationScoped
@Path("/playlists")
public class PlaylistResource {
    private PlaylistsService playlistsService;

    @Inject
    public void setPlaylistsService(PlaylistsService playlistsService) {
        this.playlistsService = playlistsService;
    }

    @ApplicationScoped
    @GET
    public Response getPlaylists(@QueryParam("token") String token) {
        try {
            return Response.status(Response.Status.OK).entity(playlistsService.getPlaylists(token)).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylists(@QueryParam("token") String token, PlaylistEntity playlistEntity) {
        try {
            return Response.status(Response.Status.OK).entity(playlistsService.addPlaylist(token, playlistEntity)).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(playlistsService.deletePlaylist(token, id)).build();

        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, PlaylistEntity playlistEntity) {
        try {
            return Response.status(Response.Status.OK).entity(playlistsService.editPlaylist(token, playlistEntity)).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylistTracks(@QueryParam("token") String token, @PathParam("id") int id) {
        try {
            return Response.status(Response.Status.OK).entity(playlistsService.getPlaylistTracks(id)).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/{playlistId}/tracks/{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId) {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(playlistsService.deleteTrackFromPlaylist(playlistId, trackId)).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/{id}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int id, @QueryParam("token") String token, TrackEntity track) {
        try {
            return Response.status(Response.Status.OK).entity(playlistsService.addTrack(id, token, track)).build();
        } catch (ServiceException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
