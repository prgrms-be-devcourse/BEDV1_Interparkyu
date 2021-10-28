package org.programmers.interparkyu.user;

import org.programmers.interparkyu.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
