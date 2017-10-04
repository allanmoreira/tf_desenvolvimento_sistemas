package persistencia;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by allanmoreira on 03/10/2017.
 */
public class MySQLConexao {

    private static Connection connection;
    private static Logger logger = Logger.getLogger(MySQLConexao.class);


    public static Connection conectar() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://198.199.105.36:3306/coin_counter_bd?zeroDateTimeBehavior=convertToNull";
            String usuario = "trab_desenv_sistemas_usr";
            String senha = "coin_counter";

            connection = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
            throw e;
        }
        return connection;
    }

}
