package edu.baylor.flarn.resources;

import lombok.Data;

@Data
public class ResponseBody {

    int status;

    public ResponseBody(int status, String message) {
        this.status = status;
        this.message = message;
    }

    String message;

}
