package persistencia.dao;

import org.apache.log4j.Logger;
import persistencia.MySQLConexao;
import persistencia.modelos.DadosBitTrex;
import persistencia.modelos.HistoricoMoeda;
import persistencia.modelos.Moeda;
import regraNegocio.BDException;

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
            sql += " WHERE idMoeda = ?";

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

    public List<HistoricoMoeda> selectHistoricoMoeda(int idMoeda, Date dataInicial, Date dataFinal) throws BDException {
        List<HistoricoMoeda> listaHistoricoMoeda = new ArrayList<>();
        //language=MySQL
        String sql =
                "SELECT " +
                    "idMoeda, valorAbertura, valorFechamento, valorAlta, " +
                    "valorBaixa, volumeMoeda, volumeBTC, dataHistorico " +
                "FROM HISTORICO_MOEDA " +
                "WHERE " +
                    "idMoeda = ? AND " +
                    "dataHistorico > ? AND " +
                    "dataHistorico  <= ?";

        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, idMoeda);
                preparedStatement.setDate(2, dataFinal);
                preparedStatement.setDate(3, dataInicial);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    HistoricoMoeda historicoMoeda = new HistoricoMoeda(
                            resultSet.getInt("idMoeda"),
                            resultSet.getBigDecimal("valorAbertura"),
                            resultSet.getBigDecimal("valorFechamento"),
                            resultSet.getBigDecimal("valorAlta"),
                            resultSet.getBigDecimal("valorBaixa"),
                            resultSet.getBigDecimal("volumeMoeda"),
                            resultSet.getBigDecimal("volumeBTC"),
                            resultSet.getDate("dataHistorico")
                    );
                    listaHistoricoMoeda.add(historicoMoeda);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
        return listaHistoricoMoeda;
    }

    public Date ultimaDataHistoricoMoeda(int idMoeda) throws BDException {
        Date ultimaData = null;
        //language=MySQL
        String sql =
                "SELECT max(dataHistorico) AS ultimaData " +
                "FROM HISTORICO_MOEDA " +
                "WHERE idMoeda = ?";

        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, idMoeda);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next())
                    ultimaData = resultSet.getDate("ultimaData");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
        return ultimaData;
    }

    public void insert(int idMoeda, ArrayList<DadosBitTrex> listaDadosBitTrex) throws BDException {
        //language=MySQL
        String sql =
                "INSERT INTO " +
                        "HISTORICO_MOEDA " +
                        "(idMoeda, valorAbertura, valorFechamento, valorAlta, valorBaixa, volumeMoeda, volumeBTC, dataHistorico) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (DadosBitTrex dadosBitTrex : listaDadosBitTrex) {
                    int cont = 1;
                    preparedStatement.setInt(cont++, idMoeda);
                    preparedStatement.setBigDecimal(cont++, dadosBitTrex.getO());
                    preparedStatement.setBigDecimal(cont++, dadosBitTrex.getC());
                    preparedStatement.setBigDecimal(cont++, dadosBitTrex.getH());
                    preparedStatement.setBigDecimal(cont++, dadosBitTrex.getL());
                    preparedStatement.setBigDecimal(cont++, dadosBitTrex.getV());
                    preparedStatement.setBigDecimal(cont++, dadosBitTrex.getBV());
                    preparedStatement.setDate(cont, dadosBitTrex.getT());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
    }
}
