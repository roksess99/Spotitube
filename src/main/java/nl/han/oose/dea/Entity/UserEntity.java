package nl.han.oose.dea.Entity;

public class UserEntity {
    private String user;
    private String password;
    private String name;
    private String token;


    public String getUser() {
        return user;
    }

    public void setUser(String username) {
        this.user = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
