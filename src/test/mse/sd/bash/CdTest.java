package mse.sd.bash;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.nio.file.*;

import mse.sd.bash.commands.Cd;

public class CdTest {
    @Test
    public void testCdRelative() {
        final Cd cd = new Cd();
        String cwd = System.getProperty("user.dir");
        cd.setCWD(cwd);
        cd.setArgs(new String[]{"./src"});
        assertDoesNotThrow(() -> {
            cd.start();
        });
        String expected = Paths.get(cwd, "./src").normalize().toString();
        assertEquals(expected, cd.newCwd);
    }

    @Test
    public void testCdAbsolute() {
        String cwd = System.getProperty("user.dir");
        String expected = Paths.get(cwd, "./src")
            .toAbsolutePath()
            .normalize()
            .toString();
        final Cd cd = new Cd();
        cd.setCWD(cwd);
        cd.setArgs(new String[]{expected});
        assertDoesNotThrow(() -> {
            cd.start();
        });
        assertEquals(expected, cd.newCwd);
    }

    @Test
    public void testCdDoesNotExist() {
        String cwd = System.getProperty("user.dir");
        final Cd cd = new Cd();
        cd.setCWD(cwd);
        cd.setArgs(new String[]{"./doesnt_exist"});
        assertThrows(IllegalArgumentException.class, () -> {
            cd.start();
        });
    }
}
