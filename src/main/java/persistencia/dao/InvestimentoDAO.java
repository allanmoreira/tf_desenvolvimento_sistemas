package persistencia.dao;

import org.apache.log4j.Logger;
import persistencia.MySQLConexao;
import persistencia.modelos.*;
import regraNegocio.BDException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDAO {

    private static Logger logger = Logger.getLogger(InvestimentoDAO.class);

    public List<Investimento> selectAll(int idInvestimento) throws BDException {
        List<Investimento> listaInvestimentos = new ArrayList<>();
        //language=MySQL
        String sql =
                "SELECT " +
                    "idInvestimento, idMoeda, idUsuario, Descricao, dataIncial, dataFinal, Quantidade " +
                "FROM INVESTIMENTOS";

        if(idInvestimento != 0)
            sql += " WHERE idInvestimento = ?";

        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                if(idInvestimento != 0)
                    preparedStatement.setInt(1, idInvestimento);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Investimento investimento = new Investimento(
                            resultSet.getInt("idInvestimento"),
                            new Moeda(resultSet.getInt("idMoeda")),
                            new Usuario(resultSet.getInt("idUsuario")),
                            resultSet.getString("Descricao"),
                            resultSet.getDate("dataIncial"),
                            resultSet.getDate("dataFinal"),
                            resultSet.getBigDecimal("Quantidade")
                    );
                    listaInvestimentos.add(investimento);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
        return listaInvestimentos;
    }

    public void insert(Investimento investimento) throws BDException {
        //language=MySQL
        String sql =
                "INSERT INTO " +
                    "coin_counter_bd.INVESTIMENTOS " +
                    "(idMoeda, idUsuario, Descricao, dataIncial, dataFinal, Quantidade) " +
                "VALUES (?, ?, ?, ?, ?, ?) ";
        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                int cont=1;
                preparedStatement.setInt(cont++, investimento.getMoeda().getIdMoeda());
                preparedStatement.setInt(cont++, investimento.getUsuario().getIdUsuario());
                preparedStatement.setString(cont++, investimento.getDescricao());
                preparedStatement.setDate(cont++, investimento.getDataInicial());
                preparedStatement.setDate(cont++, investimento.getDataFinal());
                preparedStatement.setBigDecimal(cont, investimento.getQuantidade());
                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
    }
}
