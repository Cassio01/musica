P: Qual a responsabilidade / objeto das anotações.
1.	@MappedSuperclass
Uma classe anotada com @MappedSuperclass significa que não é uma entidade e não pode ser anotada com @Entity ou
@Table, e não pode ser persistida no banco. Não pode ser consultada através de JPQL/HQL e também é uma boa pratica deixa-la 
como classe abstrata.

2.	@Version
Usado para mapear atributo de versionamento.

3.	@Entity
Uma classe anotada com @Entity refletirá as informações no banco de dados, sendo obrigatório o uso do @Id e de um construtor
sem argumentos.

4.	@Table
Especifica a tabela primaria para a classe anotada. Se a entidade não foi anota com @Table, a mesma receberá os valores padrão.

5.	@Id
O atributo mapeado com @Id ser para definir a chave primaria da tabela.

6.	@GeneratedValue
Utilizado para definir qual o tipo de estratégia para gerar a chave primaria. E é usado junto com o @Id.

7.	@Column
Define um relacionamento direto entre a classe e o banco de dados, alterando o valor padrão da JPA.

8.	@Basic
O tipo mais simples de mapeamento para uma coluna de banco de dados, podendo especificar se o atributo pode ser NULL
ou NOTNULL e também para informa o qual o tipo de carregamento do atributo se é LAZY ou EAGER.

9.	@Temporal
Utilizado para mapear campo do tipo Time, Date e DateTime.

10.	@ManyToOne
Especifica um relacionamento de valor único para outra entidade que têm vários valores.


11.	@ManyToMany
Quando a entidade tem um relacionamento unidirecional, uma das entidades precisa informar qual é o lado forte do relacionamento com
anotação @JoinTable e a outra entidade tem que estar anotada com mappedBy passando o atributo mapeado da classe forte.

12.	@OneToOne
Especifica um relacionamento de um-para-um.

13.	@JoinColumn
Especifica uma coluna para unir um relacionamento.

14.	@JoinTable
É usado no mapeamento de associações um-para-muitos e unidirecionais, e também muitos-para-muitos.

P: Qual a responsabilidade / objeto dos métodos do EntityManager:
1.	isOpen
Verifica se a transação estar aberta.

2.	close
Fecha uma transação do aberta.

3.	createQuery
Usado para criar uma consulta JPQL.

4.	find
Pesquisa pela chave primaria.

5.	merge
Faz alterações no objeto e salva, mantendo o mesmo id.

6.	persist
Salva o objeto pela primeira vez e cria um novo id.

7.	remove
Usado para remover o objeto.

P: Como instanciar o Criteria do Hibernate através do EntityManager?
	Tem que fazer os imports do pacote org.hibernate.Criteria;

	protected EntityManager em;
	
	public Session getSession() {
		return (Session) em.getDelegate();
	}
	
	public Criteria createCriteria(Class<?> clazz) {
		return getSession().createCriteria(clazz);
	}
	
	public Criteria createCriteria(Class<?> clazz, String alias) {
		return getSession().createCriteria(clazz, alias);
	}
	public void instanciarEntityManager() {
		em = JPAUtil.INSTANCE.getEntityManager();
	}
	Aqui você escolhe para qual classe quer usar:
  Criteria criteria = createCriteria(SuaClasse.class);
  Também pode dar um apelido para ela:
  Criteria criteria = createCriteria(SuaClasse.class, " apelido ");

P: Como abrir uma transação?
	em.getTransaction().begin();
  
P: Como fechar uma transação?
	if (em.isOpen())  {
		em.close();
	}
  
P: Como criar e executar uma Query com jpql?
Query query = entityManager.createQuery("DELETE FROM Pessoa p");
query.executeUpdate();

P: Qual a responsabilidade dos valores FetchType.LAZY e FetchType.EAGER?
	FetchType.LAZY: O atributo é carregado quando solicitado através deste tipo de valor;
	FetchType.EAGER: É o padrão dos atributos da entidade, mesmo que não tenha explicitado este valor ele irá trazer todos os dados
  do atributo no banco de dados.

P: Qual a responsabilidade dos valores CascadeType.PERSIST e CascadeType.REMOVE?
	CascadeType.PERSIST: Persiste as informações no banco de dados em cascata com as entidades associadas.
	CascadeType.REMOVE: Remove as informações do banco de dados em cascata com as entidades associadas, tem que tomar bastante cuidado
  ao utiliza-lo.

P: Como fazer uma operação do BATCH (DELETE ou UPDATE) através do EntityManager?
	Esta operação é bastante útil para não sobrecarregar a memória, fazendo operações de DELETE e UPDATE, a cada determinada quantidade
  de requisição se faz um flush e um clear para atualizar e limpar a memória. Deixando o sistema mais rápido.

P: Qual a explicação do exception LazyInitializationException?
	Esta exceção ocorre quando se tenta fazer uma consulta tardia no banco de dados, quando a conexão com o banco já se fechou.




