package jp.co.internous.ecsite.model.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.internous.ecsite.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUserNameAndPassword(String userNamr, String password);

}
