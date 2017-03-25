package br.com.musica.model.test;

import br.com.musica.base.test.BaseTest;
import br.com.musica.model.Pessoa;
import br.com.musica.util.JPAUtil;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.AfterClass;
import org.junit.Test;

public class PessoaTest extends BaseTest {
	
	private static final String CPF_BASE = "999.999.999-99";
	
	@SuppressWarnings("unchecked")
	@Test
	public void consultarIdNome(){
		salvarUsuario();
		
		Query query = em.createQuery("SELECT p.id, p.nomeUsuario FROM Pessoa p WHERE p.cpf = :cpf");
		query.setParameter("cpf", CPF_BASE);
		
		List<Object[]> result = query.getResultList();
		assertFalse("verifica se têm registros na lista", result.isEmpty());
		
		for (Object[] linha : result) {
			assertTrue("verifica se o primeiro item é o id", linha[0] instanceof Integer);
			assertTrue("verifica se o segundo item é o nomeUsuario", linha[1] instanceof String);
			
			Pessoa usuario = new Pessoa((Integer) linha[0],(String) linha[1]);
			assertNotNull(usuario);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void consultarPorCpf(){
		salvarUsuario();
		salvarUsuario();
		String filtro = "Cassio";
		Query query = em.createQuery("SELECT p.cpf FROM Pessoa p WHERE p.nomeUsuario LIKE :nomeUsuario");
		query.setParameter("nomeUsuario", "%" + filtro + "%");
		
		List<String> lista = query.getResultList();
		
		assertFalse("verifica se têm registros na lista", lista.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void consultarPessoaComIdNome() {
		salvarUsuario();
		
		Query query = em.createQuery("SELECT new Pessoa(p.id, p.nomeUsuario) FROM Pessoa p WHERE p.cpf = :cpf");
		query.setParameter("cpf", CPF_BASE);
		
		List<Pessoa> usuarios = query.getResultList();
		
		assertFalse("verifica se há registro na lista", usuarios.isEmpty());
		
		usuarios.forEach(usuario -> {
			assertNull("verifica se o cpf estar null", usuario.getCpf());
			usuario.setCpf(CPF_BASE);
		});

	}

	@Test
	public void pesquisarUsuario() {
		for (int i = 0; i < 10; i++) {
			salvarUsuario();
		}

		TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class);
		List<Pessoa> usuario = query.getResultList();

		assertFalse("deve ter encontrado um usuario", usuario.isEmpty());
		assertTrue("deve ter encontrado vários usuarios", usuario.size() >= 10);
	}

	@Test
	public void alterarUsuario() {
		salvarUsuario();

		TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).setMaxResults(1);

		Pessoa usuario = query.getSingleResult();

		assertNotNull("deve ter encontrado um usuario", usuario);

		Integer versao = usuario.getVersion();

		em.getTransaction().begin();

		usuario.setNomeUsuario("Cassio Alves");

		usuario = em.merge(usuario);

		em.getTransaction().commit();

		assertNotEquals("deve ter versao incrementada", versao.intValue(), usuario.getVersion().intValue());
	}

	@Test
	public void excluirUsuario() {
		salvarUsuario();

		TypedQuery<Integer> query = em.createQuery("SELECT MAX(p.id) FROM Pessoa p", Integer.class);
		Integer id = query.getSingleResult();

		em.getTransaction().begin();

		Pessoa usuario = em.find(Pessoa.class, id);
		em.remove(usuario);

		em.getTransaction().commit();

		Pessoa usuarioExcluido = em.find(Pessoa.class, id);

		assertNull("não deve ter encontrado um usuario", usuarioExcluido);
	}

	@Test
	public void salvarUsuario() {
		Pessoa usuario = new Pessoa().setNomeUsuario("Cassio").setCpf(CPF_BASE);

		assertTrue("não deve ter ID definido", usuario.isTransient());

		em.getTransaction().begin();

		em.persist(usuario);

		em.getTransaction().commit();

		assertFalse("tem id definido", usuario.isTransient());

	}

	@AfterClass
	public static void limparBaseTeste() {
		EntityManager entityManager = JPAUtil.INSTANCE.getEntityManager();

		entityManager.getTransaction().begin();

		Query query = entityManager.createQuery("DELETE FROM Pessoa p");
		int qtdRegistrosExcluidos = query.executeUpdate();

		entityManager.getTransaction().commit();

		assertTrue("certifica que a base foi limpada", qtdRegistrosExcluidos > 0);
	}
}
