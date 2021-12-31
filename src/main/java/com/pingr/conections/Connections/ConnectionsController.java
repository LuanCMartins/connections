package com.pingr.conections.Connections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(path = "/connections")
public class ConnectionsController {
    private final AccountService accountService;

    @Autowired
    public ConnectionsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/{aid}/{bid}")
    public boolean addFriends(@PathVariable("aid") Long aid, @PathVariable("bid") Long bid) {
        return this.accountService.stablishFriendshipBetween(aid, bid);
    }

    @GetMapping(path = "/{accountId}")
    public List<Account> listUserFriends(@PathVariable("accountId") Long accountId) {
        return this.accountService.listaAmigos(accountId);
    }

    @DeleteMapping("/{aid}/{bid}")
    public Boolean removeFriend(@PathVariable("aid") Long aid, @PathVariable("bid") Long bid) {
        return this.accountService.removeFriendshipBetween(aid, bid);
    }

    @GetMapping("/{id}/count")
    public Long countFriends(@PathVariable("id") Long id) {
        return this.accountService.contaAmigosAccount(id);
    }
}
