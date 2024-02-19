package org.leonardoJesus;

import org.leonardoJesus.models.requests.get.ReqMessages;
import org.leonardoJesus.models.requests.get.ReqUsers;
import org.leonardoJesus.models.requests.get.ReqVerification;
import org.leonardoJesus.models.responces.objects.Message;
import org.leonardoJesus.models.responces.objects.User;

import java.sql.*;
import java.util.ArrayList;

public class DbManager {

    private static final String url = "jdbc:mysql://localhost:3306/chatpgv";

    private Connection connection;

    public DbManager(){
        try {
            connection = DriverManager.getConnection(url, "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodos Select
    /**
     * Metodo que devuelve el usuario que intenta conectarse a la DB, si este existe
     * @param req (ReqVerification) Los parametros que se requieren para hacer la consulta
     * @return (User) El usuario que intenta entrar en la aplicacción, si no existe devolvera null
     */
    public User getVerification(ReqVerification req){
        if (req.getPassword() == null || req.getEmail() == null){
            return null;
        }

        String sqlSentence = "SELECT * FROM users WHERE uemail = ? AND upassword = ?;";
        PreparedStatement sentence = null;
        ResultSet result = null;

        User user = null;

        if (connection != null){
            try {
                sentence = connection.prepareStatement(sqlSentence);
                sentence.setString(1, req.getEmail());
                sentence.setString(2, req.getPassword());

                result = sentence.executeQuery();

                if (result.next()){
                    String email = result.getString("uemail");
                    String name = result.getString("uname");
                    String password = result.getString("upassword");
                    user = new User(email, name, password, null, false);
                }

                return user;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    /**
     * Metodo para conseguir los usuarios que han participado en un chat con el usuario
     * @param req (ReqUsers) Parametros necesarios para hacer la consulta
     * @return (ArrayList Users) Con los usuarios con los que se ha iniciado un chat, devolvera null en caso de no haver ninguno
     */
    public ArrayList<User> getChatUsers(ReqUsers req){
        if (req.getMail() == null){
            return null;
        }

        String sqlSentence = "SELECT * FROM users WHERE uemail IN (SELECT sender From messages WHERE target = ?) OR uemail IN (SELECT target FROM messages WHERE sender = ?);";
        return getUser(req, sqlSentence);
    }

    /**
     * Metodo para conseguir todos los usuarios con los que no tengas un chat
     * @param req (ReqUsers) Parametros necesarios para realizar la consulta
     * @return (ArrayList User) Devuelve todos los usuarios con los que no se tenga un chat, de no haver ninguno se devolvera null
     */
    public ArrayList<User> getAllUsers(ReqUsers req){
        if (req.getMail() != null){
            return null;
        }

        String sqlSentence = "SELECt * FROM users WHERE uemail NOT IN (SELECT sender From messages WHERE target = 'leo.figueroa2002@gmail.com') AND uemail NOT IN (SELECT target FROM messages WHERE sender = 'leo.figueroa2002@gmail.com') AND uemail != 'leo.figueroa2002@gmail.com';";
        return getUser(req, sqlSentence);
    }

    private ArrayList<User> getUser(ReqUsers req, String sql){
        PreparedStatement sentence = null;
        ResultSet result = null;

        ArrayList<User> userArray = null;

        if (connection != null){
            try{
                sentence = connection.prepareStatement(sql);
                sentence.setString(1, req.getMail());
                sentence.setString(2, req.getMail());

                result = sentence.executeQuery();

                if (result != null){
                    userArray = new ArrayList<>();
                    Boolean state;

                    while (result.next()){
                        state = (result.getInt("state")> 0);

                        userArray.add(new User(result.getString("uemail"), result.getString("uname"), null, null, state));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userArray;
    }

    public ArrayList<Message> getMessages(ReqMessages req) {
        // TODO Implement method "GetMessages"
        return null;
    }

    public void updateUser(User user) {
        // TODO Implement method "UpdateUser"
    }

    public User getNewUser(String email, String password) {
        // TODO Implement method "GetNewUser"
        return null;
    }

    public void postUser(User user) {
        // TODO Implement method "PostUser"
    }

    public void postMessage(Message message) {
        // TODO Implement method "PostMessage"
    }
}
