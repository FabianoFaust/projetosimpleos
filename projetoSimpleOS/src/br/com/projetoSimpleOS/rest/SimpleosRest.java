package br.com.projetoSimpleOS.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.projetoSimpleOS.bd.conexao.Conexao;
import br.com.projetoSimpleOS.jdbc.JDBCFuncionarioDAO;
import br.com.projetoSimpleOS.objetos.Funcionario;

@Path("simpleRest")
public class SimpleosRest extends UtilRest {
	
	@POST
	@Path("/addFuncionario")
	@Consumes("application/*")
	public Response addFuncionario(String FuncParam) {
		try {
		
			
			
			Funcionario funcionario = new ObjectMapper().readValue(FuncParam, Funcionario.class);//readValue deserealiza o que chegou, e compara os atributos, se são iguais ao do objeto contato.
		
			
		
				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				

				JDBCFuncionarioDAO jdbcfuncionario = new JDBCFuncionarioDAO(conexao);
				jdbcfuncionario.inserir(funcionario);
				conec.fecharConexao();
				
	
				return this.buildResponse("Funcionário cadastrado com sucesso.");
			} catch (Exception e) {
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());
			}
		}
	//BUSCA POR NOME	
	@POST
	@Path("/buscarFuncionarioPorNome/{nome}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarFuncionarioPorNome (@PathParam("nome") String nome){
		try{
			List<Funcionario> funcionario = new ArrayList<Funcionario>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCFuncionarioDAO jdbcfuncionario = new JDBCFuncionarioDAO(conexao);
			funcionario = jdbcfuncionario.buscarPorNome(nome);
			conec.fecharConexao();
			
			return this.buildResponse(funcionario);
		}catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	//Deletar funcionario
	
	@POST
	@Path("/deletarFuncionario/{id}")
	@Produces("application/*")
	public Response deletarFuncionario(@PathParam("id") int id){
		
		try{
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCFuncionarioDAO jdbcfuncionario = new JDBCFuncionarioDAO(conexao);
			jdbcfuncionario.deletarFuncionario(id);
			conec.fecharConexao();
			
			return this.buildResponse("Funcionário deletado com sucesso.");
		}catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/buscarFuncionarioPeloId/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarFuncionarioPeloId(@PathParam("id") int id){
		try{
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcfuncionario = new JDBCFuncionarioDAO(conexao);
			Funcionario funcionario = jdbcfuncionario.buscarPorId(id);
			return this.buildResponse(funcionario);
			
		}catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@POST
	@Path("/editarFuncionario/")
	@Produces("application/*")
	public Response editarFuncionario(String funcionarioParam){
		
		try{
			
			Funcionario funcionario = new ObjectMapper().readValue(funcionarioParam, Funcionario.class);
		
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCFuncionarioDAO jdbcfuncionario = new JDBCFuncionarioDAO(conexao);
			jdbcfuncionario.atualizar(funcionario);
			conec.fecharConexao();
			
			return this.buildResponse("Contato editado com sucesso.");
			
		}catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
}//Finalizar a classe