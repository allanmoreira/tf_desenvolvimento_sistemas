package persistencia.dao;

import org.apache.log4j.Logger;
import persistencia.MySQLConexao;
import persistencia.modelos.Moeda;
import persistencia.modelos.PeriodoHistorico;
import persistencia.modelos.Usuario;
import regra_negocio.BDException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoedaDAO {

    private static Logger logger = Logger.getLogger(MoedaDAO.class);

    public List<Moeda> selectAll(int idMoeda) throws BDException {
        List<Moeda> listaMoedas = new ArrayList<>();
        //language=MySQL
        String sql =
                "SELECT " +
                    "idMoeda, Nome, Sigla, Valor " +
                "FROM MOEDAS";

        if(idMoeda != 0)
            sql += "WHERE idMoeda = ?";

        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                if(idMoeda != 0)
                    preparedStatement.setInt(1, idMoeda);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Moeda moeda = new Moeda(
                            resultSet.getInt("idMoeda"),
                            resultSet.getString("Nome"),
                            resultSet.getString("Sigla"),
                            resultSet.getBigDecimal("Valor")
                    );
                    listaMoedas.add(moeda);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
        return listaMoedas;
    }

    public List<Moeda> selectHistoricoMoeda(int idMoeda, PeriodoHistorico periodoHistorico, int limitePeriodo) throws BDException {
        List<Moeda> listaMoedas = new ArrayList<>();
        //language=MySQL
        String sql =
                "SELECT " +
                        "idMoeda, Nome, Sigla, Valor " +
                        "FROM MOEDAS";

        if(idMoeda != 0)
            sql += "WHERE idMoeda = ?";

        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                if(idMoeda != 0)
                    preparedStatement.setInt(1, idMoeda);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Moeda moeda = new Moeda(
                            resultSet.getInt("idMoeda"),
                            resultSet.getString("Nome"),
                            resultSet.getString("Sigla"),
                            resultSet.getBigDecimal("Valor")
                    );
                    listaMoedas.add(moeda);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
        return listaMoedas;
    }

}
