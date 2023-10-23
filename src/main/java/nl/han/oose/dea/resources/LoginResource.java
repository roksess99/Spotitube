package nl.han.oose.dea.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.Entity.LoginEntity;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.services.LoginService;

@ApplicationScoped
@Path("/login")
public class LoginResource {
    private LoginService loginService;

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApplicationScoped
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Login(LoginEntity loginEntity) {
        try {
            return Response.status(Response.Status.OK).entity(loginService.validateUser(loginEntity)).build();

        } catch (ServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
