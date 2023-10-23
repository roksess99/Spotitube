package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.Entity.LoginEntity;
import nl.han.oose.dea.Entity.UserEntity;
import nl.han.oose.dea.Exceptions.DatabaseException;
import nl.han.oose.dea.Exceptions.ServiceException;
import nl.han.oose.dea.dao.UserDAO;

public class LoginService {
    private UserDAO userDAO;

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserEntity validateUser(LoginEntity loginEntity) {
        try {
            if (loginEntity != null &&
                    loginEntity.getUser() != null && !loginEntity.getUser().isEmpty() &&
                    loginEntity.getPassword() != null && !loginEntity.getPassword().isEmpty()) {

                return userDAO.validateUser(loginEntity);
            }
        } catch (DatabaseException e) {
            throw new ServiceException("User not Found");
        }
        return null;
    }

}
