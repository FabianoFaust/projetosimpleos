package br.com.projetoSimpleOS.jdbcinterface;

import java.util.List;

import br.com.projetoSimpleOS.objetos.Funcionario;

public interface FuncionarioDAO {

	public boolean inserir(Funcionario contato);
	public List<Funcionario> buscarPorNome(String nome);
	public boolean deletarFuncionario(int id);
	public Funcionario buscarPorId(int cod);
	public boolean atualizar (Funcionario funcionario);
}
