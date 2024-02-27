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
     * @return (String) El usuario que intenta entrar en la aplicacción, si no existe devolvera null
     */
    public String getVerification(ReqVerification req){
        if (req.getPassword() == null || req.getEmail() == null){
            return null;
        }

        String sqlSentence = "SELECT JSON_OBJECT('email', uemail, 'name', uname, 'password', upassword, 'image', uimage ) as 'Json' from users where uemail = ? AND upassword = ?;";
        PreparedStatement sentence = null;
        ResultSet result = null;

        String user = null;

        if (connection != null){
            try {
                sentence = connection.prepareStatement(sqlSentence);
                sentence.setString(1, req.getEmail());
                sentence.setString(2, req.getPassword());

                result = sentence.executeQuery();

                if (result.next()){
                    user = result.getString("Json");
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
     * @return (String) Con los usuarios con los que se ha iniciado un chat, devolvera null en caso de no haver ninguno
     */
    public String getChatUsers(ReqUsers req){
        if (req.getMail() == null){
            return null;
        }

        String sqlSentence = "select convert(COALESCE((select JSON_ARRAYAGG(JSON_OBJECT('email', uemail, 'name', uname, 'password', upassword, 'image', uimage )) FROM users WHERE uemail IN (SELECT sender From messages WHERE target = ?) OR uemail IN (SELECT target FROM messages WHERE sender = ?)), '{[]}'), json) as 'Json';";

        return getUser(req, sqlSentence);
    }

    /**
     * Metodo para conseguir todos los usuarios con los que no tengas un chat
     * @param req (ReqUsers) Parametros necesarios para realizar la consulta
     * @return (String) Devuelve todos los usuarios con los que no se tenga un chat, de no haver ninguno se devolvera null
     */
    public String getAllUsers(ReqUsers req){
        if (req.getMail() == null){
            return null;
        }

        String sqlSentence = "Select convert(COALESCE((SELECT JSON_ARRAYAGG(\n" +
                "\tJSON_OBJECT(\n" +
                "\t\t'email', uemail,\n" +
                "\t\t'name', uname,\n" +
                "\t\t'password', upassword,\n" +
                "\t\t'image', uimage\n" +
                "\t))\n" +
                "FROM users \n" +
                "WHERE uemail NOT IN (SELECT sender From messages WHERE target = ?) \n" +
                "AND uemail NOT IN (SELECT target FROM messages WHERE sender = ?) \n" +
                "AND uemail != 'leo.figueroa2002@gmail.com'), '[]'), JSON) AS 'Json';";

        return getUser(req, sqlSentence);
    }

    private String getUser(ReqUsers req, String sql){
        PreparedStatement sentence = null;
        ResultSet result = null;

        String userArray = null;

        if (connection != null){
            try{
                sentence = connection.prepareStatement(sql);
                sentence.setString(1, req.getMail());
                sentence.setString(2, req.getMail());

                result = sentence.executeQuery();

                if (result.next()){
                    userArray = result.getString("Json");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userArray;
    }

    /**
     * Metodo para obtener los mensajes enviados entre un usuario y otro
     * @param req (ReqMessages) Parametros necesarios para realizar la consulta
     * @return (String) Devuelve una lista con los mensajes, si no se encuentra ninguno se devolvera null
     */
    public String getMessages(ReqMessages req){
        if (req.getUser1() == null || req.getUser2() == null){
            return null;
        }

        String sqlSentence = "select convert(\n" +
                "\tJSON_OBJECT(\n" +
                "\t\t\t'menssages', \n" +
                "            COALESCE((SELECT JSON_ARRAYAGG(\n" +
                "\t\t\t\tJSON_OBJECT(\n" +
                "\t\t\t\t\t'sender' , sender,\n" +
                "                    'target', target,\n" +
                "                    'date', mdate,\n" +
                "                    'body', body\n" +
                "                ))\n" +
                "                FROM messages\n" +
                "                where (sender = 'leo.figueroa2002@gmail.com' and target = 'mario.mario@gmail')\n" +
                "                or (sender = 'mario.mario@gmail' and target = 'leo.figueroa2002@gmail.com')\n" +
                "                order by mdate\n" +
                "\t\t\t), '[]'\n" +
                "        )\n" +
                "\t), \n" +
                "    JSON\n" +
                ") as 'Json';";
        PreparedStatement sentence = null;
        ResultSet result = null;

        String messages = null;

        if (connection != null){
            try {
                sentence = connection.prepareStatement(sqlSentence);
                sentence.setString(1, req.getUser1());
                sentence.setString(2, req.getUser2());

                result = sentence.executeQuery();

                if (result.next()){
                    messages = result.getString("Json");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return messages;
    }

    public void updateUser(User user) {
        if (user == null){
            return;
        }

        String sqlSentence = "update users set uname = ?, upassword = ?, uimage = ? where uemail = ?;";

        PreparedStatement sentence = null;

        if (connection != null){
            try {
                sentence = connection.prepareStatement(sqlSentence);

                sentence.setString(1, user.getName());
                sentence.setString(2, user.getPassword());
                sentence.setString(3, user.getImage());
                sentence.setString(4, user.getEmail());

                sentence.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getNewUser(String email, String password) {
        if (email == null || password == null){
            return null;
        }

        String sqlSentence = "SELECT JSON_OBJECT('email', uemail, 'name', uname, 'password', upassword, 'image', uimage ) as 'Json' from users where uemail = ? AND upassword = ?;";

        PreparedStatement sentence = null;
        ResultSet result = null;

        if (connection != null){
            try{

                sentence = connection.prepareStatement(sqlSentence);

                sentence.setString(1, email);
                sentence.setString(2, password);

                result = sentence.executeQuery();

                if (result != null){
                    return result.getString("Json");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void postUser(User user) {
        if (user == null){
            return;
        }

        String sqlCentence= "insert into users(uemail, uname, upassword, uimage) values (?, ?, ?, ?);";
        PreparedStatement sentence= null;

        if (connection != null){
            try {

                sentence = connection.prepareStatement(sqlCentence);

                sentence.setString(1, user.getEmail());
                sentence.setString(2, user.getName());
                sentence.setString(3, user.getPassword());
                sentence.setString(4, user.getImage());

                sentence.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void postMessage(Message message) {
        if (message == null){
            return;
        }

        String sqlSentence = "insert into messages(sender, target, mdate, body) values (?, ?, now(), ?);";

        PreparedStatement sentence = null;

        if (connection != null){
            try {
                sentence = connection.prepareStatement(sqlSentence);

                sentence.setString(1, message.getSender());
                sentence.setString(2, message.getTarget());
                sentence.setString(3, message.getBody());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
