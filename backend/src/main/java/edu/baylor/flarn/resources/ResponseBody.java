package edu.baylor.flarn.resources;

import lombok.Data;

/**
 * {@link ResponseBody} class defines a default API response that does not produce any explicit output.
 * Mostly used in delete operations to indicate success or failure.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Data
public class ResponseBody {

    int status;

    public ResponseBody(int status, String message) {
        this.status = status;
        this.message = message;
    }

    String message;

}
