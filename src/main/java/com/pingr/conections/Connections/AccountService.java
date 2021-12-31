package com.pingr.conections.Connections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
    private final AccountRepository repo;
    private final FriendshipMadeProducer friendShipMadeProducer;
    private final FriendshipUnmadeProducer friendshipUnmadeProducer;

    @Autowired
    public AccountService(
            AccountRepository repo,
            FriendshipMadeProducer friendShipMadeProducer,
            FriendshipUnmadeProducer friendshipUnmadeProducer
    ) {
        this.repo = repo;
        this.friendShipMadeProducer = friendShipMadeProducer;
        this.friendshipUnmadeProducer = friendshipUnmadeProducer;
    }

    public void storeAccount(Account account) {
        this.repo.save(account);
        System.out.println("Conta salva: ");
        System.out.println(account);
    }

    public boolean stablishFriendshipBetween(Long aId, Long bId) {
        Optional<Account> aOptional = this.repo.findById(aId);
        Optional<Account> bOptional = this.repo.findById(bId);

        if (!aOptional.isPresent() || !bOptional.isPresent()) return false;

        Account a = aOptional.get();
        Account b = bOptional.get();

        Set<Account> aFriends = a.getFriends();
        aFriends.add(b);
        a.setFriends(aFriends);

        Set<Account> bFriends = b.getFriends();
        bFriends.add(a);
        b.setFriends(bFriends);

        this.repo.saveAll(List.of(a, b));

        return true;
    }

    public Account updateAccount(Account account) {
        try {
            if(account.getId() != null) {
                Optional<Account> optionalAccount = this.repo.findAccountById(account.getId());
                if(optionalAccount.isPresent()) {
                    Account contaEncontrada = optionalAccount.get();
                    contaEncontrada.setUsername(account.getUsername());
                    this.repo.saveAndFlush(contaEncontrada);
                    return contaEncontrada;
                }
            }
            return new Account();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar conta: ");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Boolean deleteAccount(Account account) {
        try {
            if(account.getId() != null) {
                Optional<Account> optionalAccount = this.repo.findAccountById(account.getId());
                if(optionalAccount.isPresent()) {
                    Account contaEncontrada = optionalAccount.get();
                    this.repo.delete(contaEncontrada);
                    this.repo.flush();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao deletar conta:");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Account> listaAmigos(Long id) {
        if (id == null) return new ArrayList<>();

        Optional<Account> optionalAccount = this.repo.findById(id);

        if(!optionalAccount.isPresent()) return new ArrayList<>();

        Account contaEncontrada = optionalAccount.get();
        Set<Account> friends = contaEncontrada.getFriends();

        return new ArrayList<>(friends);
    }

    public boolean removeFriendshipBetween(Long aid, Long bid) {
        Optional<Account> aOptional = this.repo.findById(aid);
        Optional<Account> bOptional = this.repo.findById(bid);

        if (!aOptional.isPresent() || !bOptional.isPresent()) return false;

        Account a = aOptional.get();
        Account b = bOptional.get();

        Set<Account> aFriends = a.getFriends();
        aFriends.removeIf(acc -> acc.getId().equals(b.getId()));
        a.setFriends(aFriends);

        Set<Account> bFriends = b.getFriends();
        bFriends.removeIf(acc -> acc.getId().equals(a.getId()));
        b.setFriends(bFriends);

        this.repo.saveAll(List.of(a,b));
        this.friendshipUnmadeProducer.sendMessage(List.of(a,b));
        return true;
    }

    public Long contaAmigosAccount(Long id) {
        Optional<Account> optionalAccount = this.repo.findById(id);

        if(!optionalAccount.isPresent()) return 0L;

        Account conta = optionalAccount.get();
        return Long.parseLong(String.valueOf(conta.getFriends().size()));
    }
}
