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
