package br.com.musica.model.test;

import br.com.musica.base.test.BaseTest;
import br.com.musica.model.Musica;
import br.com.musica.model.Pessoa;
import br.com.musica.util.JPAUtil;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.AfterClass;
import org.junit.Test;

public class MusicaTest extends BaseTest {
	private static final String AUTOR = "Zeca pagodinho";
	
	@SuppressWarnings("unchecked")
	@Test
	public void consultarPorMusica(){
		salvarMusica();
		
		String filtro = "Zeca";
		Query query = em.createQuery("SELECT m.nomeMusica FROM Musica m WHERE m.autor LIKE :autor");
		query.setParameter("autor", "%" + filtro + "%");
		
		List<String> lista = query.getResultList();
		
		assertFalse("verifica se têm registros na lista", lista.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void consultarPorAutor(){
		salvarMusica();
		
		String filtro = "Quem é ela";
		Query query = em.createQuery("SELECT m.autor FROM Musica m WHERE m.nomeMusica LIKE :nomeMusica");
		query.setParameter("nomeMusica", "%" + filtro + "%");
		
		List<String> lista = query.getResultList();
		
		assertFalse("verifica se têm registros na lista", lista.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void consultarIdAutor(){
		salvarMusica();
		
		Query query = em.createQuery("SELECT m.id, m.autor FROM Musica m WHERE m.autor = :autor");
		query.setParameter("autor", AUTOR);
		
		List<Object[]> result = query.getResultList();
		assertFalse("verifica se têm registros na lista", result.isEmpty());
		
		for (Object[] linha : result) {
			assertTrue("verifica se o primeiro item é o id", linha[0] instanceof Integer);
			assertTrue("verifica se o segundo item é o nomeUsuario", linha[1] instanceof String);
			
			Pessoa usuario = new Pessoa((Integer) linha[0],(String) linha[1]);
			assertNotNull(usuario);
		}
	}

	@Test
	public void pesquisarMusica() {
		for (int i = 0; i < 10; i++) {
			salvarMusica();
		}

		TypedQuery<Musica> query = em.createQuery("SELECT m FROM Musica m", Musica.class);
		List<Musica> musicas = query.getResultList();

		assertFalse("deve ter encontrado uma musica", musicas.isEmpty());
		assertTrue("deve ter encontrado varias musicas", musicas.size() >= 10);
	}

	@Test
	public void alterarMusica() {
		salvarMusica();

		TypedQuery<Musica> query = em.createQuery("SELECT m FROM Musica m", Musica.class).setMaxResults(1);

		Musica musica = query.getSingleResult();

		assertNotNull("deve ter encontrado uma musica", musica);

		Integer versao = musica.getVersion();

		em.getTransaction().begin();

		musica.setNomeMusica("Ser mais feliz");

		musica = em.merge(musica);

		em.getTransaction().commit();

		assertNotEquals("deve ter versao incrementada", versao.intValue(), musica.getVersion().intValue());
	}

	@Test
	public void excluirMusica() {
		salvarMusica();
		salvarMusica();

		TypedQuery<Integer> query = em.createQuery("SELECT MAX(m.id) FROM Musica m", Integer.class);
		Integer id = query.getSingleResult();

		em.getTransaction().begin();

		Musica musica = em.find(Musica.class, id);
		em.remove(musica);

		em.getTransaction().commit();

		Musica musicaExcluida = em.find(Musica.class, id);

		assertNull("não deve ter encontrado a musica", musicaExcluida);
	}

	@Test
	public void salvarMusica() {
		Musica musica = new Musica().setAutor(AUTOR).setNomeMusica("Quem é ela");

		assertTrue("não deve ter ID definido", musica.isTransient());

		em.getTransaction().begin();

		em.persist(musica);

		em.getTransaction().commit();

		assertNotNull("deve ter ID definido", musica.getId());
	}

	@AfterClass
	public static void deveLimparBaseTeste() {
		EntityManager entityManager = JPAUtil.INSTANCE.getEntityManager();

		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery("DELETE FROM Musica m");
		int qtdRegistrosExcluidos = query.executeUpdate();

		entityManager.getTransaction().commit();

		assertTrue("certifica que a base foi limpada", qtdRegistrosExcluidos > 0);
	}
}
