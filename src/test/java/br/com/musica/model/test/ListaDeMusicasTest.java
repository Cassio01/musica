package br.com.musica.model.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.Test;

import br.com.musica.base.test.BaseTest;
import br.com.musica.model.ListaDeMusicas;
import br.com.musica.model.Musica;
import br.com.musica.model.Pessoa;
import br.com.musica.util.JPAUtil;

public class ListaDeMusicasTest extends BaseTest {
	
	@Test
	public void totalDeMusicas() {
		ListaDeMusicas lista = criarLista();
		
		for (int i = 0; i < 10; i++) {
			lista.getMusicas().add(criarMusica("Nome da musica " + i));
		}
		
		em.getTransaction().begin();
		em.persist(lista);
		em.getTransaction().commit();
		
		assertFalse("deve ter persistido a musica", lista.isTransient());
		
		int qtdMusicasAdiconadas = lista.getMusicas().size();
		
		assertTrue("musicas adicionadas", qtdMusicasAdiconadas > 0);
		
		
	}

	@AfterClass
	public static void deveLimparBaseTeste() {
		EntityManager entityManager = JPAUtil.INSTANCE.getEntityManager();
		
		entityManager.getTransaction().begin();
		
		Query query = entityManager.createQuery("DELETE FROM ListaDeMusicas l");
		int qtdRegistrosExcluidos = query.executeUpdate();
		
		entityManager.getTransaction().commit();

		assertTrue("certifica que a base foi limpada", qtdRegistrosExcluidos > 0);
		
	}
	
	

	private Musica criarMusica(String nomeMusica){
		return new Musica().setNomeMusica(nomeMusica);
	}
	
	private ListaDeMusicas criarLista(){
		Pessoa usuario = new Pessoa().setNomeUsuario("Cássio");
		
		assertTrue("não tem id definido", usuario.isTransient());
		
		return new ListaDeMusicas().setUsuario(usuario);
	}
}
