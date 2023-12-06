package com.project.web_phim.Service;

import com.project.web_phim.Model.Account;
import com.project.web_phim.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll(Sort.by("username").ascending());
    }

    @Override
    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account updateAccount(Long id, Account acc) {
        Account _acc = accountRepository.findById(id).orElse(null);
        if(acc == null){
            return null;
        }
        _acc.setUsername((acc.getUsername()));
        _acc.setEmail(acc.getEmail());
        _acc.setRole(acc.getRole());
        _acc.setPassword(acc.getPassword());
        return accountRepository.save(_acc);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void saveAccount(Account acc) {
        accountRepository.save(acc);
    }

    @Override
    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    @Override
    public Iterable<Account> getCustomizedAccountList() {
        Page<Account> accPage = accountRepository.findAll(PageRequest.of(0,20, Sort.by("username").ascending()));
        return accPage.getContent().subList(4,7);
    }

    @Override
    public boolean chekclogin(String username, String password) {
        Account account=accountRepository.findAccountByUsernameAndPassword(username,password);
        if(account==null){
            return false;
        }
        return true;
    }

    @Override
    public Account findbyusername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public boolean finbyemail(String email) {
        Account account= accountRepository.findAccountByEmail(email);
        if(account==null){
            return false;
        }
        return true;
    }
}
