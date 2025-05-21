package it.infocube.management.user;

import it.infocube.management.users_knowledge.UsersKnowledge;
import it.infocube.management.users_knowledge.UsersKnowledgeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersKnowledgeRepository extends JpaRepository<UsersKnowledge, UsersKnowledgeId> {
}