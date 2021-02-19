package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Agent;

public interface AgentServices {

    Agent findAgentById(long id);

    public Agent save(Agent agent);
}
