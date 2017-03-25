package br.com.musica.util.test;

import org.junit.Test;
import static org.junit.Assert.*;

import br.com.musica.base.test.BaseTest;

public class JPAUtilTest extends BaseTest {

	@Test
	public void instanciaDoEntityManager() {
		assertNotNull("O entity manage n�o deve estar nulo", em);
	}

	@Test
	public void fechaEntityManager() {
		em.close();

		assertFalse("inst�ncia do EntityManager deve estar fechada", em.isOpen());
	}

	@Test
	public void abriUmaTransacao() {
		assertFalse("transa��o deve estar fechada", em.getTransaction().isActive());

		em.getTransaction().begin();

		assertTrue("transa��o deve estar aberta", em.getTransaction().isActive());
	}

}
