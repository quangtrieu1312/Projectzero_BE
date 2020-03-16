package com.trieutruong.webpage.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.repository.extend.TicketRepositoryExtend;
import com.trieutruong.webpage.request.TicketRequest;

public interface TicketRepository extends MongoRepository<Ticket, String>, TicketRepositoryExtend{
	
	Ticket findByTicketId(String ticketId);
	
	@Query("{ $or: [{ userIds : ?0 }, { ownerId: ?0 }]}")
	List<Ticket> findByUserId(String userId);

	@Query("{ 'alert.mode' : ?0 }")
	List<Ticket> findByAlertMode(String mode);

	List<Ticket> findByAlertMode(Boolean mode);

}
