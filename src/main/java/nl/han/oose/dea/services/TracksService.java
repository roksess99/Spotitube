package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.Entity.TracksEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.dao.TracksDAO;
import nl.han.oose.dea.dao.UserDAO;

public class TracksService {
    private TracksDAO tracksDAO;
    private UserDAO userDAO;
    private TracksEntity tracks;

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setTracksDAO(TracksDAO tracksDAO) {
        this.tracksDAO = tracksDAO;
    }

    public TracksEntity getAllAvailableTracks(int id) {
        if (id != 0 && !(id < 0)) {
            try {
                tracks = tracksDAO.getAllAvailableTracks(id);
            } catch (DatabaseException e) {
                throw new ServiceException("Tracks ophalen van playlist met id: " + id + " mislukt");
            }
        }
        return tracks;
    }
}
