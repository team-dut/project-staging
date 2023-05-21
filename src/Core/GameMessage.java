package Core;

import java.awt.*;

public class GameMessage {
    public static final int UPDATE = AWTEvent.RESERVED_ID_MAX + 1;
    public static final int COLLISION_TEST = AWTEvent.RESERVED_ID_MAX + 2;
    public static final int RESET = AWTEvent.RESERVED_ID_MAX + 3;
}
