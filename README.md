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
	
	2.1). Hibernate Caching with query -> 
	
	
	Hibernate Query Language ->
			We can do write queries with hql same as sql and also do joins, group by, and everything which is possible with sql queries.
