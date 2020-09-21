package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import helper.HelperEmpregado;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import suport.Request;
import uteis.ReportListener;

@Listeners({ ReportListener.class })
@Epic("Teste de API")
@Feature("Realiza a consulta, cadastro, altera��o e exclus�o dos empregados")
@Severity(SeverityLevel.TRIVIAL)
public class Empregado extends Request {

	private HelperEmpregado helper;
	private String empregadoId;

	@BeforeClass
	public void init() {
		new Request();
		helper = new HelperEmpregado();
		setBaseUri("https://inm-api-test.herokuapp.com/empregado/");
	}

	@Story("Dado que possuo acesso ao sistema, quando prencher os dados obrigat�rios corretos, ent�o o sistema realiza o cadastro do empregado")
	@Test(groups = { "Fluxo b�sico" })
	public void CEN01_cadastrarEmpregado() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			post(helper.gerarBodyCadastro().toString(), casoTeste.get("path"));
			validatorHeaders(response, "Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(response, casoTeste.get("statusCode"));
			empregadoId = response.jsonPath().getString("empregadoId");
			validatorBody(response, String.format(casoTeste.get("baseline").toString(),empregadoId));
		}
	}

	@Story("Dado que realizo o acesso ao sistema, ent�o o sistema retorna a lista de funcion�rios com sucesso")
	@Test(groups = { "Fluxo b�sico" })
	public void CEN02_recuperarTodosEmpregados() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			get(casoTeste.get("path"));
			validatorHeaders(response, "Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(response, casoTeste.get("statusCode"));
		}
	}

	@Story("Dado que realizo o acesso ao sistema, quando informe um c�digo de funcion�rio que exista na base de dados, ent�o o sistema retorna os detalhes do empregado")
	@Test(groups = { "Fluxo b�sico" })
	public void CEN03_recuperarEmpregadoId() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			get(casoTeste.get("path") + empregadoId);
			validatorHeaders(response, "Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(response, casoTeste.get("statusCode"));
			validatorBody(response, String.format(casoTeste.get("baseline").toString(),empregadoId));
		}
	}

	@Story("Dado que realizo o acesso ao sistema, quando alterar dos dados do empregado, ent�o o sistema realiza a atualiza��o com sucesso")
	@Test(groups = { "Fluxo b�sico" })
	public void CEN04_editarEmpregado() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			put(helper.gerarBodyAlteracao(), casoTeste.get("path") + empregadoId + "");
			validatorHeaders(response, "Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(response, casoTeste.get("statusCode"));
			validatorBody(response, String.format(casoTeste.get("baseline").toString(),empregadoId));
		}
	}

	@Story("Dado que realizo o acesso ao sistema, quando informar o c�digo do funcion�rio que exista na base de dados, ent�o o sistema realiza a exclus�o com sucesso e exibe uma mensagem")
	@Test(groups = { "Fluxo b�sico" })
	public void CEN05_excluirEmpregado() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			delete(casoTeste.get("path") + empregadoId + "");
			validatorHeaders(response, "Content-Type", casoTeste.get("contentType"));
			validatorBodyEmpty(response, casoTeste.get("baseline"));
			validatorStatusCode(response, casoTeste.get("statusCode"));
		}
	}
}
