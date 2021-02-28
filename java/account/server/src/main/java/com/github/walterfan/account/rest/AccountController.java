package com.github.walterfan.account.rest;


import com.github.walterfan.account.domain.Account;
import com.github.walterfan.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping("/")

public class AccountController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService accountService;


    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public Account createAccount(@RequestBody Account account) throws Exception {
        logger.info("got post request: " + account.toString());
        accountService.createAccount(account);
        return account;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public List<Account> getAccounts() {
        logger.info("got all accounts request");
        List<Account> AccountList = accountService.listAccount();
        return AccountList;
    }

    @RequestMapping(value = "accounts/{id}", method = GET)
    public Account getAccount(@PathVariable("id") int accountId) throws Exception {
        return accountService.retrieveAccount(accountId);
    }


    @RequestMapping(value = "accounts/{id}", method = PUT)
    public Account updateAccount(@PathVariable("id") int accountId, @RequestBody Account account) {
        account.setAccountID(accountId);
        accountService.updateAccount(account);
        return account;
    }

    @RequestMapping(value = "accounts/{id}", method = DELETE)
    public void deleteAccount(@PathVariable("id") int accountId) {
        accountService.deleteAccount(accountId);

    }
}
