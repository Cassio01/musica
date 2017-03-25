package br.com.musica.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Musica extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_musica", unique = true)
	private Integer id;

	@Version
	private Integer version;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "autor_musica", length = 50)
	private String autor;

	@Column(name = "nome_musica", nullable = false, length = 50)
	private String nomeMusica;

	public Musica(String autor, String nomeMusica) {
		super();
		this.autor = autor;
		this.nomeMusica = nomeMusica;
	}


	public Musica() {
	}

	public Musica(Integer id) {
		super();
		this.id = id;
	}

	public Musica(Integer id, String autor) {
		super();
		this.id = id;
		this.autor = autor;
	}

	public Musica(String autor) {
		super();
		this.autor = autor;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getAutor() {
		return autor;
	}

	public Musica setAutor(String autor) {
		this.autor = autor;
		return this;
	}

	public String getNomeMusica() {
		return nomeMusica;
	}

	public Musica setNomeMusica(String nomeMusica) {
		this.nomeMusica = nomeMusica;
		return this;
	}

	public Integer getVersion() {
		return version;
	}
}
