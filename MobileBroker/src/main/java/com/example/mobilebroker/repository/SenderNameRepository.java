package com.example.mobilebroker.repository;

import com.example.mobilebroker.entity.SenderName;
import com.example.mobilebroker.entity.SenderNameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenderNameRepository extends JpaRepository<SenderName, SenderNameId> {
}
