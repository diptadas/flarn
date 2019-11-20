package edu.baylor.flarn.resources;

import edu.baylor.flarn.models.UserType;
import lombok.Data;

@Data
public class UpdateRequest<T> {

    long id;
    T obj;
}
