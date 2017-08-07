SENAI.funcionario = new Object();

$(document).ready(
		function() {
			SENAI.funcionario.cadastrar = function() {

				// Validações formulário
				if (document.getElementById("nome").value == ""
					|| document.getElementById("telefone").value == ""
					|| document.getElementById("celular").value == ""
					|| document.getElementById("email").value == "") {
					alert("Preencha os campos obrigatórios!");
					return false;
					
		
				} else {
					
			var email = document.getElementById("email").value;
					
					if(email.indexOf("@") == -1 || // valida se existe o @
					email.indexOf(".") == -1 || // valida se existe o .
					email.indexOf("@") == 0 || // valida se tem texto antes do
												// @
					email.lastIndexOf(".") + 1 == email.length || // valida se
																	// tem texto
																	// depois do
																	// ponto
					(email.indexOf("@") + 1 == email.indexOf("."))){ // valida
																		// se
																		// tem
																		// texto
																		// entre
																		// o @ e
																		// o .{
					alert("email incorreto");
					document.getElementById("email").focus();
					return false;
					}

					var newCont = new Object();
					newCont.id = $("#idfuncionario").val();
					newCont.nome = $("#nome").val();
					newCont.endereco = $("#endereco").val();
					newCont.numero = $("#numero").val();
					newCont.bairro = $("#bairro").val();
					newCont.cidade = $("#cidade").val();
					newCont.estado = $("#estado").val();
					newCont.telefone = $("#telefone").val();
					newCont.celular = $("#celular").val();
					newCont.email = $("#email").val();
         
					var cfg = {
							
							url : "rest/simpleRest/addFuncionario",
							data : newCont,
							success : function(msg) {

								var cfg = {
										title : "Mensagem",
										height : 250,
										width : 400,
										modal : true,
										buttons : {
											"Ok" : function() {
												$(this).dialog("close");
											}
										}
								};

								$("#msg").html(msg);
								$("#msg").dialog(cfg);

								// Atualiza a tabela de contatos
								SENAI.funcionario.buscar();
							
							},// Fecha success
							error : function(err) {
								alert("Erro ao cadastrar um novo Funcionário: "
										+ err.responseText);
							}// Fechar error
					};// Fecha estrutura objeto cfg
					

					SENAI.ajax.post(cfg);
				}// Fecha o else
				
			};// Fecha a funcao SENAI.contato.cadastrar()

		SENAI.funcionario.buscar = function() {
				var valorBusca = null;
				SENAI.funcionario.exibirFuncionario(undefined, valorBusca);
			};// Fecha método SENAI.contato.buscar()


			SENAI.funcionario.exibirFuncionario = function(listaDeFuncionario, valorBusca){

				var html = "<table class='table'>";
				html += "<tr><th>Nome</th><th>Endereco</th><th>Telefone</th><th>Email</th><th>Ações</th></tr>";
				if (listaDeFuncionario != undefined && listaDeFuncionario.length > 0 && listaDeFuncionario[0].id != undefined) {
					for (var i = 0; i < listaDeFuncionario.length; i++) {
						html += "<tr>"
							+ "<td>"
							+ listaDeFuncionario[i].nome
							+ "</td>"
							+ "<td>"
							+ listaDeFuncionario[i].endereco
							+ "</td>"
							+ "<td>"
							+ listaDeFuncionario[i].celular
							+ "</td>"
							+ "<td>"
							+ listaDeFuncionario[i].email
							+ "</td>"
							+ "<td>"
							+ "<a class='link'onclick='SENAI.funcionario.buscarFuncionario("+listaDeFuncionario[i].id + ")'>Visualizar</a>"
							+ "<a class='link'onclick='SENAI.funcionario.editarFuncionario("+listaDeFuncionario[i].id + ")'>Editar</a>"
							+ "<a class='link' onclick='SENAI.funcionario.confirmarExclusao("+ listaDeFuncionario[i].id + ")'>Deletar</a>" + "</td>" + "</tr>";
					}
				} else {
					if (listaDeFuncionario == undefined || (listaDeFuncionario != undefined && listaDeFuncionario.length > 0)) {
						if (valorBusca == "") {
							valorBusca = null;
						}
						
						var cfg = {
								type : "POST",
								url : "rest/simpleRest/buscarFuncionarioPorNome/"
									+ valorBusca,
									success : function(listaDeFuncionario) {
										SENAI.funcionario
										.exibirFuncionario(listaDeFuncionario);
									},
									error : function(err) {
										alert("Erro ao consultar os Funcionário: "
												+ err.responseText);
									}
						};

						SENAI.ajax.post(cfg);

					} else {
						html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
					}
				}
				html += "</table>";
				$("#resultadoFuncionario").html(html);
			};

			SENAI.funcionario.exibirFuncionario(undefined, "");
			
			SENAI.funcionario.confirmarExclusao = function(id){
				var cfg = {
						title : "Comfirmar exclusão",
						height : 250,
						width : 400,
						modal : true,
						buttons : {
							"Ok" : function() {
								SENAI.funcionario.deletarFuncionario(id);
								$(this).dialog("close");
							},
						"Cancelar" : function() {
					$(this).dialog("close");
							}	
						}	 	
						
				};
				$("#msg").html("Deseja excluir este funcionário?");
				$("#msg").dialog(cfg);
				
			}

			SENAI.funcionario.deletarFuncionario = function(id) {


				var cfg = {
						type : "POST",
						url : "rest/simpleRest/deletarFuncionario/"+id,
						success : function(msg) {
							var cfg = {
									title : "Mensagem",
									height : 250,
									width : 400,
									modal : true,
									buttons : {
										"Ok" : function() {
											$(this).dialog("close");
										}
									}
							};
							$("#msg").html(msg);
							$("#msg").dialog(cfg);

							SENAI.funcionario.buscar();
						},
						error : function(err) {

							alert("Erro ao deletar o funcionário: "
									+ err.responseText);
						}
				};

				SENAI.ajax.post(cfg);
			}

			SENAI.funcionario.editarFuncionario = function(id){
				var cfg = {
						type:"POST",
						url: "rest/simpleRest/buscarFuncionarioPeloId/"+id,
						success:function (conta){
						
							$("#nomeEdit").val(conta.nome);
							$("#enderecoEdit").val(conta.endereco);
						    $("#numeroEdit").val(conta.numero);
						    $("#bairroEdit").val(conta.bairro);
						    $("#cidadeEdit").val(conta.cidade);
						    $("#estadoEdit").val(conta.estado);
							$("#telefoneEdit").val(conta.telefone);
							$("#celularEdit").val(conta.celular);
							$("#emailEdit").val(conta.email);
							$("#idFuncionarioEdit").val(conta.id);
							SENAI.funcionario.exibirEdicao(conta);
						},
						error: function (err){
							alert("Erro ao editar o funcionário: " + err.responseText);
						}
				}
				SENAI.ajax.post(cfg);
			}
		
			SENAI.funcionario.exibirEdicao = function(conta){
						var cfg = {
							title : "Editar Funcionário",
							height : 400,
							width : 550,
							modal : true,
							buttons : {
								"Salvar" : function() {
									var dialog = this;
									var newConta = new Object();
									
									
									newConta.id = $("#idFuncionarioEdit").val();
									 newConta.nome = $("#nomeEdit").val();
									 newConta.endereco = $("#enderecoEdit").val();
									 newConta.numero = $("#numeroEdit").val();
									 newConta.bairro = $("#bairroEdit").val();
									 newConta.cidade = $("#cidadeEdit").val();
									  newConta.estado = $("#estadoEdit").val();
									 newConta.telefone = $("#telefoneEdit").val();
									 newConta.celular = $("#celularEdit").val();
										newConta.email = $("#emailEdit").val();
										
									var cfg = {
										type : "POST",
										url : "rest/simpleRest/editarFuncionario",
										data : newConta,
										success : function(data) {
											$(dialog).dialog("close");
											
											SENAI.funcionario.buscar();
										},
										error : function(err) {
											alert("Erro ao editar o Funcionário: "
													+ err.responseText);
										}
									};
									SENAI.ajax.post(cfg);
								},
								"Cancelar" : function() {
									$(this).dialog("close");
								}
							},
							close : function() {
							}
						};
						$("#editarFuncionario").dialog(cfg);
					};
	
SENAI.funcionario.buscarFuncionario = function(id){
					 
						var cfg = {
						type:"POST",
						url: "rest/simpleRest/buscarFuncionarioPeloId/"+id,
						success:function (conta){
						
							$("#mostrarNome").val(conta.nome);
							$("#mostrarEndereco").val(conta.endereco);
						    $("#mostrarNumero").val(conta.numero);
						    $("#mostrarBairro").val(conta.bairro);
						    $("#mostrarCidade").val(conta.cidade);
						    $("#mostrarEstado").val(conta.estado);
							$("#mostrarTelefone").val(conta.telefone);
							$("#mostrarCelular").val(conta.celular);
							$("#mostrarEmail").val(conta.email);
							$("#idFuncionarioEdit").val(conta.id);
						
							
						},
						error: function (err){
							alert("Erro ao editar o funcionario: " + err.responseText);
						}
				}
				SENAI.ajax.post(cfg);
						
						var cfg = {
								title : "Dados do Funcionário.",
								height : 400,
								width : 550,
								modal : true,
								buttons : {
									"Ok" : function() {
										$(this).dialog("close");
									}
								}
						};
						$("#mostrarFuncionario").dialog(cfg);
					}

		});// Fecha documento ready
