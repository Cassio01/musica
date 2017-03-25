package br.com.musica.model.test;

import org.junit.Test;

import br.com.musica.base.test.BaseTest;
import br.com.musica.model.ListaDeMusicas;
import br.com.musica.model.Musica;
import br.com.musica.model.Pessoa;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

public class AlbumDeMusicas extends BaseTest {

	private static final String CPF_BASE = "999.999.999-99";

	// Inicio: Usando criteria para a entidade Pessoa

	@Test
	@SuppressWarnings("unchecked")
	public void consultarParteDoNomeDaPessoa() {
		salvarUsuarios(5);

		Criteria criteria = createCriteria(Pessoa.class, "p")
				.add(Restrictions.ilike("p.nomeUsuario", "ssio", MatchMode.ANYWHERE));

		List<Pessoa> usuarios = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		assertTrue("verifica se a quantidade de usuarios são pelo menos 5", usuarios.size() >= 5);

		usuarios.forEach(usuario -> assertFalse(usuario.isTransient()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void consultarPrimeiroNomeDaPessoa() {
		salvarUsuarios(5);

		Criteria criteria = createCriteria(Pessoa.class, "p")
				.add(Restrictions.ilike("p.nomeUsuario", "Cassio", MatchMode.START));

		List<Pessoa> usuarios = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		assertTrue("verifica se a quantidade de usuarios são pelo menos 5", usuarios.size() >= 5);

		usuarios.forEach(usuario -> assertFalse(usuario.isTransient()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void consultarUltimoNomeDaPessoa() {
		salvarUsuarios(5);

		Criteria criteria = createCriteria(Pessoa.class, "p")
				.add(Restrictions.ilike("p.nomeUsuario", "Alves", MatchMode.END));

		List<Pessoa> usuarios = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		assertTrue("verifica se a quantidade de usuarios são pelo menos 5", usuarios.size() >= 5);

		usuarios.forEach(usuario -> assertFalse(usuario.isTransient()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void consultarIdENomeDaPessoa() {
		salvarUsuarios(3);

		ProjectionList projection = Projections.projectionList().add(Projections.property("u.id").as("id"))
				.add(Projections.property("u.nomeUsuario").as("nomeUsuario"));

		Criteria criteria = createCriteria(Pessoa.class, "u").setProjection(projection);

		List<Object[]> usuarios = criteria.setResultTransformer(Criteria.PROJECTION).list();

		assertTrue("verifica se a quantidade de usuarios são pelo menos 3", usuarios.size() >= 3);

		usuarios.forEach(usuario -> {
			assertTrue("primeiro item deve ser o ID", usuario[0] instanceof Integer);
			assertTrue("primeiro item deve ser o nome da musica", usuario[1] instanceof String);
		});
	}

	// Fim: Usando criteria para a entidade Pessoa
	
	
	// Inicio: Usando criteria para a entidade Musica
	
	@Test
	@SuppressWarnings("unchecked")
	public void consultarNomeMusicasNoFim() {
		salvarMusicas(5);

		Criteria criteria = createCriteria(Musica.class, "m")
				.add(Restrictions.ilike("m.nomeMusica", "telecoteco", MatchMode.END));

		List<Musica> musicas = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		assertTrue("verifica se a quantidade de musicas são pelo menos 5", musicas.size() >= 5);

		musicas.forEach(musica -> assertFalse(musica.isTransient()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void consultarNomeMusicasNoInicio() {
		salvarMusicas(5);

		Criteria criteria = createCriteria(Musica.class, "m")
				.add(Restrictions.ilike("m.nomeMusica", "Maneco", MatchMode.START));

		List<Musica> musicas = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		assertTrue("verifica se a quantidade de musicas são pelo menos 5", musicas.size() >= 5);

		musicas.forEach(musica -> assertFalse(musica.isTransient()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void onsultarNomeMusicasQualquerParteDoNome() {
		salvarMusicas(5);
		

		Criteria criteria = createCriteria(Musica.class, "m")
				.add(Restrictions.ilike("m.nomeMusica", "coteco", MatchMode.ANYWHERE));

		List<Musica> musicas = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		assertTrue("verifica se a quantidade de musicas são pelo menos 5", musicas.size() >= 5);

		musicas.forEach(musica -> assertFalse(musica.isTransient()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void consultarIdENomeDaMusica() {
		salvarMusicas(3);

		ProjectionList projection = Projections.projectionList().add(Projections.property("m.id").as("id"))
				.add(Projections.property("m.nomeMusica").as("nomeMusica"));

		Criteria criteria = createCriteria(Musica.class, "m").setProjection(projection);

		List<Object[]> musicas = criteria.setResultTransformer(Criteria.PROJECTION).list();

		assertTrue("verifica se a quantidade são pelo menos 3", musicas.size() >= 1);

		musicas.forEach(musica -> {
			assertTrue("primeiro item deve ser o ID", musica[0] instanceof Integer);
			assertTrue("primeiro item deve ser o nome da musica", musica[1] instanceof String);
		});
	}
	
	// Fim: Usando criteria para a entidade Musica

	// inicio: Usando criteria para a entidade ListaDeMusicas
	@Test
	@SuppressWarnings("unchecked")
	public void onsultaListaDeMusicasPorNomeDaMusicaNoFim() {
		salvaListaDeMusicas(3);

		Criteria criteria = createCriteria(ListaDeMusicas.class, "l")
				.createAlias("l.musicas", "m", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.ilike("m.nomeMusica", "paragondê", MatchMode.END));

		List<ListaDeMusicas> lista = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		assertTrue("verifica se a lista de musicas é no minímo 1", lista.size() >= 1);

		lista.forEach(musica -> assertFalse(musica.isTransient()));
	}
	@Test
	@SuppressWarnings("unchecked")
	public void consultaListaDeMusicasPorNomeDaMusicaNoInicio() {
		salvaListaDeMusicas(3);
		
		Criteria criteria = createCriteria(ListaDeMusicas.class, "l")
				.createAlias("l.musicas", "m", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.ilike("m.nomeMusica", "jura", MatchMode.ANYWHERE));
		
		List<ListaDeMusicas> lista = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		assertTrue("verifica se a lista de musicas é no minímo 1", lista.size() >= 1);
		
		lista.forEach(musica -> assertFalse(musica.isTransient()));
	}
	@Test
	@SuppressWarnings("unchecked")
	public void consultaListaDeMusicasENomePessoaCasoExista() {
		salvaListaDeMusicas(3);
		
		Criteria criteria = createCriteria(ListaDeMusicas.class, "l")
				.createAlias("l.usuario", "u", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.like("u.nomeUsuario", "Cassio", MatchMode.START));
		
		List<ListaDeMusicas> lista = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		assertTrue("verifica se a lista de musicas é no minímo 1", lista.size() >= 1);
		
		lista.forEach(musica -> assertFalse(musica.isTransient()));
	}
	// Inicio: Usando criteria para a entidade ListaDeMusicas

	private void salvarUsuarios(int quantidade) {
		em.getTransaction().begin();

		for (int i = 0; i < quantidade; i++) {
			Pessoa usuario = new Pessoa().setNomeUsuario("Cassio Alves").setCpf(CPF_BASE);
			em.persist(usuario);
		}

		em.getTransaction().commit();
	}

	private void salvarMusicas(int quantidade) {
		em.getTransaction().begin();

		for (int i = 0; i < quantidade; i++) {
			Musica musica = new Musica().setAutor("Zeca").setNomeMusica("Maneco telecoteco");
			em.persist(musica);
		}

		em.getTransaction().commit();
	}

	private void salvaListaDeMusicas(int quantidade) {
		em.getTransaction().begin();

		for (int i = 0; i < quantidade; i++) {
			ListaDeMusicas lista = criarLista();
			lista.getMusicas().add(criarMusica("Pericles", "paragondê"));
			lista.getMusicas().add(criarMusica("Zeca", "Jura"));
			lista.getMusicas().add(criarMusica("Zeca", "Maneco telecoteco"));

			em.persist(lista);
		}

		em.getTransaction().commit();
	}

	private Musica criarMusica(String autor, String nomeMusica) {
		return new Musica().setAutor(autor).setNomeMusica(nomeMusica);
	}

	private ListaDeMusicas criarLista() {
		return criarLista(null);
	}

	private ListaDeMusicas criarLista(String cpf) {
		Pessoa usuario = new Pessoa().setNomeUsuario("Cassio").setCpf(cpf == null ? CPF_BASE : cpf);

		return new ListaDeMusicas().setUsuario(usuario);
	}
}
