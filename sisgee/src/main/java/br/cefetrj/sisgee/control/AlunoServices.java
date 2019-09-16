package br.cefetrj.sisgee.control;

import java.util.List;

import br.cefetrj.sisgee.model.dao.AlunoDAO;
import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.entity.Aluno;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Serviços de alunos. Trata a lógica de negócios
 * associada com a entidade Aluno.
 * 
 * @author Paulo Cantuária
 * @since 1.0
 */
public class AlunoServices {
	
	/**
	 * Recupera todos os alunos e retorna em uma lista.
	 * 
	 * @return lista com todos os alunos
	 */
	public static List<Aluno> listarAlunos(){
		GenericDAO<Aluno> alunoDao = PersistenceManager.createGenericDAO(Aluno.class);
		return alunoDao.buscarTodos();
	}
	
        /**
         * Método que busca um aluno
         * @param aluno
         * @return 
         */
	public static Aluno buscarAluno(Aluno aluno) {
		GenericDAO<Aluno> alunoDao = PersistenceManager.createGenericDAO(Aluno.class);
		return alunoDao.buscar(aluno.getIdAluno());
	}
	
        /**
         * Método que inclui um aluno
         * @param aluno 
         */
	public static void incluirAluno(Aluno aluno){
		GenericDAO<Aluno> alunoDao = PersistenceManager.createGenericDAO(Aluno.class);		
		PersistenceManager.getTransaction().begin();
		try{
			alunoDao.incluir(aluno);
			PersistenceManager.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			PersistenceManager.getTransaction().rollback();
		}
	}
	
        /**
         * Método que busca aluno pela matrícula
         * @param matricula
         * @return 
         */
	public static Aluno buscarAlunoByMatricula(String matricula) {
		AlunoDAO alunoDao = new AlunoDAO();
		try{
			Aluno a = alunoDao.buscarByMatricula(matricula);
			return a;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
        
        
        
        public static Aluno atualizaAlunoJson(String matricula) throws IOException{
            
            Aluno alunoBC = AlunoServices.buscarAlunoByMatricula(matricula.trim());
            
            JsonObject JO = buscarAlunoByMatriculaAPI(matricula.trim());
            
                if(!JO.getString("nome").equals("")  &&  alunoBC != null){
                    
                            String nomeJO = JO.getString("nome");
                            String cursoJO = JO.getString("curso");
                            String codCurJO = JO.getString("cod_curso");
                            String campusJO = JO.getString("campus") ;       
                    
                    alunoBC = alunoBC.atualizaAlunoSIE(alunoBC,nomeJO, cursoJO, codCurJO, campusJO);
                     
                    
                }else if(JO.getString("nome").equals("") && alunoBC != null ){
                    
                    alunoBC = null;
                }
          
            return alunoBC;
        }
        
        
	/**
     * Método que busca aluno pela matrícula
     * @param matricula
     * @return 
     * @throws java.io.IOException 
     */
	public static JsonObject buscarAlunoByMatriculaAPI(String matricula) throws IOException {
	    URL alunoGetRequest = new URL("https://gabrielpoyares.com.br/sie-api/alunos/detalhes/?token=CEFETALUNO2019&matricula="+matricula);

	    String readLine = null;

	    HttpURLConnection conection = (HttpURLConnection) alunoGetRequest.openConnection();

	    conection.setRequestMethod("GET");

	    int responseCode = conection.getResponseCode();

	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));

	        StringBuffer response = new StringBuffer();

	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
	        
	        JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
	        JsonObject o = jsonReader.readObject();
	        jsonReader.close();
	        
	        String firstName = o.getString("nome");
	        
	        System.out.println("JSON String Result " + response.toString());
	        System.out.println("JSON nome index has value " + firstName);
	        
	        return o;

	    } else {
	    	return null;
	    }
	}

}
