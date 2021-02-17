package com.lambdaschool.ordersapp.services;


import com.lambdaschool.ordersapp.models.Agent;
import com.lambdaschool.ordersapp.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "agentservices")
public class AgentServicesImpl implements AgentServices{

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public Agent findAgentById(long id) throws EntityNotFoundException {
        return agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " Not Found"));
    }

    @Transactional
    @Override
    public Agent save(Agent agent) { return agentRepository.save(agent);}
}
