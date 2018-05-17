package HSQLDB;

import org.hsqldb.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest {

    public static Connection isConnection() {
        Server hsqlServer = null;
        ResultSet rs = null;
        Connection connection = null;

        hsqlServer = new Server();
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);
        hsqlServer.setDatabaseName(0, "TestDB");
        hsqlServer.setDatabasePath(0, "file:MYDB");

        hsqlServer.start();

        // making a connection
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:TestDB", "sa", "123");
            connection.prepareStatement("drop table people if exists;").execute();
            connection.prepareStatement("create table people (name varchar(30), picture varchar(20) , status varchar(30), gender varchar(1) , age int, state varchar(5));").execute();

            connection.prepareStatement("insert into people (name, picture, status, gender, age, state) values ('Alex Smith','','student at RMIT','M',21,'WA');").execute();
            connection.prepareStatement("insert into people (name, picture, status, gender, age, state) values ('Ben Turner', 'BenPhoto.jpg','manager at Coles','M',35,'VIC');").execute();
            connection.prepareStatement("insert into people (name, picture, status, gender, age, state) values ('Hannah White','Hannah.png','student at PLC','F',14,'VIC');").execute();
            connection.prepareStatement("insert into people (name, picture, status, gender, age, state) values ('Zoe Foster','','Founder of ZFX','F',28,'VIC');").execute();
            connection.prepareStatement("insert into people (name, picture, status, gender, age, state) values ('Mark Turner','Mark.jpeg','','M',2,'VIC');").execute();


        }
        catch (SQLException e2) {
            e2.printStackTrace();
        }
        catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }

        return connection;
    }
}
