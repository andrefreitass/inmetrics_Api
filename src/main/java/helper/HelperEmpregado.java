package helper;

import java.util.HashMap;

import org.json.simple.JSONObject;

import uteis.Datapool;

public class HelperEmpregado {

	public Datapool dp;
	private HashMap<String, String> cadastro,alteracao;
	
	@SuppressWarnings("unchecked")
	public HelperEmpregado() {
		dp = new Datapool("empregado.json");
		cadastro = (HashMap<String, String>) dp.getContent("BodyCadastro");
		alteracao = (HashMap<String, String>) dp.getContent("BodyAlteracao");

	}
	
	
	@SuppressWarnings({"unchecked" })
	public JSONObject gerarBodyCadastro() {
		JSONObject empregadoJson = new JSONObject();
		empregadoJson.put("nome", cadastro.get("nome"));
		empregadoJson.put("cpf", cadastro.get("cpf"));
		empregadoJson.put("admissao", cadastro.get("admissao"));
		empregadoJson.put("cargo", cadastro.get("cargo"));
		empregadoJson.put("comissao", cadastro.get("comissao"));
		empregadoJson.put("salario", cadastro.get("salario"));
		empregadoJson.put("departamentoId", cadastro.get("departamentoId"));
		empregadoJson.put("sexo", cadastro.get("sexo"));
		empregadoJson.put("tipoContratacao", cadastro.get("tipoContratacao"));
		return empregadoJson;
	}
	
	
	@SuppressWarnings({"unchecked" })
	public JSONObject gerarBodyAlteracao() {
		JSONObject empregadoJson = new JSONObject();
		empregadoJson.put("nome", alteracao.get("nome"));
		empregadoJson.put("cpf", alteracao.get("cpf"));
		empregadoJson.put("admissao", alteracao.get("admissao"));
		empregadoJson.put("cargo", alteracao.get("cargo"));
		empregadoJson.put("comissao", alteracao.get("comissao"));
		empregadoJson.put("salario", alteracao.get("salario"));
		empregadoJson.put("departamentoId", alteracao.get("departamentoId"));
		empregadoJson.put("sexo", alteracao.get("sexo"));
		empregadoJson.put("tipoContratacao", alteracao.get("tipoContratacao"));
		return empregadoJson;
	}
}
