package tech.ydb.cdcproc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * YDB CDC Processor entry point
 * @author zinal
 */
public class Main implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private final YdbConnector yc;

    public Main(YdbConnector yc) {
        this.yc = yc;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String getVersion() {
        try {
            Properties prop = new Properties();
            try (InputStream in = Main.class.getResourceAsStream("/cdcproc_version.properties")) {
                prop.load(in);
            }
            return prop.getProperty("version");
        } catch (IOException ex) {
            LOG.error("cannot load version", ex);
            return "unknown";
        }
    }

    public static void main(String[] args) {
        LOG.info("Started {} version {}", Main.class.getPackage().getName(), getVersion());
        String configFile = "cdcproc.xml";
        if (args.length > 0) {
            configFile = args[0];
        }
        try {
            YdbConnector.Config ycc = YdbConnector.Config.fromFile(configFile);
            try (YdbConnector yc = new YdbConnector(ycc)) {
                new Main(yc).run();
            }
        } catch (Exception ex) {
            LOG.error("FAILURE", ex);
            System.exit(1);
        }
    }
}
