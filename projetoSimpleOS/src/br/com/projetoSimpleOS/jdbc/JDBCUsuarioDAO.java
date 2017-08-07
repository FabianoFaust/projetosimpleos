package br.com.projetoSimpleOS.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.projetoSimpleOS.objetos.Autenticacao;
import br.com.projetoSimpleOS.objetos.Usuario;

public class JDBCUsuarioDAO {

	private Connection conexao;

	public JDBCUsuarioDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public Usuario buscarNome(String loginn, String senhauser) {
		Usuario usuario = new Usuario();

		String comando = "SELECT userlogin, senha, nomedousuario, salt FROM login WHERE userlogin =?;";

		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, loginn);

			ResultSet rs = p.executeQuery();
			if (rs.next()) {

				String nomeUsuario = rs.getString("nomedousuario");
				String loginUsuario = rs.getString("userlogin");
				String senhabd = rs.getString("senha");
				String salt = rs.getString("salt");

				Autenticacao gerahash = new Autenticacao();
				String senhacrip = gerahash.BS64MD5(senhauser, salt);
			
				if (senhacrip.equals(senhabd)) {

					usuario.setLogin(loginUsuario);
					usuario.setNomeDoUsuario(nomeUsuario);
					usuario.setSenha(senhabd);
					usuario.setSalt(salt);

					return usuario;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

}
