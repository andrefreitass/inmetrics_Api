package uteis;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import io.restassured.path.json.JsonPath;

/**
 * Classe para obter massa de testes de arquivos JSON
 */
public class Datapool {
	private String localArquivo = null;
	public ThreadLocal<JSONArray> dpFull = new ThreadLocal<JSONArray>();
	public ThreadLocal<JsonPath> dpJsonPath = new ThreadLocal<JsonPath>();
	static String cenario = null;

	/**
	 * construtor para obter dados de arquivos json ou xls.
	 * @param localOrFile String - pode ser como parametro o path do arquivo ou
	 *                    somente o nome, sera realizada uma busca pelo arquivo
	 *                    dentro do diretorio do projeto. <strong>Exemplo: file.txt</strong>
	 */
	public Datapool(String localOrFile) {
		if (new File(localOrFile).isAbsolute() || localOrFile.contains("/") || localOrFile.contains("\\")) {
			this.localArquivo = localOrFile;
		} else {
			this.localArquivo = new Diretorios().getPathArquivo(localOrFile);
		}
		if (localOrFile.contains(".json"))
			getDatapoolJson();
	}


	/**
	 * Metodo para carregar arquivo Json e armazenar lista de cen�rios
	 * @dpFull Variavel definida com execu��o do m�todo
	 */

	public void getDatapoolJson() {
		try {
			dpFull.set((JSONArray) new JSONParser().parse(new FileReader(localArquivo)));
			dpJsonPath.set(JsonPath.with(new FileReader(localArquivo)));
		} catch (Exception e) {
			throw new AutomacaoBusinessException("Problema encontrado ao realizar parse de arquivo para JSON", e);
		}
	}

	/**
	 * Metodo para obter caso de teste, com base no ID declarado no Json e nome do
	 * metodo de testes
	 * @return Objeto Json iteravel
	 */
	public ArrayList<JsonPath>  getTestCasesToJsonPath() {
		ArrayList<JsonPath> list = new ArrayList<>();
		for (Object object : dpFull.get()) {
			if (ReportListener.getCenario().contains(((JSONObject) object).get("id").toString())) {
				for (Object json : ((JSONArray) ((JSONObject) object).get("casosTestes"))) {
					list.add(JsonPath.from(json.toString()));
				}
				return list;
			}
		}
		return null;
	}
	
	public Object getContent(String chave) {
		try {
			for (Object object : dpFull.get()) {
				if (object.toString().contains(chave)) {
					return ((JSONObject) object).get(chave);
				}
			}
			return null;
		} catch (Exception e) {
			throw new AutomacaoBusinessException("Problema encontrado ao obter objeto Json", e);
		}
	}
}
