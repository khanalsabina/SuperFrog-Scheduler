package edu.tcu.cs.frog.service;

import edu.tcu.cs.frog.dao.PlanRepository;
import edu.tcu.cs.frog.domain.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public List<Plan> findAll(){
        return planRepository.findAll();
    }

    public Plan findById(Integer id){
        return planRepository.findById(id).get();
    }

    public void delete(Integer id) {
        planRepository.deleteById(id);
    }

    public Plan save(Plan newPlan) {
        return planRepository.save(newPlan);
    }
}
