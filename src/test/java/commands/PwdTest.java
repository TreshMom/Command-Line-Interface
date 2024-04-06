package commands;

import mse.sd.bash.commands.Pwd;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PwdTest {
    @Test
    public void testPwd() {
        try {
            Pwd pwd = new Pwd();
            pwd.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
