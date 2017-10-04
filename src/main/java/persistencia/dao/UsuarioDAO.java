package persistencia.dao;

import org.apache.log4j.Logger;
import persistencia.MySQLConexao;
import persistencia.modelos.Usuario;
import regra_negocio.BDException;

import java.sql.*;

public class UsuarioDAO {

    private static Logger logger = Logger.getLogger(UsuarioDAO.class);

    public int insert(Usuario usuario) throws BDException {
        int idUsuario = 0;
        //language=MySQL
        String sql =
                "INSERT INTO " +
                    "usuario (nome, email, senha) " +
                "VALUES (?, ?, ?)";
        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, usuario.getNome());
                preparedStatement.setString(2, usuario.getEmail());
                preparedStatement.setString(3, usuario.getSenha());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                idUsuario = resultSet.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
        return idUsuario;
    }

    public Usuario selectByEmailSenha(String email, String senha) throws BDException {
        Usuario usuario = null;
        //language=MySQL
        String sql =
                "SELECT "+
                    "id_usuario, nome, email, senha "+
                "FROM "+
                    "usuario " +
                "WHERE "+
                    "email = ? AND " +
                    "senha = ?";
        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, senha);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){
                        usuario = new Usuario(
                                resultSet.getInt("id_usuario"),
                                resultSet.getString("nome"),
                                resultSet.getString("email"),
                                resultSet.getString("senha")
                        );
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
        return usuario;
    }

    public boolean emailExistente(String email) throws BDException {
        boolean emailExistente = false;
        //language=MySQL
        String sql =
                "SELECT "+
                    "email "+
                "FROM "+
                    "usuario " +
                "WHERE "+
                    "email = ?";
        try (Connection conexao = MySQLConexao.conectar()) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next())
                        emailExistente = true;
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            throw new BDException(ex.getMessage());
        }
        return emailExistente;
    }
}
