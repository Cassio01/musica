package br.com.musica.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class Pessoa extends BaseEntity<Integer>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="id_pessoa")
	private Integer id;

	@Version
	private Integer version;
	
	@Column(name = "cpf_pessoa", length = 17)
	private String cpf;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<ListaDeMusicas> lista;

	@Column(name = "nome_usuario", nullable = false, length = 50)
	private String nomeUsuario;
	public Pessoa() {
		
	}

	public Pessoa(Integer id, String nomeUsuario) {
		super();
		this.id = id;
		this.nomeUsuario = nomeUsuario;
	}

	public Pessoa(String nomeUsuario) {
		super();
		this.nomeUsuario = nomeUsuario;
	}


	public Pessoa(Integer id) {
		super();
		this.id = id;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public Pessoa setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
		return this;
	}

	public String getCpf() {
		return cpf;
	}

	public Pessoa setCpf(String cpf) {
		this.cpf = cpf;
		return this;
	}
}
