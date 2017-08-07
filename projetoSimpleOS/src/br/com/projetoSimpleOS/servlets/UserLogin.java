package br.com.projetoSimpleOS.servlets;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.projetoSimpleOS.bd.conexao.Conexao;
import br.com.projetoSimpleOS.jdbc.JDBCUsuarioDAO;
import br.com.projetoSimpleOS.objetos.Usuario;


@WebServlet(urlPatterns = "/loginServlet")
public class UserLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		Usuario usuario = new Usuario();

		usuario.setLogin(request.getParameter("login"));
		usuario.setSenha(request.getParameter("senha"));

		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
		Usuario retornoDao = jdbcUsuario.buscarNome(usuario.getLogin(), usuario.getSenha());

		
		if (retornoDao!= null) {
			HttpSession sessao = request.getSession();
			sessao.setAttribute("userlogado", retornoDao);
			response.sendRedirect("/projetoSimpleOS/pages/index.html");
		} else {
			response.sendRedirect("/projetoSimpleOS/loginusuario.html");

		}
	}
	
	//GERADOR HASH MD5 

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			try {
				process(request, response);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			process(request, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}