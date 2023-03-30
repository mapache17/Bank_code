package com.example.jpa_bank.repository;

import com.example.jpa_bank.entity.TransactionEntity;
import com.example.jpa_bank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

}