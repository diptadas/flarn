package edu.baylor.flarn.resources;


import lombok.Data;

/**
 * Can remove later, may use it.
 * @param <T>
 */
@Data
public class UpdateRequest<T> {

    long id;
    T obj;
}
