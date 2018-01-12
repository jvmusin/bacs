package istu.bacs.util;

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
        for (PlatformUnitInitializer initializer : initializers)
            initializer.init();
    }
}