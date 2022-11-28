package jp.co.internous.ecsite.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.internous.ecsite.model.entity.User;

public interface FindUserRepository extends JpaRepository<User, Long> {
	
	User findByUserNameAndPasswordAndIsAdmin(String userNamr, String password, int isAdmin);
}
