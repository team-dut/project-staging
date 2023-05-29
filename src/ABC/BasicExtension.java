/*
    Name: Group 15 from NH4-TTH2
    Members:
        Pham Tien Dat - ITITIU21172
        Do Tan Loc - ITCSIU21199
        Mai Xuan Thien - ITITIU21317
        Pham Quoc Huy - ITITIU21215
    Purpose: A "template class" for extension
*/

package ABC;

import java.io.IOException;

public class BasicExtension {
    public boolean isEnabled = false;

    public boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void loadExtension() throws IOException {
    }
}
