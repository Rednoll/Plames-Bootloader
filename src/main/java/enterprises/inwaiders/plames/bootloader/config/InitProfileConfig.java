package enterprises.inwaiders.plames.bootloader.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("init")
@ComponentScan(basePackages = "enterprises.inwaiders.plames.bootloader")
public class InitProfileConfig {

}
