package istu.bacs.commons.initializer;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PlatformInitializer {

    private final PlatformUnitInitializer[] initializers;

    public PlatformInitializer(PlatformUnitInitializer[] initializers) {
        this.initializers = initializers;
    }

    @PostConstruct
    public void init() {
        for (PlatformUnitInitializer initializer : initializers) {
            try {
                initializer.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}