package it.unifi.stlab.faultflow.businessLogic.controller;

import java.util.List;

public interface BaseController<T> {

    void add(T t);
    T find(String id);
    List<T> findAll();
    void update(T t);
    void delete(T t);
}
