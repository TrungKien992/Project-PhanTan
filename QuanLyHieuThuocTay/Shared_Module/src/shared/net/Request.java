package shared.net;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private ActionType action;
    private Object data;

    public Request(ActionType action, Object data) {
        this.action = action;
        this.data = data;
    }

    public ActionType getAction() {
        return action;
    }

    public Object getData() {
        return data;
    }
}
