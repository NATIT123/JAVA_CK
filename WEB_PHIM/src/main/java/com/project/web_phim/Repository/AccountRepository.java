package com.project.web_phim.Repository;

import com.project.web_phim.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findAccountByUsernameAndPassword(String username,String password);

    public Account findAccountByUsername(String username);

    public Account findAccountByEmail(String email);
}
