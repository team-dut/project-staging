/*
    Name: Group 15 from NH3-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: Game messages to send to event handlers
*/

package Core;

import java.awt.*;

public class GameMessage {
    public static final int UPDATE = AWTEvent.RESERVED_ID_MAX + 1;
    public static final int COLLISION_TEST = AWTEvent.RESERVED_ID_MAX + 2;
    public static final int RESET = AWTEvent.RESERVED_ID_MAX + 3;
}
