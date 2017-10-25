package regraNegocio;

import persistencia.dao.UsuarioDAO;
import persistencia.modelos.Usuario;

public class LoginControle {

    private UsuarioDAO usuarioDAO;

    public LoginControle() {
        usuarioDAO = new UsuarioDAO();
    }

    public int insereUsuario(String nome, String email, String senha) throws BDException, ValidacaoException {
        if(nome.equals(""))
            throw new ValidacaoException("Informe o nome!");
        if(email.equals(""))
            throw new ValidacaoException("Informe o email!");
        if(senha.equals(""))
            throw new ValidacaoException("Informe a senha!");

        Usuario usuario;
        boolean emailExistente = usuarioDAO.emailExistente(email);
        if(!emailExistente) {
            if(nome.length() > 50)
                throw new ValidacaoException("O nome deve ter no máximo 50 caracteres!");
            if(email.length() > 50)
                throw new ValidacaoException("O email deve ter no máximo 50 caracteres!");
            if(senha.length() > 50)
                throw new ValidacaoException("A senha deve ter no máximo 50 caracteres!");
            usuario = new Usuario(nome, email, senha);
        } else {
            throw new ValidacaoException("Email já cadastrado!");
        }
        return usuarioDAO.insert(usuario);
    }

    public Usuario selecionaPorEmailSenha(String email, String senha) throws BDException, ValidacaoException {
        if(email.equals(""))
            throw new ValidacaoException("Informe o email!");
        if(senha.equals(""))
            throw new ValidacaoException("Informe a senha!");
        return usuarioDAO.selectByEmailSenha(email, senha);
    }
}
