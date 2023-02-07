package com.nttdata.bootcamp.movement.service.Impl;

import com.nttdata.bootcamp.movement.entity.Movement;
import com.nttdata.bootcamp.movement.repository.MovementRepository;
import com.nttdata.bootcamp.movement.service.MovementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@AllArgsConstructor
@Service
public class MovementServiceImpl implements MovementService {
    @Autowired
    private MovementRepository movementRepository;

    @Override
    public Flux<Movement> findAll() {
        Flux<Movement> movementsFlux = movementRepository.findAll();
        return movementsFlux;
    }

    @Override
    public Flux<Movement> findByAccountNumber(String accountNumber) {
        Flux<Movement> movementsFlux = movementRepository
                .findAll()
                .filter(x -> x.getAccountNumber().equals(accountNumber));
        return movementsFlux;
    }

    @Override
    public Flux<Movement> findCommissionByAccountNumber(String accountNumber) {
        Flux<Movement> movementsFlux = movementRepository
                .findAll()
                .filter(x -> x.getAccountNumber().equals(accountNumber) && x.getCommission() > 0);
        return movementsFlux;
    }

    @Override
    public Mono<Movement> findByNumber(String Number) {
        Mono<Movement> movementsMono = movementRepository
                .findAll()
                .filter(x -> x.getMovementNumber().equals(Number))
                .next();
        return movementsMono;
    }

    public Mono<Movement> saveMovement(Movement dataMovement) {
        dataMovement.setStatus("active");
        return movementRepository.save(dataMovement);

    }

    @Override
    public Mono<Movement> updateMovement(Movement dataMovement) {

        Mono<Movement> transactionMono = findByNumber(dataMovement.getMovementNumber());
        try {
            dataMovement.setDni(transactionMono.block().getDni());
            dataMovement.setAmount(transactionMono.block().getAmount());
            dataMovement.setAccountId(transactionMono.block().getAccountId());
            dataMovement.setMovementNumber(transactionMono.block().getMovementNumber());
            dataMovement.setProductType(transactionMono.block().getProductType());
            dataMovement.setStatus(transactionMono.block().getStatus());
            dataMovement.setTransactionDate(transactionMono.block().getTransactionDate());
            return movementRepository.save(dataMovement);
        } catch (Exception e) {
            return Mono.<Movement>error(new Error("The movement " + dataMovement.getMovementNumber() + " do not exists"));
        }
    }

    @Override
    public Mono<Void> deleteMovement(String Number) {
        Mono<Movement> movementsMono = findByNumber(Number);
        try {
            Movement movement = movementsMono.block();
            return movementRepository.delete(movement);
        } catch (Exception e) {
            return Mono.<Void>error(new Error("The movement number" + Number + " do not exists"));
        }
    }
    
    @Override
    public Flux<Movement> geDebitByIdAccount(Integer accountId){
        return movementRepository.findAll()
                .filter(tran -> tran.getAccountId() == accountId);
    }
    
    @Override
    public Mono<Movement> geDebitByIdCustomer(String customerId){
        return movementRepository.findAll()
                .filter(tran -> tran.getCustomerId().equals(customerId)).next();
    }
    
    @Override
    public Flux<Movement> getMovementByIdCustomer(String customerId){
        return movementRepository.findAll()
                .filter(tran -> tran.getCustomerId().equals(customerId));
    }

}
