# Hibernate-Learnings
All the Topics of Hibernate framework (Object Relational Mapping)


HIBERNATE - BEST ORM FRAMEWORK (OBJECT RELATIONAL MAPPING)
		-> This framework is bascially used for mapping objects into particular table inside database using orm functionality;
		Internal steps to make sure HIBERNATE work.
		
		Alien alien = new Alien();        // this is the Entity that is mapped to database.
    	alien.setId(102);
    	alien.setName("Rangoli");
    	alien.setColor("red");
		
		
		1. Configuration con = new Configuration().configure().addAnnotatedClass(Alien.class);
		2. SessionFactory sf = con.buildSessionFactory();
		3. Session session = sf.openSession();
		4. Transaction tn = session.beginTransaction();
		5. session.save(tn);
		6. tn.commit();
		
	-> this is how hibernate works internally by using "hibernate.cfg.xml" file if we intensionally want to change the name we need add the name in configure() method in Configuration (Configuration con = new Configuration().configure("hibernate.xml").addAnnotatedClass(Alien.class))
	
	-> How "hibernate.cfg.xml" file looks and different properties =>
	
			<?xml version="1.0" encoding="UTF-8"?>
		<!DOCTYPE hibernate-configuration PUBLIC
			"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
			"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
		<hibernate-configuration>
			<session-factory>
				<!-- MySQL database connection settings -->
				<property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
				<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/practice</property>
				<property name="hibernate.connection.username">hcn</property>
				<property name="hibernate.connection.password">admin@123</property>
				
				<!-- Hibernate dialect -->
				<property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

				<!-- Other optional Hibernate properties -->
				<property name="hibernate.show_sql">true</property>
				<property name="hibernate.format_sql">true</property>
				<property name="hibernate.hbm2ddl.auto">update</property>
				
			</session-factory>
		</hibernate-configuration>
		
		Properties : 
		
		1. show_sql : true -> means we want to see what are the tables being created or updated or deleted inside the console
		2. format_sql : true -> format the hibernate query into a more readable way.
		3. i). hbm2ddl.auto : update -> everytime we run the application if the table exists if there are any entity level changes the table will be updated.
			ii). hbm2ddl.auto : create -> everytime we run the application the table will be created again & again and the existing data will be lost. so always we need to use update.
			
			
	Annotations:
	
	Example: 
	
		@Entity
		@Table
		public class Alien {}      -> By default if we dont mention any name the hibernate uses class name as the table name.
		
		(i).	@Entity without name:
				JPQL: Uses the class name (Alien).
				Table: Uses the class name (converted to lowercase in most cases, e.g., alien).
		
		
		(ii).  @Entity(name = "ALIEN")
				@Table
				public class Alien {}   -> name on @Entity is used for writing queries in jpql and not used by database tables and uses class name;
				
				@Entity(name = "ALIEN"):
				JPQL: Uses the specified name (ALIEN).
				Table: Still uses the class name (Alien or alien).
		
		
		(iii).	@Entity
				@Table(name = "ALIEN")
				public class Alien {}   -> name on @Table is used by database table and is widely used and for jpql we need to use class name;
				
				@Table(name = "ALIEN"):
				JPQL: Uses the class name (Alien).
				Table: Uses the specified table name (ALIEN).
				
	@Transient -> this annotation is used to hide the particular variable from to not create in database;
	@Embeddable  -> this annotation is used to reference another class whose reference is in main class 
		Example : private FullName fullname;  (main class)
		
				@Embeddable
				public class FullName {           -> child class whose reference resides in main class (Alien)
					private String first_name;
					private String last_name;
				}
				
	
	Mappings -> 4 types (i). one to one, (ii). one to many, (iii). many to one, (iv). many to many.
	
	@OneToOne -> This is a one-to-one relationship between two entities. In the database, one row in the first table corresponds to exactly one row in the second  table. The foreign key is typically stored in one of the tables (the parent table).
	
		Example : -> Here we referenced Student and Laptop where student having laptop object with onetoone relation to referene laptop class.
		
			@Entity
			public class Student {
				
				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				@Column(name="ID")
				private Integer id;
				
				private String name;
				private Integer marks;
				
				@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
				@JoinColumn(name = "LAPTOP_ID", referencedColumnName = "ID")
				private Laptop laptop;
			}
	
	
	@OneToMany -> In this relationship, one entity (parent) can be related to multiple instances of another entity (child). The foreign key is stored in the child table to reference the parent.
	
		Example : -> One Student can have multiple Laptops, and the foreign key STUDENT_ID is stored in the Laptop table to establish the relationship.
		
			@Entity
			public class Student {
				
				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				@Column(name="ID")
				private Integer id;
				
				private String name;
				private Integer marks;
				
				@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
				@JoinColumn(name = "STUDENT_ID")
				private List<Laptop> laptop = new ArrayList<>();
			}
	
	@ManyToOne  -> This is the reverse of the One-to-Many relationship. Many child entities are related to one parent entity. The foreign key is stored in the child table.
	
		Example : -> Many Laptop entities can be associated with one Student, and the foreign key STUDENT_ID is stored in the Laptop table.
		
			@Entity
			public class Student {

				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				@Column(name="ID")
				private Integer id;
				
				private String name;
				private Integer marks;

				@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
				private List<Laptop> laptops = new ArrayList<>();
			}
		
			@Entity
			public class Laptop {

				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				@Column(name="ID")
				private Integer id;
				
				private String model;
				
				@ManyToOne
				@JoinColumn(name = "STUDENT_ID")  // Foreign key in the "Laptop" table refers to "Student" ID
				private Student student;
			}

			
			
	@ManyToMany  -> Here we will be having a seperate table to store data from both the entities for a many to many relation.
	
		Example : -> Here we referenced Student and Laptop where student having laptop object with onetoone relation to referene laptop class.
		
			@Entity
			public class Student {
				
				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				@Column(name="ID")
				private Integer id;
				
				private String name;
				private Integer marks;
				
				@ManyToMany(cascade = CascadeType.ALL)
				@JoinTable(name = "student_laptop", 
					joinColumns = @JoinColumn(name = "student_id"), 
					inverseJoinColumns = @JoinColumn(name = "laptop_id"))
				private List<Laptop> laptop = new ArrayList<>();
			}
			
			@Entity
			public class Laptop {

				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				@Column(name="ID")
				private Integer id;
				
				private String model;
				
				@ManyToMany(mappedBy = "laptop", cascade = CascadeType.ALL)
				private List<Student> student = new ArrayList<>();
			}
			
	EAGER LOADING AND LAZY LOADING : By default the hibernate fires query in lazy loaded manner and we need to explicitly need to make the hibernate eager load while fetching data.
		(i). to make the hibernate to eager load the data we can use fetch=FetchType.EAGER.
		(ii). by default Hibernate is lazy loaded because it things we only want data from one single table not from child table so it just queries to lazy load the data.
		
		Example : -> One Student can have multiple Laptops, and the foreign key STUDENT_ID is stored in the Laptop table to establish the relationship. so by using fetchType eager we can make sure that the parent table data and child table data is retrived from the database using hibernate.
		
			@Entity
			public class Student {
				
				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				@Column(name="ID")
				private Integer id;
				
				private String name;
				private Integer marks;
				
				@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
				@JoinColumn(name = "STUDENT_ID")
				private List<Laptop> laptop = new ArrayList<>();
			}
			
			
			
	HIBERNATE CACHING: -> Hibernate provides several caching mechanisms to optimize the database by reducing the number of database queries. there are two levels of caching in hibernate. (i). First-Level cache (session cache), (ii). Second-Level cache (SessionFactory cache), (iii). quere cache.

	1. First-Level cache (session cache) -> This is a cache technique which is enabled by default and cannot disable this cache. In this caching technique when inside an api call which we can take it as session when opened to make transactions when the api is fetching the data from same entity the data will be cached inside the session and that cache is until the session is closed. In between the session if the same entity is called multiple times hibernate will not query the database and returns data from the stored cache from that session.
		(a). Transaction-scoped.
		(b). Automatically managed by Hibernate.
		(c). Cannot be disabled.
		
	2. Second-Level Cache (SessionFactory cache)  -> Second level cache technique is technique in which this cache is available for different sessions in the application until or unless the data is updated and we need to set that using @cache(usage = CacheConcurrencyStrategy.READ_WRITE) with this when the cache data is overwritten in the database then cache is marked as needing to be refreshed and this cache is not available until cached again or queried to database for updated data.
	-> This is a Entity level cache so only entities marked with @Cacheable and @Cache(usage=CacheConcurrencyStrategy.READ_WRITE) can be cached to SessionFactory.
	
		(a). SessionFactory-scoped.
		(b). Can be shared across sessions.
		(c). Helps to avoid repeated queries across multiple transactions.
		(d). Must be explicitly configured.
		
	1. READ_ONLY:
		Best for data that doesn't change (e.g., reference or lookup data).
		Suitable for highly read-intensive scenarios.
		Updates to cached entities are not allowed.
		
	2. READ_WRITE:
		Allows reading and writing of cached entities.
		Ensures strong consistency by using transactional guarantees, typically with locks.
		
	3. NONSTRICT_READ_WRITE:
		Data can be read from the cache and updated, but it doesn't guarantee strong consistency.
		Some stale data may be present if one session updates the entity and another reads before the changes are flushed.
		
	4. TRANSACTIONAL:
		Provides the strongest consistency guarantees using underlying transaction management.
		Usually, it integrates with JTA (Java Transaction API) to maintain cache consistency in distributed systems.
		
	-> We need to use third-party cache provides for second_level cache most used are -> 1. EHCache, 2. Redis, 3. Infinispan, 4. Hazelcast
	
	Example : 
			@Entity
			@Cacheable
			@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
			public class Alien {}
