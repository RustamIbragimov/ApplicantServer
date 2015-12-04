package model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ribra on 11/8/2015.
 */
public class JDBCDriver {
    private final static String JDBC_DRIVER = "org.postgresql.Driver";
    private final static String URL = "jdbc:postgresql://localhost:5432/Aiesec";
    private final static String USER = "postgres";
    private final static String PASSWORD = "301096";
    private static Connection connection;
    private static JDBCDriver instance = new JDBCDriver();
    private static Logger logger = Logger.getLogger(JDBCDriver.class.getName());

    private JDBCDriver() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addAll(List<Person> list) {
        try {
            Statement stm = connection.createStatement();
            for (Person x : list) {
                String creationDate = x.getCreationDate();
                String name = x.getName();
                String birthDate = x.getBirthDate();
                String city = x.getCity();
                String phoneNumber = x.getPhoneNumber();
                String email = x.getEmail();
                String university = x.getUniversity();
                String place = x.getPlace();
                String reason = x.getReason();

                creationDate = creationDate.replace("'", "''");
                name = name.replace("'", "''");
                birthDate = birthDate.replace("'", "''");
                city = city.replace("'", "''");
                phoneNumber = phoneNumber.replace("'", "''");
                email = email.replace("'", "''");
                university = university.replace("'", "''");
                place = place.replace("'", "''");
                reason = reason.replace("'", "''");

                String sql = String.format("INSERT INTO People(creation_date, name, birth_date, city,\n" +
                        "                  phone_number, email, university, \n" +
                        "                  place, reason)\n" +
                        "VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                        creationDate, name, birthDate, city, phoneNumber, email, university, place, reason);
                stm.execute(sql);
                logger.info(sql);
            }
            logger.info("All applicants are added");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getAll() {
        String sql = "SELECT creation_date, name, birth_date, city, phone_number,\n" +
                "email, university, place, reason\n" +
                "FROM People;";
        List<Person> list = getListBySQL(sql);
        return list;
    }

    public void insertPerson(Person person) {
        try {
            Statement stm = connection.createStatement();

            String creationDate = person.getCreationDate();
            String name = person.getName();
            String birthDate = person.getBirthDate();
            String city = person.getCity();
            String phoneNumber = person.getPhoneNumber();
            String email = person.getEmail();
            String university = person.getUniversity();
            String place = person.getPlace();
            String reason = person.getReason();

            creationDate = creationDate.replace("''", "''");
            name = name.replace("'", "''");
            birthDate = birthDate.replace("'", "''");
            city = city.replace("'", "''");
            phoneNumber = phoneNumber.replace("'", "''");
            email = email.replace("'", "''");
            university = university.replace("'", "''");
            place = place.replace("'", "''");
            reason = reason.replace("'", "''");

            String sql = String.format("INSERT INTO People(creation_date, name, birth_date, city,\n" +
                            "                  phone_number, email, university, \n" +
                            "                  place, reason)\n" +
                            "VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                    creationDate, name, birthDate, city, phoneNumber, email, university, place, reason);
            stm.execute(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Person> findByNumber(String number) {
        List<Person> list = new LinkedList<Person>();
        try {
            String sql = "SELECT creation_date, name, birth_date, city, phone_number,\n" +
                    "email, university, place, reason, image, attended\n" +
                    "FROM People\n" +
                    "WHERE phone_number LIKE ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + number);
            ResultSet rs = preparedStatement.executeQuery();
            list = getPeopleFromResultSet(rs);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Person> findByName(String nameStr) {
        List<Person> list = new LinkedList<Person>();
        try {
            Statement stm = connection.createStatement();
            String sql = "SELECT creation_date, name, birth_date, city, phone_number,\n" +
                    "email, university, place, reason, image, attended\n" +
                    "FROM People\n" +
                    "WHERE name LIKE '%" + nameStr + "%';";
            ResultSet rs = stm.executeQuery(sql);
            list = getPeopleFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Person> getPeopleFromResultSet(ResultSet rs) throws SQLException, IOException, ClassNotFoundException {
        List<Person> list = new LinkedList<Person>();
        while (rs.next()) {
            String creationDate = rs.getString("creation_date");
            String name = rs.getString("name");
            String birthDate = rs.getString("birth_date");
            String city = rs.getString("city");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String university = rs.getString("university");
            String place = rs.getString("place");
            String reason = rs.getString("reason");
            byte[] imgBytes = rs.getBytes("image");
            SerializableImage image = Util.bytesToImage(imgBytes);
            boolean attended = rs.getBoolean("attended");

            Person person = new Person(creationDate, name, birthDate, city,
                    phoneNumber, email, university, place, reason, image);
            person.setAttended(attended);
            list.add(person);
        }
        return list;
    }


    public List<Person> getListBySQL(String sql) {
        List<Person> list = new LinkedList<Person>();

        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                String creationDate = rs.getString("creation_date");
                String name = rs.getString("name");
                String birthDate = rs.getString("birth_date");
                String city = rs.getString("city");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String university = rs.getString("university");
                String place = rs.getString("place");
                String reason = rs.getString("reason");

                Person person = new Person(creationDate, name, birthDate, city, phoneNumber, email, university, place, reason);
                list.add(person);
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void setAttended(String phoneNumber) {
        String sql = "UPDATE People\n" +
                "SET attended = true\n" +
                "WHERE phone_number = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePhoto(Person person) {
        String sql = "UPDATE People\n" +
                "SET image = ?\n" +
                "WHERE phone_number = ? AND name = ?;";
        try {
            byte[] imgBytes = Util.imgToBytes(person.getPhoto().getImage());

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBytes(1, imgBytes);
            stm.setString(2, person.getPhoneNumber());
            stm.setString(3, person.getName());
            stm.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        return connection;
    }

    public static JDBCDriver getInstance() {
        return instance;
    }
}
