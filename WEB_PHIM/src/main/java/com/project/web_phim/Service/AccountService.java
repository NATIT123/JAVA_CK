package com.project.web_phim.Service;

import com.project.web_phim.Model.Account;
import java.util.Optional;

public interface AccountService {
    public Iterable<Account> getAllAccounts();

    public Optional<Account> getAccount(Long id);

    public Account updateAccount(Long id,Account acc);

    public void deleteAccount(Long id);

    public void saveAccount(Account acc);

    public void deleteAllAccounts();

    public Iterable<Account> getCustomizedAccountList();

    public boolean chekclogin(String username,String password);

    public Account findbyusername(String username);

    public boolean finbyemail(String email);

}
