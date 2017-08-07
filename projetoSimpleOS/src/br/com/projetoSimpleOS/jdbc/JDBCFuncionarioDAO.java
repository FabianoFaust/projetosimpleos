package br.com.projetoSimpleOS.jdbc;

import java.sql.Connection;  
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import br.com.projetoSimpleOS.jdbcinterface.FuncionarioDAO;
import br.com.projetoSimpleOS.objetos.Funcionario;

public class JDBCFuncionarioDAO implements FuncionarioDAO{
	private Connection conexao;

	public JDBCFuncionarioDAO(Connection conexao){
		this.conexao = conexao;
	}
	public boolean inserir (Funcionario funcionario){
		System.out.println(funcionario.getNumero());
		String comando = "insert into funcionarios " +
				"(nome, endereco, numero, bairro, cidade, estado, telefone , celular, email)" +
				"values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement p;
		try{
				System.out.println("passei");
				p = this.conexao.prepareStatement(comando);
				p.setString(1,funcionario.getNome());
				p.setString(2,funcionario.getEndereco());
				p.setString(3, funcionario.getNumero());
				p.setString(4,funcionario.getBairro());
				p.setString(5,funcionario.getCidade());
		    	p.setString(6,funcionario.getEstado());
		    	p.setLong(7,funcionario.getTelefone());
				p.setLong(8,funcionario.getCelular());
				p.setString(9,funcionario.getEmail());	
				p.execute();
		
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Funcionario> buscarPorNome(String nome){
		String comando = "select * from funcionarios ";
		if(!nome.equals("null") && !nome.equals("")){
			comando += "where nome like'" + nome + "%'";
		}

		List<Funcionario> listFuncionario = new ArrayList<Funcionario>();
		Funcionario funcionario = null;
		try{
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				funcionario = new Funcionario();
				int id = rs.getInt("idfuncionario");
				String nome1= rs.getString("nome");
				String endereco = rs.getString("endereco");
				  //int numero = rs.getInt("numero");
			 	  //String bairro = rs.getString("bairro");
			    	//String cidade = rs.getString("cidade");
			      //	String estado = rs.getString("estado");
				   //int telefone = rs.getInt("telefone");
				long celular = rs.getLong("celular");
				String email = rs.getString("email");


				funcionario.setId(id);
				funcionario.setNome(nome1);
				funcionario.setEndereco(endereco);
			//	funcionario.setNumero(numero);
			//	funcionario.setBairro(bairro);
			//	funcionario.setCidade(cidade);
		//		funcionario.setEstado(estado);
		//		funcionario.setTelefone(telefone);
				funcionario.setCelular(celular);
				funcionario.setEmail(email);
	

				listFuncionario.add(funcionario);	
			}
		}catch (Exception e){
			e.printStackTrace();

		}
		return listFuncionario;
	}
	
	public boolean deletarFuncionario(int id) {
		String comando = "delete from funcionarios where idfuncionario = " +id;
		Statement p;

		try{
			p =  this.conexao.createStatement();
			p.execute(comando);
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
	public Funcionario buscarPorId(int cod) {
		String comando = "select * from funcionarios where idfuncionario = "+cod;
		Funcionario funcionario = new Funcionario();
		
		try{
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()){
			
				int id = rs.getInt("idfuncionario");
				String nome1= rs.getString("nome");
				String endereco = rs.getString("endereco");
				  String numero = rs.getString("numero");
			 	  String bairro = rs.getString("bairro");
			    	String cidade = rs.getString("cidade");
			      String estado = rs.getString("estado");
				   long telefone = rs.getLong("telefone");
				long celular = rs.getLong("celular");
				String email = rs.getString("email");


				funcionario.setId(id);
				funcionario.setNome(nome1);
				funcionario.setEndereco(endereco);
				funcionario.setNumero(numero);
				funcionario.setBairro(bairro);
				funcionario.setCidade(cidade);
		    	funcionario.setEstado(estado);
		  		funcionario.setTelefone(telefone);
				funcionario.setCelular(celular);
				funcionario.setEmail(email);
	
			}

		} catch (SQLException e){
			e.printStackTrace();
		}
		return funcionario;

	}
	public boolean atualizar(Funcionario funcionario){
		
	
		String comando = "Update funcionarios set nome=?, endereco=?, numero=?, bairro=?, cidade=?, estado=?, telefone=?, celular=?, email=? where idfuncionario = ";
		comando += funcionario.getId();
		PreparedStatement p;
		try{
			p = this.conexao.prepareStatement(comando);
			
			p.setString(1,funcionario.getNome());
			p.setString(2,funcionario.getEndereco());
			p.setString(3,funcionario.getNumero());
			p.setString(4,funcionario.getBairro());
			p.setString(5,funcionario.getCidade());
			p.setString(6,funcionario.getEstado());
			p.setLong(7,funcionario.getTelefone());
			p.setLong(8,funcionario.getCelular());
			p.setString(9,funcionario.getEmail());
			
			p.execute();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}