package br.cefetrj.sisgee.control;

import java.util.List;

import br.cefetrj.sisgee.model.dao.GenericDAO;
import br.cefetrj.sisgee.model.dao.PersistenceManager;
import br.cefetrj.sisgee.model.entity.ProfessorOrientador;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Serviços de Professores. Trata a lógica de negócios
 * associada com a entidade ProfessorOrientador.
 * 
 * @author Paulo Cantuária
 * @since 1.0
 */
public class ProfessorOrientadorServices {
	
	/**
	 * Recupera todos os professores e retorna em uma lista.
	 * 
	 * @return lista com todos os professores
	 * 
	 */
	public static List<ProfessorOrientador> listarProfessorOrientador() throws IOException{
		GenericDAO<ProfessorOrientador> professorOrientadorDao = PersistenceManager.createGenericDAO(ProfessorOrientador.class);
                
                atualizaListaProfessor();
                       
                List<ProfessorOrientador> listProf = professorOrientadorDao.buscarTodos();
                
                Collections.sort(listProf);
                
		return  listProf;
	}
        
        public static void atualizaListaProfessor() throws IOException{
            
                GenericDAO<ProfessorOrientador> professorOrientadorDao = PersistenceManager.createGenericDAO(ProfessorOrientador.class);
            
                List<ProfessorOrientador> listProfAux = professorOrientadorDao.buscarTodos();
                List<ProfessorOrientador> listProfAPI = new ArrayList<ProfessorOrientador>();
            
            JsonArray  arrayJo = listarProfessorOrientadorAPI();
                   
                
                for (int i=0;i<arrayJo.size();i++) {
				JsonObject item = arrayJo.getJsonObject(i);
				String nome = item.getString("nome");
				String siape = item.getString("siape");
                                ProfessorOrientador Prof = new ProfessorOrientador();
                                Prof.setNomeProfessorOrientador(nome);
                                listProfAPI.add(Prof);
			}
                
                //listProfAPI.get(0)
                
                boolean novo = true;
                
                for(ProfessorOrientador ProfApi: listProfAPI){
                    novo = true;
                    System.out.println("ET1");
                    
                    for(ProfessorOrientador profBanc: listProfAux){
                        if(profBanc.getNomeProfessorOrientador().equalsIgnoreCase(ProfApi.getNomeProfessorOrientador())){
                            novo = false;
                            System.out.println(ProfApi.getNomeProfessorOrientador());
                            System.out.println(profBanc.getNomeProfessorOrientador());
                        }
                    }
                    
                    if(novo){
                        incluirProfessorOrientador(ProfApi);
                        System.out.println("ET33333333");
   
                 }
                }
        }
                /*
                for(ProfessorOrientador Pr: listProfAux ){
                    
                    ProfessorOrientador auxi = null;
                    
                    for(ProfessorOrientador prAp: listProfAPI){
                        igual = false;
                        auxi = prAp;
                        if((Pr.getNomeProfessorOrientador()).equalsIgnoreCase(prAp.getNomeProfessorOrientador())){
                            igual = true;                        
                        }
                        if(igual = false){
                            System.out.println("??????");
                            String nomeA = auxi.getNomeProfessorOrientador();
                            ProfessorOrientador p = new ProfessorOrientador();
                            p.setNomeProfessorOrientador(nomeA);
                            incluirProfessorOrientador(p);
                        }    
                        
                              
                    }*/
                    
                      
               
    
	
        /**
         * Método para buscar um professor.
         * 
         * @param professorOrientador
         * 
         * @return id do professor
         * 
         */
	public static ProfessorOrientador buscarProfessorOrientador(ProfessorOrientador professorOrientador) {
		GenericDAO<ProfessorOrientador> professorOrientadorDao = PersistenceManager.createGenericDAO(ProfessorOrientador.class);
		return professorOrientadorDao.buscar(professorOrientador.getIdProfessorOrientador());
	}
	
        /**
         * Método para incluir um professor.
         * 
         * @param professorOrientador
         * 
         *  return
         *  
         */
	public static void incluirProfessorOrientador(ProfessorOrientador professorOrientador){
		GenericDAO<ProfessorOrientador> professorOrientadorDao = PersistenceManager.createGenericDAO(ProfessorOrientador.class);
		PersistenceManager.getTransaction().begin();
		try{
			professorOrientadorDao.incluir(professorOrientador);
			PersistenceManager.getTransaction().commit();
                        System.out.println("INCLUIU?");
		}catch(Exception e){
                        System.out.println("DEU ERRO AO INCLUIR");
			PersistenceManager.getTransaction().rollback();
		}
	}
        
        /**
	 * Recupera todos os professores da API do SIE e retorna em uma lista.
	 * 
	 * @return lista com todos os professores
     * @throws java.io.IOException
	 */
	public static JsonArray listarProfessorOrientadorAPI() throws IOException {
		URL alunoGetRequest = new URL("https://gabrielpoyares.com.br/sie-api/professores/listar/?token=CEFETALUNO2019");

	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) alunoGetRequest.openConnection();
	    conection.setRequestMethod("GET");
	    int responseCode = conection.getResponseCode();

	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader( new InputStreamReader( conection.getInputStream()) );

	        StringBuffer response = new StringBuffer();

	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
		
			JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
			JsonArray a = jsonReader.readArray();
			jsonReader.close();
			
			for (int i=0;i<a.size();i++) {
				JsonObject item = a.getJsonObject(i);
				String nome = item.getString("nome");
				String siape = item.getString("siape");
			}
			
			return a;
			
	    } else {
	    	return null;
	    }
	}
	
	

}
