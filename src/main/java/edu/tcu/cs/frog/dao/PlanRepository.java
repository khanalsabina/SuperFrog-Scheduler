package edu.tcu.cs.frog.dao;

import edu.tcu.cs.frog.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

}
