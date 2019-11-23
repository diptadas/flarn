package edu.baylor.flarn.resources;


import lombok.Data;

@Data
public class UpdateRequest<T> {

    long id;
    T obj;
}
