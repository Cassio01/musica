package br.com.musica.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class ListaDeMusicas extends BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_lista")
	private Integer id;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa", insertable = true, updatable = false, nullable = false)
	private Pessoa usuario;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "lista_musicas", joinColumns = @JoinColumn(name = "id_lista"), inverseJoinColumns = @JoinColumn(name = "id_musica"))
	private List<Musica> musicas;

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public List<Musica> getMusicas() {
		if (musicas == null) {
			musicas = new ArrayList<Musica>();
		}
		return musicas;
	}

	public Pessoa getUsuario() {
		return usuario;
	}

	public ListaDeMusicas setUsuario(Pessoa usuario) {
		this.usuario = usuario;
		return this;
	}
}
