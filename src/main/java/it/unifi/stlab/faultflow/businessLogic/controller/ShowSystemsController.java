package it.unifi.stlab.faultflow.businessLogic.controller;

import it.unifi.stlab.faultflow.dao.SystemDao;
import it.unifi.stlab.faultflow.dto.system.OutputSystemDto;
import it.unifi.stlab.faultflow.mapper.SystemMapper;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Dependent
@Default
@Transactional
public class ShowSystemsController {

    @Inject
    SystemDao systemDao;

    public List<OutputSystemDto> getAllSystems() {
        List<OutputSystemDto> res = new ArrayList<>();
        for(System s : systemDao.getAll()){
            res.add(SystemMapper.systemToOutputSystem(s));
        }
        return res;
    }
}
