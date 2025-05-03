@Query("SELECT t FROM Ticket t WHERE t.status = 'CREATED' AND t.createdDate < :cutoff")
List<Ticket> findOldPendingTickets(LocalDateTime cutoff);

@Query("SELECT t FROM Ticket t WHERE t.status = 'RESOLVED' AND t.updatedDate < :cutoff")
List<Ticket> findResolvedOlderThan(LocalDateTime cutoff);
