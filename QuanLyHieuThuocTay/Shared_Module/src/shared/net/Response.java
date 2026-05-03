package shared.net;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status; // e.g., "OK", "ERROR"
    private Object data;
    private String message;

    public Response(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Response(String status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
